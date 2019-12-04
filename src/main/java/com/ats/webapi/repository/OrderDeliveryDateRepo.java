package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.OrderDeliveryDate;

public interface OrderDeliveryDateRepo extends JpaRepository<OrderDeliveryDate, Integer> {
	
	@Query(value = "SELECT\n" + 
			"    o.fr_id,\n" + 
			"    m.fr_name,\n" + 
			"    SUM(o.order_rate * order_qty) AS ttl_amt " + 
			"FROM  " + 
			"    t_order o,\n" + 
			"    m_franchisee m\n" + 
			"WHERE\n" + 
			"    o.delivery_date BETWEEN :fromDate AND :toDate AND m.fr_id = o.fr_id\n" + 
			"GROUP BY\n" + 
			"    m.fr_id\n" + 
			"ORDER BY\n" + 
			"    m.fr_name",nativeQuery=true)
	public List<OrderDeliveryDate> getOrderByDeliveyDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
