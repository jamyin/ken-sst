package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.evaluat.dto.EvaluatAnswerDto;
import com.tianfang.evaluat.dto.EvaluatDto;
import com.tianfang.evaluat.dto.EvaluatQuestionDto;
import com.tianfang.evaluat.dto.EvaluatResultDto;
import com.tianfang.evaluat.dto.EvaluatScoreDto;
import com.tianfang.evaluat.service.IEvaluatAnswerService;
import com.tianfang.evaluat.service.IEvaluatQuestionService;
import com.tianfang.evaluat.service.IEvaluatResultService;
import com.tianfang.evaluat.service.IEvaluatScoreService;
import com.tianfang.evaluat.service.IEvaluatService;
@Controller
@RequestMapping(value = "eva")
public class EvaluatController extends BaseController{
	
	protected Logger logger = Logger.getLogger(EvaluatController.class);
	
	@Autowired
	private IEvaluatService iEvaluatService;
	
	@Autowired
	private IEvaluatQuestionService iEvaluatQuestionService;
	
	@Autowired
	private IEvaluatAnswerService iEvaluatAnswerService;
	
	@Autowired
	private IEvaluatScoreService iEvaluatScoreService;
	
	@Autowired
	private IEvaluatResultService iEvaluatResultService;
	/*
	 * 消息助手 身心评测 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Response<Object> evaList(EvaluatDto dto,PageQuery page){
		Response<Object> response = new Response<Object>();
		if(Objects.equal(dto.getEvaType(),DataStatus.EVA_TWO)){
			List<EvaluatDto> resultList = iEvaluatService.findEvaluatBySql(dto);
			if(resultList!=null){
				EvaluatDto evaDto = resultList.get(0);
				String evaId = evaDto.getId();
				try {
					response.setStatus(DataStatus.HTTP_SUCCESS);
					response.setData(initAllDataList(evaId));
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus(DataStatus.HTTP_FAILE);
					response.setMessage("系统异常");
					logger.error(e.getMessage());
				}				
			}
		}else{
			try {
				PageResult<EvaluatDto> datas = iEvaluatService.findEvaluatBySql(dto, page);
				response.setStatus(DataStatus.HTTP_SUCCESS);
				response.setData(datas);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(DataStatus.HTTP_FAILE);
				response.setMessage("系统异常");
				logger.error(e.getMessage());
			}			
		}
		return response;
	}
	
	/**
	 * 点击开始测试 列出全部的数据
	 */
	@RequestMapping(value = "queryById")
	@ResponseBody
	public Response<List<EvaluatQuestionDto>> queryById(String evaId){
		Response<List<EvaluatQuestionDto>> response = new Response<List<EvaluatQuestionDto>>();
		try {
			response.setStatus(DataStatus.HTTP_SUCCESS);
			response.setData(initAllDataList(evaId));
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常");
			logger.error(e.getMessage());
		}
		return response;
	}
	
	private Map<String,List<EvaluatAnswerDto>> changeListToMap(List<EvaluatAnswerDto> eaList){
		Map<String, List<EvaluatAnswerDto>> map = Maps.newHashMap();
		Iterator<EvaluatAnswerDto> its = eaList.iterator();
		List<EvaluatAnswerDto> dataList = null;
		while(its.hasNext()){
			EvaluatAnswerDto eaDto = its.next();
			String keyCode = eaDto.getEvaQuestionId();
			if(map.containsKey(keyCode)){
				dataList = map.get(keyCode);
				dataList.add(eaDto);
			}else{
				dataList = new ArrayList<EvaluatAnswerDto>();
				dataList.add(eaDto);
			}
			map.put(keyCode, dataList);
		}
		return map;
	}

	private List<EvaluatQuestionDto> changeListToMap(List<EvaluatQuestionDto> eqList,List<EvaluatAnswerDto> eaList) {		
		Map<String, List<EvaluatAnswerDto>> mapKey = changeListToMap(eaList);
		for(EvaluatQuestionDto eqDto : eqList){
			List<EvaluatAnswerDto> answerList = mapKey.get(eqDto.getId());
			eqDto.setAnswerList(answerList);
		}
		return eqList;
	}


//	[
//	    {
//	        "evaId": "1",
//	        "sumScore":333
//	    }
//	]
	/**
	 * 
		 * 此方法描述的是：评测提交结果
		 * @author: cwftalus@163.com
		 * @version: 2016年2月2日 上午11:31:20
		 * @param evaId 
		 * @param sumScore
		 * @param userId
	 */
	@RequestMapping(value = "doSubmit")
	@ResponseBody
	public Response<EvaluatScoreDto> doSubmit(String evaId,Integer sumScore,String userId){
		Response<EvaluatScoreDto> result = new Response<EvaluatScoreDto>();
		EvaluatScoreDto esDto = new EvaluatScoreDto();
		esDto.setEvaId(evaId);
		esDto.setStartScore(sumScore);
		esDto.setEndScore(sumScore);
		List<EvaluatScoreDto> dataList = iEvaluatScoreService.findEvaluatScoreBySql(esDto);
		if(!dataList.isEmpty()){
			esDto = dataList.get(0);
			result.setData(esDto);

			//保存处理
			saveOrUpdateEvalatResult(sumScore,userId,esDto);
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("数据加载失败,请稍微重新再试");
		}
		return result;
	}
	/**
	 * 异步提交数据保存处理
	 */
	@Async
	private void saveOrUpdateEvalatResult(Integer sumScore,String userId,EvaluatScoreDto esDto){
		EvaluatResultDto erdto = new EvaluatResultDto();
		/**
		 * 注册用户对评测已经处理则做修改操作
		 */
		erdto.setEvaId(esDto.getEvaId());
		erdto.setSumScore(sumScore);
		erdto.setEvaScoreId(esDto.getId());
		erdto.setUserId(userId);
		boolean isTrue = false;
		if(!StringUtils.isEmpty(userId)){
			List<EvaluatResultDto> dataList = iEvaluatResultService.findEvaluatResultBySql(erdto);
			if(dataList!=null && dataList.size()>0){
				erdto.setId(dataList.get(0).getId());;
				isTrue = true;
			}
		}

		if(isTrue){
			iEvaluatResultService.modify(erdto);	
		}else{
			iEvaluatResultService.save(erdto);	
		}
	}
	
	private List<EvaluatQuestionDto> initAllDataList(String evaId){
		
		EvaluatQuestionDto eqDto = new EvaluatQuestionDto();
		eqDto.setEvaId(evaId);
		eqDto.setStat(DataStatus.ENABLED);
		//列出测评对应的所有题目List信息
		List<EvaluatQuestionDto> eqList = iEvaluatQuestionService.findEvaluatQuesBySql(eqDto);
		//列出所有评测对应题目中所有的答案信息
		EvaluatAnswerDto eaDto = new EvaluatAnswerDto();
		eaDto.setEvaId(evaId);
		eaDto.setStat(DataStatus.ENABLED);
		List<EvaluatAnswerDto> eaList = iEvaluatAnswerService.findEvaluatAnswerBySql(eaDto);
		
		return changeListToMap(eqList,eaList);		
	}
}
