package com.abs.service;


import com.abs.dao.EmployeeRepo;
import com.abs.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {


    @Autowired
    private EmployeeRepo employeeRepoImpl;

    public void  saveAllEmployees(List<Employee> employees){
        employeeRepoImpl.saveAll(employees);
    }

    public List<Employee> getAllEmp(){
        return employeeRepoImpl.findAll();
    }


}
