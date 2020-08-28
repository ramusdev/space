package org.belev.demo;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/database")
public class DatabaseSeeder {
    private ShipRepository shipRepository;
    private TouristRepository touristRepository;
    private int numberSeedOfTourist = 1000;
    private int numberSeedOfShip = 500;

    public DatabaseSeeder(ShipRepository shipRepository, TouristRepository touristRepository) {
        this.shipRepository = shipRepository;
        this.touristRepository = touristRepository;
    }

    // Seed tourists
    @GetMapping(value = "/seed")
    public void seedTourists() {

        for (int i = 0; i < numberSeedOfTourist; i++) {
            seedTourist();
        }

        for (int i = 0; i < numberSeedOfShip; i++) {
            seedShip();
        }

    }

    public void seedShip() {
        Faker faker = new Faker();
        String direction = faker.space().planet();
        int seats = faker.number().numberBetween(1, 100);
        int price = faker.number().numberBetween(10, 1000);
        int daysToAdd = faker.number().numberBetween(1, 365);

        Date currentDate = new Date();
        Date arrivalDate = DateUtils.addDays(currentDate, daysToAdd);
        Date departureDate = DateUtils.addDays(arrivalDate, daysToAdd);

        LocalDateTime arrivalDateLocal = LocalDateTime.ofInstant(arrivalDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime departureDateLocal = LocalDateTime.ofInstant(departureDate.toInstant(), ZoneId.systemDefault());

        Ship ship = new Ship();
        ship.setDirection(direction);
        ship.setSeats(seats);
        ship.setSeatsAvailable(seats);
        ship.setPrice(price);
        ship.setArrivalDate(arrivalDateLocal);
        ship.setDepartureDate(departureDateLocal);

        shipRepository.save(ship);

    }

    public void seedTourist() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String country = faker.country().name();
        String gender = faker.bool().bool() ? "Male" : "Female";
        String description = faker.lorem().sentence(100);

        Date dateFakerBirthday = faker.date().birthday(19, 90);
        LocalDateTime date = LocalDateTime.ofInstant(dateFakerBirthday.toInstant(), ZoneId.systemDefault());

        Tourist tourist = new Tourist();
        tourist.setFirstName(firstName);
        tourist.setLastName(lastName);
        tourist.setCountry(country);
        tourist.setGender(gender);
        tourist.setDescription(description);
        tourist.setDateOfBirth(date);

        touristRepository.save(tourist);
    }
}
