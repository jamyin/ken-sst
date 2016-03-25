package com.tianfang.user.service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.app.OptionUserApp;
import com.tianfang.user.app.VoteApp;
import com.tianfang.user.app.VoteOptionApp;
import com.tianfang.user.dao.VoteDao;
import com.tianfang.user.dto.*;
import com.tianfang.user.pojo.Vote;
import com.tianfang.user.pojo.VoteUserTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;

@Service
public class VoteServiceImpl implements IVoteService {
	
	@Autowired
	private VoteDao voteDao;

	@Override
	public String save(VoteDto dto){
		checkObjIsNull(dto);
		String id = UUIDGenerator.getUUID();
		Vote v = BeanUtils.createBeanByTarget(dto, Vote.class);
		v.setId(id);
		voteDao.insertSelective(v);
		return id;
	}
	
	@Override
	@Transactional
	public String save(VoteDto dto, List<VoteUserTempDto> temps,
			List<VoteOptionDto> options) {
		Vote v = BeanUtils.createBeanByTarget(dto, Vote.class);
		voteDao.insertSelective(v);
		voteDao.insertBatchVoteOption(options);
		voteDao.insertBatchVoteUserTemp(temps);
		return v.getId();
	}

	@Override
	public void del(String id){
		checkIdIsNull(id);
		Vote v = voteDao.selectByPrimaryKey(id);
		checkObjIsNotExist(v);
		v.setStat(DataStatus.DISABLED);
		voteDao.updateByPrimaryKeySelective(v);
	}

	@Override
	public void update(VoteDto dto){
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(voteDao.selectByPrimaryKey(dto.getId()));
		Vote v = BeanUtils.createBeanByTarget(dto, Vote.class);
		voteDao.updateByPrimaryKeySelective(v);
	}
	
	@Override
	public void update(VoteUserTempDto temp) {
		checkObjIsNull(temp);
		VoteUserTemp obj = BeanUtils.createBeanByTarget(temp, VoteUserTemp.class);
		voteDao.updateVoteUserTemp(obj);
	}
	
	@Override
	public VoteDto getVoteById(String id){
		checkIdIsNull(id);
		Vote vote = voteDao.selectByPrimaryKey(id);
		return PojoToDto(vote);
	}

	@Override
	public List<VoteDto> findVoteByParam(VoteDto dto){
		List<Vote> votes = voteDao.findVoteByParam(dto);
		return ListPojoToDto(votes);
	}

