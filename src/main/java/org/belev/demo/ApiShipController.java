package org.belev.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/ship")
public class ApiShipController {
    private ShipRepository shipRepository;
    private TouristRepository touristRepository;

    public ApiShipController(ShipRepository shipRepository, TouristRepository touristRepository) {
        this.shipRepository = shipRepository;
        this.touristRepository = touristRepository;
    }

    // Single ship
    @GetMapping(value = "/{id}")
    public Optional<Ship> single(@PathVariable Long id) {
        Optional<Ship> ship = shipRepository.findById(id);

        return ship;
    }

    // Add new ship
    @PostMapping(value = "/add")
    public String add(@RequestBody Ship ship) {
        List<Tourist> touristNotFull = ship.getTouristsAdded();
        for (int i = 0; i < touristNotFull.size(); i++) {
            Long touristId = touristNotFull.get(i).getId();
            Tourist tourist = touristRepository.findById(touristId).get();
            ship.addTourist(tourist);
        }

        shipRepository.save(ship);

        return "Added new ship";
    }

    // Update ship
    @PutMapping(value = "/update/{id}")
    public String update(@RequestBody Ship updatedShip, @PathVariable Long id) {
        updatedShip.setId(id);
        List<Tourist> touristNotFull = updatedShip.getTouristsAdded();
        System.out.println("-------------------------->");
        System.out.println(touristNotFull.size());

        for (int i = 0; i < touristNotFull.size(); i++) {
            Long touristId = touristNotFull.get(i).getId();
            Tourist tourist = touristRepository.findById(touristId).get();
            updatedShip.addTourist(tourist);
        }

        shipRepository.save(updatedShip);

        return "Ship updated";
    }

    // Get all ships
    @GetMapping(value = "/all")
    public Iterable<Ship> all() {
        return shipRepository.findAll();
    }

    // Delete ship
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id) {
        shipRepository.deleteById(id);

        return "Ship deleted";
    }
}
