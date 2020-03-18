package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.pettycash.OtherBillDetailAdv;
import com.ats.webapi.model.pettycash.PettyCashDao;
import com.ats.webapi.model.pettycash.PettyCashManagmt;
import com.ats.webapi.model.pettycash.SellBillAmtModel;
import com.ats.webapi.model.pettycash.SellBillDetailAdv;
import com.ats.webapi.model.pettycash.SpCakeAdv;
import com.ats.webapi.model.pettycash.SpCakeAmtModel;
import com.ats.webapi.repository.OtherBillDetailAdvRepo;
import com.ats.webapi.repository.PettyCashManagmtRepo;
import com.ats.webapi.repository.SellBillAmtModelRepo;
import com.ats.webapi.repository.SellBillDetailAdvRepo;
import com.ats.webapi.repository.SpCakeAdvRepo;
import com.ats.webapi.repository.SpCakeAmtModelRepo;

@RestController
public class PettyCashApiController {

	@Autowired
	PettyCashManagmtRepo pettyRepo;

	@Autowired
	SpCakeAdvRepo spRepo;

	@Autowired
	SellBillDetailAdvRepo sellBillRepo;

	@Autowired
	OtherBillDetailAdvRepo otherBillRepo;

	@Autowired
	SellBillAmtModelRepo sellBillAmtModelRepo;

	@Autowired
	SpCakeAmtModelRepo spCakeAmtModelRepo;

	@RequestMapping(value = { "/getPettyCashDetails" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashDetails(@RequestParam("frId") int frId) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByFrIdAndStatusLimit1(frId, 0);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}

	@RequestMapping(value = { "/getPettyCashList" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatus(frId, 0);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashList : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}

	@RequestMapping(value = { "/getPettyCashAmts" }, method = RequestMethod.POST)
	public PettyCashDao getPettyCashAmts(@RequestParam("frId") int frId, @RequestParam("date") String date) {
		PettyCashDao pettyDao = new PettyCashDao();
		try {
			List<SpCakeAdv> spCake = spRepo.getSpCakeAdv(frId, date);
			SellBillDetailAdv sellBillAdv = sellBillRepo.getSellBillDetailAdv(frId, date);
			OtherBillDetailAdv otherBill = otherBillRepo.getOtherBillDetailAdv(frId, date);

			pettyDao.setSpCakAdv(spCake);
			pettyDao.setSellBillAdv(sellBillAdv);
			pettyDao.setOtherBillAdv(otherBill);

			System.out.println("Date----------------" + pettyDao.toString());
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyDao;
	}

	@RequestMapping(value = { "/addPettyCash" }, method = RequestMethod.POST)
	public PettyCashManagmt addPettyCash(@RequestBody PettyCashManagmt pettycash) {
		PettyCashManagmt cash = new PettyCashManagmt();
		try {
			cash = pettyRepo.save(pettycash);
		} catch (Exception e) {
			System.err.println("Exception in addPettyCash : " + e.getMessage());
			e.printStackTrace();
		}
		return cash;
	}

	@RequestMapping(value = { "/getPettyCashById" }, method = RequestMethod.POST)
	public PettyCashManagmt getPettyCashById(@RequestParam("id") int id) {
		PettyCashManagmt petty = new PettyCashManagmt();
		try {
			petty = pettyRepo.findByPettycashId(id);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return petty;
	}

	@RequestMapping(value = { "/getPettyCashListDateWise" }, method = RequestMethod.POST)
	public List<PettyCashManagmt> getPettyCashList(@RequestParam("frId") int frId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<PettyCashManagmt> pettyList = new ArrayList<>();
		try {
			pettyList = pettyRepo.findByFrIdAndStatusDateWise(frId, 0, fromDate, toDate);
		} catch (Exception e) {
			System.err.println("Exception in getPettyCashListDateWise : " + e.getMessage());
			e.printStackTrace();
		}
		return pettyList;
	}

	// Anmol
	@RequestMapping(value = { "/getSellBillAmtForPettyCash" }, method = RequestMethod.POST)
	public List<SellBillAmtModel> getSellBillAmtForPettyCash(@RequestParam("frId") int frId,
			@RequestParam("date") String date) {
		List<SellBillAmtModel> amtList = new ArrayList<>();
		try {
			amtList = sellBillAmtModelRepo.getSellBillAmtForPettyCash(frId, date);
		} catch (Exception e) {
			System.err.println("Exception in getSellBillAmtForPettyCash : " + e.getMessage());
			e.printStackTrace();
		}
		return amtList;
	}

	/*
	 * // Anmol
	 * 
	 * @RequestMapping(value = { "/getSpCakeAmtForPettyCash" }, method =
	 * RequestMethod.POST) public SpCakeAmtModel
	 * getSpCakeAmtForPettyCash(@RequestParam("frId") int frId,
	 * 
	 * @RequestParam("date") String date) { SpCakeAmtModel amt = new
	 * SpCakeAmtModel(); try { amt =
	 * spCakeAmtModelRepo.getSpCakeAmtForPettyCash(frId, date); } catch (Exception
	 * e) { System.err.println("Exception in getSpCakeAmtForPettyCash : " +
	 * e.getMessage()); e.printStackTrace(); } return amt; }
	 */

}
