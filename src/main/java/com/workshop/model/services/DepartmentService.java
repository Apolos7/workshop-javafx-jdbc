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

    public void saveOrUpdate(Department department) {
        if (department.getId() != null) {
            dao.update(department);
        } else {
            dao.insert(department);
        }
    }

}