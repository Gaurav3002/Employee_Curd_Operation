package net.javaguide.springboot_Webapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.javaguide.springboot_Webapp.model.Employee;
import net.javaguide.springboot_Webapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    //display list of employee
    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Employee> list = employeeService.getAllEmployee();
        model.addAttribute("listEmployee", list);
        return "index";
    }

    @GetMapping("/showEmpForm")
    public String showEmpForm(Model model){
        //create a model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee ){
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/showFormUpdate/{id}")
    public String showFormUpdate(@PathVariable(value = "id") long id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        // set model as a modelattribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }
    @GetMapping("/deleteEmp/{id}")
public String deleteEmp(@PathVariable(value = "id") long id){
        this.employeeService.deleteEmpById(id);
        return "redirect:/";

}
@GetMapping("/export/csv")
    public String exportCsv(HttpServletResponse response, Model model) throws IOException {
        // Fetch employee data
        List<Employee> employeeList = employeeService.getAllEmployee();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=employees.csv");
         if(employeeList.isEmpty()){

             try(OutputStream outputStream = response.getOutputStream()){
                 String header = "No data available";
                 outputStream.write(header.getBytes());
             }
            model.addAttribute("downloadStatus", "No data available for download");

         }else{
             try (OutputStream outputStream = response.getOutputStream()) {
                 writeCsv(employeeList, outputStream);
             }
             model.addAttribute("downloadStatus", "file download successfully");

         }
         return "redirect:/";
    }

    private void writeCsv(List<Employee> employeeList, OutputStream outputStream) throws IOException {
        StringBuilder csvData = new StringBuilder();

        // Add CSV header (column names)
        csvData.append("ID,First Name,Last Name,EmailID\n");

        // Add each employee's data as a CSV row
        for (Employee employee : employeeList) {
            csvData.append(employee.getId()).append(",")
                    .append(employee.getFirstName()).append(",")
                    .append(employee.getLastName()).append(",")
                    .append(employee.getEmail()).append(",");
        }

        // Write the CSV data to the output stream
        outputStream.write(csvData.toString().getBytes());
    }

}
