package com.ats.webapi.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.GetCurrentStockDetails;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.ItemSup;
import com.ats.webapi.model.OpsDSRModel;
import com.ats.webapi.model.PostBillHeader;
import com.ats.webapi.model.bill.Expense;
import com.ats.webapi.model.dailysales.DailySalesRegular;
import com.ats.webapi.model.dailysales.DailySalesReportDao;
import com.ats.webapi.model.dailysales.DailySalesReportModel;
import com.ats.webapi.model.dailysales.SpDailySales;
import com.ats.webapi.repository.CustListFromBillRepo;
import com.ats.webapi.repository.ExpenseRepo;
import com.ats.webapi.repository.PostBillHeaderRepository;
import com.ats.webapi.repository.dailysales.DailySalesRegularReportRepository;
import com.ats.webapi.repository.dailysales.DailySalesReportModelRepo;
import com.ats.webapi.repository.dailysales.DailySpSalesRepository;

@RestController
public class DailySalesReportController {

	@Autowired
	DailySalesRegularReportRepository dailySalesRegularReportRepo;

	@Autowired
	DailySpSalesRepository dailySpSalesRepository;

	@Autowired
	DailySalesReportModelRepo dailySalesReportModelRepo;

	@Autowired
	PostBillHeaderRepository postBillHeaderRepository;

	@Autowired
	ExpenseRepo expenseRepo;

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
	public @ResponseBody OpsDSRModel getDailySalesDataPrint(@RequestParam("frId") int frId,
			@RequestParam("date") String date) {

		System.err.println("frId" + frId);
		OpsDSRModel res = new OpsDSRModel();

		float amt = dailySalesRegularReportRepo.getOpeningAmtForDSR(frId, date);
		System.err.println("OPENING AMT - " + amt);

		res.setOpeningAmt(amt);

		float purchase = dailySalesRegularReportRepo.getPurchaseAmtForDSR(frId, date);
		System.err.println("PURCHASE AMT - " + purchase);

		res.setPurchase(purchase);

		float grngvn = dailySalesRegularReportRepo.getGrnGvnAmtForDSR(frId, date);
		System.err.println("GRNGVN AMT - " + grngvn);

		res.setGrnGvn(grngvn);

		float sale = dailySalesRegularReportRepo.getSaleAmtForDSR(frId, date);
		System.err.println("SALE AMT - " + sale);

		res.setSale(sale);

		return res;

	}

	@RequestMapping(value = { "/saveDSR" }, method = RequestMethod.POST)
	public @ResponseBody DailySalesReportModel saveDSR(@RequestBody DailySalesReportModel dsr) {

		DailySalesReportModel res = null;
		try {
			res = dailySalesReportModelRepo.save(dsr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = { "/getDSRByDate" }, method = RequestMethod.POST)
	public @ResponseBody DailySalesReportModel getDSRByDate(@RequestParam("date") String date) {

		DailySalesReportModel res = null;
		try {
			res = dailySalesReportModelRepo.findByDsrDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = { "/getPurBillIds" }, method = RequestMethod.POST)
	public @ResponseBody String getPurBillIds(@RequestParam("frId") int frId, @RequestParam("date") String date) {

		String res = null;
		try {
			res = dailySalesRegularReportRepo.getPurchaseBillIds(frId, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = { "/getExpenseIds" }, method = RequestMethod.POST)
	public @ResponseBody String getExpenseIds(@RequestParam("frId") int frId, @RequestParam("date") String date) {

		String res = null;
		try {
			res = dailySalesRegularReportRepo.getExpenseIds(frId, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/getBillListByIds", method = RequestMethod.POST)
	public @ResponseBody List<PostBillHeader> getBillListByIds(@RequestParam("ids") List<Integer> ids) {
		System.out.println("inside rest");

		List<PostBillHeader> billDetailsList = null;

		billDetailsList = postBillHeaderRepository.getBillListByIds(ids);
		System.err.println("OUT - "+billDetailsList);

		return billDetailsList;

	}

	@RequestMapping(value = "/getExpenseListByIds", method = RequestMethod.POST)
	public @ResponseBody List<Expense> getExpenseListByIds(@RequestParam("ids") List<Integer> ids) {
		System.out.println("inside rest");

		List<Expense> res = null;

		res = expenseRepo.getExpenseListByIds(ids);

		return res;

	}

}
