package org.belev.demo;

import org.springframework.beans.factory.annotation.Autowired;
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

        Employee employeeOne = new Employee(
                "Tom",
                "Kolin",
                "Some Tom from USA",
                "Male",
                "USA",
                "10.10.1990"
        );

        Employee employeeTwo = new Employee(
                "Ma",
                "Cin",
                "Some Tom from China",
                "Male",
                "China",
                "10.10.1991"
        );

        ship.addEmployee(employeeOne);
        ship.addEmployee(employeeTwo);

        shipRepository.save(ship);

        return "Ship added";
    }

    // TODO
    @PutMapping(value = "/update/{id}")
    public String update(@RequestBody Ship newShip, Long id) {
        Optional<Ship> updatedShip = shipRepository.findById(id);

        newShip.setId(id);
        shipRepository.save(newShip);

        return "Ship updated";
    }

    @GetMapping(value = "/all")
    public Iterable<Ship> all() {
        return shipRepository.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id) {
        shipRepository.deleteById(id);

        return "Item deleted";
    }
}
