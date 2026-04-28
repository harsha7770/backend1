package com.mentorship.backend.config;

import com.mentorship.backend.model.User;
import com.mentorship.backend.model.Session;
import com.mentorship.backend.repository.UserRepository;
import com.mentorship.backend.repository.SessionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import com.mentorship.backend.repository.MatchingRepository;
import com.mentorship.backend.repository.ProgressRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, 
                                 SessionRepository sessionRepository,
                                 MatchingRepository matchingRepository,
                                 ProgressRepository progressRepository) {
        return args -> {
            // Delete all existing data to start fresh
            sessionRepository.deleteAll();
            matchingRepository.deleteAll();
            progressRepository.deleteAll();
            userRepository.deleteAll();

            // Seed specific mentors as requested
            seedMentor(userRepository, "Hitesh", "hitesh@example.com", "[\"React\", \"Frontend\"]", "Frontend expert and educator.", 5, 50);
            seedMentor(userRepository, "Pav Kohli", "pav@example.com", "[\"Backend\", \"System Design\"]", "Backend architecture guru.", 8, 80);

            // Add one admin so login works
            User adminObj = new User(null, "System Admin", "admin@example.com", "admin", "admin", 
                    null, null, "Platform Administrator.", 0, 0, null, null);
            userRepository.save(adminObj);

            // Seed nice mentees with experience
            User charlieObj = new User(null, "Charlie Williams", "charlie@example.com", "password123", "mentee", 
                    null, null, "Aspiring Frontend Developer.", 2, 0, null, null);
            charlieObj.setInterests("[\"Frontend Development\", \"UI/UX\"]");
            
            User danaObj = new User(null, "Dana White", "dana@example.com", "password123", "mentee", 
                    null, null, "Data Analyst looking to transition to Data Science.", 1, 0, null, null);
            danaObj.setInterests("[\"Data Analysis\", \"Python\"]");

            User evaObj = new User(null, "Eva Green", "eva@example.com", "password123", "mentee", 
                    null, null, "Backend enthusiast learning scalable systems.", 3, 0, null, null);
            evaObj.setInterests("[\"Backend Development\", \"Java\", \"Spring\"]");

            userRepository.save(charlieObj);
            userRepository.save(danaObj);
            userRepository.save(evaObj);
        };
    }

    private void seedMentor(UserRepository repo, String name, String email, String expertise, String bio, int exp, int rate) {
        if (!repo.findByEmail(email).isPresent()) {
            User mentor = new User(null, name, email, "password123", "mentor", null, null, bio, exp, rate, null, null);
            mentor.setExpertise(expertise);
            repo.save(mentor);
            System.out.println("Seeded mentor: " + name);
        }
    }
}
