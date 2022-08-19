package com.workshop.model.services;

import java.util.ArrayList;
import java.util.List;

import com.workshop.model.entities.Department;

public class DepartmentService {


    public List<Department> findAll() {
        // MOCK -> fake data for test purpose
        List<Department> list = new ArrayList<>();

        list.add(new Department(1, "Computers"));
        list.add(new Department(2, "Books"));
        list.add(new Department(3, "Eletronics"));
        return list;
    }

}