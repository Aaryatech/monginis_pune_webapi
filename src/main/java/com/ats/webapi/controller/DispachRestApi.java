package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.FrList;
import com.ats.webapi.model.Item;
import com.ats.webapi.model.ItemListForDispatchReport;
import com.ats.webapi.model.StaionListWithFranchiseeList;
import com.ats.webapi.repository.FrListRepository;
import com.ats.webapi.repository.ItemListForDispatchReportRepository;
import com.ats.webapi.repository.ItemRepository;
import com.ats.webapi.repository.RouteMasterRepository;  

@RestController
public class DispachRestApi {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	RouteMasterRepository routeMasterRepository;
	
	@Autowired
	FrListRepository frListRepository;
	
	@Autowired
	ItemListForDispatchReportRepository itemListForDispatchReportRepository;
	
	@RequestMapping(value = { "/itemListGroupByStationNo" }, method = RequestMethod.GET)
	public @ResponseBody List<Double> itemListGroupByStationNo() {

		
		List<Double> items=new ArrayList<Double>(); 
		 try {
			 
		 
				items=itemRepository.itemListGroupByStationNo(0);
				 
				
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return items;
	}
	
	@RequestMapping(value = { "/getItemListForDispatchReport" }, method = RequestMethod.GET)
	public @ResponseBody List<Item> getItemListForDispatchReport() {

		
		List<Item> items=new ArrayList<Item>(); 
		 try {
			 
		 
				items=itemRepository.findByDelStatusOrderByItemGrp2AscItemSortIdAsc(0);
				 
				
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return items;
	}
	
	@RequestMapping(value = { "/showRouteListNewByabcType" }, method = RequestMethod.POST)
	@ResponseBody
	public List<RouteMaster> showRouteListNew(@RequestParam("abcType") int abcType) {

		List<RouteMaster> routeList = new ArrayList<>();
		try {
			
			if(abcType==0) {
				routeList=routeMasterRepository.findByDelStatusOrderByRouteNameAsc(0);
			}else {
				routeList=routeMasterRepository.findByDelStatusAndAbcTypeOrderByRouteNameAsc(0,abcType);
			}
			 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		return routeList;
	}
	
	
	@RequestMapping(value = { "/getAbcDepatchReport" }, method = RequestMethod.POST)
	@ResponseBody
	public List<StaionListWithFranchiseeList> getAbcDepatchReport(@RequestParam("date") String date,@RequestParam("abcType") List<Integer> abcTypeList,
			@RequestParam("stationNos") List<Integer> stationNos,@RequestParam("routId") int routId,@RequestParam("menuIds") List<Integer> menuIds) {

		List<StaionListWithFranchiseeList> stnList = new ArrayList<>();
		try {
			
			
			
			int index=0;
			int frIndex=0;
			for(int i = 0 ; i<stationNos.size() ; i++) {
				
				StaionListWithFranchiseeList staionListWithFranchiseeList = new StaionListWithFranchiseeList();
				staionListWithFranchiseeList.setStationNo(stationNos.get(i)); 
				int count = itemListForDispatchReportRepository.getcount(stationNos.get(i));
				
				List<FrList> list = new ArrayList<>();
				if(routId==0) {
					 list = frListRepository.findByAbcType(abcTypeList,frIndex);
				}else {
					 list = frListRepository.findByAbcType(abcTypeList,frIndex,routId);
				}
				
				
				try {
					
					frIndex=list.get(list.size()-1).getId();
					
				}catch (Exception e) {
					 
				}
				
				for(int j=0 ; j<list.size();j++) {
					
					System.err.println("index " + index);
					
					
					List<ItemListForDispatchReport> itemList = itemListForDispatchReportRepository.getItemByFrIdAndDate(list.get(j).getFrId(),date,index,stationNos.get(i),menuIds);
					list.get(j).setItemList(itemList);
					
					try {
						
						index=itemList.get(itemList.size()-1).getId();
						
					}catch (Exception e) {
						 
					}
				}
				staionListWithFranchiseeList.setItemCount(count);
				staionListWithFranchiseeList.setList(list);
				stnList.add(staionListWithFranchiseeList);
			}
			 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		return stnList;
	}
	

}
