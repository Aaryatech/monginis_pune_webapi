package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.LogisSetting;

public interface LogisSettingRepo extends JpaRepository<LogisSetting, Integer>{

	LogisSetting findByKey(String key);
	
	

}
