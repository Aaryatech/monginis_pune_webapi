package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.OrderDeliveryDate;

public interface OrderDeliveryDateRepo extends JpaRepository<OrderDeliveryDate, Integer> {
	
	@Query(value = "SELECT o.fr_id,\n" + 
					"		m.fr_name,\n" + 
					"       SUM(o.order_mrp) AS ttl_amt\n" + 
					"FROM t_order o , m_franchisee m\n" + 
					"WHERE o.delivery_date BETWEEN :fromDate AND :toDate AND\n" + 
					"	m.fr_id=o.fr_id\n" + 
					"    GROUP BY m.fr_id \n" + 
					"    ORDER BY m.fr_name",nativeQuery=true)
	public List<OrderDeliveryDate> getOrderByDeliveyDate(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
