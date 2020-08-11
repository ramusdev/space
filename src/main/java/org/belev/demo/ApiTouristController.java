package org.belev.demo;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/tourist")
public class ApiTouristController {
    private TouristRepository touristRepository;
    private ShipRepository shipRepository;
    // private Logger logger = LoggerFactory.getLogger(ApiTouristController.class);

    public ApiTouristController(ShipRepository shipRepository, TouristRepository touristRepository) {
        this.shipRepository = shipRepository;
        this.touristRepository = touristRepository;
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
    public JSONObject delete(@PathVariable Long id) {
        touristRepository.deleteById(id);

        // logger.debug("This is logger debug");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", "1");
        jsonObject.put("message", "Success! Tourist deleted!");

        return jsonObject;
    }

    // Add new tourist
    @PostMapping(value = "/add")
    public JSONObject addTourist(@RequestBody Tourist tourist, HttpServletResponse response) {
        long shipId = tourist.getShipIdentifier();
        if (shipId != 0) {
            Ship ship = shipRepository.findById(shipId).get();
            int seats = ship.getSeatsAvailable();

            if (seats < 1) {
                // return "{\"success\":0, \"message\":\"Error! Not enough seats!\"}";
            }

            ship.setSeatsAvailable(seats - 1);
            tourist.setShip(ship);
        } else {
            tourist.setShip(null);
        }

        // Save
        Tourist savedTourist = touristRepository.save(tourist);
        Long savedTouristId = savedTourist.getId();

        //System.out.println(savedTouristId);
        // Redirect
        // RedirectView redirectView = new RedirectView("/tourist/edit/1", true);
        // redirectView.setContextRelative(true);
        // redirectView.setUrl("/update/{savedTouristId}");
        // return "{\"success\":1, \"message\":\"Success! Tourist added!\"}";
        // return new RedirectView("/tourist/edit/28");

        // response.setHeader("Location", "http://127.0.0.1:3000/tourist/edit/2");
        // response.setStatus(302);
        // response.sendRedirect("http://127.0.0.1:3000/tourist/edit/2");

        // response.setHeader("Location", "http://127.0.0.1:3000/tourist/edit/100");
        // response.setStatus(302);
        // return "{\"success\":1, \"message\":\"Success! Tourist edited!\"}";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirect", "/tourist/edit/" + savedTouristId);

        return jsonObject;
    }

    // Edit tourist
    @PutMapping(value = "/update/{id}")
    public JSONObject update(@PathVariable Long id, @RequestBody Tourist updatedTourist) {
        long shipUpdatedId = updatedTourist.getShipIdentifier();
        if (shipUpdatedId != 0) {
            Ship ship = shipRepository.findById(shipUpdatedId).get();

            if (! this.updateSeats(updatedTourist)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", 0);
                jsonObject.put("message", "Error! Not enough seats!");
                return jsonObject;
            }

            updatedTourist.setShip(ship);
        } else {
            updatedTourist.setShip(null);
        }

        touristRepository.save(updatedTourist);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", 1);
        jsonObject.put("message", "Success! Tourist edited");

        return jsonObject;
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
        }

        return true;
    }
}

