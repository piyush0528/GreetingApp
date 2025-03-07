package com.example.GreetingApp.controller;

import com.example.GreetingApp.customExceptions.ResourceNotFoundException;
import com.example.GreetingApp.repository.GreetingRepository;
import com.example.GreetingApp.services.GreetingService;
import com.example.GreetingApp.model.Greeting;
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
    @GetMapping("/simple")
    public String getSimpleGreeting() {
        return greetingService.getSimpleGreeting();
    }
    @PostMapping("/savetorepo")
    public Greeting saveGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return greetingService.saveGreeting(firstName, lastName);
    }
    @GetMapping("/findall")
    public ResponseEntity<?> getAllGreetings() {
        try {
            List<Greeting> greetings = greetingRepository.findAll();
            return ResponseEntity.ok(greetings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching greetings: " + e.getMessage());
        }
    }
    @PostMapping("/add")
    public Greeting createGreeting(@RequestBody Greeting greeting) {
        return greetingRepository.save(greeting);
    }
    @PutMapping("update/{id}")
    public Greeting updateGreeting(@PathVariable Long id, @RequestBody Greeting greetingDetails) {
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Greeting not found with id " + id));
        greeting.setMessage(greetingDetails.getMessage());
        return greetingRepository.save(greeting);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteGreeting(@PathVariable Long id) {
        Greeting greeting = greetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Greeting not found with id " + id));
        greetingRepository.delete(greeting);
        return ResponseEntity.ok().build();
    }
    @GetMapping("getId/{id}")
    public Greeting getGreetingById(@PathVariable Long id) {
        return greetingService.getGreetingById(id);
    }
    @GetMapping("/repositoryshow/all")
    public List<Greeting> getGreetings() {
        return greetingService.getAllGreetings();
    }

    @PutMapping("/updaterepository/{id}")
    public Greeting updateGreetinginRepository(@PathVariable Long id, @RequestBody Greeting updatedGreeting) {
        return greetingService.updateGreeting(id, updatedGreeting.getMessage());
    }
    @DeleteMapping("deletefromrepo/{id}")
    public ResponseEntity<String> deleteGreetingbyrepository(@PathVariable Long id) {
        try {
            greetingService.deleteGreetingbyrepo(id);
            return ResponseEntity.ok("Greeting deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}