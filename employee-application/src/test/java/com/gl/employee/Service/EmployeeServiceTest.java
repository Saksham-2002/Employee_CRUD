package com.gl.employee.Service;

import com.gl.employee.Entity.Employee;
import com.gl.employee.Repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;

import com.gl.employee.Service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void init() {
        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Saksham");
        employee1.setLastName("Sharma");
        employee1.setEmailId("saksham.sharma@gmail.com");

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Ayush");
        employee2.setLastName("Tyagi");
        employee2.setEmailId("ayush.tyagi@gmail.com");

    }

    @Test
    void createEmployee() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee newEmployee = employeeService.createEmployee(employee1);

        assertNotNull(newEmployee);
        assertThat(newEmployee.getFirstName()).isEqualTo("Saksham");
    }

    @Test
    void getEmployeeById() {

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee1));
        Employee existingEmployee = employeeService.getEmployeeById(employee1.getId());
        assertNotNull(existingEmployee);
        assertThat(existingEmployee.getId()).isNotEqualTo(null);
    }

    @Test
    void getEmployeeByIdForException() {

        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee2));
        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById(employee1.getId());
        });
    }

    @Test
    void updateEmployee() {

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee1));

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        employee1.setFirstName("Ankit");
        Employee exisitingEmployee = employeeService.updateEmployeeById(employee1.getId(), employee1);

        assertNotNull(exisitingEmployee);
        assertEquals("Ankit", employee1.getFirstName());
    }

    @Test
    void deleteEmployee() {

        Long EmployeeId = 1L;
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee1));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        employeeService.deleteEmployee(EmployeeId);

        verify(employeeRepository, times(1)).delete(employee1);

    }

}
