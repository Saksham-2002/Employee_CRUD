package com.gl.employee.Service;

import com.gl.employee.Entity.Employee;
import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(Employee employee);

    public Employee getEmployeeById(Long id);

    public List<Employee> getAllEmployees();

    public Employee updateEmployeeById(Long id, Employee updatedEmployee);

    public void deleteEmployee(Long id);


}
