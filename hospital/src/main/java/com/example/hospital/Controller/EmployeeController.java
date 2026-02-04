package com.example.hospital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Entity.Employee;
import com.example.hospital.Repository.EmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository empRep;

    @GetMapping
    public List<Employee> getAll() {
        return empRep.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return empRep.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return empRep.save(employee);
    }

    @PostMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee emp = empRep.findById(id).orElse(null);
        if (emp != null) {
            emp.setName(employee.getName());
            emp.setPosition(employee.getPosition());
            emp.setHireDate(employee.getHireDate());
            return empRep.save(emp);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        empRep.deleteById(id);
        return "Employee deleted successfully";
    }
}
