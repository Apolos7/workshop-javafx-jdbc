package com.workshop.model.dao;

import java.util.List;

import com.workshop.model.entities.Department;
import com.workshop.model.entities.Seller;

public interface SellerDao {

	void insert(Seller seller);

	void update(Seller seller);

	void deleteById(Integer id);

	Seller findById(Integer id);

	List<Seller> findAll();
	
	List<Seller> findByDepartment(Department department);

}
