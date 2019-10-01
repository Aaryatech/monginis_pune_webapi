package com.ats.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.LogisSetting;

public interface LogisSettingRepo extends JpaRepository<LogisSetting, Integer>{

	LogisSetting findByKey(String key);

	@Transactional
	@Modifying
	@Query("UPDATE LogisSetting SET keyValue=:value  WHERE settingId = :id ")
	int updateKeyValue(@Param("id") int id,@Param("value") String value);
	
	

}
