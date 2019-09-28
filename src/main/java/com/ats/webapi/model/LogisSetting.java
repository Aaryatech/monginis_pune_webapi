package com.ats.webapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_logis_setting")
public class LogisSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="setting_id")
	private int settingId;
	
	@Column(name="key")
	private String key;
	
	@Column(name="key_value")
	private String keyValue;

	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	@Override
	public String toString() {
		return "LogisSetting [settingId=" + settingId + ", key=" + key + ", keyValue=" + keyValue + "]";
	} 
	
	

}
