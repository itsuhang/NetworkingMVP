package com.suhang.networkmvp.domain;

/**
 * Created by 苏杭 on 2016/11/23 11:10.
 */

public class DeleteHistoryBean extends ErrorBean{
	public static final String URL = "api/other/deleteHistory.php";
	public static final String METHOD = "deleteHistory";
	private String  failedList;

	public String getFailedList() {
		return failedList;
	}

	public void setFailedList(String failedList) {
		this.failedList = failedList;
	}

	@Override
	public String toString() {
		return "DeleteHistoryBean{" +
				"failedList='" + failedList + '\'' +
				'}';
	}

}
