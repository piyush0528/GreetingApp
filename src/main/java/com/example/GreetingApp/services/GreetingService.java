package com.example.GreetingApp.services;

import com.example.GreetingApp.model.Greeting;
import com.example.GreetingApp.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    @Autowired
    private GreetingRepository greetingRepository;
    public String getSimpleGreeting() {
        return "Hello World";
    }

    public Greeting saveGreeting(String firstName, String lastName) {
        String message;
        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            message = "Hello, " + firstName + "!";
        } else if (lastName != null) {
            message = "Hello, " + lastName + "!";
        } else {
            message = "Hello World!";
        }

        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }
    public Greeting getGreetById(Long id) {
        return greetingRepository.findById(id).orElseThrow(()->new RuntimeException("Greeting not found with id: " + id));
    }
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
    public Greeting updateGreeting(Long id, String newGreeting) {
        Optional<Greeting> oldGreeting = greetingRepository.findById(id);
        if(oldGreeting.isPresent()){
            Greeting greeting = oldGreeting.get();
            greeting.setMessage(newGreeting);
            return greetingRepository.save(greeting);
        }
        else {
            throw new RuntimeException("Greeting not found with id: " + id);
        }
    }
    public void deleteGreeting(Long id) {
        if(greetingRepository.findById(id).isPresent()){
            greetingRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Greeting not found with id: " + id);
        }
    }
}
