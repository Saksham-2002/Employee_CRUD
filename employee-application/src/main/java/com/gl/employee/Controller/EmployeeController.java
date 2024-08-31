package com.gl.employee.Controller;


import com.gl.employee.Entity.Employee;
import com.gl.employee.Service.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/employees")
@CacheConfig(cacheNames = "employees")
public class EmployeeController {

    private static final Logger logger = getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee)
    {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Cacheable(key="#id")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id)
    {
        logger.info("Fetching employee from database with id {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Cacheable("employees")
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @CachePut(key="#id")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee)
    {
        Employee employee = employeeService.updateEmployeeById(id, updatedEmployee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(key="#id")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully with id "+ id, HttpStatus.OK);
    }

}
