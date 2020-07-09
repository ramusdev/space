package org.belev.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/tourist")
public class ApiTouristController {
    private TouristRepository touristRepository;
    private ShipRepository shipRepository;

    public ApiTouristController(ShipRepository shipRepository, TouristRepository touristRepository) {
        this.shipRepository = shipRepository;
        this.touristRepository = touristRepository;
    }

    @GetMapping(value = "/test")
    public String test() {
        System.out.println("Inside test controller");

        return "Inside test controller return";
    }

    @GetMapping(value = "/addcustom")
    public String addCustom() {
        //Ship ship = new Ship("Mars", 100, 250, "10.10.2020", "11.10.2020");
        //shipRepository.save(ship);

        Ship ship2 = shipRepository.findById(new Long(3)).get();

        Tourist tourist = new Tourist(
                "First name",
                "Last name",
                "Some text",
                "Male",
                "Ukraine",
                "10.10.2019",
                10,
                ship2
        );

        touristRepository.save(tourist);

        System.out.println(touristRepository.findAll());
        return "Custom employee added";
    }

    @GetMapping(value = "/{id}")
    public Optional<Tourist> getById(@PathVariable Long id) {
        return touristRepository.findById(id);
    }

    @GetMapping(value = "/all")
    public Iterable<Tourist> allEmployees() {
        return touristRepository.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id) {
        touristRepository.deleteById(id);

        return "Item deleted";
    }

    // Add new tourist
    @PostMapping(value = "/add")
    public String addTourist(@RequestBody Tourist tourist) {
        long shipId = tourist.getShipIdentifier();
        if (shipId != 0) {
            Ship ship = shipRepository.findById(shipId).get();
            tourist.setShip(ship);
        } else {
            tourist.setShip(null);
        }

        touristRepository.save(tourist);

        return "Tourist added";
    }

    // Edit tourist
    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @RequestBody Tourist updatedTourist) {
        // updatedTourist.setId(id);
        long shipId = updatedTourist.getShipIdentifier();

        if (shipId != 0) {
            Ship ship = shipRepository.findById(shipId).get();
            int shipSeatsAvailable = ship.getSeatsAvailable();
            if (shipSeatsAvailable < 1) {
                return "{\"success\":0, \"message\":\"Error! Not enough seats!\"}";
            }

            updatedTourist.setShip(ship);
        } else {
            updatedTourist.setShip(null);
        }

        touristRepository.save(updatedTourist);

        return "{\"success\":1, \"message\":\"Success! Tourist edited!\"}";
    }
}

