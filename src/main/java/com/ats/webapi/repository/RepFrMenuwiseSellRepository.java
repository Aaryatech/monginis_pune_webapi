package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import com.ats.webapi.model.report.GetRepMenuwiseSell;

public interface RepFrMenuwiseSellRepository extends JpaRepository<GetRepMenuwiseSell, Integer>{
	
	@Query(value="SELECT\r\n" + 
			"    d.sell_bill_detail_no,\r\n" + 
			"    h.fr_id,\r\n" + 
			"    f.fr_name,\r\n" + 
			"    c.cat_name,\r\n" + 
			"    d.cat_id,\r\n" + 
			"    SUM(d.qty) AS qty,\r\n" + 
			"    SUM(d.mrp * d.qty) AS amount\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_detail d,\r\n" + 
			"    t_sell_bill_header h,\r\n" + 
			"    m_category c,\r\n" + 
			"    m_franchisee f\r\n" + 
			"WHERE\r\n" + 
			"    h.bill_date BETWEEN :fromDate AND :toDate AND c.cat_id = d.cat_id AND h.sell_bill_no = d.sell_bill_no AND h.fr_id IN(:frId) AND h.fr_id = f.fr_id AND h.del_status = 0\r\n" + 
			"GROUP BY\r\n" + 
			"    d.cat_id,\r\n" + 
			"    h.fr_id\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    t_sp_cake.sp_order_no AS sell_bill_detail_no,\r\n" + 
			"    t_sp_cake.fr_id,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    'Special Cake' AS cat_name,\r\n" + 
			"    5 AS cat_id,\r\n" + 
			"    COUNT(t_sp_cake.sp_order_no) AS qty,\r\n" + 
			"    SUM(t_sp_cake.sp_grand_total) AS amount\r\n" + 
			"FROM\r\n" + 
			"    t_sp_cake,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate AND t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id = t_sp_cake.fr_id AND t_sp_cake.sp_book_for_mob_no != '0'\r\n" + 
			"GROUP BY\r\n" + 
			"    t_sp_cake.fr_id "
 ,nativeQuery=true)
	List<GetRepMenuwiseSell> getRepFrMenuwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
	

}
