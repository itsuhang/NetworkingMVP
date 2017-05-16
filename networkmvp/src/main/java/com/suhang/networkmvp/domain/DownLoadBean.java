package com.suhang.networkmvp.domain;

/**
 * Created by 苏杭 on 2016/11/16 10:24.
 */

public class DownLoadBean extends ErrorBean {
	private String path;

	public DownLoadBean(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
