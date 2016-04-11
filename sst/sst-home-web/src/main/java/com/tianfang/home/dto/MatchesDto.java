package com.tianfang.home.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.tianfang.train.dto.TeamDto;

public class MatchesDto implements Serializable{

		@Getter
		@Setter
		private String title;
		
		
		@Getter
		@Setter
		private List<TeamDto> list;
}
