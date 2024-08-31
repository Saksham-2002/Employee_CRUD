package com.gl.employee.Service.Impl;

import com.gl.employee.Entity.Employee;
import com.gl.employee.Exception.ResourceNotFoundException;
import com.gl.employee.Repository.EmployeeRepository;
import com.gl.employee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Employee Not Found by id " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployeeById(Long id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmailId(updatedEmployee.getEmailId());
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
