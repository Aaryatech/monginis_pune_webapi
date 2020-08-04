package com.ats.webapi.repository.salesreturnreport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.salesvaluereport.SalesReturnValueDao;
import com.ats.webapi.model.salesvaluereport.SalesReturnValueItemDao;

public interface SalesReturnValueItemDaoRepo extends JpaRepository<SalesReturnValueItemDao, Integer> {

/*	@Query(value = "select\n" + "        CONCAT(:month,\n" + "        \"\",\n" + "        id) as id,\n"
			+ "        id as item_id,\n" + "        coalesce((SELECT\n"
			+ "            SUM(t_bill_detail.grand_total) as grand_total  \n" + "        FROM\n"
			+ "            t_bill_detail,\n" + "            t_bill_header\n" + "        WHERE\n"
			+ "            DATE_FORMAT(t_bill_header.bill_date,'%Y-%m')=:month\n"
			+ "            AND t_bill_header.del_status=0\n"
			+ "            And t_bill_header.bill_no=t_bill_detail.bill_no\n"
			+ "            and t_bill_detail.cat_id!=5\n" + "            and m_item.id=t_bill_detail.item_id),\n"
			+ "        0) as grand_total,\n" + "        coalesce((select\n"
			+ "            SUM(grn_gvn_amt) as grn_qty\n" + "        from\n" + "            t_credit_note_header,\n"
			+ "            t_credit_note_details\n" + "           \n" + "        where\n"
			+ "            t_credit_note_header.crn_id=t_credit_note_details.crn_id\n"
			+ "            and t_credit_note_header.is_grn=1\n"
			+ "            and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month\n"
			+ "            and t_credit_note_details.cat_id!=5\n"
			+ "            and m_item.id=t_credit_note_details.item_id\n" + "            ),\n"
			+ "        0) as grn_qty,\n" + "        coalesce((select\n" + "            SUM(grn_gvn_amt) as gvn_qty\n"
			+ "        from\n" + "            t_credit_note_header,\n" + "            t_credit_note_details\n"
			+ "           \n" + "        where\n"
			+ "            t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n"
			+ "            and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month\n"
			+ "            and t_credit_note_header.is_grn=0\n" + "            and t_credit_note_details.cat_id!=5\n"
			+ "            and m_item.id=t_credit_note_details.item_id\n" + "            ),\n"
			+ "        0) as gvn_qty  \n" + "    from\n" + "        m_item\n" + "    where\n"
			+ "        m_item.item_grp2 IN (:subCatId)\n" + "        and m_item.del_status=0  ", nativeQuery = true)
	List<SalesReturnValueItemDao> getSalesReturnValueItemReport1(@Param("month") String month,
			@Param("subCatId") List<Integer> subCatId);*/
	
	
	//Sachin 4-08-2020
	
	@Query(value = " SELECT\n" + 
			"    CONCAT(:month,\n" + 
			"    \"\",\n" + 
			"    id) as id,\n" + 
			"    id as item_id,\n" + 
			"COALESCE((bill_data.grand_total),0) as grand_total, COALESCE((grn_data.grn_qty),0) as grn_qty,COALESCE((gvn_data.gvn_qty),0) as gvn_qty\n" + 
			
			"from(select m_item.id,m_item.item_id from m_item where  m_item.item_grp2 IN (\n" + 
			"       :subCatId\n" + 
			"    )         \n" + 
			"    and m_item.del_status=0   \n" + 
			") item_data\n" + 
			"\n" + 
			"    left join (SELECT\n" + 
			"        SUM(t_bill_detail.grand_total) as grand_total   ,  t_bill_detail.item_id      \n" + 
			"    FROM\n" + 
			"        t_bill_detail,\n" + 
			"        t_bill_header         \n" + 
			"    WHERE\n" + 
			"        DATE_FORMAT(t_bill_header.bill_date,'%Y-%m')=:month             \n" + 
			"        AND t_bill_header.del_status=0             \n" + 
			"        And t_bill_header.bill_no=t_bill_detail.bill_no             \n" + 
			"        and t_bill_detail.cat_id!=5  group by t_bill_detail.item_id           \n" + 
			"        ) bill_data on item_data.id=bill_data.item_id  \n" + 
			"\n" + 
			"    left join (select\n" + 
			"        SUM(grn_gvn_amt) as grn_qty  ,t_credit_note_details.item_id       \n" + 
			"    from\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details                     \n" + 
			"    where\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id             \n" + 
			"        and t_credit_note_header.is_grn=1             \n" + 
			"        and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month            \n" + 
			"        and t_credit_note_details.cat_id!=5  group by t_credit_note_details.item_id             \n" + 
			"        ) grn_data on item_data.id=grn_data.item_id\n" + 
			" \n" + 
			"left join (select SUM(grn_gvn_amt) as gvn_qty ,t_credit_note_details.item_id         \n" + 
			"    from\n" + 
			"        t_credit_note_header,\n" + 
			"        t_credit_note_details                     \n" + 
			"    where\n" + 
			"        t_credit_note_header.crn_id=t_credit_note_details.crn_id               \n" + 
			"        and DATE_FORMAT(t_credit_note_header.crn_date,'%Y-%m')=:month             \n" + 
			"        and t_credit_note_header.is_grn=0             \n" + 
			"        and t_credit_note_details.cat_id!=5 group by t_credit_note_details.item_id              \n" + 
			"    ) gvn_data on item_data.id=gvn_data.item_id   ", nativeQuery = true)
	List<SalesReturnValueItemDao> getSalesReturnValueItemReport1(@Param("month") String month,
			@Param("subCatId") List<Integer> subCatId);
	
	


}
