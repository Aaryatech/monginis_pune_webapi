package com.ats.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ats.webapi.model.bill.Expense;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

	
	@Query(value = "SELECT m_expense.exp_id,\n" + 
			"    m_expense.chalan_no,\n" + 
			"    m_expense.exp_type,\n" + 
			"    m_expense.img_name,\n" + 
			"    m_expense.ch_amt,\n" + 
			"    m_expense.fr_id,\n" + 
			"    m_expense.del_status,\n" + 
			"    m_expense.remark,\n" + 
			"    m_expense.status,\n" + 
			"    m_expense.exp_date,\n" + 
			"    m_expense.ex_int1,\n" + 
			"    m_expense.ex_int2,\n" + 
			"    m_expense.ex_int3,\n" + 
			"    m_expense.ex_int4,\n" + 
			"    m_expense.ex_var1,\n" + 
			"    m_expense.ex_var2,\n" + 
			"    m_expense.ex_var3,\n" + 
			"    m_expense.ex_var4\n" + 
			" from m_expense where   m_expense.del_status=0 AND m_expense.fr_id=:frId AND m_expense.exp_type=:type AND m_expense.exp_date BETWEEN :fromDate AND :toDate  ", nativeQuery = true)
 	List<Expense> getExpenseList(@Param("frId") int frId,@Param("type") int type,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
 	
	@Query(value = "SELECT m_expense.exp_id,\n" + 
			"    m_expense.chalan_no,\n" + 
			"    m_expense.exp_type,\n" + 
			"    m_expense.img_name,\n" + 
			"    m_expense.ch_amt,\n" + 
			"    m_expense.fr_id,\n" + 
			"    m_expense.del_status,\n" + 
			"    m_expense.remark,\n" + 
			"    m_expense.status,\n" + 
			"    m_expense.exp_date,\n" + 
			"    m_expense.ex_int1,\n" + 
			"    m_expense.ex_int2,\n" + 
			"    m_expense.ex_int3,\n" + 
			"    m_expense.ex_int4,\n" + 
			"    m_expense.ex_var1,\n" + 
			"    m_expense.ex_var2,\n" + 
			"    m_expense.ex_var3,\n" + 
			"    m_expense.ex_var4\n" + 
			" from m_expense where  m_expense.del_status=0 AND m_expense.exp_date BETWEEN :fromDate AND :toDate AND exp_type=:type AND m_expense.fr_id IN(:frIdList) AND m_expense.ex_int1=0 ", nativeQuery = true)
	List<Expense> getAllExpenseList(@Param("type") int type,@Param("fromDate") String fromDate,@Param("toDate") String toDate,@Param("frIdList") List<String> frIdList);
	
	@Query(value = "SELECT m_expense.exp_id,\n" + 
			"    m_expense.chalan_no,\n" + 
			"    m_expense.exp_type,\n" + 
			"    m_expense.img_name,\n" + 
			"    m_expense.ch_amt,\n" + 
			"    m_expense.fr_id,\n" + 
			"    m_expense.del_status,\n" + 
			"    m_expense.remark,\n" + 
			"    m_expense.status,\n" + 
			"    m_expense.exp_date,\n" + 
			"    m_expense.ex_int1,\n" + 
			"    m_expense.ex_int2,\n" + 
			"    m_expense.ex_int3,\n" + 
			"    m_expense.ex_int4,\n" + 
			"    m_expense.ex_var1,\n" + 
			"    m_expense.ex_var2,\n" + 
			"    m_expense.ex_var3,\n" + 
			"    m_expense.ex_var4\n" + 
			" from m_expense where  m_expense.del_status=0  AND m_expense.exp_date BETWEEN :fromDate AND :toDate AND exp_type=:type AND m_expense.ex_int1=0", nativeQuery = true)
	List<Expense> getAllExpenseList(@Param("type") int type,@Param("fromDate") String fromDate,@Param("toDate") String toDate);
	
	 
	
	
	@Query(value = "SELECT m_expense.exp_id,\n" + 
			"    m_expense.chalan_no,\n" + 
			"    m_expense.exp_type,\n" + 
			"    m_expense.img_name,\n" + 
			"    m_expense.ch_amt,\n" + 
			"    m_expense.fr_id,\n" + 
			"    m_expense.del_status,\n" + 
			"    m_expense.remark,\n" + 
			"    m_expense.status,\n" + 
			"    m_expense.exp_date,\n" + 
			"    m_expense.ex_int1,\n" + 
			"    m_expense.ex_int2,\n" + 
			"    m_expense.ex_int3,\n" + 
			"    m_expense.ex_int4,\n" + 
			"    m_expense.ex_var1,\n" + 
			"    m_expense.ex_var2,\n" + 
			"    m_expense.ex_var3,\n" + 
			"    m_expense.ex_var4\n" + 
			" from m_expense where   m_expense.del_status=0 AND m_expense.fr_id=:frId", nativeQuery = true)
 	List<Expense> getExpenseList(@Param("frId") int frId);
	
	
	

	@Transactional
	@Modifying
	@Query(" UPDATE Expense SET del_status=1 WHERE exp_id=:suppId")
	int deleteExpense(@Param("suppId")int suppId);

	 Expense findByExpId(int expId);
	 
	 
	 

		@Transactional
		@Modifying
		@Query(" UPDATE Expense SET status=2 WHERE exp_id=:expId")
		int updateExpStatus(@Param("expId")int expId);
		
		@Transactional
		@Modifying
		@Query(" UPDATE Expense SET ex_int1=:flag, ex_var1=:billIds  WHERE exp_id =:expId")
	 	int updateExpenseBillSettle(@Param("expId") int expId,@Param("billIds") String billIds,@Param("flag") int flag);

		
		@Query(value = "SELECT COALESCE(SUM(ch_amt),0) FROM m_expense WHERE fr_id=:frId AND exp_date=:date AND del_status=0", nativeQuery = true)
		float getTotalExpenseByFrAndDate(@Param("frId") int frId,@Param("date") String date);
		
		
		@Query(value = "SELECT e.exp_id,e.chalan_no,e.exp_type,e.img_name,e.ch_amt,e.fr_id,e.del_status,e.remark,e.status,e.exp_date,e.ex_int1,e.ex_int2,e.ex_int3,e.ex_int4,t.exp_type_name as ex_var1,e.ex_var2,e.ex_var3,e.ex_var4 from m_expense e,m_expense_type t WHERE e.del_status=0 AND e.exp_type=t.exp_type_id AND e.exp_id IN(:ids)", nativeQuery = true)
	 	List<Expense> getExpenseListByIds(@Param("ids") List<Integer> ids);
		
}
