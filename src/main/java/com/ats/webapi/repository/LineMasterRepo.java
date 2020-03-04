package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.LineMaster;

public interface LineMasterRepo extends JpaRepository<LineMaster, Integer>{
	
	
	List<LineMaster> findAllByDelStatus(int delStatus);
	

}
