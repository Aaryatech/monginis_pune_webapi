package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.commons.Common;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.LineMaster;
import com.ats.webapi.model.prod.GetProdVariation;
import com.ats.webapi.repository.ItemRepository;
import com.ats.webapi.repository.LineMasterRepo;
import com.ats.webapi.repository.prod.GetProdHeaderRepo;
import com.ats.webapi.repository.prod.GetProdVariationRepo;

@RestController
public class NewProdApiController {

	@Autowired 
	GetProdVariationRepo getProdVariationRepo;
	@Autowired LineMasterRepo lineMasterRepo;
	
	@RequestMapping(value = { "/getLineMasterList" }, method = RequestMethod.GET)
	public @ResponseBody List<LineMaster> getLineMasterList() {
		List<LineMaster> lineMstList = new ArrayList<LineMaster>();
		try {
			lineMstList=lineMasterRepo.findAllByDelStatus(0);
		}catch (Exception e) {
			lineMstList = new ArrayList<LineMaster>();
			e.printStackTrace();
		}
		
		return lineMstList;
	}
	
	@RequestMapping(value = { "/getProdVariation" }, method = RequestMethod.POST)
	public @ResponseBody List<GetProdVariation> getProdVariation(@RequestParam("date") String date,
			 @RequestParam("menuId") String menuId,@RequestParam("selectedMenu") List<String> selectedMenu) {
System.err.println("selectedMenu Rec "+selectedMenu);
		List<GetProdVariation> stockList = null;
		try {
			date = Common.convertToYMD(date);
			stockList=getProdVariationRepo.getGetProdVariation(date, menuId,selectedMenu);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return stockList;
	}
	
	@Autowired  GetProdHeaderRepo getProdHeaderRepo;
	
	
	@RequestMapping(value = { "/getCountOfProdPlanForCatId" }, method = RequestMethod.POST)
	public @ResponseBody Object getCountOfProdPlanForCatId(@RequestParam("catId") int catId,
			 @RequestParam("status") int status, @RequestParam("date") String date, @RequestParam("isPlanned") int isPlanned) {
		Integer result=0;
		try {
			date = Common.convertToYMD(date);
			result=getProdHeaderRepo.getCountOfProdPlanForCatId(catId, status,date,isPlanned);
		}catch (Exception e) {
			result=0;
			e.printStackTrace();
		}
		return result;
	}
	@Autowired
	ItemRepository itemRepository;
	
	@RequestMapping(value = { "/updateItemLineId" }, method = RequestMethod.POST)
	public @ResponseBody Info updateItemLineId(@RequestParam("items") List<Integer> items,@RequestParam("lineId") String lineId) {
		Info info=null;
		try {
		if(!items.isEmpty()) {				
		     int isUpdate=itemRepository.updateItemLineId(items,lineId);
		     info=new Info();
		     if(isUpdate>0) {
		    	 info.setError(false);
		    	 info.setMessage("success");
		     }else {
		    	 info.setError(true);
		    	 info.setMessage("failed");
		     }
		}
		}
		catch (Exception e) {
			info=new Info();
			info.setError(true);
			info.setMessage("exception");
			e.printStackTrace();
		}
		
		return info;
	}
}
