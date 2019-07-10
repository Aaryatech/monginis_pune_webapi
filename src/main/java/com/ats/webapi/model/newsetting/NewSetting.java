package com.ats.webapi.model.newsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="t_setting_new")
public class NewSetting implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="setting_id")
	private int settingId;
	
	@Column(name="setting_key")
	private String settingKey;
	
	@Column(name="setting_value1")
	private String settingValue1;
	
	@Column(name="setting_value2")
	private String settingValue2;
	
	@Column(name="ex_varchar1")
	private String exVarchar1;
	
	@Column(name="ex_varchar2")
	private String exVarchar2;
	
	@Column(name="ex_varchar3")
	private String exVarchar3;
	
	@Column(name="del_status")
	private int delStatus;

	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

	public String getSettingValue1() {
		return settingValue1;
	}

	public void setSettingValue1(String settingValue1) {
		this.settingValue1 = settingValue1;
	}

	public String getSettingValue2() {
		return settingValue2;
	}

	public void setSettingValue2(String settingValue2) {
		this.settingValue2 = settingValue2;
	}

	public String getExVarchar1() {
		return exVarchar1;
	}

	public void setExVarchar1(String exVarchar1) {
		this.exVarchar1 = exVarchar1;
	}

	public String getExVarchar2() {
		return exVarchar2;
	}

	public void setExVarchar2(String exVarchar2) {
		this.exVarchar2 = exVarchar2;
	}

	public String getExVarchar3() {
		return exVarchar3;
	}

	public void setExVarchar3(String exVarchar3) {
		this.exVarchar3 = exVarchar3;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "NewSetting [settingId=" + settingId + ", settingKey=" + settingKey + ", settingValue1=" + settingValue1
				+ ", settingValue2=" + settingValue2 + ", exVarchar1=" + exVarchar1 + ", exVarchar2=" + exVarchar2
				+ ", exVarchar3=" + exVarchar3 + ", delStatus=" + delStatus + "]";
	}
	
	
	
}
