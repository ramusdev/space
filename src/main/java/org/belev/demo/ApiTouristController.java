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
            if (ship.getSeatsAvailable() < 1) {
                return "{\"success\":0, \"message\":\"Error! Not enough seats!\"}";
            }
            tourist.setShip(ship);
        } else {
            tourist.setShip(null);
        }

        touristRepository.save(tourist);
        return "{\"success\":1, \"message\":\"Success! Tourist added!\"}";
    }

    // Edit tourist
    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @RequestBody Tourist updatedTourist) {
        // Tourist oldTourist = touristRepository.findById(updatedTourist.getId()).get();
        // Ship oldShip = oldTourist.getShip();

        long shipUpdatedId = updatedTourist.getShipIdentifier();
        if (shipUpdatedId != 0) {
            Ship ship = shipRepository.findById(shipUpdatedId).get();
            // int shipSeatsAvailable = ship.getSeatsAvailable();
            // if (shipSeatsAvailable < 1 && !ship.equals(oldShip)) {
                //return "{\"success\":0, \"message\":\"Error! Not enough seats!\"}";
            // }
            updatedTourist.setShip(ship);
        } else {
            updatedTourist.setShip(null);
        }

        if (! this.updateSeats(updatedTourist)) {
            return "{\"success\":0, \"message\":\"Error! Not enough seats!\"}";
        }

        touristRepository.save(updatedTourist);
        return "{\"success\":1, \"message\":\"Success! Tourist edited!\"}";
    }

    public boolean updateSeats(Tourist touristUpdated) {
        Tourist touristOld = touristRepository.findById(touristUpdated.getId()).get();
        Ship shipOld = touristOld.getShip();
        Long shipUpdatedId = touristUpdated.getShipIdentifier();

        if (touristUpdated.getShip() != null) {
            Ship ship = touristUpdated.getShip();
            if (ship.getSeatsAvailable() < 1 && !ship.equals(shipOld)) {
                return false;
            }
        }

        // Free seats
        if (shipOld != null && shipUpdatedId == 0) {
            System.out.print("This console first ------------------>");
            System.out.println(shipUpdatedId);

            int seatsAvailable = shipOld.getSeatsAvailable();
            shipOld.setSeatsAvailable(seatsAvailable + 1);
            // shipRepository.save(shipOld);
        } else if (shipOld != null && shipUpdatedId != 0) {
            System.out.print("This console second ------------------>");
            System.out.println(shipUpdatedId);

            int seatsOldAvailable = shipOld.getSeatsAvailable();
            shipOld.setSeatsAvailable(seatsOldAvailable + 1);

            Ship shipUpdated = shipRepository.findById(shipUpdatedId).get();
            int seatsUpdatedAvailable = shipUpdated.getSeatsAvailable();
            shipUpdated.setSeatsAvailable(seatsUpdatedAvailable - 1);
        } else if (shipOld == null && shipUpdatedId != 0) {
            System.out.print("This console third ------------------>");
            System.out.println(shipUpdatedId);

            Ship shipUpdated = shipRepository.findById(shipUpdatedId).get();
            int seatsAvailableUpdated = shipUpdated.getSeatsAvailable();
            shipUpdated.setSeatsAvailable(seatsAvailableUpdated - 1);
            // shipRepository.save(shipUpdated);
        }

        return true;
    }

}

