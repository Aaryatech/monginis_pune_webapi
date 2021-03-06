package com.ats.webapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.webapi.model.ItemWithSubCat;
import com.ats.webapi.model.Order;
import com.ats.webapi.model.Orders;
import com.ats.webapi.repository.GetFrItemRepository;
import com.ats.webapi.repository.PrevItemOrderRepository;

@Service("PrevItemOrderService")
public class PrevItemOrderServiceImpl  implements PrevItemOrderService {

	@Autowired
	private PrevItemOrderRepository prevItemOrderRepository;

	@Override
	public List<Orders> findFrItemOrders(List<Integer>items , String frId, String date, String menuId) {

		List<Orders>list =prevItemOrderRepository.findAllOrders(items ,  frId,  date, menuId);
		return list;
	}

	@Override
	public List<Orders> findFrItemOrders1030Menu(List<Integer> items, String frId, String date, String menuId) {
		System.err.println("date "+date + "menu id " +menuId);
		List<Orders>list =prevItemOrderRepository.findAllOrdersFor1030Order(items ,  frId,  date, menuId);
		System.err.println("list order of 10 30 pm " +list.toString());
		return list;
	}

	

}