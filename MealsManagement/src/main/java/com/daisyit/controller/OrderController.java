package com.daisyit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;

import com.daisyit.db.hibernate.*;
import com.daisyit.utils.Util;
import com.daisyit.db.hibernate.HibetnateUtil;
import com.daisyit.entity.Catering;
import com.daisyit.entity.CateringId;
import com.daisyit.entity.Staff;

@ManagedBean(name = "orderController")
@ViewScoped
public class OrderController {

	private Date cateringDate;
	private String staffName;
	private String department;
	private List<MealOrder> orderList;
	private HibernateStaffDAO hStaffDao;
	private Session session;
	private HibernateMealDAO hMealDao ;


	private List<String> mealTimes;

	private List<String> mealTypes;

	private List<String> localtions;

	@PostConstruct
	public void init() {
		List<Staff> staffs;

		mealTypes = new ArrayList<>();
		mealTimes = new ArrayList<>();
		localtions = new ArrayList<>();
		session = HibetnateUtil.openSession();
		hMealDao = new HibernateMealDAO();
		hStaffDao = new HibernateStaffDAO();
		hMealDao.setSession(session);
		hStaffDao.setSession(session);

		mealTimes.add("Lunch");
		mealTimes.add("Dinner");
		mealTimes.add("Supper,Breakfast");

		mealTypes = hMealDao.getMealNames();

		orderList = new ArrayList<>();
		staffs = hStaffDao.getAllStaffs("F");
		for (Staff staff : staffs) {
			MealOrder mealOrder = new MealOrder();
			mealOrder.setStaffId(staff.getStaffId());
			mealOrder.setStaffName(staff.getName());
			mealOrder.setLocation(staff.getCountry());
			mealOrder.setStatus(false);
			orderList.add(mealOrder);
		}
		localtions.add("Ha Noi");
	}

	public void addClickListener() {
		orderList.add(
				new MealOrder("1111", "Chu Trong Khanh", "Lunch", "VietNam", Util.getCurrentDate().toString(), "Ha Noi", false));
	}

	public void submitClickListener() {
		if (session.isConnected()) {
			java.sql.Date date = Util.getCurrentDate();
			List<Catering> cateringList = new ArrayList<>();
			HibernateCateringDAO hCateringDao = new HibernateCateringDAO();
			
			hCateringDao.setSession(session);
			System.err.println("22222222222222222" + orderList.size());
			for (MealOrder mealOrder : orderList) {
				String mealId = hMealDao.getMealId(mealOrder.getMealType());
				mealId = "JP";
				cateringList.add(new Catering(new CateringId(mealOrder.getStaffId(), mealOrder.getMealTime(), date), mealId, "12", "12",
						false, "12", mealOrder.getStatus(), "12", "12", date, date));
			}
			if (cateringList != null)
				hCateringDao.addMultiCaterings(cateringList);
			HibetnateUtil.closeSession(session);
		}
		
	}

	public List<String> getLocaltions() {
		return localtions;
	}

	public void setLocaltions(List<String> localtions) {
		this.localtions = localtions;
	}

	public List<String> getMealTimes() {
		return mealTimes;
	}

	public void setMealTimes(List<String> mealTimes) {
		this.mealTimes = mealTimes;
	}

	public List<String> getMealTypes() {
		return mealTypes;
	}

	public void setMealTypes(List<String> mealTypes) {
		this.mealTypes = mealTypes;
	}

	public Date getCateringDate() {
		return cateringDate;
	}

	public void setCateringDate(Date cateringDate) {
		this.cateringDate = cateringDate;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<MealOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<MealOrder> orderList) {
		this.orderList = orderList;
	}

}
