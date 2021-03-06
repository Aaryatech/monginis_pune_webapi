package com.ats.webapi.model.einv;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EInvBillHeaderRepo extends JpaRepository<EInvBillHeader, Integer>{
	
	@Query(value=" SELECT t_bill_header.ex_varchar1 as irn_ack_data, t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date, " + 
			" t_bill_header.fr_id,t_bill_header.tax_applicable,t_bill_header.taxable_amt,t_bill_header.total_tax, " + 
			" t_bill_header.grand_total,t_bill_header.disc_amt,t_bill_header.round_off, " + 
			" t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum,t_bill_header.party_name, " + 
			" t_bill_header.party_gstin,t_bill_header.party_address,t_bill_header.veh_no,0 as cess_sum " + 
			" " + 
			" FROM t_bill_header WHERE t_bill_header.del_status=0 and t_bill_header.bill_no IN(:billIdList) ",nativeQuery=true)
	public List<EInvBillHeader>  getBillHeaderforEInv(@Param("billIdList") List<String> billIdList);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_bill_header  SET ex_varchar1=:irnAndAckNo  WHERE bill_no=:billNo", nativeQuery=true)
	int updatebillHeaderForEInv(@Param("irnAndAckNo") String irnAndAckNo,@Param("billNo") int billNo);
	
	
	//For E Invoice of Cred Note
	
	 
	 @Query(value="  SELECT t_credit_note_header.crn_no as invoice_no, t_credit_note_header.crn_id as bill_no , t_credit_note_header.ex_varchar2 as irn_ack_data,  " + 
	 		"	 t_credit_note_header.crn_date as bill_date,t_credit_note_header.fr_id, 0 as tax_applicable, " + 
	 		"	 t_credit_note_header.crn_taxable_amt as taxable_amt,t_credit_note_header.crn_total_tax as total_tax, " + 
	 		"	 t_credit_note_header.crn_grand_total as grand_total, " + 
	 		"	 0 as disc_amt, t_credit_note_header.round_off, -1 as sgst_sum, -1 as cgst_sum, -1 as igst_sum, " + 
	 		"	 null as party_name, null as party_gstin, null as party_address, 0 as cess_sum, -1 as veh_no " + 
	 		"\n" + 
	 		"	 from t_credit_note_header WHERE t_credit_note_header.crn_id IN (:crnIdList) ",nativeQuery=true)
		public List<EInvBillHeader>  getCredNoteHeaderforEInv(@Param("crnIdList") List<String> crnIdList);

	@Transactional
	@Modifying
	@Query(value="UPDATE t_credit_note_header  SET ex_varchar2=:irnAndAckNo  WHERE crn_id=:billNo", nativeQuery=true)
	int updateIRNForEInvInCN(@Param("irnAndAckNo") String irnAndAckNo,@Param("billNo") int billNo);
	
}
