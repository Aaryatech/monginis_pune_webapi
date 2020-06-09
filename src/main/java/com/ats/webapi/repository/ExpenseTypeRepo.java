package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.bill.ExpenseType;

public interface ExpenseTypeRepo extends JpaRepository<ExpenseType, Integer> {
	
	List<ExpenseType> findAllByIsActiveAndDelStatus(int i, int j);

}
