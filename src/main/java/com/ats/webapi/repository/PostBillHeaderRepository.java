package com.ats.webapi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.PostBillHeader;

public interface PostBillHeaderRepository extends JpaRepository<PostBillHeader, Integer> {

	PostBillHeader save(PostBillHeader postBillHeader);

	@Query(value = " SELECT * FROM t_bill_header WHERE del_status=0 AND bill_date BETWEEN :fromDate AND :toDate AND fr_id IN(:frId)\n"
			+ "", nativeQuery = true)
	List<PostBillHeader> getBillDetailByFrId(@Param("frId") List<Integer> frId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

	@Query(value = " SELECT * FROM t_bill_header WHERE del_status=0 AND bill_date BETWEEN :fromDate AND :toDate \n"
			+ "", nativeQuery = true)
	List<PostBillHeader> getBillDetail(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Transactional
	@Modifying
	@Query("UPDATE PostBillHeader i SET i.frId=:frId,i.frCode=:frCode,i.partyName=:frName,i.partyGstin=:frGstNo,i.partyAddress=:frAddress  WHERE i.billNo=:billNo ")
	int updatefrinfo(@Param("billNo")int billNo,@Param("frId") int frId, @Param("frCode") String frCode,@Param("frName") String frName,@Param("frGstNo") String frGstNo,@Param("frAddress") String frAddress);

	
	@Query(value = " SELECT\n" + 
			"    h.bill_no,\n" + 
			"    h.invoice_no,\n" + 
			"    h.bill_date,\n" + 
			"    h.bill_date_time,\n" + 
			"    h.fr_id,\n" + 
			"    h.fr_code,\n" + 
			"    h.tax_applicable,\n" + 
			"    h.taxable_amt,\n" + 
			"    h.total_tax,\n" + 
			"    COALESCE(SUM(d.bill_qty*d.mrp),0) as grand_total,\n" + 
			"    h.remark,\n" + 
			"    h.status,\n" + 
			"    h.del_status,\n" + 
			"    h.time,\n" + 
			"    h.disc_amt,\n" + 
			"    h.is_tally_sync,\n" + 
			"    h.round_off,\n" + 
			"    h.sgst_sum,\n" + 
			"    h.cgst_sum,\n" + 
			"    h.igst_sum,\n" + 
			"    h.party_name,\n" + 
			"    h.party_gstin,\n" + 
			"    h.party_address,\n" + 
			"    h.veh_no,\n" + 
			"    h.bill_time,\n" + 
			"    h.ex_varchar1,\n" + 
			"    h.ex_varchar2\n" + 
			"FROM\n" + 
			"    t_bill_header h,\n" + 
			"    t_bill_detail d\n" + 
			"WHERE\n" + 
			"    h.bill_no = d.bill_no AND h.bill_no IN(:ids) AND d.cat_id != 5\n" + 
			"GROUP BY\n" + 
			"    h.bill_no"
			+ "", nativeQuery = true)
	List<PostBillHeader> getBillListByIds(@Param("ids") List<Integer> ids);
	
	
	// sum(CASE WHEN payment_mode = 1 THEN payable_amt ELSE 0 END) as cash,

	// (SELECT m_fr_opening_stock_header.month FROM m_fr_opening_stock_header WHERE
	// fr_id=15 AND is_month_closed=0)
	/*
	 * SELL - SELECT SUM(t_sell_bill_detail.qty) FROM t_sell_bill_detail WHERE
	 * t_sell_bill_detail.item_id =8 AND t_sell_bill_detail.sell_bill_no IN(SELECT
	 * t_sell_bill_header.sell_bill_no FROM t_sell_bill_header WHERE
	 * t_sell_bill_header.fr_id=37 AND t_sell_bill_header.bill_date BETWEEN
	 * '2017-10-01' AND '2017-10-26')
	 * 
	 * Stock- SELECT m_fr_opening_stock_detail.opening_stock FROM
	 * m_fr_opening_stock_detail WHERE m_fr_opening_stock_detail.item_id=22 AND
	 * m_fr_opening_stock_detail.opening_stock_header_id IN(SELECT
	 * m_fr_opening_stock_header.opening_stock_header_id FROM
	 * m_fr_opening_stock_header WHERE m_fr_opening_stock_header.fr_id=17 AND
	 * m_fr_opening_stock_header.month=10)
	 */

}
