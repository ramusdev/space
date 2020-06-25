package org.belev.demo;
import net.minidev.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/ship")
public class ApiShipController {
    private ShipRepository shipRepository;

    public ApiShipController(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    // Single ship
    @GetMapping(value = "/{id}")
    public Optional<Ship> single(@PathVariable Long id) {
        return shipRepository.findById(id);
    }

    // Add new ship
    @PostMapping(value = "/add")
    public String add(@RequestBody Ship ship) {
        // System.out.println(ship);
        // JSONArray touristsJsonArray = ship.getTouristsAdded();
        // System.out.println(jsonTourists);
        ship.setTourists(ship.getTouristsAdded());

        shipRepository.save(ship);

        return "Added new ship";
    }

    // Update ship
    @PutMapping(value = "/update/{id}")
    public String update(@RequestBody Ship updatedShip, @PathVariable Long id) {
        // Optional<Ship> updatedShip = shipRepository.findById(id);
        updatedShip.setId(id);
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
