package com.workshop.model.dao;

import com.workshop.db.DB;
import com.workshop.model.dao.impl.DepartmentDaoJDBC;
import com.workshop.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}

}
