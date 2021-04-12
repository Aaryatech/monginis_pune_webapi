package com.ats.webapi.model.einv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EInvBillDetailRepo extends JpaRepository<EInvBillDetail, Integer> {
	
	@Query(value = " SELECT -1 as grn_type,-1 as is_grn, t_bill_detail.bill_detail_no,t_bill_detail.bill_no, " + 
			" t_bill_detail.order_id,t_bill_detail.item_id,t_bill_detail.bill_qty,t_bill_detail.order_qty, " + 
			" t_bill_detail.mrp,t_bill_detail.rate,t_bill_detail.base_rate,t_bill_detail.taxable_amt,t_bill_detail.sgst_rs, " + 
			" t_bill_detail.cgst_rs,t_bill_detail.igst_rs,t_bill_detail.cess_rs,t_bill_detail.hsn_code, " + 
			" t_bill_detail.total_tax,t_bill_detail.grand_total,t_bill_detail.expiry_date,t_bill_detail.disc_per, " + 
			" CASE  WHEN t_bill_detail.cat_id=5 THEN (SELECT m_sp_cake.sp_name  FROM  m_sp_cake WHERE m_sp_cake.sp_id= t_bill_detail.item_id) "
			+ " ELSE (SELECT  m_item.item_name FROM m_item WHERE t_bill_detail.item_id=m_item.id)END AS item_name , " + 
			" CASE WHEN t_bill_detail.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom  FROM m_spcake_sup  " + 
			" WHERE  m_spcake_sup.sp_id=t_bill_detail.item_id)  " + 
			" ELSE (SELECT m_item_sup.item_uom FROM m_item_sup   " + 
			" WHERE  m_item_sup.item_id=t_bill_detail.item_id)   " + 
			" END AS item_uom, t_bill_detail.cgst_per,t_bill_detail.sgst_per,t_bill_detail.igst_per,t_bill_detail.cess_per from t_bill_detail  " + 
			" WHERE bill_no=:billNo ", nativeQuery = true)
	List<EInvBillDetail> getEInvBillDetail(@Param("billNo") int billNo);
	
	/*SELECT cnd.crnd_id as bill_detail_no,cnd.bill_no,cnd.grn_gvn_id as order_id,
    cnd.item_id,cnd.grn_gvn_qty as bill_qty, cnd.grn_gvn_qty as order_qty,cnd.base_rate as mrp,
    cnd.base_rate as rate, base_rate,cnd.taxable_amt,cnd.sgst_rs,cnd.cgst_rs,cnd.igst_rs,cnd.cess_rs,
    cnd.hsn_code,cnd.total_tax,cnd.grn_gvn_amt as grand_total,null as expiry_date,0 as disc_per,
    
     CASE  WHEN cnd.cat_id=5 THEN (SELECT m_sp_cake.sp_name  FROM  m_sp_cake WHERE m_sp_cake.sp_id= 	 cnd.item_id) 
	  ELSE (SELECT  m_item.item_name FROM m_item WHERE cnd.item_id=m_item.id)END AS item_name ,   
     
	 CASE WHEN cnd.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom  FROM m_spcake_sup    
	 WHERE  m_spcake_sup.sp_id=cnd.item_id)    
	 ELSE (SELECT m_item_sup.item_uom FROM m_item_sup     
	 WHERE  m_item_sup.item_id=cnd.item_id)     
	 END AS item_uom, 
    
    cnd.cgst_per,cnd.sgst_per,cnd.igst_per,cnd.cess_per
    
    
    FROM t_credit_note_details cnd WHERE cnd.crn_id=5*/
    
  
    @Query(value = " SELECT cnd.crnd_id as bill_detail_no,cnd.bill_no,cnd.grn_gvn_id as order_id," + 
    		"    cnd.item_id,cnd.grn_gvn_qty as bill_qty, cnd.grn_gvn_qty as order_qty,cnd.base_rate as mrp," + 
    		"    cnd.base_rate as rate, cnd.base_rate,cnd.taxable_amt,cnd.sgst_rs,cnd.cgst_rs,cnd.igst_rs,cnd.cess_rs," + 
    		"    cnd.hsn_code,cnd.total_tax,cnd.grn_gvn_amt as grand_total,null as expiry_date,t_bill_detail.disc_per," + 
    		"    " + 
    		"     CASE  WHEN cnd.cat_id=5 THEN (SELECT m_sp_cake.sp_name  FROM  m_sp_cake WHERE m_sp_cake.sp_id=cnd.item_id) " + 
    		"	  ELSE (SELECT  m_item.item_name FROM m_item WHERE cnd.item_id=m_item.id)END AS item_name ,   " + 
    		"     " + 
    		"	 CASE WHEN cnd.cat_id=5 THEN (SELECT m_spcake_sup.sp_uom  FROM m_spcake_sup    " + 
    		"	 WHERE  m_spcake_sup.sp_id=cnd.item_id)    " + 
    		"	 ELSE (SELECT m_item_sup.item_uom FROM m_item_sup     " + 
    		"	 WHERE  m_item_sup.item_id=cnd.item_id)     " + 
    		"	 END AS item_uom, " + 
    		"    " + 
    		"    cnd.cgst_per,cnd.sgst_per,cnd.igst_per,cnd.cess_per," + 
    		"    " + 
    		"    cnd.grn_type,cnd.is_grn" + 
    		"    " + 
    		"    " + 
    		"    FROM t_credit_note_details cnd ,t_bill_detail,t_grn_gvn  WHERE cnd.grn_gvn_id=t_grn_gvn.grn_gvn_id "
    		+ " and t_grn_gvn.bill_detail_no=t_bill_detail.bill_detail_no "
    		+ " and cnd.crn_id=:crnId", nativeQuery = true)
	List<EInvBillDetail> getCredNoteDetailForEInvOfCN(@Param("crnId") int crnId);
	
    
}
