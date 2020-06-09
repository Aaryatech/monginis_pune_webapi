package com.ats.webapi.repository.dailysales;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.webapi.model.dailysales.DailySalesReportModel;

public interface DailySalesReportModelRepo extends JpaRepository<DailySalesReportModel, Integer> {
	
	
	DailySalesReportModel findByDsrDate(String date);
	
	DailySalesReportModel findByDsrId(int Id);

}
