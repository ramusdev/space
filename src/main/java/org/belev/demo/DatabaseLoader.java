package org.belev.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DatabaseLoader implements CommandLineRunner {
    private final TouristRepository repository;

    @Autowired
    public DatabaseLoader(TouristRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        // this.repository.save(new Employee("John", "Kolisar", "Fighter on the ring"));
    }
}
