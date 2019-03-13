package com.ats.webapi.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.CrnDetailsSummary;
import com.ats.webapi.model.CrnHsnwiseExcelReport;
import com.ats.webapi.model.grngvn.GetCreditNoteHeaders;
import com.ats.webapi.model.grngvn.GetCreditNoteHeadersList;
import com.ats.webapi.model.grngvn.GetCreditNoteReport;
import com.ats.webapi.model.grngvn.GetCreditNoteReportList;
import com.ats.webapi.model.grngvn.GetCrnDetails;
import com.ats.webapi.model.grngvn.GetCrnDetailsList;
import com.ats.webapi.model.grngvn.PostCreditNoteDetails;
import com.ats.webapi.repository.CrnDetailSummaryRepository;
import com.ats.webapi.repository.CrnHsnwiseExcelReportRepository;
import com.ats.webapi.repository.getcreditnote.GetCreditNoteDetailRepo;
import com.ats.webapi.repository.getcreditnote.GetCreditNoteHeaderRepo;
import com.ats.webapi.repository.getcreditnote.GetCreditNoteReportRepo;

@RestController // 2 March
public class GetCreditNoteApi {

	@Autowired
	GetCreditNoteHeaderRepo getCreditNoteHeaderRepo;

	@Autowired
	GetCreditNoteDetailRepo creditNoteDetailRepo;
	
	@Autowired
	GetCreditNoteReportRepo getCreditNoteReportRepo;
	
	@Autowired
	CrnDetailSummaryRepository crnDetailSummaryRepository;
	//27/04
	@Autowired
	CrnHsnwiseExcelReportRepository crnHsnwiseExcelReportRepository;
	
	@RequestMapping(value = { "/getCreditNoteReport" }, method = RequestMethod.POST)
	public @ResponseBody GetCreditNoteReportList getCreditNoteReport( @RequestParam("crnIdList") List<String> crnIdList)

	{
		GetCreditNoteReportList crnReportListResponse = new GetCreditNoteReportList();
		List<GetCreditNoteReport>  headerList = new ArrayList<>();

		try {

			/*Date fDate = Common.convertToSqlDate(fromDate);
			Date tDate = Common.convertToSqlDate(toDate);

			if (frIdList.get(0).equalsIgnoreCase("0")) {
				System.out.println("In zero case ");
				headerList = getCreditNoteReportRepo.getCredNoteReportAllFr(fDate, tDate);
			} else {
				System.out.println("In other case ");
*/
				headerList = getCreditNoteReportRepo.getCredNoteReportSelFr(crnIdList);
			
			crnReportListResponse.setCreditNoteReport(headerList);
			System.err.println("Headers " + headerList);
		} catch (Exception e) {
			System.out.println("Exce In /GetCreditNoteApi getting cn /getCreditNoteReport " + e.getMessage());

			e.printStackTrace();
		}

		return crnReportListResponse;
	}
	
	
	

	@RequestMapping(value = { "/getCreditNoteHeaders" }, method = RequestMethod.POST)
	public @ResponseBody GetCreditNoteHeadersList getCreditNoteHeaders(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("frIdList") List<String> frIdList)

