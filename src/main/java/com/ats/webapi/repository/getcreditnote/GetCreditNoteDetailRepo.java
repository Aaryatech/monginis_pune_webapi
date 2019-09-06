package com.ats.webapi.repository.getcreditnote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.grngvn.GetCrnDetails;
import com.ats.webapi.model.grngvn.PostCreditNoteDetails;

public interface GetCreditNoteDetailRepo extends JpaRepository<GetCrnDetails, Integer> {

	/*@Query(value = "SELECT detail.*,"
			+ "CASE WHEN detail.cat_id=5 THEN (SELECT m_sp_cake.sp_name from  m_sp_cake WHERE detail.item_id=m_sp_cake.sp_id)"
			+ "ELSE (SELECT m_item.item_name from m_item where m_item.id=detail.item_id) END AS item_name,"
			+ "CASE WHEN detail.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom from  m_spcake_sup WHERE detail.item_id=m_spcake_sup.sp_id)"
			+ "		ELSE (SELECT m_item_sup.item_uom from m_item_sup where m_item_sup.item_id=detail.item_id) END AS item_uom "
			+ " FROM t_credit_note_details detail WHERE   detail.crn_id IN (:crnId) ", nativeQuery = true)*/
	
	@Query(value = "SELECT   detail.crnd_id,detail.crn_id,detail.bill_no,detail.bill_date,detail.item_id,detail.grn_gvn_id,detail.is_grn,detail.grn_type,detail.grn_gvn_date,detail.grn_gvn_qty,detail.taxable_amt,detail.total_tax,detail.grn_gvn_amt,detail.cgst_per,detail.sgst_per,detail.igst_per,detail.cgst_rs,detail.sgst_rs,detail.igst_rs,detail.del_status,detail.cat_id,detail.base_rate,detail.ref_invoice_no,detail.grngvn_srno,detail.grn_gvn_header_id,detail.hsn_code,detail.cess_rs, CASE WHEN detail.cat_id=5 THEN (SELECT m_sp_cake.sp_name from  m_sp_cake WHERE detail.item_id=m_sp_cake.sp_id)\n" + 
			"			 ELSE (SELECT m_item.item_name from m_item where m_item.id=detail.item_id) END AS item_name,\n" + 
			"		\n" + 
			"			 CASE WHEN detail.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom from  m_spcake_sup WHERE detail.item_id=m_spcake_sup.sp_id)\n" + 
			"			 		ELSE (SELECT m_item_sup.item_uom from m_item_sup where m_item_sup.item_id=detail.item_id) END AS item_uom \n" + 
			"			 		\n" + 
			"			  ,  ((d.grand_total/d.bill_qty)*detail.grn_gvn_qty)-detail.grn_gvn_amt as cess_per   \n" + 
			"			  \n" + 
			"			  FROM t_credit_note_details detail,t_grn_gvn g,t_bill_detail d \n" + 
			"			  WHERE    \n" + 
			"			  g.grn_gvn_id=detail.grn_gvn_id and g.bill_detail_no=d.bill_detail_no \n" + 
			"			  \n" + 
			"			  and  detail.crn_id IN (:crnId) ", nativeQuery = true)
	List<GetCrnDetails> getCrnDetailsById(@Param("crnId") List<String> crnId);
/*
	@Query(value = "SELECT detail.*,\n" + 
			"				CASE WHEN detail.cat_id=5 THEN (SELECT m_sp_cake.sp_name from  m_sp_cake WHERE detail.item_id=m_sp_cake.sp_id)\n" + 
			"				ELSE (SELECT m_item.item_name from m_item where m_item.id=detail.item_id) END AS item_name,\n" + 
			"				CASE WHEN detail.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom from  m_spcake_sup WHERE detail.item_id=m_spcake_sup.sp_id)\n" + 
			"						ELSE (SELECT m_item_sup.item_uom from m_item_sup where m_item_sup.item_id=detail.item_id) END AS item_uom \n" + 
			"					 FROM t_credit_note_details detail,t_credit_note_header WHERE t_credit_note_header.crn_id=detail.crn_id and  FIND_IN_SET(t_credit_note_header.crn_no, (select credit_note_id from t_grn_gvn_header where grn_gvn_header_id=:grnGvnHeaderId)) ", nativeQuery = true)
	*/
	@Query(value = "\n" + 
			"SELECT  detail.crnd_id,detail.crn_id,detail.bill_no,detail.bill_date,detail.item_id,detail.grn_gvn_id,detail.is_grn,detail.grn_type,detail.grn_gvn_date,detail.grn_gvn_qty,detail.taxable_amt,detail.total_tax,detail.grn_gvn_amt,detail.cgst_per,detail.sgst_per,detail.igst_per,detail.cgst_rs,detail.sgst_rs,detail.igst_rs,detail.del_status,detail.cat_id,detail.base_rate,detail.ref_invoice_no,detail.grngvn_srno,detail.grn_gvn_header_id,detail.hsn_code,detail.cess_rs,  CASE WHEN detail.cat_id=5 THEN (SELECT m_sp_cake.sp_name from  m_sp_cake WHERE detail.item_id=m_sp_cake.sp_id)  \n" + 
			"							ELSE (SELECT m_item.item_name from m_item where m_item.id=detail.item_id) END AS item_name,\n" + 
			"CASE WHEN detail.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom from  m_spcake_sup WHERE detail.item_id=m_spcake_sup.sp_id)  \n" + 
			"ELSE (SELECT m_item_sup.item_uom from m_item_sup where m_item_sup.item_id=detail.item_id) END AS item_uom,\n" + 
			"   ((d.grand_total/d.bill_qty)*detail.grn_gvn_qty)-detail.grn_gvn_amt as cess_per  \n" + 
			" FROM t_credit_note_details detail,t_credit_note_header,t_grn_gvn g,t_bill_detail d WHERE t_credit_note_header.crn_id=detail.crn_id and \n" + 
			"g.grn_gvn_id=detail.grn_gvn_id and g.bill_detail_no=d.bill_detail_no and\n" + 
			" FIND_IN_SET(t_credit_note_header.crn_no, (select credit_note_id from t_grn_gvn_header where grn_gvn_header_id=:grnGvnHeaderId)) ", nativeQuery = true)
	
	
	List<GetCrnDetails> getCrnDetailsByGrnGvnHeaderId(@Param("grnGvnHeaderId") int grnGvnHeaderId);

}
