package com.tianfang.common.verify;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *验证码属性配置类
 * @author rkzhang
 *
 */
@ToString
public class VerfiyCodeConfig {

	/**
	* 验证码图片的宽度。
	*/
	@Getter
	@Setter
	private int width = 100;
	 
	/**
	* 验证码图片的高度。
	*/
	@Getter
	@Setter
	private int height = 35;
	 
	 
	/**
	* 验证码字符个数
	*/
	@Getter
	@Setter
	private int codeCount = 4;
	 
	/**
	* xx
	*/
	@Getter
	@Setter
	private int xx = 0;
	 
	/**
	* 字体高度
	*/
	@Getter
	@Setter
	private int fontHeight;
	 
	/**
	* codeY
	*/
	@Getter
	@Setter
	private int codeY;
	 
	/**
	* codeSequence
	*/
	@Getter
	@Setter
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	@Getter
	@Setter
	private String sessionKey;
	
	public void calculate(){
		xx = width / (codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;
	}
}
