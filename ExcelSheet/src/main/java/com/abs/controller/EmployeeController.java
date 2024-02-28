package com.abs.controller;

import com.abs.model.Employee;
import com.abs.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeServiceimpl;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadexcel(@RequestParam("sheet") MultipartFile  sheet) {

        if (sheet.isEmpty()) {

            return ResponseEntity.ok("Please select a file to upload");

        }
        try {


            List<Employee> employees = parsing(sheet.getInputStream());

            employeeServiceimpl.saveAllEmployees(employees);

            return ResponseEntity.ok("file uploaded successfully");
        } catch (IOException e) {
            return new ResponseEntity<>("faild", HttpStatus.valueOf(e.getMessage()));
        }

    }

    private List<Employee> parsing(InputStream inputStream) throws IOException {

        Workbook webk = WorkbookFactory.create(inputStream);

        Sheet sheet = webk.getSheetAt(0);

        List<Employee> employees = new ArrayList<>();



        for (Row row : sheet) {


            if (row.getRowNum() == 0) continue;



            Employee employee = Employee.builder()
                    .name(row.getCell(0).getStringCellValue())
                    .salary(row.getCell(1).getNumericCellValue())
                    .build();

            employees.add(employee);
        }

        webk.close();
        return employees;
    }



    @GetMapping("/employ")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        return ResponseEntity.ok(employeeServiceimpl.getAllEmp());
    }


}
