package com.ats.webapi.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.GetCurrentStockDetails;
import com.ats.webapi.model.OpsDSRModel;
import com.ats.webapi.model.dailysales.DailySalesRegular;
import com.ats.webapi.model.dailysales.DailySalesReportDao;
import com.ats.webapi.model.dailysales.SpDailySales;
import com.ats.webapi.repository.dailysales.DailySalesRegularReportRepository;
import com.ats.webapi.repository.dailysales.DailySpSalesRepository;

@RestController
public class DailySalesReportController {

	@Autowired
	DailySalesRegularReportRepository dailySalesRegularReportRepo;

	@Autowired
	DailySpSalesRepository dailySpSalesRepository;

	@RequestMapping(value = "/getDailySalesData", method = RequestMethod.POST)
	public @ResponseBody DailySalesReportDao getDailySalesData(@RequestParam("frId") int frId,
			@RequestParam("date") String date, @RequestParam("currentMonth") int currentMonth,
			@RequestParam("year") int year) {

		System.err.println("frId" + frId + "date" + date + "currentMonth" + currentMonth + "year" + year);
		DailySalesReportDao dailySalesReportDaoList = new DailySalesReportDao();
		List<SpDailySales> spDailySalesList = new ArrayList<>();
		List<DailySalesRegular> dailySalesReport = dailySalesRegularReportRepo.getDailySalesData(frId, date,
				currentMonth, year);
		DailySalesRegular dailySalesOtherReport = dailySalesRegularReportRepo.getDailySalesOtherData(frId, date,
				currentMonth, year);
		List<SpDailySales> spDailySList = dailySpSalesRepository.getSpSalesSpOrder(frId, date);

		dailySalesReport.add(dailySalesOtherReport);
		dailySalesReportDaoList.setSpDailySalesList(spDailySList);
		dailySalesReportDaoList.setDailySalesRegularList(dailySalesReport);
		return dailySalesReportDaoList;

	}
	
	
	@RequestMapping(value = "/getDailySalesDataPrint", method = RequestMethod.POST)
	public @ResponseBody OpsDSRModel getDailySalesDataPrint(@RequestParam("frId") int frId,@RequestParam("date") String date) {

		System.err.println("frId" + frId );
		OpsDSRModel res = new OpsDSRModel();

		float amt=dailySalesRegularReportRepo.getOpeningAmtForDSR(frId,date);
		System.err.println("OPENING AMT - "+amt);
		
		res.setOpeningAmt(amt);
		
		float purchase=dailySalesRegularReportRepo.getPurchaseAmtForDSR(frId,date);
		System.err.println("PURCHASE AMT - "+purchase);
		
		res.setPurchase(purchase);
		
		float grngvn=dailySalesRegularReportRepo.getGrnGvnAmtForDSR(frId,date);
		System.err.println("GRNGVN AMT - "+grngvn);
		
		res.setGrnGvn(grngvn);
		
		float sale=dailySalesRegularReportRepo.getSaleAmtForDSR(frId,date);
		System.err.println("SALE AMT - "+sale);
		
		res.setSale(sale);
		
		return res;

	}

}
