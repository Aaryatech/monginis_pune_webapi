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

import com.ats.webapi.model.AllMenus;
import com.ats.webapi.model.DispatchStationItem;
import com.ats.webapi.model.ErrorMessage;
import com.ats.webapi.model.FrList;
import com.ats.webapi.model.FranchiseForDispatch;
import com.ats.webapi.model.Info;
import com.ats.webapi.model.Item;
import com.ats.webapi.model.ItemListForDispatchReport;
import com.ats.webapi.model.SectionMaster;
import com.ats.webapi.model.StaionListWithFranchiseeList;
import com.ats.webapi.repository.DispatchReportRepositoryForItemwiseMin;
import com.ats.webapi.repository.FrListRepository;
import com.ats.webapi.repository.FranchiseForDispatchRepository;
import com.ats.webapi.repository.ItemListForDispatchReportRepository;
import com.ats.webapi.repository.ItemRepository;
import com.ats.webapi.repository.MainMenuConfigurationRepository;
import com.ats.webapi.repository.RouteMasterRepository;
import com.ats.webapi.repository.SectionMasterRepository;  

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
	
	@Autowired
	SectionMasterRepository sectionMasterRepository;
	
	@Autowired
	MainMenuConfigurationRepository mainMenuConfigurationRepository;
	
	@Autowired
	FranchiseForDispatchRepository franchiseForDispatchRepository;
	
	@Autowired
	DispatchReportRepositoryForItemwiseMin dispatchReportRepositoryForItemwiseMin;
	
	@RequestMapping(value = { "/saveSection" }, method = RequestMethod.POST)
	public @ResponseBody SectionMaster saveSection(@RequestBody SectionMaster sectionMaster) {

		
		SectionMaster save = new SectionMaster(); 
		 try {
			 
		 
			 save= sectionMasterRepository.save(sectionMaster);
				 
				
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return save;
	}
	
	@RequestMapping(value = { "/deleteSection" }, method = RequestMethod.POST)
	public @ResponseBody ErrorMessage deleteSection(@RequestParam("sectionId") int sectionId) {

		int isDeleted = sectionMasterRepository.deleteSection(sectionId);
		ErrorMessage infoRes = new ErrorMessage();
		if(isDeleted>=1)
		{
			infoRes.setError(false);
			infoRes.setMessage("deleteSection Deleted");
		}
		else
		{
			infoRes.setError(true);
			infoRes.setMessage("deleteSection Deletion Failed");
		}
		return infoRes;
	}
	
	@RequestMapping(value = { "/getSectionList" }, method = RequestMethod.GET)
	public @ResponseBody List<SectionMaster> getSectionList() {

		
		List<SectionMaster> sectionList = new ArrayList<>(); 
		 try {
			 
		 
			 sectionList= sectionMasterRepository.findByDelStatus(0);
				 
			 for(int i=0 ; i<sectionList.size() ; i++) {
				 
				 try {
					 
					 String[] menuIds = sectionList.get(i).getMenuIds().split(",");
					 List<AllMenus> menus=new ArrayList<AllMenus>();
					 menus=mainMenuConfigurationRepository.findByMenuIdIn(menuIds);
					 sectionList.get(i).setMenuList(menus);
					 
				 }catch(Exception e) {
					 
				 }
			 }
				
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return sectionList;
	}
	
	@RequestMapping(value = { "/getSectionListOnly" }, method = RequestMethod.GET)
	public @ResponseBody List<SectionMaster> getSectionListOnly() {

		
		List<SectionMaster> sectionList = new ArrayList<>(); 
		 try {
			 
		 
			 sectionList= sectionMasterRepository.findByDelStatus(0);
				  
				
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return sectionList;
	}
	
	@RequestMapping(value = { "/getSectionById" }, method = RequestMethod.POST)
	public @ResponseBody SectionMaster getSectionById(@RequestParam("sectionId") int sectionId) {

		
		SectionMaster getSectionById = new SectionMaster(); 
		 try {
			 
		 
			 getSectionById= sectionMasterRepository.findBySectionIdAndDelStatus(sectionId,0);
			 try {
				 
				 String[] menuIds = getSectionById.getMenuIds().split(",");
				 List<AllMenus> menus=mainMenuConfigurationRepository.findByMenuIdIn(menuIds);
				 getSectionById.setMenuList(menus);
				 
			 }catch(Exception e) {
				 
			 }
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
		return getSectionById;
	}
	
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
	@RequestMapping(value = { "/getAbcDepatchReportMin" }, method = RequestMethod.POST)
	@ResponseBody
	public List<StaionListWithFranchiseeList> getAbcDepatchReportMin(@RequestParam("date") String date,@RequestParam("abcType") List<Integer> abcTypeList,
			@RequestParam("stationNos") List<Integer> stationNos,@RequestParam("routId") int routId,@RequestParam("menuIds") List<Integer> menuIds) {

		List<StaionListWithFranchiseeList> stnList = new ArrayList<>();
		try {
			List<Integer> frList = new ArrayList<>();
			if(routId==0) {
				frList = frListRepository.findByAbcTypeMin(abcTypeList);
			}else {
				frList = frListRepository.findByAbcTypeMin(abcTypeList,routId);
			}
			List<DispatchStationItem> allItemList =dispatchReportRepositoryForItemwiseMin.getItemByFrIdAndDateMin(stationNos,date,frList,menuIds);
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
				for(int k=0 ; k<list.size();k++) {
					
				List<ItemListForDispatchReport> itemList =new ArrayList<>();
					
				for(int j=0 ; j<allItemList.size();j++) {
					
					
					if(allItemList.get(j).getFrId()==list.get(k).getFrId() && 	allItemList.get(j).getItemMrp2()==Integer.parseInt(""+stationNos.get(i)))
					{
						System.err.println("stationNos.get(i)"+stationNos.get(i));
						ItemListForDispatchReport dRport=new ItemListForDispatchReport();
						dRport.setId(allItemList.get(j).getId());
						dRport.setItemId(allItemList.get(j).getItemId());
						dRport.setItemMrp2((double)stationNos.get(i));
						dRport.setItemName(allItemList.get(j).getItemName());
						dRport.setOrderQty(allItemList.get(j).getOrderQty());
						dRport.setEditQty(allItemList.get(j).getOrderQty());
						itemList.add(dRport);
					}
				}
				list.get(k).setItemList(itemList);
				}
			/*	for(int j=0 ; j<list.size();j++) {
					
					System.err.println("index " + index);
					
					
					List<ItemListForDispatchReport> itemList = itemListForDispatchReportRepository.getItemByFrIdAndDate(list.get(j).getFrId(),date,index,stationNos.get(i),menuIds);
					list.get(j).setItemList(itemList);
					
					try {
						
						index=itemList.get(itemList.size()-1).getId();
						
					}catch (Exception e) {
						 
					}
				}*/
				staionListWithFranchiseeList.setItemCount(count);
				staionListWithFranchiseeList.setList(list);
				stnList.add(staionListWithFranchiseeList);
			}
			 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		return stnList;
	}
	@RequestMapping(value = { "/getFranchiseForDispatchRouteID" }, method = RequestMethod.POST)
	@ResponseBody
	public List<FranchiseForDispatch> getFranchiseForDispatchRouteID(@RequestParam("routeId") List<Integer> routeId) {

		List<FranchiseForDispatch> routeList = new ArrayList<>();
		try { 
			
			System.err.println(routeId);
			routeList=franchiseForDispatchRepository.getFranchiseForDispatchRouteID(routeId);
			 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		return routeList;
	}
	

}
