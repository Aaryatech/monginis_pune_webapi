package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.OPSViewSellBillsModel;

public interface OPSViewSellBillsModelRepo extends JpaRepository<OPSViewSellBillsModel,Integer>{

	@Query(value =  "SELECT\r\n" + 
			"    t1.*\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        UUID() AS id, t_sell_bill_header.sell_bill_no, t_sell_bill_header.bill_type, t_sell_bill_header.invoice_no, t_sell_bill_header.bill_date, t_sell_bill_header.taxable_amt, t_sell_bill_header.total_tax, t_sell_bill_header.grand_total, t_sell_bill_header.paid_amt, t_sell_bill_header.remaining_amt, t_sell_bill_header.payment_mode, t_sell_bill_header.discount_per, t_sell_bill_header.payable_amt, m_franchisee.fr_name\r\n" + 
			"    FROM\r\n" + 
			"        t_sell_bill_header, m_franchisee\r\n" + 
			"    WHERE\r\n" + 
			"        m_franchisee.fr_id = t_sell_bill_header.fr_id AND t_sell_bill_header.fr_id IN(:frId) AND t_sell_bill_header.bill_date BETWEEN :fromDate AND :toDate AND t_sell_bill_header.del_status = 0\r\n" + 
			"    UNION ALL\r\n" + 
			"SELECT\r\n" + 
			"    UUID() AS id, t_sp_cake.sp_order_no AS sell_bill_no, 'S' AS bill_type, t_sp_cake.sp_book_for_mob_no AS invoice_no, t_sp_cake.sp_delivery_date AS bill_date,(\r\n" + 
			"        t_sp_cake.sp_grand_total -(\r\n" + 
			"            t_sp_cake.tax_1_amt + t_sp_cake.tax_2_amt\r\n" + 
			"        )\r\n" + 
			"    ) AS taxable_amt,\r\n" + 
			"    (\r\n" + 
			"        t_sp_cake.tax_1_amt + t_sp_cake.tax_2_amt\r\n" + 
			"    ) AS total_tax,\r\n" + 
			"    t_sp_cake.sp_grand_total AS grand_total,\r\n" + 
			"    t_sp_cake.sp_grand_total AS paid_amt,\r\n" + 
			"    0 AS remaining_amt,\r\n" + 
			"    1 AS payment_mode,\r\n" + 
			"    t_sp_cake.disc AS discount_per,\r\n" + 
			"    t_sp_cake.sp_grand_total AS payable_amt,\r\n" + 
			"    m_franchisee.fr_name\r\n" + 
			"FROM\r\n" + 
			"    t_sp_cake,\r\n" + 
			"    m_franchisee\r\n" + 
			"WHERE\r\n" + 
			"    t_sp_cake.sp_delivery_date BETWEEN :fromDate AND :toDate AND t_sp_cake.fr_id IN(:frId) AND t_sp_cake.fr_id = m_franchisee.fr_id AND t_sp_cake.sp_book_for_mob_no != '0' AND t_sp_cake.del_status = 0) t1\r\n" + 
			"ORDER BY\r\n" + 
			"    t1.invoice_no,\r\n" + 
			"    t1.bill_date ", nativeQuery = true)
	
	List<OPSViewSellBillsModel> getFrSellBillHeader(@Param("fromDate") String fromDate ,@Param("toDate") String toDate ,@Param("frId") List<String> frId);
	

}
