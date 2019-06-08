package com.ats.webapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.route.GetRouteMgmt;
import com.ats.webapi.model.route.GetRouteMgmtRepo;
import com.ats.webapi.model.route.RouteMgmt;
import com.ats.webapi.model.route.RouteMgmtRepo;
import com.ats.webapi.model.tally.Franchisee;
import com.ats.webapi.repository.tally.TallyFranchiseeRepository;

@RestController
public class RouteMgmtApiController {

	@Autowired
	RouteMgmtRepo routeMgmtRepo;

	@Autowired
	GetRouteMgmtRepo getRouteMgmtRepo;

	@Autowired
	TallyFranchiseeRepository tallyFranchiseeRepository;

	// --------------------------------------Route Mgmt-------------------------

	@RequestMapping(value = { "/saveRouteManagement" }, method = RequestMethod.POST)
	public @ResponseBody RouteMgmt saveRouteManagement(@RequestBody RouteMgmt routeMgmt) {

		RouteMgmt res = new RouteMgmt();

		try {

			res = routeMgmtRepo.saveAndFlush(routeMgmt);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return res;

	}

	@RequestMapping(value = { "/getAllRouteMgmtList" }, method = RequestMethod.GET)
	public @ResponseBody List<RouteMgmt> getAllRouteMgmtList() {

		List<RouteMgmt> routeList = new ArrayList<RouteMgmt>();

		try {

			routeList = routeMgmtRepo.findByDelStatusAndIsActive(0, 1);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return routeList;

	}

	@RequestMapping(value = { "/getRouteByRouteDetailId" }, method = RequestMethod.POST)
	public @ResponseBody RouteMgmt getRouteByRouteDetailId(@RequestParam("routeTrayId") int routeTrayId) {
		System.out.println("routeTrayIdrouteTrayId" + routeTrayId);

		RouteMgmt routeMgmt = new RouteMgmt();

		try {
			routeMgmt = routeMgmtRepo.findByRouteTrayIdAndDelStatus(routeTrayId, 0);
			System.out.println("routeMgmt" + routeMgmt.toString());

		} catch (Exception e) {

			e.printStackTrace();

		}
		return routeMgmt;

	}

	@RequestMapping(value = { "/deleteRouteMgmt" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteRouteMgmt(@RequestParam("routeTrayId") int routeTrayId) {

		Info info = new Info();

		try {
			int delete = routeMgmtRepo.deleteRouteMgmt(routeTrayId);

			if (delete == 1) {
				info.setError(false);
				info.setMessage("successfully Deleted");
			} else {
				info.setError(true);
				info.setMessage(" Deleted to Delete");
			}

		} catch (Exception e) {

			e.printStackTrace();
			info.setError(true);
			info.setMessage(" Deleted to Delete");

		}
		return info;

	}

	@RequestMapping(value = { "/getRouteMgmtList" }, method = RequestMethod.GET)
	public @ResponseBody List<GetRouteMgmt> getRouteMgmtList() {

		List<GetRouteMgmt> list = new ArrayList<GetRouteMgmt>();
		try {

			list = getRouteMgmtRepo.getAllRouteMgmt();

			for (int i = 0; i < list.size(); i++) {

				List<Integer> frIdList = Stream.of(list.get(i).getFrIds().split(",")).map(Integer::parseInt)
						.collect(Collectors.toList());
				List<Franchisee> franchiseeList = tallyFranchiseeRepository.findByIsTallySync();

				franchiseeList = tallyFranchiseeRepository.getFranchisee(frIdList);

				String frName = "";
				int x = 0;
				for (int j = 0; j < franchiseeList.size(); j++) {

					frName = franchiseeList.get(j).getCustomerName() + "," + frName;
					if (franchiseeList.size() > 1)
						x = 1;

				}
				if (x == 1)
					list.get(i).setFrName(frName.substring(0, frName.length() - 1));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;

	}

	@RequestMapping(value = { "/getRouteMgmtByRouteTrayId" }, method = RequestMethod.POST)
	public @ResponseBody GetRouteMgmt getRouteMgmtByRouteTrayId(@RequestParam("routeTrayId") int routeTrayId) {

		GetRouteMgmt getRouteMgmt = new GetRouteMgmt();
		try {

			getRouteMgmt = getRouteMgmtRepo.getAllRouteMgmtByRoutId(routeTrayId);

			List<Integer> frIdList = Stream.of(getRouteMgmt.getFrIds().split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());
			List<Franchisee> franchiseeList = tallyFranchiseeRepository.findByIsTallySync();

			franchiseeList = tallyFranchiseeRepository.getFranchisee(frIdList);

			String frName = "";
			int x = 0;
			for (int j = 0; j < franchiseeList.size(); j++) {

				frName = franchiseeList.get(j).getCustomerName() + "," + frName;
				if (franchiseeList.size() > 1)
					x = 1;

			}
			if (x == 1)
				getRouteMgmt.setFrName(frName.substring(0, frName.length() - 1));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return getRouteMgmt;

	}
}
