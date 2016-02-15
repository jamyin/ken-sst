package com.tianfang.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.tianfang.user.dto.VoteDto;
import com.tianfang.user.dto.VoteExDto;
import com.tianfang.user.pojo.Vote;

@Service
public class VoteServiceImpl implements IVoteService {
	
	@Autowired
	private VoteDao voteDao;

	@Override
	public String save(VoteDto dto) throws Exception {
		checkObjIsNull(dto);
		String id = UUIDGenerator.getUUID();
		Vote v = BeanUtils.createBeanByTarget(dto, Vote.class);
		v.setId(id);
		voteDao.insertSelective(v);
		return id;
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		Vote v = voteDao.selectByPrimaryKey(id);
		checkObjIsNotExist(v);
		v.setStat(DataStatus.DISABLED);
		voteDao.updateByPrimaryKeySelective(v);
	}

	@Override
	public void update(VoteDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(voteDao.selectByPrimaryKey(dto.getId()));
		Vote v = BeanUtils.createBeanByTarget(dto, Vote.class);
		voteDao.updateByPrimaryKeySelective(v);
	}

	@Override
	public VoteDto getVoteById(String id) throws Exception {
		checkIdIsNull(id);
		VoteDto dto = BeanUtils.createBeanByTarget(voteDao.selectByPrimaryKey(id), VoteDto.class);
		return dto;
	}

	@Override
	public List<VoteDto> findVoteByParam(VoteDto dto) throws Exception {
		List<VoteDto> list = BeanUtils.createBeanListByTarget(voteDao.findVoteByParam(dto), VoteDto.class);
		return list;
	}

	@Override
	public PageResult<VoteDto> findVoteByParam(VoteDto dto, PageQuery query)
			throws Exception {
		int total = voteDao.countVoteByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<VoteDto> list = voteDao.findVoteByParam(dto, query);
			return new PageResult<VoteDto>(query, list);
		}
		return null;
	}
	
	@Override
	public VoteApp getVoteAppById(String id) throws Exception {
		checkIdIsNull(id);
		
		List<VoteExDto> dtos = voteDao.findVoteExById(id);
		return voteExToVoteApp(id, dtos);
	}

	private VoteApp voteExToVoteApp(String id, List<VoteExDto> dtos) {
		VoteApp app = null;
		if (null != dtos && dtos.size() > 0){
			app = new VoteApp(id, dtos.get(0).getTitle(), dtos.get(0).getOptionNum(), dtos.get(0).getEndTime(), dtos.get(0).getIsAnonymous(),
					dtos.get(0).getUserId(), dtos.get(0).getUserName());
			Map<String, VoteOptionApp> map = new HashMap<String, VoteOptionApp>();
			VoteOptionApp option;
			for (VoteExDto dto : dtos){
				if (map.containsKey(dto.getOptionId())){
					option = map.get(dto.getOptionId());
					appendUser(option, dto);
				}else{
					option = new VoteOptionApp(dto.getOptionId(), dto.getOptionText());
					appendUser(option, dto);
					map.put(dto.getOptionId(), option);
				}
			}
			if (null != map && map.size() > 0){
				List<VoteOptionApp> options = new ArrayList<VoteOptionApp>();
				Set<Entry<String,VoteOptionApp>> entrySet = map.entrySet();
				for (Entry<String,VoteOptionApp> entry : entrySet){
					options.add(entry.getValue());
				}
				app.setOptions(options);
			}
		}
		return app;
	}

	private void appendUser(VoteOptionApp option, VoteExDto dto) {
		List<OptionUserApp> users;
		OptionUserApp user;
		if (!StringUtils.isBlank(dto.getOptionUserId())){
			user = new OptionUserApp(dto.getOptionUserId(), dto.getOptionNickName());
			if (null == option.getUsers()){
				users = new ArrayList<OptionUserApp>();
			}else{
				users = option.getUsers();
			}
			users.add(user);
			option.setUsers(users);
		}
	}
	
	private void checkObjIsNull(Object obj) {
		if (obj == null){
			throw new RuntimeException("对不起,投票对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,投票主键ID为空!");
		}
	}

	private void checkObjIsNotExist(Object obj) {
		if (obj == null){
			throw new RuntimeException("对不起,投票对象不存在!");
		}
	}
}