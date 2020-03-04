package com.ats.webapi.repository.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.prod.GetProdVariation;

public interface GetProdVariationRepo extends JpaRepository<GetProdVariation, Integer> {

	@Query(value=" SELECT  a.id,a.item_id,a.item_name," + 
			" COALESCE(b.op_total,0) AS op_total," + 
			" COALESCE(c.production_qty,0)AS production_qty," + 
			" COALESCE(c.rejected_qty,0) AS rejected_qty," + 
			" COALESCE(c.remaining_qty,0) AS remaining_qty," + 
			" COALESCE(d.sel_menu_order_qty,0) AS sel_menu_order_qty," + 
			" COALESCE(e.other_menu_order_qty,0) AS other_menu_order_qty " + 
			"" + 
			" FROM " + 
			" ( SELECT m_item.id,m_item.item_id,m_item.item_name,m_cat_sub.prefix FROM m_item,m_category,m_cat_sub WHERE m_item.item_grp1=m_category.cat_id AND m_cat_sub.sub_cat_id=m_item.item_grp2 and m_item.del_status=0 and m_item.item_is_used!=4 and m_category.cat_id=2 )  a " + 
			" " + 
			" LEFT JOIN (SELECT finished_good_stock_detail.op_total,finished_good_stock_detail.item_id FROM finished_good_stock_detail,finished_good_stock  WHERE finished_good_stock_detail.fin_stock_id=finished_good_stock.fin_stock_id AND finished_good_stock.fin_good_stock_status=0 and finished_good_stock.fin_good_stock_date=:date) b on a.id=b.item_id " + 
			"" + 
			" LEFT JOIN (SELECT SUM(t_production_plan_detail.production_qty) as production_qty ,SUM(t_production_plan_detail.rejected_qty) as rejected_qty,SUM(t_production_plan_detail.remaining_qty) as remaining_qty ,t_production_plan_detail.item_id FROM t_production_plan_header,t_production_plan_detail WHERE t_production_plan_detail.production_header_id=t_production_plan_header.production_header_id AND t_production_plan_header.production_status>3  AND  t_production_plan_header.production_date=:date GROUP BY  t_production_plan_detail.item_id ) c on a.id=c.item_id " + 
			"" + 
			"" + 
			" LEFT JOIN  (SELECT SUM(t_order.order_qty) as sel_menu_order_qty,t_order.item_id FROM t_order WHERE  t_order.production_date=:date AND t_order.menu_id=:menuId GROUP BY t_order.item_id) d on a.id=d.item_id " + 
			"" + 
			" LEFT JOIN  (SELECT SUM(t_order.order_qty) as other_menu_order_qty,t_order.item_id FROM t_order WHERE  t_order.production_date=:date AND t_order.menu_id IN (:selectedMenu) GROUP BY t_order.item_id) e on a.id=e.item_id " + 
			"" + 
			" GROUP by a.id order BY a.prefix,a.item_name asc " + 
			"",nativeQuery=true)
	
	List<GetProdVariation> getGetProdVariation(@Param("date") String date, @Param("menuId") String menuId,@Param("selectedMenu") List<String> selectedMenu);
	
}
