package com.suhang.networkmvp.domain;


import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.event.ErrorCode;

/**
 * Created by sh on 2016/3/11 16:13.
 */
public class ErrorBean extends ErrorCode {

	public ErrorBean(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public ErrorBean() {

	}

	//错误代码
	protected String code;
	//错误描述
	protected String desc;
	//错误类型(如:是否需要提示给用户)
	protected String type = Constants.ERRORTYPE_ONE;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
