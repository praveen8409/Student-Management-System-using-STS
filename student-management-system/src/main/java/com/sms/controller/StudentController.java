package com.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sms.entity.Student;
import com.sms.service.StudentService;

@Controller
public class StudentController {
	
	private StudentService studentService;
 
	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	// handler method to handle list students and return mode and view
		@GetMapping("students")
		public String listStudents(Model model) {
			model.addAttribute("students",studentService.getAllStudents());
			return "students";
		}
	
		@GetMapping("/students/new")
		public String createStudentForm(Model model) {
			
			// Create Student object to hold student form data
			Student student = new Student();
			
			model.addAttribute("student", student);
			return "create_student";
		}
		
		@PostMapping("/students")
		public String saveStudent(@ModelAttribute("student") Student student) {
			studentService.saveStudent(student);
			return "redirect:/students";
		}
		
		@GetMapping("/students/edit/{id}")
		public String editStudentForm(@PathVariable Long id, Model model) {
			model.addAttribute("student", studentService.getStudentById(id));
			return "edit_student";
		}
		
		@PostMapping("/students/{id}")
		public String updaeStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
			
			// Get student from database by id
			
			Student existStudent = studentService.getStudentById(id);
			
			existStudent.setFirstName(student.getFirstName());
			existStudent.setLastName(student.getLastName());
			existStudent.setEmail(student.getEmail());
			
			// save updatedvstudent object
			studentService.updateStudent(existStudent);
			
			return "redirect:/students";
		}
		
		@GetMapping("/students/{id}")
		public String deleteStudent(@PathVariable Long id) {
			studentService.deleteStudent(id);
			return "redirect:/students";
		}
	
}
