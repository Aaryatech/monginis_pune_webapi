package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.reportv2.CrNoteRegItem;
import com.ats.webapi.model.reportv2.CrNoteRegSp;
import com.ats.webapi.model.reportv2.CrNoteRegisterList;
import com.ats.webapi.model.reportv2.GstRegisterItem;
import com.ats.webapi.model.reportv2.GstRegisterList;
import com.ats.webapi.model.reportv2.GstRegisterSp;
import com.ats.webapi.model.reportv2.HSNWiseReport;
import com.ats.webapi.model.reportv2.SalesReport;
import com.ats.webapi.repository.reportv2.CrNoteRegItemRepo;
import com.ats.webapi.repository.reportv2.CrNoteRegSpRepo;
import com.ats.webapi.repository.reportv2.GstRegisterItemRepo;
import com.ats.webapi.repository.reportv2.GstRegisterSpRepo;
import com.ats.webapi.repository.reportv2.HSNWiseReportRepo;
import com.ats.webapi.repository.reportv2.SalesReportRepo;

@RestController
public class ReportControllerV2 {

	@Autowired
	SalesReportRepo getSalesReportRepo;
	@Autowired
	GstRegisterItemRepo getGstRegisterItemRepo;

	@Autowired
	GstRegisterSpRepo getGstRegisterSpRepo;

	@Autowired
	CrNoteRegSpRepo getCrNoteRegSpRepo;
	@Autowired
	CrNoteRegItemRepo getCrNoteRegItemRepo;

	@Autowired
	HSNWiseReportRepo hSNWiseReportRepo;

	@RequestMapping(value = { "/getHsnBillReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnBillReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {
		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNWiseReportRepo.getReport(fromDate, toDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return saleList;
	}

	@RequestMapping(value = { "/getHsnReport" }, method = RequestMethod.POST)
	public @ResponseBody List<HSNWiseReport> getHsnReport(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		List<HSNWiseReport> saleList = new ArrayList<>();
		try {

			saleList = hSNWiseReportRepo.getReportHsn(fromDate, toDate);
			System.out.println(saleList.toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return saleList;
	}

	@RequestMapping(value = { "/getSalesReportV2" }, method = RequestMethod.POST)
	public @ResponseBody List<SalesReport> getSalesReportV2(@RequestParam("frIdList") List<String> frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		List<SalesReport> saleList = new ArrayList<>();

		if (frIdList.contains("-1")) {

			saleList = getSalesReportRepo.getSalesReportAllFr(fromDate, toDate);

		} else {

			saleList = getSalesReportRepo.getSalesReportSpecFr(fromDate, toDate, frIdList);
		}

		return saleList;
	}

	@RequestMapping(value = { "/getGstRegister" }, method = RequestMethod.POST)
	public @ResponseBody GstRegisterList getGstRegister(@RequestParam("frIdList") String frIdList,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

		GstRegisterList gstList = new GstRegisterList();

		if (frIdList.contains("-1")) {

			List<GstRegisterItem> saleList1 = new ArrayList<>();
			List<GstRegisterSp> saleList2 = new ArrayList<>();

			saleList1 = getGstRegisterItemRepo.getGstRegisterAllFrItem(fromDate, toDate);
			gstList.setGstRegItemList(saleList1);

			saleList2 = getGstRegisterSpRepo.getGstRegisterAllFrSp(fromDate, toDate);
			gstList.setGstRegSpList(saleList2);

		} else {

			List<GstRegisterItem> saleList1 = new ArrayList<>();
			List<GstRegisterSp> saleList2 = new ArrayList<>();

			saleList1 = getGstRegisterItemRepo.getGstRegisterSpecFrItem(fromDate, toDate, frIdList);
			gstList.setGstRegItemList(saleList1);

			saleList2 = getGstRegisterSpRepo.getGstRegisterSpecFrSp(fromDate, toDate, frIdList);
			gstList.setGstRegSpList(saleList2);

		}
		System.err.println("size Item  gstList " + gstList.getGstRegItemList().size());
		System.err.println("size Sp  gstList " + gstList.getGstRegSpList());

		return gstList;
	}

	@RequestMapping(value = { "/getCrNoteRegister" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegister(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList;

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItem(fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSp(fromDate, toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}

	@RequestMapping(value = { "/getCrNoteRegisterDone" }, method = RequestMethod.POST)
	public @ResponseBody CrNoteRegisterList getCrNoteRegisterDone(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {

		CrNoteRegisterList crNoteList = new CrNoteRegisterList();

		List<CrNoteRegItem> crNoteRegItemList;
		List<CrNoteRegSp> crNoteRegSpList;

		crNoteRegItemList = getCrNoteRegItemRepo.getCrNoteRegItemDone(fromDate, toDate);
		crNoteList.setCrNoteRegItemList(crNoteRegItemList);

		crNoteRegSpList = getCrNoteRegSpRepo.getCrNoteRegSpDone(fromDate, toDate);
		crNoteList.setCrNoteRegSpList(crNoteRegSpList);

		System.err.println("size Item  crNoteList " + crNoteList.getCrNoteRegItemList().size());
		System.err.println("size Sp  crNoteList " + crNoteList.getCrNoteRegSpList());

		return crNoteList;
	}
}
