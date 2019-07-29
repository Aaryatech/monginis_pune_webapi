package com.ats.webapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.salesreport3.FrAndSubCatBillData;
import com.ats.webapi.model.salesreport3.FrAndSubCatGrnGvnData;
import com.ats.webapi.model.salesreport3.FrWiseSubCat;
import com.ats.webapi.model.salesreport3.SubCatWiseBillData;
import com.ats.webapi.model.salesreport3.TempFrWiseSubCat;
import com.ats.webapi.model.salesreport3.TempSubCatWiseBillData;
import com.ats.webapi.model.salesreport3.YearlyFrSubCatData;
import com.ats.webapi.repository.salesreport3.FrAndSubCatBillDataRepo;
import com.ats.webapi.repository.salesreport3.FrAndSubCatGrnGvnDataRepo;

@RestController
public class SalesReportApiController3 {
	
	@Autowired
	FrAndSubCatBillDataRepo frAndSubCatBillDataRepo;

	@Autowired
	FrAndSubCatGrnGvnDataRepo frAndSubCatGrnGvnDataRepo;
	
	// ----------YEARLY REPORT--------------

	@RequestMapping(value = { "/getYearlyFrSubCatSaleReport" }, method = RequestMethod.POST)
	public @ResponseBody List<YearlyFrSubCatData> getYearlyFrSubCatSaleReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("subCatIdList") List<Integer> subCatIdList,
			@RequestParam("frIdList") List<Integer> frIdList) {

		System.err.println("PARAMETER ---------------- FROM DATE : " + fromDate + "        TO DATE : " + toDate
				+ "     SC : " + subCatIdList + "          FR : " + frIdList);

		List<YearlyFrSubCatData> reportList = new ArrayList<>();
		List<FrWiseSubCat> frWiseSubCatList = new ArrayList<>();

		try {

			List<FrAndSubCatBillData> soldList = frAndSubCatBillDataRepo.getBillData(fromDate, toDate, frIdList,
					subCatIdList);
			List<FrAndSubCatGrnGvnData> varList = frAndSubCatGrnGvnDataRepo.getVariationData(fromDate, toDate, frIdList,
					subCatIdList);
			List<FrAndSubCatGrnGvnData> retList = frAndSubCatGrnGvnDataRepo.getReturnData(fromDate, toDate, frIdList,
					subCatIdList);

			System.err.println("SOLD -- " + soldList);
			System.err.println("VAR -- " + varList);
			System.err.println("RET -- " + retList);

			ArrayList<Integer> frIds = new ArrayList<>();
			ArrayList<String> frNames = new ArrayList<>();

			if (soldList != null) {
				for (int i = 0; i < soldList.size(); i++) {
					if (!frIds.contains(soldList.get(i).getFrId())) {
						frIds.add(soldList.get(i).getFrId());
						frNames.add(soldList.get(i).getFrName());
					}
				}
			}

			if (varList != null) {
				for (int i = 0; i < varList.size(); i++) {
					if (!frIds.contains(varList.get(i).getFrId())) {
						frIds.add(varList.get(i).getFrId());
						frNames.add(varList.get(i).getFrName());
					}
				}
			}

			if (retList != null) {
				for (int i = 0; i < retList.size(); i++) {
					if (!frIds.contains(retList.get(i).getFrId())) {
						frIds.add(retList.get(i).getFrId());
						frNames.add(retList.get(i).getFrName());
					}
				}
			}

			System.err.println("FR IDS ----------------------------------- " + frIds);
			System.err.println("FR NAMES ----------------------------------- " + frNames);

			for (int i = 0; i < frIds.size(); i++) {

				FrWiseSubCat frWiseList = new FrWiseSubCat();

				frWiseList.setFrId(frIds.get(i));
				frWiseList.setFrName(frNames.get(i));

				ArrayList<SubCatWiseBillData> subCatBillList = new ArrayList<>();
				ArrayList<Integer> tempSubCatIdList = new ArrayList<>();

				for (int j = 0; j < soldList.size(); j++) {

					if (frIds.get(i) == soldList.get(j).getFrId()) {

						if (!tempSubCatIdList.contains(soldList.get(j).getSubCatId())) {

							tempSubCatIdList.add(soldList.get(j).getSubCatId());

							SubCatWiseBillData bill = new SubCatWiseBillData();

							bill.setSubCatId(soldList.get(j).getSubCatId());
							bill.setSubCatName(soldList.get(j).getSubCatName());

							subCatBillList.add(bill);
						}

					}

				}

				for (int j = 0; j < varList.size(); j++) {

					if (frIds.get(i) == varList.get(j).getFrId()) {

						if (!tempSubCatIdList.contains(varList.get(j).getSubCatId())) {

							tempSubCatIdList.add(varList.get(j).getSubCatId());

							SubCatWiseBillData bill = new SubCatWiseBillData();

							bill.setSubCatId(varList.get(j).getSubCatId());
							bill.setSubCatName(varList.get(j).getSubCatName());

							subCatBillList.add(bill);
						}

					}

				}

				for (int j = 0; j < retList.size(); j++) {

					if (frIds.get(i) == retList.get(j).getFrId()) {

						if (!tempSubCatIdList.contains(retList.get(j).getSubCatId())) {

							tempSubCatIdList.add(retList.get(j).getSubCatId());

							SubCatWiseBillData bill = new SubCatWiseBillData();

							bill.setSubCatId(retList.get(j).getSubCatId());
							bill.setSubCatName(retList.get(j).getSubCatName());

							subCatBillList.add(bill);
						}

					}

				}

				frWiseList.setSubCatList(subCatBillList);

				frWiseSubCatList.add(frWiseList);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar calFrom = Calendar.getInstance();
			Calendar calTo = Calendar.getInstance();

			Date d1 = sdf.parse(fromDate);
			Date d2 = sdf.parse(toDate);

			calFrom.setTime(d1);
			calTo.setTime(d2);

			while (calFrom.before(calTo)) {

				System.err.println(
						"--------------------------------------------NEW-----------------------------------------------");

				SimpleDateFormat sdfMonthYear = new SimpleDateFormat("MMM-yyyy");

				int month = calFrom.get(Calendar.MONTH) + 1;
				int year = calFrom.get(Calendar.YEAR);

				YearlyFrSubCatData data = new YearlyFrSubCatData();
				data.setMonthId(month);
				data.setYearId("" + year);
				data.setDateStr(sdfMonthYear.format(calFrom.getTime()));

				List<TempFrWiseSubCat> tempFrWiseSubCatList = new ArrayList<>();

				if (frWiseSubCatList != null) {
					for (int i = 0; i < frWiseSubCatList.size(); i++) {
						TempFrWiseSubCat fr = new TempFrWiseSubCat();
						fr.setFrId(frWiseSubCatList.get(i).getFrId());
						fr.setFrName(frWiseSubCatList.get(i).getFrName());

						List<TempSubCatWiseBillData> tempScList = new ArrayList<>();
						if (frWiseSubCatList.get(i).getSubCatList() != null) {

							for (int j = 0; j < frWiseSubCatList.get(i).getSubCatList().size(); j++) {
								TempSubCatWiseBillData scData = new TempSubCatWiseBillData();
								scData.setSubCatId(frWiseSubCatList.get(i).getSubCatList().get(j).getSubCatId());
								scData.setSubCatName(frWiseSubCatList.get(i).getSubCatList().get(j).getSubCatName());
								tempScList.add(scData);
							}
						}
						fr.setSubCatList(tempScList);
						tempFrWiseSubCatList.add(fr);
					}
				}

				data.setDataList(tempFrWiseSubCatList);

				System.err.println("MONTH - " + month);
				System.err.println("NEW DATA - " + tempFrWiseSubCatList);


				if (soldList != null) {
					for (int i = 0; i < soldList.size(); i++) {

						if (soldList.get(i).getMonth() == month
								&& soldList.get(i).getYear().equalsIgnoreCase(String.valueOf(year))) {

							System.err.println("---MATCHED ---------- " + soldList.get(i).getMonth() + " - "
									+ soldList.get(i).getYear());

							if (tempFrWiseSubCatList != null) {

								for (int j = 0; j < tempFrWiseSubCatList.size(); j++) {

									TempFrWiseSubCat frData = tempFrWiseSubCatList.get(j);

									if (frData.getFrId() == soldList.get(i).getFrId()) {

										System.err.println("---FR MATCHED ---------- " + soldList.get(i).getFrId());

										for (int k = 0; k < frData.getSubCatList().size(); k++) {

											TempSubCatWiseBillData billData = frData.getSubCatList().get(k);

											if (soldList.get(i).getSubCatId() == billData.getSubCatId()) {

												System.err.println("---SUB CAT MATCHED ---------- "
														+ soldList.get(i).getSubCatId());

												billData.setSoldQty(soldList.get(i).getSoldQty());
												billData.setSoldAmt(soldList.get(i).getSoldAmt());
												billData.setTaxableAmt(soldList.get(i).getTaxableAmt());
												billData.setTaxAmt(soldList.get(i).getTaxAmt());

												break;
											}
										}
									}

								}

							}


						}

					}
				}

				if (varList != null) {
					for (int i = 0; i < varList.size(); i++) {

						if (varList.get(i).getMonth() == month
								&& varList.get(i).getYear().equalsIgnoreCase(String.valueOf(year))) {


							if (tempFrWiseSubCatList != null) {

								for (int j = 0; j < tempFrWiseSubCatList.size(); j++) {

									TempFrWiseSubCat frData = tempFrWiseSubCatList.get(j);

									if (frData.getFrId() == varList.get(i).getFrId()) {

										System.err.println("---FR MATCHED ---------- " + varList.get(i).getFrId());

										for (int k = 0; k < frData.getSubCatList().size(); k++) {

											TempSubCatWiseBillData billData = frData.getSubCatList().get(k);

											if (varList.get(i).getSubCatId() == billData.getSubCatId()) {

												System.err.println("---SUB CAT MATCHED ---------- "
														+ varList.get(i).getSubCatId());

												billData.setVarQty(varList.get(i).getQty());
												billData.setVarAmt(varList.get(i).getAmt());
												billData.setVarTaxableAmt(varList.get(i).getTaxableAmt());
												billData.setVarTaxAmt(varList.get(i).getTaxAmt());

												break;
											}
										}
									}

								}

							}

						}

					}
				}

				if (retList != null) {


					for (int i = 0; i < retList.size(); i++) {

						if (retList.get(i).getMonth() == month
								&& retList.get(i).getYear().equalsIgnoreCase(String.valueOf(year))) {


							if (tempFrWiseSubCatList != null) {

								for (int j = 0; j < tempFrWiseSubCatList.size(); j++) {

									TempFrWiseSubCat frData = tempFrWiseSubCatList.get(j);

									if (frData.getFrId() == retList.get(i).getFrId()) {

										System.err.println("---FR MATCHED ---------- " + retList.get(i).getFrId());

										for (int k = 0; k < frData.getSubCatList().size(); k++) {

											TempSubCatWiseBillData billData = frData.getSubCatList().get(k);

											if (retList.get(i).getSubCatId() == billData.getSubCatId()) {

												System.err.println("---SUB CAT MATCHED ---------- "
														+ retList.get(i).getSubCatId());

												billData.setRetQty(retList.get(i).getQty());
												billData.setRetAmt(retList.get(i).getAmt());
												billData.setRetTaxableAmt(retList.get(i).getTaxableAmt());
												billData.setRetTaxAmt(retList.get(i).getTaxAmt());

												break;
											}
										}
									}

								}

							}

						}

					}
				}

				reportList.add(data);

				calFrom.add(Calendar.MONTH, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reportList;
	}

}
