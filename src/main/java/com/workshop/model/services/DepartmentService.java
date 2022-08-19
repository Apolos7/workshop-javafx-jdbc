package com.workshop.model.services;

import java.util.List;

import com.workshop.model.dao.DaoFactory;
import com.workshop.model.dao.DepartmentDao;
import com.workshop.model.entities.Department;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        List<Department> list = dao.findAll();
        return list;
    }

}