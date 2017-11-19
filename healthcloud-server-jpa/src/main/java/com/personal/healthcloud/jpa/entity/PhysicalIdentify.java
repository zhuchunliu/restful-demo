package com.personal.healthcloud.jpa.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 中医体质辨识
 */
@Data
@Entity
@Table(name = "app_tb_physical_identify")
public class PhysicalIdentify {

	@Id
	private String id;
	private String openid;
	private String content;
	private String result;
	@Column(name = "del_flag")
	private String delFlag = "0";
	@Column(name = "create_by")
	private String createBy;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_by")
	private String updateBy;
	@Column(name = "update_date")
	private Date updateDate;

}
