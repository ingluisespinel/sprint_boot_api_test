package com.lespinel.springboot.rest_api_test.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lespinel.springboot.rest_api_test.entities.User;
import com.lespinel.springboot.rest_api_test.entities.UserRepository;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable(value="id") Integer id)
	throws Exception
	{
		return userRepo.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"USUARIO NO ENCONTRADO"));
	}
	
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		return userRepo.save(user);
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(value="id") Integer id,@Valid @RequestBody User user) {
		User userToUpdate=userRepo.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"USUARIO NO ENCONTRADO"));
		userToUpdate.setName(user.getName());
		userToUpdate.setAge(user.getAge());
		return userRepo.save(userToUpdate);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") Integer id){
		User userToDelete=userRepo.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"USUARIO NO ENCONTRADO"));
		userRepo.delete(userToDelete);
		return ResponseEntity.ok().build();
	}

}
