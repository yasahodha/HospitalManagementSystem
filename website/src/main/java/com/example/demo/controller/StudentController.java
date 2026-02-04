package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Controller
public class StudentController {

	private final StudentRepository repo;
	
	public StudentController(StudentRepository repo) {
		this.repo =repo;
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/add")
	public String addStudentForm(Model model) {
		model.addAttribute("student", new Student());
		return "add-student";
	}
	
	@PostMapping("/save")
	public String saveStudent(@ModelAttribute Student student) {
		System.out.println(student);
		repo.save(student);
		return "redirect:/students";
	}
	
	@PostMapping("/update")
	public String updateStudent(@ModelAttribute Student student) {
		repo.save(student);
		return "redirect:/students";
	}

	@PostMapping("/delete")
	public String deleteStudent(@ModelAttribute Student student) {
		repo.delete(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("student", repo.findAll());
		return "students";
	}
}
