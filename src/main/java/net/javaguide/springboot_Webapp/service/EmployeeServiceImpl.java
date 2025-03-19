package net.javaguide.springboot_Webapp.service;


import net.javaguide.springboot_Webapp.model.Employee;
import net.javaguide.springboot_Webapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
  private EmployeeRepository emprepo;

    @Override
    public List<Employee> getAllEmployee() {
        return  emprepo.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.emprepo.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = emprepo.findById(id);
        Employee employee = null;
        if(optional.isPresent()){
            employee = optional.get();
        }else{
            throw new RuntimeException("Employee Not Found");
        }
        return employee;
    }



    @Override
    public void deleteEmpById(long id) {
       this.emprepo.deleteById(id);

    }
}
