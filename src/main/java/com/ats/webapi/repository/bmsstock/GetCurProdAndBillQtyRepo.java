package com.ats.webapi.repository.bmsstock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.webapi.model.stock.GetCurProdAndBillQty;

public interface GetCurProdAndBillQtyRepo extends JpaRepository<GetCurProdAndBillQty, Integer> {
	// Prev Query
	@Query(value = " SELECT m_item.id,m_item.item_name, \n"
			+ "coalesce((Select SUM(t_production_plan_detail.production_qty) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate "
			+ "AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id "
			+ "AND m_item.id=t_production_plan_detail.item_id),0) AS prod_qty, \n" + "\n"
			+ "coalesce((Select SUM(t_production_plan_detail.rejected_qty) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate AND"
			+ " t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id AND"
			+ " m_item.id=t_production_plan_detail.item_id),0) AS rejected_qty,\n" + "\n"
			+ "coalesce((Select SUM( t_bill_detail.bill_qty) FROM t_bill_header,t_bill_detail"
			+ " WHERE t_bill_header.remark BETWEEN :timestamp AND :curTimeStamp  AND t_bill_header.bill_no=t_bill_detail.bill_no "
			+ "AND m_item.id=t_bill_detail.item_id),0) AS bill_qty,"

			+ "coalesce((Select SUM(t_production_plan_detail.int5) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate AND"
			+ " t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id AND"
			+ " m_item.id=t_production_plan_detail.item_id),0) AS damaged_qty \n" + "\n"
			+ "FROM m_item where m_item.item_grp1=:catId AND m_item.del_status=:delStatus " + "", nativeQuery = true)
	List<GetCurProdAndBillQty> getCurProdAndBillQty(@Param("prodDate") String prodDate, @Param("catId") int catId,
			@Param("delStatus") int delStatus, @Param("timestamp") String timestamp,
			@Param("curTimeStamp") String curTimeStamp);

//New Query

	// changed for getting All Items without catIds 27 Jan 2018

	@Query(value = " SELECT m_item.id,m_item.item_name, \n"
			+ "coalesce((Select SUM(t_production_plan_detail.production_qty) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate "
			+ "AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id "
			+ "AND m_item.id=t_production_plan_detail.item_id),0) AS prod_qty, \n" + "\n"
			+ "coalesce((Select SUM(t_production_plan_detail.rejected_qty) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate AND"
			+ " t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id AND"
			+ " m_item.id=t_production_plan_detail.item_id),0) AS rejected_qty,\n" + "\n"
			+ "coalesce((Select SUM( t_bill_detail.bill_qty) FROM t_bill_header,t_bill_detail"
			+ " WHERE t_bill_header.remark BETWEEN :timestamp AND :curTimeStamp  AND t_bill_header.bill_no=t_bill_detail.bill_no "
			+ "AND m_item.id=t_bill_detail.item_id),0) AS bill_qty,"

			+ "coalesce((Select SUM(t_production_plan_detail.int5) FROM t_production_plan_detail,"
			+ "t_production_plan_header where t_production_plan_header.production_date=:prodDate AND"
			+ " t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id AND"
			+ " m_item.id=t_production_plan_detail.item_id),0) AS damaged_qty \n" + "\n"
			+ "FROM m_item where m_item.del_status=:delStatus " + "", nativeQuery = true)
	List<GetCurProdAndBillQty> getCurProdAndBillQtyAllCat(@Param("prodDate") String prodDate,
			@Param("delStatus") int delStatus, @Param("timestamp") String timestamp,
			@Param("curTimeStamp") String curTimeStamp);

	@Query(value = " SELECT\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.production_qty) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS prod_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.rejected_qty) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS rejected_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM( t_order.order_qty) \n" + 
			"        FROM\n" + 
			"            t_order\n" + 
			"        WHERE\n" + 
			"            t_order.production_date=:prodDate \n" + 
			"            AND m_item.id=t_order.item_id and t_order.menu_id IN (:menuId)),\n" + 
			"        0) AS bill_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.int5) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate  \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS damaged_qty   \n" + 
			"    FROM\n" + 
			"        m_item \n" + 
			"    where\n" + 
			"        m_item.item_grp1=:catId\n" + 
			"        AND m_item.del_status=:delStatus ", nativeQuery = true)
	List<GetCurProdAndBillQty> getCurProdAndOrderQty(@Param("prodDate") String prodDate, @Param("catId") int catId,
			@Param("delStatus") int delStatus, @Param("menuId") List<String> menuId);

	@Query(value = " SELECT\n" + 
			"        m_item.id,\n" + 
			"        m_item.item_name,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.production_qty) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS prod_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.rejected_qty) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS rejected_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM( t_order.order_qty) \n" + 
			"        FROM\n" + 
			"            t_order\n" + 
			"        WHERE\n" + 
			"            t_order.production_date=:prodDate \n" + 
			"            AND m_item.id=t_order.item_id and t_order.menu_id IN (:menuId)),\n" + 
			"        0) AS bill_qty,\n" + 
			"        coalesce((Select\n" + 
			"            SUM(t_production_plan_detail.int5) \n" + 
			"        FROM\n" + 
			"            t_production_plan_detail,\n" + 
			"            t_production_plan_header \n" + 
			"        where\n" + 
			"            t_production_plan_header.production_date=:prodDate  \n" + 
			"            AND t_production_plan_header.production_header_id=t_production_plan_detail.production_header_id \n" + 
			"            AND m_item.id=t_production_plan_detail.item_id),\n" + 
			"        0) AS damaged_qty   \n" + 
			"    FROM\n" + 
			"        m_item \n" + 
			"    where " + 
		"m_item.del_status=:delStatus ", nativeQuery = true)
	List<GetCurProdAndBillQty> getCurProdAndOrderQtyAllCat(@Param("prodDate") String prodDate,
			@Param("delStatus") int delStatus, @Param("menuId") List<String> menuId);

}
