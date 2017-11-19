package com.personal.healthcloud.service;


import com.personal.healthcloud.jpa.entity.PhysicalIdentify;

public interface PhysicalIdentifyService {
	/**
	 * 提交中医体质标识
	 * @param openid
	 *
	 * @param content
	 * @return
	 */
	String physiqueIdentify(String openid, String content);

	/**
	 * 根据用户获取最近一次中医体质辨识结果
	 * @param openid
	 * @return
	 */
	PhysicalIdentify getRecentPhysicalIdentify(String openid);
}