	{
		GetCreditNoteHeadersList headerResponse = new GetCreditNoteHeadersList();
		List<GetCreditNoteHeaders> headerList = new ArrayList<>();

		try {

			Date fDate = Common.convertToSqlDate(fromDate);
			Date tDate = Common.convertToSqlDate(toDate);

			if (frIdList.get(0).equalsIgnoreCase("0")) {
				System.out.println("In zero case ");
				headerList = getCreditNoteHeaderRepo.getCreditHeadersAllFr(fDate, tDate);
			} else {
				System.out.println("In other case ");

				headerList = getCreditNoteHeaderRepo.getCreditHeadersSelectedFr(fDate, tDate, frIdList);
			}
			headerResponse.setCreditNoteHeaders(headerList);
			System.err.println("Headers " + headerList);
		} catch (Exception e) {
			System.out.println("Exce In getting cn Headers " + e.getMessage());

			e.printStackTrace();
		}

		return headerResponse;
	}
	@RequestMapping(value = { "/getCrnDetailsByGrnGvnHeaderId" }, method = RequestMethod.POST)
	public @ResponseBody GetCrnDetailsList getCrnDetailsByGrnGvnHeaderId(@RequestParam("grnGvnHeaderId") int grnGvnHeaderId) {

		GetCrnDetailsList returnList = new GetCrnDetailsList();
		List<GetCrnDetails> details = new ArrayList<>();

		try {

			details = creditNoteDetailRepo.getCrnDetailsByGrnGvnHeaderId(grnGvnHeaderId);

			returnList.setCrnDetails(details);

			System.out.println("CREDIT NOTE DETAILS " + details);

		} catch (Exception e) {
			System.err.println("Exce in  Getting Credit note Details " + e.getMessage());

			e.printStackTrace();
		}

		return returnList;
	}
	@RequestMapping(value = { "/getCrnDetails" }, method = RequestMethod.POST)
	public @ResponseBody GetCrnDetailsList getCrnDetails(@RequestParam("crnId") List<String> crnId) {

		GetCrnDetailsList returnList = new GetCrnDetailsList();
		List<GetCrnDetails> details = new ArrayList<>();

		try {

			details = creditNoteDetailRepo.getCrnDetailsById(crnId);

			returnList.setCrnDetails(details);

			System.out.println("CREDIT NOTE DETAILS " + details);

		} catch (Exception e) {
			System.err.println("Exce in  Getting Credit note Details " + e.getMessage());

			e.printStackTrace();
		}

		return returnList;
	}
	@RequestMapping(value = { "/getCrnDetailsSummary" }, method = RequestMethod.POST)
	public @ResponseBody List<CrnDetailsSummary>  getCrnDetailsSummary(@RequestParam("crnId") List<String> crnId) {

		List<CrnDetailsSummary> details = new ArrayList<CrnDetailsSummary>();

		try {

			details = crnDetailSummaryRepository.getCrnDetailsSummaryById(crnId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return details;
	}
	//-------------------------------For Crn Note Frontend--------------------------------
	@RequestMapping(value = { "/getCrnHeadersByGrnGvnHeaderId" }, method = RequestMethod.POST)
	public @ResponseBody GetCreditNoteHeadersList getCrnHeadersByGrnGvnHeaderId(
			@RequestParam("grnGvnHeaderId") int grnGvnHeaderId)

	{
		GetCreditNoteHeadersList headerResponse = new GetCreditNoteHeadersList();
		List<GetCreditNoteHeaders> headerList = new ArrayList<>();

		try {

			headerList = getCreditNoteHeaderRepo.getCrnHeadersByGrnGvnHeaderId(grnGvnHeaderId);
			headerResponse.setCreditNoteHeaders(headerList);
		} catch (Exception e) {
			System.out.println("Exce In getting cn Headers " + e.getMessage());

			e.printStackTrace();
		}
		return headerResponse;
	}
	//----------------------------------------------------------------------------------
	@RequestMapping(value = { "/getCreditNoteHeadersByCrnIds" }, method = RequestMethod.POST)
	public @ResponseBody GetCreditNoteHeadersList getCreditNoteHeadersByCrnIds(
			@RequestParam("crnIdList") List<String> crnIdList)

	{
		GetCreditNoteHeadersList headerResponse = new GetCreditNoteHeadersList();
		List<GetCreditNoteHeaders> headerList = new ArrayList<>();

		try {

			headerList = getCreditNoteHeaderRepo.getCreditHeadersByHeaderIds(crnIdList);
			headerResponse.setCreditNoteHeaders(headerList);
		} catch (Exception e) {
			System.out.println("Exce In getting cn Headers " + e.getMessage());

			e.printStackTrace();
		}
		return headerResponse;
	}
	
	@RequestMapping(value = { "/getCrnHsnwiseExcelReport" }, method = RequestMethod.POST)
	public @ResponseBody List<CrnHsnwiseExcelReport> getCrnHsnwiseExcelReport(@RequestParam("crnIdList") List<String> crnIdList)

	{
		List<CrnHsnwiseExcelReport> report = new ArrayList<CrnHsnwiseExcelReport>();

		try {

			report = crnHsnwiseExcelReportRepository.getCrnHsnwiseExcelReport(crnIdList);
		} catch (Exception e) {
			System.out.println("Exce In getting crn Report " + e.getMessage());

			e.printStackTrace();
		}
		return report;
	}
	
}
