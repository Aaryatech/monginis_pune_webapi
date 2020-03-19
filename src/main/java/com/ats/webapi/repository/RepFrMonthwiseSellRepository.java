package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.report.GetRepMonthwiseSell;

public interface RepFrMonthwiseSellRepository extends JpaRepository<GetRepMonthwiseSell, Integer>{

	
	@Query(value="SELECT\r\n" + 
			"    t_sell_bill_header.bill_date,\r\n" + 
			"    t_sell_bill_header.sell_bill_no,\r\n" + 
			"    t_sell_bill_header.fr_id,\r\n" + 
			"    SUM(\r\n" + 
			"        CASE WHEN t_sell_bill_header.payment_mode = 1 THEN t_sell_bill_header.payable_amt ELSE 0\r\n" + 
			"    END\r\n" + 
			") AS cash,\r\n" + 
			"SUM(\r\n" + 
			"    CASE WHEN t_sell_bill_header.payment_mode = 2 THEN t_sell_bill_header.payable_amt ELSE 0\r\n" + 
			"END\r\n" + 
			") AS card,\r\n" + 
			"SUM(\r\n" + 
			"    CASE WHEN t_sell_bill_header.payment_mode = 3 THEN t_sell_bill_header.payable_amt ELSE 0\r\n" + 
			"END\r\n" + 
			") AS other,\r\n" + 
			"m_franchisee.fr_name,\r\n" + 
			"CONCAT(\r\n" + 
			"    MONTHNAME(t_sell_bill_header.bill_date),\r\n" + 
			"    '-',\r\n" + 
			"    YEAR(t_sell_bill_header.bill_date)\r\n" + 
			") AS MONTH\r\n" + 
			"FROM\r\n" + 
			"    t_sell_bill_header,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.fr_id IN(:frId) AND m_franchisee.fr_id = t_sell_bill_header.fr_id AND t_sell_bill_header.del_status = 0\r\n" + 
			"GROUP BY\r\n" + 
			"    MONTH(t_sell_bill_header.bill_date),\r\n" + 
			"    t_sell_bill_header.fr_id\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    t_sp_cake.sp_delivery_date AS bill_date,\r\n" + 
			"    t_sp_cake.sp_order_no AS bill_no,\r\n" + 
			"    t_sp_cake.fr_id,\r\n" + 
			"    SUM(t_sp_cake.sp_grand_total) AS cash,\r\n" + 
			"    0 AS card,\r\n" + 
			"    0 AS other,\r\n" + 
			"    m_franchisee.fr_name,\r\n" + 
			"    CONCAT(\r\n" + 
			"        MONTHNAME(t_sp_cake.sp_delivery_date),\r\n" + 
			"        '-',\r\n" + 
			"        YEAR(t_sp_cake.sp_delivery_date)\r\n" + 
			"    ) AS MONTH\r\n" + 
			"FROM\r\n" + 
			"    t_sp_cake,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate AND t_sp_cake.fr_id IN(:frId) AND m_franchisee.fr_id = t_sp_cake.fr_id AND t_sp_cake.sp_book_for_mob_no != '0'\r\n" + 
			"GROUP BY\r\n" + 
			"    MONTH(t_sp_cake.sp_delivery_date),\r\n" + 
			"    t_sp_cake.fr_id",nativeQuery=true)
			List<GetRepMonthwiseSell> getRepFrMonthwiseSell(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("frId") List<String> frId);
}
