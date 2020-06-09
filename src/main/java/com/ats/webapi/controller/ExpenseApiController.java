package com.ats.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ats.webapi.model.Info;
import com.ats.webapi.model.bill.Expense;
import com.ats.webapi.model.bill.ExpenseType;
import com.ats.webapi.repository.ExpenseRepo;
import com.ats.webapi.repository.ExpenseTypeRepo;

@RestController
public class ExpenseApiController {

	@Autowired
	ExpenseRepo expenseRepo;
	
	@Autowired
	ExpenseTypeRepo expenseTypeRepo;

	@RequestMapping(value = { "/saveExpense" }, method = RequestMethod.POST)
	@ResponseBody
	public Info saveExpense(@RequestBody Expense routeMaster) {

		Info info = new Info();
		Expense jsonResult = expenseRepo.save(routeMaster);

		try {
			if (jsonResult != null) {
				info.setError(false);
				info.setMessage("Success");
			} else {
				info.setError(true);
				info.setMessage("Failed");
			}

		} catch (Exception e) {

			info.setError(true);
			info.setMessage("Failed");

		}

		return info;
	}

	@RequestMapping(value = "/getExpenseByFrId", method = RequestMethod.POST)
	public @ResponseBody List<Expense> getExpense(@RequestParam("frId") int frId, @RequestParam("type") int type,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		List<Expense> expList = null;

		try {

			if (fromDate == null || toDate == null || type == 0) {
				expList = expenseRepo.getExpenseList(frId);

			} else {
				expList = expenseRepo.getExpenseList(frId, type, fromDate, toDate);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return expList;

	}

	@RequestMapping(value = "/getAllExpense", method = RequestMethod.POST)
	public @ResponseBody List<Expense> getAllExpense(@RequestParam("type") int type,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("frIdList") List<String> frIdList) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		List<Expense> grnItemConfigList = null;

		try {
			System.err.println("frIdList" + frIdList.toString());

			if (frIdList.contains("-1")) {
				grnItemConfigList = expenseRepo.getAllExpenseList(type, fromDate, toDate);
			} else {
				grnItemConfigList = expenseRepo.getAllExpenseList(type, fromDate, toDate, frIdList);

			}

			System.out.println("grn Item getItemForManualGrn  Rest: " + grnItemConfigList.toString());

		} catch (Exception e) {

			System.out.println(
					"restApi Exce for Getting Man GRN Item Conf /getItemsForManGrnByFrAndBill" + e.getMessage());
			e.printStackTrace();
		}

		return grnItemConfigList;

	}

	@RequestMapping(value = { "/deleteExpense" }, method = RequestMethod.POST)
	public @ResponseBody Info deleteExpense(@RequestParam("expId") int expId) {

		Info info = new Info();
		try {

			int delete = expenseRepo.deleteExpense(expId);

			if (delete == 1) {
				info.setError(false);
				info.setMessage("deleted Successfully ");
			} else {
				info.setError(true);
				info.setMessage("deleted UnSuccessfully ");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return info;

	}

	@RequestMapping(value = "/getExpenseByExpId", method = RequestMethod.POST)
	public @ResponseBody Expense getExpenseByExpId(@RequestParam("expId") int expId) {
		System.out.println("inside rest /getItemsForManGrnByFrAndBill");
		Expense grnItemConfigList = null;

		try {

			grnItemConfigList = expenseRepo.findByExpId(expId);

		} catch (Exception e) {

			System.out.println(
					"restApi Exce for Getting Man GRN Item Conf /getItemsForManGrnByFrAndBill" + e.getMessage());
			e.printStackTrace();
		}

		return grnItemConfigList;

	}
	
	
	@RequestMapping(value = "/getAllExpenseType", method = RequestMethod.POST)
	public @ResponseBody List<ExpenseType> getAllExpenseType() {
		List<ExpenseType> expList = null;

		try {
				expList = expenseTypeRepo.findAllByIsActiveAndDelStatus(0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return expList;

	}
	
	@RequestMapping(value = "/getTotalExpenseByFrAndDate", method = RequestMethod.POST)
	public @ResponseBody float getTotalExpenseByFrAndDate(@RequestParam("frId") int frId,@RequestParam("date") String date) {
		float res=0;

		try {
				res = expenseRepo.getTotalExpenseByFrAndDate(frId,date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	
	

}
