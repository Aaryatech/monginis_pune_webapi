package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.HSNWiseReport;

public interface HSNWiseReportRepo extends JpaRepository<HSNWiseReport, Integer> {

	@Query(value = " SELECT m_item_sup.item_hsncd,m_item.item_tax1,m_item.item_tax2, SUM(t_bill_detail.bill_qty) as bill_qty, SUM(t_bill_detail.taxable_amt) as taxable_amt,"
			+ " SUM(t_bill_detail.cgst_rs) as cgst_rs, SUM(t_bill_detail.sgst_rs) as sgst_rs FROM m_item_sup,m_item,t_bill_header,t_bill_detail WHERE"
			+ " m_item.id=m_item_sup.item_id AND m_item.id=t_bill_detail.item_id AND t_bill_header.bill_no=t_bill_detail.bill_no AND "
			+ "t_bill_header.bill_date BETWEEN :fromDate AND :toDate GROUP BY m_item_sup.item_hsncd order by "
			+ "  m_item_sup.item_hsncd\r\n" + "", nativeQuery = true)

	List<HSNWiseReport> getReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

	@Query(value = " SELECT m_item_sup.item_hsncd,m_item.item_tax1,m_item.item_tax2, SUM(t_credit_note_details.grn_gvn_qty) as bill_qty, "
			+ "SUM(t_credit_note_details.taxable_amt) as taxable_amt, SUM(t_credit_note_details.cgst_rs) as cgst_rs, SUM(t_credit_note_details.sgst_rs) as sgst_rs "
			+ " FROM m_item_sup,m_item,t_credit_note_details,t_credit_note_header WHERE m_item.id=m_item_sup.item_id"
			+ " AND m_item.id=t_credit_note_details.item_id AND t_credit_note_header.crn_id=t_credit_note_details.crn_id AND"
			+ " t_credit_note_header.crn_date BETWEEN :fromDate AND :toDate GROUP BY m_item_sup.item_hsncd order by  "
			+ " m_item_sup.item_hsncd" + "", nativeQuery = true)

	List<HSNWiseReport> getReportHsn(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}
