package com.example.GreetingApp.service;

import com.piyush.GreetingApp.model.Greeting;
import com.piyush.GreetingApp.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    public String getSimpleGreeting() {
        return "Hello World!";
    }
    public String getGreeting(String firstName, String lastName) {
        if(firstName != null && lastName != null) {
            return "Hello " + firstName + " " + lastName;
        } else if (firstName != null) {
            return "Hello " + firstName;
        }
        else if (lastName != null) {
            return "Hello " + lastName;
        }
        else {
            return "Hello World!";
        }
    }
    @Autowired
    private GreetingRepository greetingRepository;
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