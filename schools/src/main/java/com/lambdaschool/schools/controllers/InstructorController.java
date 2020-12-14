package com.lambdaschool.schools.controllers;


import com.lambdaschool.schools.models.Instructor;
import com.lambdaschool.schools.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/instructors")
public class InstructorController {
	private final InstructorService instructorService;

	@Autowired
	public InstructorController(InstructorService instructorService) {
		this.instructorService = instructorService;
	}

	@GetMapping(value="/instructor/{instructorid}/advice", produces="application/json")
	public ResponseEntity<?> listInstructorAdvice(@PathVariable long instructorid) {
		Instructor updatedInstructor = instructorService.addAdvice(instructorid);
		return new ResponseEntity<>(updatedInstructor, HttpStatus.OK);
	}

}