	@Override
	public PageResult<VoteDto> findVoteByParam(VoteDto dto, PageQuery query){
		int total = voteDao.countVoteByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<Vote> votes = voteDao.findVoteByParam(dto, query);
			return new PageResult<VoteDto>(query, ListPojoToDto(votes));
		}
		return null;
	}
	
	@Override
	public VoteApp getVoteAppById(String id){
		checkIdIsNull(id);
		List<VoteExDto> dtos = voteDao.findVoteExById(id);
		return voteExToVoteApp(id, dtos);
	}
	
	@Override
	public List<VoteDto> findVoteTempByParam(VoteParams params) {
		List<VoteDto> votes = voteDao.findVoteTempByParam(params);
		if (null != votes && votes.size() > 0){
			for (VoteDto vote : votes){
				checkVoteOverdue(vote);
			}
		}
		return votes;
	}

	@Override
	public PageResult<VoteDto> findVoteTempByParam(VoteParams params, PageQuery query) {
		int total = voteDao.countVoteTempByParam(params);
		if (total > 0){
			query.setTotal(total);
			List<VoteDto> votes = voteDao.findVoteTempByParam(params, query);
			if (null != votes && votes.size() > 0){
				for (VoteDto vote : votes){
					checkVoteOverdue(vote);
				}
			}
			return new PageResult<VoteDto>(query, votes);
		}
		return null;
	}

	public VoteDto getLast(String userId){
		VoteDto last = voteDao.getLast(userId);
		checkVoteOverdue(last);
		return last;
	}

	private VoteApp voteExToVoteApp(String id, List<VoteExDto> dtos){
		VoteApp app = null;
		if (null != dtos && dtos.size() > 0){
			Integer amount = dtos.get(0).getAmount();
			app = new VoteApp(id, dtos.get(0).getTitle(), dtos.get(0).getOptionNum(), dtos.get(0).getEndTime(), dtos.get(0).getIsAnonymous(),
					dtos.get(0).getPublishId(), dtos.get(0).getPublishName(), dtos.get(0).getAmount(), dtos.get(0).getCreateTime());
			Map<String, VoteOptionApp> map = new HashMap<String, VoteOptionApp>();
			VoteOptionApp option;
			for (VoteExDto dto : dtos){
				if (map.containsKey(dto.getOptionId())){
					option = map.get(dto.getOptionId());
					appendUser(option, dto);
				}else{
					option = new VoteOptionApp(dto.getOptionId(), dto.getOptionText(), dto.getOptionPic());
					appendUser(option, dto);
					map.put(dto.getOptionId(), option);
				}
			}
			if (null != map && map.size() > 0){
				List<VoteOptionApp> options = new ArrayList<VoteOptionApp>();
				Set<Entry<String,VoteOptionApp>> entrySet = map.entrySet();
				for (Entry<String,VoteOptionApp> entry : entrySet){
					VoteOptionApp value = entry.getValue();
					if (null != amount && null != value.getUsers() && value.getUsers().size() > 0){
						value.setPercent((double) (value.getUsers().size()/amount));
					}else {
						value.setPercent(0.00);
					}
					options.add(value);
				}
				app.setOptions(options);
			}
		}
		return app;
	}

	private void appendUser(VoteOptionApp option, VoteExDto dto){
		List<OptionUserApp> users;
		OptionUserApp user;
		if (!StringUtils.isBlank(dto.getOptionUserId())){
			user = new OptionUserApp(dto.getOptionUserId(), dto.getOptionUserNickName(), dto.getOptionUserPic());
			if (null == option.getUsers()){
				users = new ArrayList<OptionUserApp>();
			}else{
				users = option.getUsers();
			}
			users.add(user);
			option.setUsers(users);
		}
	}
	
	private void checkObjIsNull(Object obj){
		if (obj == null){
			throw new RuntimeException("对不起,投票对象为空!");
		}
	}
	
	private void checkIdIsNull(String id){
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,投票主键ID为空!");
		}
	}

	private void checkObjIsNotExist(Object obj){
		if (obj == null){
			throw new RuntimeException("对不起,投票对象不存在!");
		}
	}
	
	/**
	 * 投票POJO集合转为DTO
	 * @param votes
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日下午5:32:14
	 */
	private List<VoteDto> ListPojoToDto(List<Vote> votes) {
		if (null != votes && votes.size() > 0){
			List<VoteDto> list = new ArrayList<VoteDto>(votes.size());
			VoteDto temp;
			for (Vote vote : votes){
				temp = PojoToDto(vote);
				if (null != temp){
					list.add(temp);
				}
			}
			return list;
		}
		
		return null;
	}
	
	/**
	 * 投票POJO 转为 DTO
	 * @param vote
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日下午5:27:53
	 */
	private VoteDto PojoToDto(Vote vote) {
		if (null != vote && (null == vote.getStat() || vote.getStat() == DataStatus.ENABLED)){
			VoteDto dto = BeanUtils.createBeanByTarget(vote, VoteDto.class);
			checkVoteOverdue(dto);
			return dto;
		}
		return null;
	}
	
	/**
	 * 判断投票是否截止
	 * @param dto
	 * @author xiang_wang
	 * 2016年3月8日下午5:25:23
	 */
	private void checkVoteOverdue(VoteDto dto) {
		if (null != dto && null != dto.getEndTime()){
			if (new Date().getTime() < dto.getEndTime().getTime()){
				dto.setOverdue(DataStatus.DISABLED);
			}else{
				dto.setOverdue(DataStatus.ENABLED);
			}
		}
	}
}