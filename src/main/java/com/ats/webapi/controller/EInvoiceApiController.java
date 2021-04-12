package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.ErrorMessage;
import com.ats.webapi.model.FranchiseSup;
import com.ats.webapi.model.einv.EInvBillDetail;
import com.ats.webapi.model.einv.EInvBillDetailRepo;
import com.ats.webapi.model.einv.EInvBillHeader;
import com.ats.webapi.model.einv.EInvBillHeaderRepo;
import com.ats.webapi.repository.FranchiseSupRepository;

@RestController
public class EInvoiceApiController {

	@Autowired EInvBillHeaderRepo eInvBillHeader;
	
	@Autowired EInvBillDetailRepo eInvBillDetailRepo;
	
	
	@RequestMapping(value = { "/getBillListForEInvoice" }, method = RequestMethod.POST)
	public @ResponseBody List<EInvBillHeader> getBillListForEInvoice(
			@RequestParam("billIdList") List<String> billIdList) {
		System.err.println("In getBillListForEInvoice");
		List<EInvBillHeader> billHeadList = new ArrayList<>();
		try {

			billHeadList = eInvBillHeader.getBillHeaderforEInv(billIdList);

			for (int i = 0; i < billHeadList.size(); i++) {
				try {

					List<EInvBillDetail> billDetail = eInvBillDetailRepo
							.getEInvBillDetail(billHeadList.get(i).getBillNo());


					billHeadList.get(i).seteInvBillDetail(billDetail);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return billHeadList;
	}
	
	
	@RequestMapping(value = { "/updateIRNForEInvInBill" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateIRNForEInvInBill(@RequestParam int billNo,
			@RequestParam String irnAndAckNo) {
		//System.err.println("Hiiii");
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setError(true);
		try {
			int x = 0;
			x = eInvBillHeader.updatebillHeaderForEInv(irnAndAckNo, billNo);
			if (x > 0) {
				errorMessage = new ErrorMessage();
				errorMessage.setError(false);
				errorMessage.setMessage("success");
			}
			// errorMessage.set
		} catch (Exception e) {
			errorMessage = new ErrorMessage();
			errorMessage.setError(true);
			errorMessage.setMessage("exception " + e.getMessage());
			e.printStackTrace();
			
		}
		return errorMessage;
	}
	
	
	
	
	 @Autowired
	    FranchiseSupRepository franchiseSupRepository;
	
	@RequestMapping(value = { "/getfrSupByFrID" }, method = RequestMethod.POST)
	public @ResponseBody FranchiseSup getfrSupByFrID(@RequestParam("frId") int frId) {

		FranchiseSup getFranchiseSupRes = null;
		try {
			getFranchiseSupRes = franchiseSupRepository.findFranchiseSupByFrId(frId);

			if (getFranchiseSupRes != null) {
				getFranchiseSupRes.setError(false);
				getFranchiseSupRes.setMessage("FranchiseSup Found Successfully");
			} else {
				getFranchiseSupRes = new FranchiseSup();
				getFranchiseSupRes.setError(true);
				getFranchiseSupRes.setMessage("FranchiseSup Not Found");
			}
		} catch (Exception e) {
			getFranchiseSupRes = new FranchiseSup();
			getFranchiseSupRes.setError(true);
			getFranchiseSupRes.setMessage("FranchiseSup Not Found");
			System.out.println("Exception In getFranchiseSup:" + e.getMessage());
		}

		return getFranchiseSupRes;

	}
	
	
	/********* E Invoice of Credit note*////
	
	@RequestMapping(value = { "/getCredNoteListForEInvoice" }, method = RequestMethod.POST)
	public @ResponseBody List<EInvBillHeader> getCredNoteListForEInvoice(
			@RequestParam("crnIdList") List<String> crnIdList) {
		System.err.println("In getCredNoteListForEInvoice");
		List<EInvBillHeader> crnHeadList = new ArrayList<>();
		try {

			crnHeadList = eInvBillHeader.getCredNoteHeaderforEInv(crnIdList);

			for (int i = 0; i < crnHeadList.size(); i++) {
				try {

					List<EInvBillDetail> billDetail = eInvBillDetailRepo
							.getCredNoteDetailForEInvOfCN(crnHeadList.get(i).getBillNo());


					crnHeadList.get(i).seteInvBillDetail(billDetail);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return crnHeadList;
	}
	
	@RequestMapping(value = { "/updateIRNForEInvInCN" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage updateIRNForEInvInCN(@RequestParam int billNo,
			@RequestParam String irnAndAckNo) {
		//System.err.println("Hiiii");
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setError(true);
		try {
			int x = 0;
			x = eInvBillHeader.updateIRNForEInvInCN(irnAndAckNo, billNo);
			if (x > 0) {
				errorMessage = new ErrorMessage();
				errorMessage.setError(false);
				errorMessage.setMessage("success");
			}
			// errorMessage.set
		} catch (Exception e) {
			errorMessage = new ErrorMessage();
			errorMessage.setError(true);
			errorMessage.setMessage("exception " + e.getMessage());
			e.printStackTrace();
			
		}
		return errorMessage;
	}
	
	
	
}
