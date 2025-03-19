package net.javaguide.springboot_Webapp.service;

import net.javaguide.springboot_Webapp.model.Employee;
import java.util.*;

public interface EmployeeService {
    List<Employee> getAllEmployee();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmpById(long id);
}
