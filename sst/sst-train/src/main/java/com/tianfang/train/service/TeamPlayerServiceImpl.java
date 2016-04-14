package com.tianfang.train.service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.TeamPlayerDao;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.pojo.TeamPlayer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPlayerServiceImpl implements ITeamPlayerService {

	@Autowired
	private TeamPlayerDao playerDao;
	
	@Override
	public String save(TeamPlayerDto dto) {
		checkObjIsNull(dto);
		TeamPlayer obj = BeanUtils.createBeanByTarget(dto, TeamPlayer.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		playerDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		TeamPlayer obj = playerDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		playerDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(TeamPlayerDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		TeamPlayer obj = BeanUtils.createBeanByTarget(dto, TeamPlayer.class);
		playerDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public TeamPlayerDto getTeamPlayerById(String id) {
		checkIdIsNull(id);
		TeamPlayer obj = playerDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			TeamPlayerDto dto = BeanUtils.createBeanByTarget(obj, TeamPlayerDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto) {
		List<TeamPlayerDto> list = playerDao.findTeamPlayerByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, TeamPlayerDto.class);
		}
		return null;
	}

	@Override
	public PageResult<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto, PageQuery query) {
		int total = playerDao.countTeamPlayerByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<TeamPlayerDto> results = playerDao.findTeamPlayerByParam(dto, query);
			return new PageResult<TeamPlayerDto>(query, results);
		}
		return null;
	}
	
	@Override
	public List<TeamPlayerDto> findTeamPlayerByTeamId(String teamId) {
		checkTeamIdIsNull(teamId);
		TeamPlayerDto dto = new TeamPlayerDto();
		dto.setTeamId(teamId);
		return findTeamPlayerByParam(dto);
	}

	@Override
	public TeamPlayerDto getTeamPlayeByUserId(String userId) {
		checkUserIdIsNull(userId);
		TeamPlayerDto dto = new TeamPlayerDto();
		dto.setUserId(userId);
		List<TeamPlayerDto> playeies = findTeamPlayerByParam(dto);
		if (null != playeies && playeies.size() > 0){
			if (playeies.size() > 1){
				throw new RuntimeException("数据异常,请联系技术人员,处理sst_team_player表数据!");
			}
			return playeies.get(0);
		}
		return null;
	}

	private void checkTeamIdIsNull(String teamId) {
		if (StringUtils.isBlank(teamId)){
			throw new RuntimeException("对不起,赛事球员对象球队ID为空!");
		}
	}

	private void checkUserIdIsNull(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,赛事球员对象用户ID为空!");
		}
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球员对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事球员对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球员对象不存在!");
		}
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月27日 上午11:20:18
	 */
	@Override
	public int addTeamPlayer(TeamPlayerDto teamPlayerDto) {
		TeamPlayer teamPlayer = BeanUtils.createBeanByTarget(teamPlayerDto, TeamPlayer.class);
		return playerDao.insertSelective(teamPlayer);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:38
	 */
	@Override
	public int updateTeamPlayer(TeamPlayerDto teamPlayerDto) {
		TeamPlayer teamPlayer = BeanUtils.createBeanByTarget(teamPlayerDto, TeamPlayer.class);
		return playerDao.updateByPrimaryKeySelective(teamPlayer);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:51:34
	 */
	@Override
	public int delTeamPlayer(TeamPlayerDto teamPlayerDto) {
		TeamPlayer teamPlayer = BeanUtils.createBeanByTarget(teamPlayerDto, TeamPlayer.class);
		return playerDao.deleteByPrimaryKey(teamPlayer.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	TeamPlayer teamPlayer = playerDao.selectByPrimaryKey(id);
	            if (null == teamPlayer) {
	                return 0;//无此条记录
	            }
	            teamPlayer.setStat(DataStatus.DISABLED);
	            playerDao.updateByPrimaryKeySelective(teamPlayer);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月27日 下午1:17:12
	 */
	@Override
	public List<TeamPlayerDto> findTeamPlayer(TeamPlayerDto teamPlayerDto) {
		TeamPlayer teamPlayer = BeanUtils.createBeanByTarget(teamPlayerDto, TeamPlayer.class);
		List<TeamPlayer> list = playerDao.selectByParameter(teamPlayer);
		List<TeamPlayerDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamPlayerDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月27日 下午2:18:10
	 */
	@Override
	public PageResult<TeamPlayerDto> findTeamPlayerViewByPage(TeamPlayerDto teamPlayerDto , PageQuery page) {
		TeamPlayer teamPlayer = BeanUtils.createBeanByTarget(teamPlayerDto, TeamPlayer.class);
		List<TeamPlayerDto> list = playerDao.findTeamPlayerByTeamPlayer(teamPlayerDto, page);
		int total = playerDao.countTeamPlayerByTeamPlayer(teamPlayerDto);
		page.setTotal(total);
		List<TeamPlayerDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamPlayerDto.class);
		return new PageResult<TeamPlayerDto>(page, list);
	}
	
}