package com.example.GreetingApp.controller;

import com.example.GreetingApp.customExceptions.ResourceNotFoundException;
import com.example.GreetingApp.model.Greeting;
import com.example.GreetingApp.repository.GreetingRepository;
import com.example.GreetingApp.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    @Autowired
    private GreetingRepository greetingRepository;
    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public List<Greeting> getGreetings(){
        return greetingRepository.findAll();
    }

    @PostMapping
    public Greeting createGreeting(@RequestBody Greeting greeting){
        return greetingRepository.save(greeting);
    }

    @PutMapping("/{id}")
    public Greeting updateGreeting(@PathVariable Long id, @RequestBody Greeting greetingDetails){
        Greeting greeting = greetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Greeting not found with id " + id));
        greeting.setMessage(greetingDetails.getMessage());
        return greetingRepository.save(greeting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGreeting(@PathVariable Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return ResponseEntity.ok("Greeting deleted successfully!");
        } else {
            throw new ResourceNotFoundException("Greeting not found with id " + id);
        }
    }

    @GetMapping("/simple")
    public String getSimpleGreeting() {
        return greetingService.getSimpleGreeting();
    }

    @PostMapping("/save")
    public Greeting saveGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return greetingService.saveGreeting(firstName, lastName);
    }
    @GetMapping("getId/{id}")
    public Greeting getGreetingById(@PathVariable Long id){
        return greetingService.getGreetById(id);
    }
    @GetMapping("/getAll")
    public List<Greeting> getAllGreetings(){
        return greetingService.getAllGreetings();
    }
    @PutMapping("/updateRepo/{id}")
    public Greeting updateGreetingRepo(@PathVariable Long id, @RequestBody Greeting greetingDetails){
        return greetingService.updateGreeting(id, greetingDetails.getMessage());
    }
    @DeleteMapping("/deleteFromRepo/{id}")
    public ResponseEntity<String> deleteGreetingRepo(@PathVariable Long id){
        try {
            greetingService.deleteGreeting(id);
            return ResponseEntity.ok("Greeting deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
