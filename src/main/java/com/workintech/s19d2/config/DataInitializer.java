package com.workintech.s19d2.config;

import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByAuthority("USER").isEmpty()) {
                roleRepository.save(new Role(null, "USER", null));
            }
            if (roleRepository.findByAuthority("ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ADMIN", null));
            }
        };
    }
}
