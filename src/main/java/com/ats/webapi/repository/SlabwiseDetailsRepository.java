package com.ats.webapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.bill.SlabwiseBill;
@Repository
public interface SlabwiseDetailsRepository extends JpaRepository<SlabwiseBill, Integer>{

	//@Query(value="select cgst_per+sgst_per as tax_per,SUM(taxable_amt) as taxable_amt,SUM(cgst_rs) as cgst_amt,SUM(sgst_rs) as sgst_amt,SUM(total_tax) as total_tax,SUM(grand_total) as grand_total from  t_bill_detail where bill_no=:billNo group by cgst_per+sgst_per",nativeQuery=true)
	
	@Query(value="select  m_item_sup.item_hsncd,t_bill_detail.cgst_per+t_bill_detail.sgst_per as tax_per,SUM(t_bill_detail.bill_qty) as bill_qty,SUM(t_bill_detail.taxable_amt) as taxable_amt,SUM(t_bill_detail.cgst_rs) as cgst_amt,SUM(t_bill_detail.sgst_rs) as sgst_amt,SUM(t_bill_detail.total_tax) as total_tax,SUM(t_bill_detail.grand_total) as grand_total from  t_bill_detail,m_item_sup where m_item_sup.item_id=t_bill_detail.item_id and bill_no=:billNo group by m_item_sup.item_hsncd",nativeQuery=true)
	List<SlabwiseBill> getSlabwiseBillData(@Param("billNo")int billNo);

}
