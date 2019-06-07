package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.HSNWiseReport;

public interface HSNWiseReportRepo extends JpaRepository<HSNWiseReport, Integer> {

	@Query(value = "SELECT\r\n" + 
			"            m_item_sup.item_hsncd AS id,  \r\n" + 
			"            m_item_sup.item_hsncd,\r\n" + 
			"            m_item.item_tax1,\r\n" + 
			"            m_item.item_tax2,\r\n" + 
			"            SUM(t_bill_detail.bill_qty) as bill_qty,\r\n" + 
			"            SUM(t_bill_detail.taxable_amt) as taxable_amt,\r\n" + 
			"            SUM(t_bill_detail.cgst_rs) as cgst_rs,\r\n" + 
			"            SUM(t_bill_detail.sgst_rs) as sgst_rs \r\n" + 
			"        FROM\r\n" + 
			"            m_item_sup,\r\n" + 
			"            m_item,\r\n" + 
			"            t_bill_header,\r\n" + 
			"            t_bill_detail \r\n" + 
			"        WHERE\r\n" + 
			"            m_item.id=m_item_sup.item_id \r\n" + 
			"            AND t_bill_detail.cat_id!=5 \r\n" + 
			"            and m_item.id=t_bill_detail.item_id \r\n" + 
			"            AND t_bill_header.bill_no=t_bill_detail.bill_no \r\n" + 
			"            AND      t_bill_header.bill_date BETWEEN :fromDate AND :toDate \r\n" + 
			"        GROUP BY\r\n" + 
			"            m_item_sup.item_hsncd      \r\n" + 
			"        union all      \r\n" + 
			"        \r\n" + 
			"        SELECT CONCAT(m_spcake_sup.sp_hsncd,5)   as id,\r\n" + 
			"            m_spcake_sup.sp_hsncd as item_hsncd,\r\n" + 
			"            m_sp_cake.sp_tax1 as item_tax1,\r\n" + 
			"            m_sp_cake.sp_tax2 as item_tax2,\r\n" + 
			"            SUM(t_bill_detail.bill_qty) as bill_qty,\r\n" + 
			"            SUM(t_bill_detail.taxable_amt) as taxable_amt,\r\n" + 
			"            SUM(t_bill_detail.cgst_rs) as cgst_rs,\r\n" + 
			"            SUM(t_bill_detail.sgst_rs) as sgst_rs \r\n" + 
			"        FROM\r\n" + 
			"            m_sp_cake,\r\n" + 
			"            m_spcake_sup,\r\n" + 
			"            t_bill_header,\r\n" + 
			"            t_bill_detail \r\n" + 
			"        WHERE\r\n" + 
			"            m_sp_cake.sp_id=m_spcake_sup.sp_id \r\n" + 
			"            AND t_bill_detail.cat_id=5 \r\n" + 
			"            AND m_sp_cake.sp_id=t_bill_detail.item_id \r\n" + 
			"            AND t_bill_header.bill_no=t_bill_detail.bill_no \r\n" + 
			"            AND      t_bill_header.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"        GROUP BY\r\n" + 
			"            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = "SELECT\r\n" + 
			"      m_item_sup.item_hsncd AS id,\r\n" + 
			"            m_item_sup.item_hsncd,\r\n" + 
			"            m_item.item_tax1,\r\n" + 
			"            m_item.item_tax2,\r\n" + 
			"            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,\r\n" + 
			"            SUM(t_credit_note_details.taxable_amt) as taxable_amt,\r\n" + 
			"            SUM(t_credit_note_details.cgst_rs) as cgst_rs,\r\n" + 
			"            SUM(t_credit_note_details.sgst_rs) as sgst_rs     \r\n" + 
			"        FROM\r\n" + 
			"            m_item_sup,\r\n" + 
			"            m_item,\r\n" + 
			"            t_credit_note_details,\r\n" + 
			"            t_credit_note_header \r\n" + 
			"        WHERE\r\n" + 
			"            m_item.id=m_item_sup.item_id     \r\n" + 
			"            AND m_item.id=t_credit_note_details.item_id \r\n" + 
			"            AND t_credit_note_details.cat_id!=5 \r\n" + 
			"            and t_credit_note_header.crn_id=t_credit_note_details.crn_id \r\n" + 
			"            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate \r\n" + 
			"        GROUP BY\r\n" + 
			"            m_item_sup.item_hsncd      \r\n" + 
			"        union   all    \r\n" + 
			"        SELECT\r\n" + 
			"        \r\n" + 
			"        CONCAT(m_spcake_sup.sp_hsncd,5) as id,\r\n" + 
			"            m_spcake_sup.sp_hsncd as item_hsncd,\r\n" + 
			"            m_sp_cake.sp_tax1 as item_tax1,\r\n" + 
			"            m_sp_cake.sp_tax2 as item_tax2,\r\n" + 
			"            SUM(t_credit_note_details.grn_gvn_qty) as bill_qty,\r\n" + 
			"            SUM(t_credit_note_details.taxable_amt) as taxable_amt,\r\n" + 
			"            SUM(t_credit_note_details.cgst_rs) as cgst_rs,\r\n" + 
			"            SUM(t_credit_note_details.sgst_rs) as sgst_rs     \r\n" + 
			"        FROM\r\n" + 
			"            m_sp_cake,\r\n" + 
			"            m_spcake_sup,\r\n" + 
			"            t_credit_note_details,\r\n" + 
			"            t_credit_note_header \r\n" + 
			"        WHERE\r\n" + 
			"            m_sp_cake.sp_id=m_spcake_sup.sp_id \r\n" + 
			"            AND t_credit_note_details.cat_id=5 \r\n" + 
			"            and m_sp_cake.sp_id=t_credit_note_details.item_id \r\n" + 
			"            AND t_credit_note_header.crn_id=t_credit_note_details.crn_id \r\n" + 
			"            AND     t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate\r\n" + 
			"        GROUP BY\r\n" + 
			"            item_hsncd", nativeQuery = true)

	List<HSNWiseReport> getReportHsn(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
