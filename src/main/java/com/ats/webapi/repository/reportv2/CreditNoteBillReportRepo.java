package com.ats.webapi.repository.reportv2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.reportv2.CreditNoteBillReport;

public interface CreditNoteBillReportRepo extends JpaRepository<CreditNoteBillReport, Integer> {

	@Query(value = " SELECT ch.crn_date,cd.bill_date, cd.item_id,cd.bill_no,SUM(cd.grn_gvn_qty) AS grn_gvn_qty,SUM(cd.grn_gvn_amt) "
			+ "AS grn_gvn_amt ,ch.crn_no,i.item_name,bh.invoice_no FROM m_item i, t_credit_note_details cd,t_credit_note_header ch,"
			+ "t_bill_header bh WHERE cd.crn_id=ch.crn_id AND cd.bill_no IN(:billNoList) AND i.id=cd.item_id AND bh.bill_no= cd.bill_no "
			+ "GROUP BY cd.item_id", nativeQuery = true)

	List<CreditNoteBillReport> getCrNoteRegItem(@Param("billNoList") List<Integer> billNoList);

}
