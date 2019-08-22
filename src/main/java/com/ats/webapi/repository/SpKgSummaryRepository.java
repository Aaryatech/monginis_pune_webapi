package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ats.webapi.model.report.SpKgSummaryDao;

@Repository
public interface SpKgSummaryRepository extends JpaRepository<SpKgSummaryDao, String>{

	@Query(value=" select\n" + 
			"        UUID() as uid,\n" + 
			"        a.* \n" + 
			"    from\n" + 
			"        (select\n" + 
			"            t1.sp_id as id,\n" + 
			"            t1.sp_name,\n" + 
			"            t1.sp_selected_weight,\n" + 
			"            t1.sp_qty,\n" + 
			"            t1.sp_value,\n" + 
			"            coalesce((t2.crn_qty),\n" + 
			"            0) as crn_qty,\n" + 
			"            coalesce((t2.crn_selected_weight),\n" + 
			"            0)as crn_selected_weight,\n" + 
			"            coalesce((t2.grn_gvn_amt),\n" + 
			"            0)as grn_gvn_amt \n" + 
			"        from\n" + 
			"            (Select\n" + 
			"                t.sp_id,\n" + 
			"                m.sp_name,\n" + 
			"                t.sp_selected_weight,\n" + 
			"                count(*) as sp_qty,\n" + 
			"               SUM(d.grand_total)   as sp_value \n" + 
			"            from\n" + 
			"                t_sp_cake t,\n" + 
			"                m_sp_cake m ,t_bill_detail d" + 
			"            where\n" + 
			"                t.del_status=0 \n" + 
			"                and m.sp_id=t.sp_id \n" + 
			"                and t.is_bill_generated=2 and  d.order_id=t.sp_order_no and d.cat_id=5" + 
			"                and  t.fr_id IN(\n" + 
			"                :frId\n" + 
			"                ) \n" + 
			"                and t.sp_delivery_date between :fromDate and  :toDate\n" + 
			"            group by\n" + 
			"                t.sp_id,\n" + 
			"                t.sp_selected_weight \n" + 
			"            order by\n" + 
			"                t.sp_id,\n" + 
			"                m.sp_name,\n" + 
			"                t.sp_selected_weight) t1 \n" + 
			"        LEFT JOIN\n" + 
			"            (\n" + 
			"                select\n" + 
			"                    t_credit_note_details.item_id as sp_id,\n" + 
			"                    count(*) as crn_qty,\n" + 
			"                    (select\n" + 
			"                        t.sp_selected_weight \n" + 
			"                    from\n" + 
			"                        t_sp_cake t \n" + 
			"                    where\n" + 
			"                        t.sp_order_no=(\n" + 
			"                            select\n" + 
			"                                order_id \n" + 
			"                            from\n" + 
			"                                t_bill_detail \n" + 
			"                            where\n" + 
			"                                t_bill_detail.bill_detail_no=(\n" + 
			"                                    select\n" + 
			"                                        bill_detail_no \n" + 
			"                                    from\n" + 
			"                                        t_grn_gvn \n" + 
			"                                    where\n" + 
			"                                        t_grn_gvn.grn_gvn_id=t_credit_note_details.grn_gvn_id\n" + 
			"                                )\n" + 
			"                            )\n" + 
			"                    ) as crn_selected_weight,  SUM(t_credit_note_details.grn_gvn_amt) as grn_gvn_amt  \n" + 
			"                from\n" + 
			"                    t_credit_note_details,\n" + 
			"                    t_credit_note_header \n" + 
			"                where\n" + 
			"                    t_credit_note_details.cat_id=5 \n" + 
			"                    and t_credit_note_header.crn_date between :fromDate and  :toDate\n" + 
			"                    and t_credit_note_header.fr_id IN(\n" + 
			"                      :frId\n" + 
			"                    ) \n" + 
			"                    and t_credit_note_header.crn_id=t_credit_note_details.crn_id  \n" + 
			"                group by\n" + 
			"                    t_credit_note_details.item_id,\n" + 
			"                    crn_selected_weight) t2 \n" + 
			"                        ON t1.sp_id = t2.sp_id \n" + 
			"                        and t1.sp_selected_weight=t2.crn_selected_weight \n" + 
			"                UNION\n" + 
			"                select\n" + 
			"                    t2.sp_id as id,\n" + 
			"                    t2.sp_name,\n" + 
			"                    t2.crn_selected_weight as sp_selected_weight,\n" + 
			"                    coalesce((t1.sp_qty),\n" + 
			"                    0) as sp_qty,\n" + 
			"                    coalesce((t1.sp_value),\n" + 
			"                    0)as sp_value,\n" + 
			"                    coalesce((t2.crn_qty),\n" + 
			"                    0) as crn_qty,\n" + 
			"                    coalesce((t2.crn_selected_weight),\n" + 
			"                    0) as crn_selected_weight,\n" + 
			"                    coalesce((t2.grn_gvn_amt),\n" + 
			"                    0) as grn_gvn_amt \n" + 
			"                from\n" + 
			"                    (Select\n" + 
			"                        t.sp_id,\n" + 
			"                        m.sp_name,\n" + 
			"                        t.sp_selected_weight,\n" + 
			"                        count(*) as sp_qty,\n" + 
			"                          SUM(d.grand_total)   as sp_value \n" + 
			"                    from\n" + 
			"                        t_sp_cake t,\n" + 
			"                        m_sp_cake m ,t_bill_detail d" + 
			"                    where\n" + 
			"                        t.del_status=0 \n" + 
			"                        and m.sp_id=t.sp_id \n" + 
			"                        and t.is_bill_generated=2 and  d.order_id=t.sp_order_no and d.cat_id=5" + 
			"                        and  t.fr_id IN(\n" + 
			"                           :frId\n" + 
			"                        ) \n" + 
			"                        and t.sp_delivery_date between :fromDate and  :toDate\n" + 
			"                    group by\n" + 
			"                        t.sp_id,\n" + 
			"                        t.sp_selected_weight \n" + 
			"                    order by\n" + 
			"                        t.sp_id,\n" + 
			"                        m.sp_name,\n" + 
			"                        t.sp_selected_weight) t1 \n" + 
			"                RIGHT JOIN\n" + 
			"                    (\n" + 
			"                        select\n" + 
			"                            t_credit_note_details.item_id as sp_id,\n" + 
			"                            count(*) as crn_qty,\n" + 
			"                            m_sp_cake.sp_name,\n" + 
			"                            (select\n" + 
			"                                t.sp_selected_weight \n" + 
			"                            from\n" + 
			"                                t_sp_cake t \n" + 
			"                            where\n" + 
			"                                t.sp_order_no=(\n" + 
			"                                    select\n" + 
			"                                        order_id \n" + 
			"                                    from\n" + 
			"                                        t_bill_detail \n" + 
			"                                    where\n" + 
			"                                        t_bill_detail.bill_detail_no=(\n" + 
			"                                            select\n" + 
			"                                                bill_detail_no \n" + 
			"                                            from\n" + 
			"                                                t_grn_gvn \n" + 
			"                                            where\n" + 
			"                                                t_grn_gvn.grn_gvn_id=t_credit_note_details.grn_gvn_id\n" + 
			"                                        )\n" + 
			"                                    )\n" + 
			"                            ) as crn_selected_weight, SUM(t_credit_note_details.grn_gvn_amt) as grn_gvn_amt \n" + 
			"                        from\n" + 
			"                            t_credit_note_details,\n" + 
			"                            t_credit_note_header,\n" + 
			"                            m_sp_cake \n" + 
			"                        where\n" + 
			"                            t_credit_note_details.cat_id=5  \n" + 
			"                            and t_credit_note_header.fr_id IN(\n" + 
			"                              :frId\n" + 
			"                            ) \n" + 
			"                            and t_credit_note_header.crn_date  between :fromDate and  :toDate\n" + 
			"                            and t_credit_note_header.crn_id=t_credit_note_details.crn_id \n" + 
			"                            and m_sp_cake.sp_id=t_credit_note_details.item_id \n" + 
			"                        group by\n" + 
			"                            t_credit_note_details.item_id,\n" + 
			"                            crn_selected_weight) t2 \n" + 
			"                                ON t1.sp_id = t2.sp_id \n" + 
			"                                and t1.sp_selected_weight=t2.crn_selected_weight ) a \n" + 
			"                    order by\n" + 
			"                        sp_selected_weight",nativeQuery=true)
	List<SpKgSummaryDao> getSpKgSummaryReport(@Param("fromDate")String fromDate,@Param("toDate") String toDate,@Param("frId") List<Integer> frId);

}
