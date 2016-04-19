package com.tianfang.home.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.HtmlRegexpUtil;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.home.dto.CompRound;
import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionNewsDto;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDatasDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.dto.TeamResultDto;
import com.tianfang.train.enums.AuditType;
import com.tianfang.train.service.ICompetitionApplyService;
import com.tianfang.train.service.ICompetitionMatchService;
import com.tianfang.train.service.ICompetitionNewsService;
import com.tianfang.train.service.ICompetitionNoticeService;
import com.tianfang.train.service.ICompetitionRoundService;
import com.tianfang.train.service.ICompetitionService;
import com.tianfang.train.service.ICompetitionTeamService;
import com.tianfang.train.service.ITeamPlayerDatasService;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.train.service.ITeamResultService;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.enums.UserType;
import com.tianfang.user.service.IUserInfoService;
import com.tianfang.user.service.IUserService;

/**		
 * <p>Title: CompController </p>
 * <p>Description: 类描述:赛事相关操作接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月26日下午5:28:26
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "comp")
public class CompController extends BaseController {

	protected static final Log logger = LogFactory.getLog(CompController.class);

	@Autowired
	private ICompetitionService compService;
	@Autowired
	private ICompetitionNoticeService noticeService;
	@Autowired
	private ITeamService teamService;
	@Autowired
	private ICompetitionApplyService applyService;
	@Autowired
	private ICompetitionRoundService roundService;
	@Autowired
	private ICompetitionMatchService matchService;
	@Autowired
	private ICompetitionTeamService cTeamService;
	@Autowired
	private ITeamPlayerDatasService playerDatasService;
	@Autowired
	private ICompetitionNewsService cnewsService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ITeamResultService iTeamResultService;
	@Autowired
	private ITeamPlayerService playerService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 赛事分页查询接口
	 *
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:04:33
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Response<PageResult<CompetitionDto>> findComp(CompetitionDto dto, PageQuery query) {
		Response<PageResult<CompetitionDto>> response = new Response<PageResult<CompetitionDto>>();
		try {
			PageResult<CompetitionDto> datas = compService.findCompetitionViewByPage(dto, query);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}

		return response;
	}

	/**
	 * <p>Description: 赛事报名管理,根据参数查询赛事报名列表 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 *
	 * @param params
	 * @return Response<List<CompetitionDto>>
	 * @author wangxiang
	 * @date 16/4/8 上午10:05
	 * @version 1.0
	 */
	@RequestMapping(value = "listApply")
	@ResponseBody
	public Response<List<CompetitionDto>> listApply(String userId, CompetitionApplyDto params) {
		Response<List<CompetitionDto>> response = new Response<>();
		UserDto user = getUserByCache(userId);
		if (StringUtils.isBlank(userId) || null == user) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("用户不存在!");
			return response;
		}
		TeamPlayerDto player = playerService.getTeamPlayeByUserId(userId);
		if (null == player) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("对不起,用户未加入球队!");
			return response;
		}
		params.setTeamId(player.getTeamId());
		List<CompetitionDto> datas = applyService.findCompApplyByParams(params);

		response.setData(datas);
		return response;
	}

	/**
	 * 赛事详情接口
	 *
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:04:46
	 */
	@RequestMapping(value = "getComp")
	@ResponseBody
	public Response<CompetitionDto> getComp(String id) {
		Response<CompetitionDto> response = new Response<CompetitionDto>();
		try {
			CompetitionDto comp = compService.getCompById(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(comp);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事公告分页
	 *
	 * @param compId
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:16:43
	 */
	@RequestMapping(value = "notices")
	@ResponseBody
	public Response<PageResult<CompetitionNoticeDto>> notices(String compId, PageQuery query) {
		Response<PageResult<CompetitionNoticeDto>> response = new Response<PageResult<CompetitionNoticeDto>>();
		try {
			if (StringUtils.isBlank(compId)) {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("赛事ID为空");
				return response;
			}
			CompetitionNoticeDto dto = new CompetitionNoticeDto();
			dto.setCompId(compId);
			PageResult<CompetitionNoticeDto> datas = noticeService.findCompNoticeViewByPage(dto, query);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事公告详情
	 *
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:21:46
	 */
	@RequestMapping(value = "getNotice")
	@ResponseBody
	public Response<CompetitionNoticeDto> getNotice(String id) {
		Response<CompetitionNoticeDto> response = new Response<CompetitionNoticeDto>();
		try {
			CompetitionNoticeDto data = noticeService.getNoticeById(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事动态列表(分页)
	 *
	 * @param compId
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月3日上午9:51:13
	 */
	@RequestMapping(value = "news")
	@ResponseBody
	public Response<PageResult<CompetitionNewsDto>> news(String compId, PageQuery query) {
		Response<PageResult<CompetitionNewsDto>> response = new Response<PageResult<CompetitionNewsDto>>();
		try {
			if (StringUtils.isBlank(compId)) {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("赛事ID为空");
				return response;
			}
			CompetitionNewsDto dto = new CompetitionNewsDto();
			dto.setCompId(compId);
			PageResult<CompetitionNewsDto> datas = cnewsService.findCompetitionNewsByPage(dto, query);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事动态详情
	 *
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月3日上午10:45:30
	 */
	@RequestMapping(value = "getNews")
	@ResponseBody
	public Response<CompetitionNewsDto> getNews(String id) {
		Response<CompetitionNewsDto> response = new Response<CompetitionNewsDto>();
		try {
			CompetitionNewsDto data = cnewsService.getCompetitionNews(id);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事下所有参赛球队
	 *
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月28日下午4:46:15
	 */
	@RequestMapping(value = "teams")
	@ResponseBody
	public Response<List<TeamDto>> teams(String compId) {
		Response<List<TeamDto>> response = new Response<List<TeamDto>>();
		try {
			List<TeamDto> teams = teamService.findAllByCompId(compId);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(teams);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 球队详情
	 *
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月28日下午4:48:48
	 */
	@RequestMapping(value = "getTeam")
	@ResponseBody
	public Response<TeamDto> getTeam(String id) {
		Response<TeamDto> response = new Response<TeamDto>();
		try {
			TeamDto team = teamService.getTeamById(id);
			if (null == team) {
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("球队不存在");
				return response;
			}
			team.setSummary(HtmlRegexpUtil.filterHtml(team.getSummary()));

			TeamResultDto trDto = new TeamResultDto();
			trDto.setTeamId(id);
			List<TeamResultDto> resultList = iTeamResultService.findTeamResultByParam(trDto);
			team.setDataList(resultList);

			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(team);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事去报名接口
	 *
	 * @param userId
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年3月4日下午3:50:34
	 */
	@RequestMapping(value = "toApply")
	@ResponseBody
	public Response<CompetitionApplyDto> toApply(String userId, String compId) {
		Response<CompetitionApplyDto> result = new Response<CompetitionApplyDto>();
		CompetitionApplyDto compApply = new CompetitionApplyDto();
		compApply.setCreateUserId(userId);
		compApply.setCompId(compId);
		if (checkCompApply(result, compApply)) {
			result.setData(compApply);
		}

		return result;
	}

	/**
	 * 赛事报名提交接口
	 *
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日上午10:00:45
	 */
	@RequestMapping(value = "apply")
	@ResponseBody
	public Response<String> apply(CompetitionApplyDto dto, String userId) {
		Response<String> result = new Response<String>();
		dto.setCreateUserId(userId);
		try {
			if (checkCompApply(result, dto)) {
				applyService.addCompetitionApply(dto);
				result.setMessage("恭喜您,报名成功!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("系统异常!");
		}

		return result;
	}

	/**
	 * 查询赛事id查询所有场次下的比赛
	 *
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午3:41:11
	 */
	@RequestMapping(value = "rounds")
	@ResponseBody
	public Response<List<CompRound>> rounds(String compId) {
		Response<List<CompRound>> response = new Response<List<CompRound>>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			return response;
		}
		try {
			List<CompRound> datas = findCompRounds(compId);
			response.setData(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 根据赛事分每轮查询比赛记录
	 *
	 * @param compId    赛事id
	 * @param currRound 当前轮数页码
	 * @return
	 */
	@RequestMapping(value = "queryMatchs")
	@ResponseBody
	public Response<RoundDetail> queryMatchs(String compId, int currRound) {
		Response<RoundDetail> response = new Response<RoundDetail>();
		if (StringUtils.isBlank(compId)) {
			logger.error("queryMatchs 赛事id为空!");
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("参数异常!");
			return response;
		}
		currRound = currRound <= 0 ? 1 : currRound;
		CompetitionRoundDto param = new CompetitionRoundDto();
		param.setCompId(compId);
		PageQuery query = new PageQuery();
		query.setCurrPage(currRound);
		query.setPageSize(1);
		PageResult<CompetitionRoundDto> round = roundService.findRoundByParam(param, query);
		if (null != round) {
			if (null != round.getResults() && round.getResults().size() > 0) {
				try {
					PageResult<CompetitionMatchDto> matchs = findMathByPage(compId, round.getResults().get(0).getId());
					RoundDetail detail = new RoundDetail();
					detail.setName(round.getResults().get(0).getName());
					detail.setCurrRound(currRound);
					if (round.getCurrPage() > 1) {
						detail.setBefore(round.getCurrPage() - 1);
					}
					if (round.getCurrPage() < round.getTotalPage()) {
						detail.setNext(round.getCurrPage() + 1);
					}
					detail.setMatchs(matchs);
					response.setData(detail);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					response.setStatus(DataStatus.HTTP_FAILE);
				}
			}
		}

		return response;
	}

	/**
	 * 分页查询该轮下的比赛信息
	 *
	 * @param compId  赛事id
	 * @param roundId 轮次id
	 * @return
	 */
	private PageResult<CompetitionMatchDto> findMathByPage(String compId, String roundId) {
		CompetitionMatchDto param = new CompetitionMatchDto();
		param.setCompId(compId);
		param.setCroundId(roundId);
		PageQuery query = new PageQuery();
		query.setCurrPage(1);
		query.setPageSize(1000);
		PageResult<CompetitionMatchDto> matchs = matchService.findCompetitionMatchViewByPage(param, query);

		return matchs;
	}


	/**
	 * 通过比赛日期查询当天的比赛
	 *
	 * @param compId
	 * @param matchDate
	 * @return
	 * @author xiang_wang
	 * 2016年2月22日下午4:07:47
	 */
	@RequestMapping(value = "roundsByDate")
	@ResponseBody
	public Response<CompRound> roundsByDate(String compId, String matchDate) {
		Response<CompRound> response = new Response<CompRound>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			return response;
		}
		try {
			CompRound datas = findCompRounds(compId, matchDate);
			response.setData(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 根据赛事场次id查询比赛
	 *
	 * @param compId
	 * @param roundId
	 * @return
	 * @author xiang_wang
	 * 2016年2月22日下午4:14:21
	 */
	@RequestMapping(value = "roundsByRoundId")
	@ResponseBody
	public Response<CompRound> roundsByRoundId(String compId, String roundId) {
		Response<CompRound> response = new Response<CompRound>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			return response;
		}
		try {
			CompRound datas = getCompRoundsByCroundId(compId, roundId);
			response.setData(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 比赛详情接口
	 *
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月3日上午9:30:51
	 */
	@RequestMapping(value = "getMatch")
	@ResponseBody
	public Response<CompetitionMatchDto> getMatch(String id) {
		Response<CompetitionMatchDto> response = new Response<CompetitionMatchDto>();
		if (StringUtils.isBlank(id)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			return response;
		}
		try {
			CompetitionMatchDto datas = matchService.getMatchById(id);
			response.setData(datas);
			response.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("查询失败");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 赛事球队积分榜(分页)
	 *
	 * @param compId
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午4:07:46
	 */
	@RequestMapping(value = "scoreboard")
	@ResponseBody
	public Response<PageResult<CompetitionTeamDto>> findTeams(String compId, PageQuery query) {
		Response<PageResult<CompetitionTeamDto>> response = new Response<PageResult<CompetitionTeamDto>>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("参数异常");
			return response;
		}
		CompetitionTeamDto dto = new CompetitionTeamDto();
		dto.setCompId(compId);
		try {
			PageResult<CompetitionTeamDto> datas = cTeamService.findCompetitionTeamByParam(dto, query);
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}

		return response;
	}

	/**
	 * 赛事射手榜
	 *
	 * @param compId
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午5:00:31
	 */
	@RequestMapping(value = "topScorer")
	@ResponseBody
	public Response<PageResult<TeamPlayerDatasDto>> topScorer(String compId, PageQuery query) {
		Response<PageResult<TeamPlayerDatasDto>> response = new Response<PageResult<TeamPlayerDatasDto>>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("参数异常");
			return response;
		}
		try {
			PageResult<TeamPlayerDatasDto> datas = playerDatasService.findTeamPlayerDatasByCompId(compId, query, " num desc,red,yellow");
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}

		return response;
	}

	/**
	 * 赛事红黄牌
	 *
	 * @param compId
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午5:00:49
	 */
	@RequestMapping(value = "cards")
	@ResponseBody
	public Response<PageResult<TeamPlayerDatasDto>> cards(String compId, PageQuery query) {
		Response<PageResult<TeamPlayerDatasDto>> response = new Response<PageResult<TeamPlayerDatasDto>>();
		if (StringUtils.isBlank(compId)) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("参数异常");
			return response;
		}
		try {
			PageResult<TeamPlayerDatasDto> datas = playerDatasService.findTeamPlayerDatasByCompId(compId, query, " red desc, yellow desc");
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(datas);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}

		return response;
	}

	/**
	 * 查询场次下的比赛并组装数据
	 *
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午3:28:24
	 */
	private List<CompRound> findCompRounds(String compId) {
		List<CompRound> crs = null;
		List<CompetitionRoundDto> rounds = roundService.findRoundByCompId(compId);
		if (null != rounds && rounds.size() > 0) {
			List<CompetitionMatchDto> matchs = matchService.findMatchByCompId(compId);
			if (null != matchs && matchs.size() > 0) {
				Map<String, List<CompetitionMatchDto>> map = new HashMap<String, List<CompetitionMatchDto>>(rounds.size());
				List<CompetitionMatchDto> temp;
				for (CompetitionMatchDto match : matchs) {
					if (map.containsKey(match.getCroundId())) {
						map.get(match.getCroundId()).add(match);
					} else {
						temp = new ArrayList<CompetitionMatchDto>();
						temp.add(match);
						map.put(match.getCroundId(), temp);
					}
				}

				crs = new ArrayList<CompRound>(rounds.size());
				for (CompetitionRoundDto round : rounds) {
					CompRound cr = new CompRound();
					cr.setId(round.getId());
					cr.setName(round.getName());
					cr.setMatchs(map.get(round.getId()));

					crs.add(cr);
				}
			}
		}

		return crs;
	}

	/**
	 * 根据日期获得当天的比赛数据
	 *
	 * @param compId
	 * @param matchDate
	 * @return
	 * @author xiang_wang
	 * 2016年2月22日下午4:05:24
	 */
	private CompRound findCompRounds(String compId, String matchDate) {
		CompRound result = new CompRound();
		List<CompetitionRoundDto> rounds = roundService.findRoundByCompId(compId);
		if (null != rounds && rounds.size() > 0) {
			CompetitionMatchDto params = new CompetitionMatchDto();
			params.setCompId(compId);
			params.setMatchDateStr(matchDate);
			List<CompetitionMatchDto> matchs = matchService.findCompetitionMatch(params);
			if (null != matchs && matchs.size() > 0) {
				String croundId = matchs.get(0).getCroundId();
				result.setMatchs(matchs);
				assemblyCompRound(result, rounds, croundId);
			}
		}

		return result;
	}

	/**
	 * 根据赛事场次id查询比赛
	 *
	 * @param compId
	 * @param croundId
	 * @return
	 * @author xiang_wang
	 * 2016年2月22日下午4:12:50
	 */
	private CompRound getCompRoundsByCroundId(String compId, String croundId) {
		CompRound result = new CompRound();
		List<CompetitionRoundDto> rounds = roundService.findRoundByCompId(compId);
		if (null != rounds && rounds.size() > 0) {
			CompetitionMatchDto params = new CompetitionMatchDto();
			params.setCompId(compId);
			params.setCroundId(croundId);
			List<CompetitionMatchDto> matchs = matchService.findCompetitionMatch(params);
			if (null != matchs && matchs.size() > 0) {
				result.setMatchs(matchs);
				assemblyCompRound(result, rounds, croundId);
			}
		}

		return result;
	}

	/**
	 * 组装赛事场次数据,上一轮和下一轮id
	 *
	 * @param result
	 * @param rounds
	 * @param croundId
	 * @author xiang_wang
	 * 2016年2月23日上午9:21:55
	 */
	private void assemblyCompRound(CompRound result, List<CompetitionRoundDto> rounds, String croundId) {
		for (int i = 0, len = rounds.size(); i < len; i++) {
			if (croundId.equals(rounds.get(i).getId())) {
				result.setId(croundId);
				result.setName(rounds.get(i).getName());
				if (i >= 1) {
					result.setBefore(rounds.get(i - 1).getId());
				}
				if (i < len - 1) {
					result.setNext(rounds.get(i + 1).getId());
				}
			}
		}
	}

	/**
	 * 校验赛事是否满足报名条件
	 * 条件:	1.登陆后才可报名
	 * 2.只有队长才有只能报名
	 * 3.如果有待审核的报名请用户等待审核
	 * 4.如果报名成功.提示用户已报名
	 * 5.如果没有teamId则创建球队并报名
	 *
	 * @param result
	 * @param compApply
	 * @return
	 * @author xiang_wang
	 * 2016年3月4日下午4:45:06
	 */
	private boolean checkCompApply(Response<?> result, CompetitionApplyDto compApply) {
		if (StringUtils.isBlank(compApply.getCreateUserId())) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("请先登录后报名");
			return false;
		}
		if (StringUtils.isBlank(compApply.getCompId())) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("系统异常");
			return false;
		}
		UserDto user = userService.getUserById(compApply.getCreateUserId());
		if (null != user) {
			// 只有队长才有权利创建和报名球队
			if (null == user.getUtype() && user.getUtype() == UserType.GENERAL.getIndex()) {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("对不起.普通用户没有权限报名~");
				return false;
			}

			UserInfoDto userInfo = userInfoService.getUserInfo(user.getId());
			if (null == userInfo) {
				result.setStatus(DataStatus.HTTP_INFO);
				result.setMessage("对不起.请补充参赛信息~");
				return false;
			}
			try {
				TeamDto team = null;
				boolean isCreate = false; // 是否需要创建球队
				TeamPlayerDto player = playerService.getTeamPlayeByUserId(user.getId());
				if (null != player) {
					team = teamService.getTeamById(player.getTeamId());
					if (null == team.getStat() || team.getStat() == DataStatus.ENABLED) {
						// 如果球队信息有更改,则更新球队信息
						boolean flag = false;
						if (StringUtils.isNotBlank(compApply.getTeamIcon()) && (null == team.getIcon() || !team.getIcon().equals(compApply.getTeamIcon()))) {
							team.setIcon(compApply.getTeamIcon());
							flag = true;
						}
						if (!team.getName().equals(compApply.getTeamName())) {
							team.setName(compApply.getTeamName());
							flag = true;
						}
						if (!team.getContacts().equals(compApply.getContacts())) {
							team.setContacts(compApply.getContacts());
							flag = true;
						}
						if (!team.getMobile().equals(compApply.getMobile())) {
							team.setMobile(compApply.getMobile());
							flag = true;
						}
						if (flag) {
							teamService.updateTeam(team);
						}
					} else {
						isCreate = true;
					}
				} else {
					isCreate = true;
				}

				if (isCreate) {
					// 队长没有球队,则创建球队
					String teamId = UUIDGenerator.getUUID();
					team = new TeamDto();
					team.setId(teamId);
					team.setIcon(compApply.getTeamIcon());
					team.setName(compApply.getTeamName());
					team.setContacts(compApply.getContacts());
					team.setMobile(compApply.getMobile());
					team.setCreateUserId(user.getId());
					team.setCreateUserName(userInfo.getName());
					team.setStat(DataStatus.ENABLED);
					teamService.addTeam(team);

					// 给新增的球队添加第一条球员数据
					TeamPlayerDto createPlayer = assemblyPlayer(user, teamId, userInfo);
					playerService.save(createPlayer);
				}

				CompetitionApplyDto param = new CompetitionApplyDto();
				param.setCompId(compApply.getCompId());
				param.setTeamId(team.getId());
				List<CompetitionApplyDto> list = applyService.findCompetitionApply(param);
				if (null != list && list.size() > 0) {
					for (CompetitionApplyDto temp : list) {
						if (temp.getAuditType().intValue() == AuditType.UNAUDIT.getIndex()) {
							result.setStatus(DataStatus.HTTP_FAILE);
							result.setMessage("赛事已申请,请耐心等待审核!");
							return false;
						}
						if (temp.getAuditType().intValue() == AuditType.PASS.getIndex()) {
							result.setStatus(DataStatus.HTTP_FAILE);
							result.setMessage("您已经报名成功了~");
							return false;
						}
					}
				}
				compApply.setTeamId(team.getId());
				compApply.setTeamIcon(team.getIcon());
				compApply.setTeamName(team.getName());
				compApply.setContacts(user.getNickName());
				compApply.setMobile(user.getMobile());
				compApply.setCreateUserName(user.getNickName());
				result.setStatus(DataStatus.HTTP_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("系统异常");
				return false;
			}
		} else {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户不存在");
			return false;
		}

		return true;
	}

	/**
	 * <p>Description: 组装球队球员数据 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 *
	 * @param user
	 * @param teamId
	 * @param userInfo
	 * @return
	 * @author wangxiang
	 * @date 16/4/14 上午11:58
	 * @version 1.0
	 */
	private TeamPlayerDto assemblyPlayer(UserDto user, String teamId, UserInfoDto userInfo) {
		TeamPlayerDto player = new TeamPlayerDto();
		player.setMobile(userInfo.getMobile());
		player.setAge(userInfo.getAge());
		player.setCardNo(userInfo.getCardNo());
		player.setGender(userInfo.getGender());
		player.setName(userInfo.getName());
		player.setStudentNo(userInfo.getStudentNo());
		player.setSchool(userInfo.getSchool());
		// 保存用户头像
		player.setPic(user.getPic());
		player.setTeamId(teamId);
		player.setUserId(user.getId());

		return player;
	}
}

/**
 * 每轮下比赛记录数据封装
 */
class RoundDetail implements Serializable {

	@Getter
	@Setter
	private Integer currRound;  // 当前轮数据

	@Getter
	@Setter
	private String name;		// 赛事场次名(第几轮)

	@Getter
	@Setter
	private Integer before; // 上一轮

	@Getter
	@Setter
	private Integer next;   // 下一轮

	@Getter
	@Setter
	private PageResult<CompetitionMatchDto> matchs; // 当前轮下比赛记录
}