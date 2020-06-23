package org.belev.demo;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/ship")
public class ApiShipController {
    private ShipRepository shipRepository;

    public ApiShipController(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @GetMapping(value = "/addcustom")
    public String add() {

        Ship ship = new Ship(
                "ISS",
                100,
                250,
                "20.12.2020",
                "21.12.2020"
        );

        Tourist touristOne = new Tourist(
                "Tom",
                "Kolin",
                "Some Tom from USA",
                "Male",
                "USA",
                "10.10.1990"
        );

        Tourist touristTwo = new Tourist(
                "Ma",
                "Cin",
                "Some Tom from China",
                "Male",
                "China",
                "10.10.1991"
        );

        ship.addTourist(touristOne);
        ship.addTourist(touristTwo);
        shipRepository.save(ship);

        return "Ship added";
    }

    // Single ship
    @GetMapping(value = "/{id}")
    public Optional<Ship> single(@PathVariable Long id) {
        return shipRepository.findById(id);
    }

    // Add new ship
    @PostMapping(value = "/add")
    public String add(@RequestBody Ship ship) {
        System.out.println(ship);
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
