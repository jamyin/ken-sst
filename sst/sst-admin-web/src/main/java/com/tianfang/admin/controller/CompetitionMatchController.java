package com.tianfang.admin.controller;

import com.tianfang.admin.dto.*;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.constants.TeamPlayerPositionEnum;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.*;
import com.tianfang.train.enums.PeopleType;
import com.tianfang.train.enums.TeamType;
import com.tianfang.train.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 比赛Controller
 * @author YIn
 * @time:2016年1月15日 上午9:39:09
 * @ClassName: competitionMatchController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition/match")
public class CompetitionMatchController extends BaseController {

	@Autowired
	private ICompetitionMatchService matchService;
	@Autowired
	private ICompetitionService compService;
	@Autowired
	private ICompetitionRoundService roundService;
	@Autowired
	private ITeamService teamService;
	@Autowired
	private IMatchDatasService matchDatasService;
	@Autowired
	private ITeamPlayerService playserService;
	
	/**
	 * 跳转至新增比赛页面
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午2:10:23
	 */
	@RequestMapping(value = "createMatch")
	public ModelAndView createMatch(String croundId) {
		if (StringUtils.isBlank(croundId)){
			return selectComp();
		}
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionRoundDto round = roundService.getRoundById(croundId);
		List<TeamDto> teams = teamService.findAllByCompId(round.getCompId());
		mv.addObject("round", round);
		mv.addObject("teams", teams);
		mv.addObject("types", TeamType.values());
		mv.addObject("peopleTypes", PeopleType.values());
		mv.setViewName("/competition/match/match_add");
		return mv;
	}
	
