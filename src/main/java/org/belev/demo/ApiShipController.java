package org.belev.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        // Init available seats
        ship.setAvailableSeats(ship.getSeats());

        // New available seats
        int newSeats = ship.getAvailableSeats() - touristNotFull.size();
        ship.setAvailableSeats(newSeats);

        // Set tourists
        // List<Tourist> touristNotFull = ship.getTouristsAdded();
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

        Ship currentShip = shipRepository.findById(id).get();
        List<Tourist> currentTourists = currentShip.getTourists();

        // List<Tourist> touristsNotFull = updatedShip.getTouristsAdded();
        List<Tourist> addedTourists = updatedShip.getTouristsAdded();

        /*
        List<Tourist> addedTourists = new ArrayList<Tourist>();
        for (int i = 0; i < touristsNotFull.size(); i++) {
            Long touristId = touristsNotFull.get(i).getId();
            Tourist tourist = touristRepository.findById(touristId).get();
            addedTourists.add(tourist);
        }
        */

        // Filter
        List<Tourist> touristsToRemove = new ArrayList<Tourist>();
        for (int i = 0; i < currentTourists.size(); i++) {
            boolean isExist = false;
            for (int k = 0; k < addedTourists.size(); k++) {
                if (currentTourists.get(i).getId() == addedTourists.get(k).getId()) {
                    isExist = true;
                }
            }
            if (! isExist) {
                touristsToRemove.add(currentTourists.get(i));
            }
        }

        for (int i = 0; i < touristsToRemove.size(); i++) {
            updatedShip.removeTourist(touristsToRemove.get(i));
        }

        for (int i = 0; i < addedTourists.size(); i++) {
            updatedShip.addTourist(addedTourists.get(i));
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