	/**
	 * 先选择赛事
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日上午11:57:39
	 */
	@RequestMapping(value = "selectComp")
	public ModelAndView selectComp() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> comps = compService.findAllCompetition();
		mv.addObject("comps", comps);
		mv.setViewName("/competition/match/selectComp");
		return mv;
	}
	
	/**
	 * 根据赛事id获取场次记录
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午1:13:30
	 */
	@ResponseBody
	@RequestMapping(value="/selectRound")
	public Response<List<CompetitionRoundDto>> selectRound(String compId){
		Response<List<CompetitionRoundDto>> data = new Response<List<CompetitionRoundDto>>();
		List<CompetitionRoundDto> dtos = roundService.findRoundByCompId(compId);
		data.setStatus(DataStatus.HTTP_SUCCESS);
		data.setData(dtos);
		return data;
	}

	/**
	 * 保存比赛
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:27:56
	 */
	@ResponseBody
	@RequestMapping(value="/save")
	public Response<String> addCompetitionMatch(CompetitionMatchDto dto){
		Response<String> data = new Response<String>();
		TeamDto homeTeam = teamService.getTeamById(dto.getHomeTeamId());
		TeamDto visitingTeam = teamService.getTeamById(dto.getVisitingTeamId());
		AdminDto admin = getSessionAdmin();
		try {
			matchService.saveMatch(dto, homeTeam, visitingTeam, admin.getId(), admin.getAccount());
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	/**
	 * 编辑
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:33:27
	 */
	@RequestMapping(value = "edit")
	public ModelAndView goEdit(String id) {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionMatchDto match = matchService.getMatchById(id);
		List<TeamDto> teams = teamService.findAllByCompId(match.getCompId());
		mv.addObject("match", match);
		mv.addObject("teams", teams);
		mv.addObject("types", TeamType.values());
		mv.addObject("peopleTypes", PeopleType.values());
		mv.setViewName("/competition/match/match_edit");
		return mv;
	}
	
	/**
	 * 根据主键Id编辑比赛
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="update")
	public Response<String> updateCompetitionMatch(CompetitionMatchDto dto){
		Response<String> data = new Response<String>();
		TeamDto homeTeam = teamService.getTeamById(dto.getHomeTeamId());
		TeamDto visitingTeam = teamService.getTeamById(dto.getVisitingTeamId());
		AdminDto admin = getSessionAdmin();
		try {
			matchService.updateMatch(dto, homeTeam, visitingTeam, admin.getId(), admin.getAccount());
			data.setMessage("编辑成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("编辑失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionMatchDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="del")
	public Response<String> delCompetitionMatchLogic(CompetitionMatchDto competitionMatchDto){
		Response<String> data = new Response<String>();
		int flag = matchService.delCompetitionMatch(competitionMatchDto);
		if(flag > 0){
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("删除失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午5:54:33
	 * @param competitionMatchDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public Response<String> delCompetitionMatch(CompetitionMatchDto competitionMatchDto){
		Response<String> data = new Response<String>();
		competitionMatchDto.setStat(0); //逻辑删除
		int status = matchService.updateCompetitionMatch(competitionMatchDto);
		if(status > 0){
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		}else{
			data.setMessage("删除失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:14:51
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="delByIds")
    public Response<String> delByIds(String  ids) throws Exception{
		Response<String> data = new Response<String>();
		
		if (StringUtils.isEmpty(ids)) {
			data.setMessage("请选择需要删除的项！");
			data.setStatus(DataStatus.HTTP_FAILE);
	        return data;
	    }
	    Integer resObject =(Integer) matchService.delByIds(ids);
	    if (resObject == 0) {
	    	data.setMessage("批量删除失败！");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
	    if (resObject == 1) {
	    	data.setMessage("批量删除成功！");
			data.setStatus(DataStatus.HTTP_SUCCESS);
            return data;
        }
	    data.setMessage("删除异常！");
		data.setStatus(DataStatus.HTTP_FAILE);
	    return data;
	}
	
	/**
	 * 查询比赛-不分页
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionMatchDto
	 * @return
	 */
	@RequestMapping(value="find")
	@ResponseBody
	public Response<List<CompetitionMatchDto>> findCompetitionMatch(CompetitionMatchDto competitionMatchDto){
		Response<List<CompetitionMatchDto>> data = new Response<List<CompetitionMatchDto>>();
		
		List<CompetitionMatchDto> result = matchService.findCompetitionMatch(competitionMatchDto);
		if(result.size() > 0){
			data.setMessage("查询比赛成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询比赛失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台比赛显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:03
	 * @param dto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView list(CompetitionMatchDto dto, ExtPageQuery page){
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionMatchDto> result = matchService.findCompetitionMatchViewByPage(dto, page.changeToPageQuery());
		mv.addObject("pageList", result);
		mv.addObject("matchTypes", TeamType.values());
		mv.addObject("params", dto);
		mv.setViewName("/competition/match/match_list");
		return mv;
	}

	/**
	 * 添加/编辑 比赛球队数据
	 * @param matchId
     * @return
     */
	@RequestMapping(value="addTeamDatas")
	public ModelAndView addTeamDatas(String matchId){
		ModelAndView mv = getModelAndView();
		CompetitionMatchDto match = matchService.getMatchById(matchId);
		if (match != null){
			TeamDto home = teamService.getTeamById(match.getHomeTeamId());
			TeamDto vs = teamService.getTeamById(match.getVisitingTeamId());
			// 1.先查询是否添加过数据
			List<MatchTeamBaseDatasDto> teamDatas = matchDatasService.queryTeamDatasByMatchId(matchId);
			if (null != teamDatas && teamDatas.size() > 0){
				for (MatchTeamBaseDatasDto teamData : teamDatas){
					if (home.getId().equals(teamData.getTeamId())){
						mv.addObject("homeDatas", teamData);
					}
					if (vs.getId().equals(teamData.getTeamId())){
						mv.addObject("vsDatas", teamData);
					}
				}
			}
			mv.addObject("home", home);
			mv.addObject("vs", vs);
			mv.addObject("matchId", matchId);
		}

		mv.setViewName("/competition/match/addTeamDatas");
		return mv;
	}

	/**
	 * 保存/编辑 比赛球队基本数据
	 * @param dto
	 * @return
     */
	@RequestMapping(value="saveTeamDatas")
	@ResponseBody
	public Response<String> saveTeamDatas(TeamDataDto dto){
		Response<String> response = new Response<String>();
		try {
			List<MatchTeamBaseDatasDto> datas = assemblyDto(dto);
			if (null != dto.getId() && dto.getId().length > 0 && StringUtils.isNotBlank(dto.getId()[0])){
                System.out.println("update");
                matchDatasService.batchUpdateTeamDatas(datas);
                response.setMessage("修改成功");
            }else{
                System.out.println("save");
                matchDatasService.batchInsertTeamDatas(datas);
                response.setMessage("保存成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常!");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 选择添加那个球队
	 * @param matchId
	 * @param type 0-添加基本数据,1-添加时间轴数据
	 * @return
     */
	@RequestMapping(value="selectTeam")
	public ModelAndView selectTeam(String matchId, Integer type){
		ModelAndView mv = getModelAndView();
		CompetitionMatchDto match = matchService.getMatchById(matchId);
		if (match != null){
			TeamDto home = teamService.getTeamById(match.getHomeTeamId());
			TeamDto vs = teamService.getTeamById(match.getVisitingTeamId());
			mv.addObject("home", home);
			mv.addObject("vs", vs);
			mv.addObject("matchId", matchId);
			mv.addObject("type", type);
		}

		mv.setViewName("/competition/match/selectTeam");
		return mv;
	}

	/**
	 * 添加/编辑 比赛球员数据
	 * @param matchId
	 * @param teamId
     * @return
     */
	@RequestMapping(value="addPlayerDatas")
	public ModelAndView addPlayerDatas(String matchId, String teamId){
		ModelAndView mv = getModelAndView();
		TeamPlayerDto param = new TeamPlayerDto();
		param.setTeamId(teamId);
		List<TeamPlayerDto> players = playserService.findTeamPlayerByParam(param);
		List<MatchPlayerBaseDatasDto> playerDatas = getMatchPlayerBaseDatasDtos(matchId, teamId);
		List<PlayerDto> playerDtos = assemblyDto(players, playerDatas);
		mv.addObject("players", playerDtos);
		mv.addObject("teamId", teamId);
		mv.addObject("matchId", matchId);
		mv.setViewName("/competition/match/addPlayerDatas");
		return mv;
	}

	/**
	 * 保存/修改 比赛球员数据
	 * @param dto
	 * @return
     */
	@RequestMapping(value="savePlayerDatas")
	@ResponseBody
	public Response<String> savePlayerDatas(PlayerDataDto dto){
		Response<String> response = new Response<String>();
		if (null == dto || null == dto.getPlayerId()){
			response.setMessage("亲,请填写数据!");
			response.setStatus(DataStatus.HTTP_FAILE);
			return response;
		}
		try {
			List<MatchPlayerBaseDatasDto> datas = assemblyDto(dto);
			if (null != dto.getId() && dto.getId().length > 0 && StringUtils.isNotBlank(dto.getId()[0])){
				System.out.println("update");
				matchDatasService.updatePlayerBaseDatas(datas);
				response.setMessage("修改成功");
			}else{
				System.out.println("save");
				matchDatasService.batchInsertPlayerBaseDatas(datas);
				response.setMessage("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常!");
			logger.error(e.getMessage());
		}
		return response;
	}


	/**
	 * 添加/编辑 比赛时间轴数据
	 * @param matchId
	 * @param teamId
	 * @return
	 */
	@RequestMapping(value="addHotDatas")
	public ModelAndView addHotDatas(String matchId, String teamId){
		ModelAndView mv = getModelAndView();
		TeamPlayerDto param = new TeamPlayerDto();
		param.setTeamId(teamId);
		List<TeamPlayerDto> players = playserService.findTeamPlayerByParam(param);
		List<MatchPlayerHotDatasDto> hotDatas = getHotDatas(matchId, teamId);
		mv.addObject("players", players);
		mv.addObject("hotTypes", MatchPlayerHotDatasDto.Type.values());
		mv.addObject("hotDatas", hotDatas);
		mv.addObject("teamId", teamId);
		mv.addObject("matchId", matchId);
		mv.setViewName("/competition/match/addHotDatas");
		return mv;
	}

	/**
	 * 保存/修改 比赛时间轴数据
	 * @param dto
	 * @return
     */
	@RequestMapping(value="saveHotDatas")
	@ResponseBody
	public Response<String> saveHotDatas(HotDatasDto dto){
		Response<String> response = new Response<String>();
		if (null == dto || null == dto.getPlayerId()){
			response.setMessage("亲,请填写数据!");
			response.setStatus(DataStatus.HTTP_FAILE);
			return response;
		}
		try {
			List<MatchPlayerHotDatasDto> datas = assemblyDto(dto);
			if (null != dto.getId() && dto.getId().length > 0 && StringUtils.isNotBlank(dto.getId()[0])){
				System.out.println("update");
				matchDatasService.updatePlayerHotDatas(datas);
				response.setMessage("修改成功");
			}else{
				System.out.println("save");
				matchDatasService.batchInsertPlayerHotDatas(datas);
				response.setMessage("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常!");
			logger.error(e.getMessage());
		}
		return response;
	}

	/**
	 * 根据比赛id,球队id查询时间轴数据
	 * @param matchId
	 * @param teamId
	 * @return
	 */
	private List<MatchPlayerHotDatasDto> getHotDatas(String matchId, String teamId) {
		MatchPlayerHotDatasDto param = new MatchPlayerHotDatasDto();
		param.setMatchId(matchId);
		param.setTeamId(teamId);
		List<MatchPlayerHotDatasDto> matchPlayerHotDatasDtos = matchDatasService.queryPlayerHotDatasByParams(param);
		if (null != matchPlayerHotDatasDtos && matchPlayerHotDatasDtos.size() > 0){
			return matchPlayerHotDatasDtos;
		}
		return null;
	}

	/**
	 * 将页面接收数据转为MatchPlayerHotDatasDto
	 * @param dto
	 * @return
     */
	private List<MatchPlayerHotDatasDto> assemblyDto(HotDatasDto dto) {
		if (null != dto && null != dto.getPlayerId()){
			List<MatchPlayerHotDatasDto> datas = new ArrayList<>(dto.getPlayerId().length);
			MatchPlayerHotDatasDto data;
			for (int i = 0, len = dto.getPlayerId().length; i < len; i++){
				data = new MatchPlayerHotDatasDto();
				data.setMatchId(dto.getMatchId());
				data.setTeamId(dto.getTeamId());
				data.setMinute(dto.getMinute()[i]);
				data.setPlayerId(dto.getPlayerId()[i]);
				data.setType(dto.getType()[i]);

				datas.add(data);
			}
			return datas;
		}
		return null;
	}

	/**
	 * 根据比赛id,球队id查询球员数据
	 * @param matchId
	 * @param teamId
	 * @return
	 */
	private List<MatchPlayerBaseDatasDto> getMatchPlayerBaseDatasDtos(String matchId, String teamId) {
		MatchPlayerBaseDatasDto params = new MatchPlayerBaseDatasDto();
		params.setMatchId(matchId);
		params.setTeamId(teamId);
		return matchDatasService.queryPlayerBaseDatasByParams(params);
	}

	/**
	 * 组装在页面展示的数据
	 * @param playerDtos
	 * @param playerDatas
     * @return
     */
	private List<PlayerDto> assemblyDto(List<TeamPlayerDto> playerDtos, List<MatchPlayerBaseDatasDto> playerDatas){
		if (null != playerDtos && playerDtos.size() > 0){
			Map<String, MatchPlayerBaseDatasDto> map = null;
			if (null != playerDatas && playerDatas.size() > 0){
				map = new HashMap<>(playerDatas.size());
				for (MatchPlayerBaseDatasDto pd : playerDatas){
					map.put(pd.getPlayerId(), pd);
				}
			}
			List<PlayerDto> players = new ArrayList<PlayerDto>(playerDtos.size());
			PlayerDto player;
			MatchPlayerBaseDatasDto datas;
			for (TeamPlayerDto dto : playerDtos){
				player = new PlayerDto();
				player.setPlayerId(dto.getId());
				player.setName(dto.getName());
				player.setNum(dto.getNum());
				player.setPosition(TeamPlayerPositionEnum.getName(dto.getPosition()));
				if (null != map){
					if (map.containsKey(dto.getId())){
						datas = map.get(dto.getId());
						player.setId(datas.getId());
						player.setYellow(datas.getYellow());
						player.setAssist(datas.getAssist());
						player.setClearanceKick(datas.getClearanceKick());
						player.setFoul(datas.getFoul());
						player.setGoal(datas.getGoal());
						player.setMinute(datas.getMinute());
						player.setPass(datas.getPass());
						player.setRed(datas.getRed());
						player.setReserve(datas.getReserve());
						player.setSave(datas.getSave());
						player.setShot(datas.getShot());
						player.setShotRight(datas.getShotRight());
						player.setTackle(datas.getTackle());
					}else{
						// 被禁用
						player.setDisabled(DataStatus.ENABLED);
					}
				}

				players.add(player);
			}

			return players;
		}

		return null;
	}

	/**
	 * 将网页上接收的数据.组装成比赛球员数据
	 * @param dto
	 * @return
     */
	private List<MatchPlayerBaseDatasDto> assemblyDto(PlayerDataDto dto) {
		if (null != dto && null != dto.getPlayerId()){
			List<MatchPlayerBaseDatasDto> datas = new ArrayList<>(dto.getPlayerId().length);
			MatchPlayerBaseDatasDto data;
			for (int i = 0, len = dto.getPlayerId().length; i < len; i++){
				data = new MatchPlayerBaseDatasDto();
				data.setTeamId(dto.getTeamId());
				data.setYellow(dto.getYellow()[i]);
				data.setAssist(dto.getAssist()[i]);
				data.setClearanceKick(dto.getClearanceKick()[i]);
				data.setFoul(dto.getFoul()[i]);
				data.setGoal(dto.getGoal()[i]);
				data.setMatchId(dto.getMatchId());
				data.setMinute(dto.getMinute()[i]);
				data.setPass(dto.getPass()[i]);
				data.setPlayerId(dto.getPlayerId()[i]);
				data.setRed(dto.getRed()[i]);
				data.setReserve(dto.getReserve()[i]);
				data.setSave(dto.getSave()[i]);
				data.setShot(dto.getShot()[i]);
				data.setShotRight(dto.getShotRight()[i]);
				data.setTackle(dto.getTackle()[i]);

				datas.add(data);
			}

			return datas;
		}
		return null;
	}

	/**
	 * 将页面接收的比赛球队数据转为MatchTeamBaseDatasDto
	 *
	 * @param dto
	 * @return
     */
	private List<MatchTeamBaseDatasDto> assemblyDto(TeamDataDto dto){
		if (null != dto){
			List<MatchTeamBaseDatasDto> datas = new ArrayList<MatchTeamBaseDatasDto>(2);
			MatchTeamBaseDatasDto data;
			for (int i = 0, len = dto.getTeamId().length; i < len; i++){
				data = new MatchTeamBaseDatasDto();
				data.setMatchId(dto.getMatchId());
				data.setClearanceKick(dto.getClearanceKick()[i]);
				data.setCorner(dto.getCorner()[i]);
				data.setFreeKick(dto.getFreeKick()[i]);
				data.setGoal(dto.getGoal()[i]);
				data.setGoalOut(dto.getGoalOut()[i]);
				data.setOffside(dto.getOffside()[i]);
				data.setPass(dto.getPass()[i]);
				data.setPassCross(dto.getPassCross()[i]);
				data.setRed(dto.getRed()[i]);
				data.setShot(dto.getShot()[i]);
				data.setShotPost(dto.getShotPost()[i]);
				data.setShotRight(dto.getShotRight()[i]);
				data.setTackle(dto.getTackle()[i]);
				data.setYellow(dto.getYellow()[i]);
				data.setTeamId(dto.getTeamId()[i]);
				data.setTrapRate(dto.getTrapRate()[i]);

				datas.add(data);
			}

			return datas;
		}

		return null;
	}
}