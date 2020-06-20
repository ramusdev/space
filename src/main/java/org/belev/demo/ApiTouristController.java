package org.belev.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
@RequestMapping("/api/tourist")
public class ApiTouristController {
    private EmployeeRepository employeeRepository;
    private ShipRepository shipRepository;

    public ApiTouristController(ShipRepository shipRepository, EmployeeRepository employeeRepository) {
        this.shipRepository = shipRepository;
        this.employeeRepository = employeeRepository;
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

        Employee employee = new Employee(
                "First name",
                "Last name",
                "Some text",
                "Male",
                "Ukraine",
                "10.10.2019",
                10,
                ship2
        );

        employeeRepository.save(employee);

        System.out.println(employeeRepository.findAll());
        return "Custom employee added";
    }

    @GetMapping(value = "/{id}")
    public Optional<Employee> getById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(value = "/all")
    public Iterable<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);

        return "Item deleted";
    }

    @PostMapping(value = "/add")
    public String addTourist(@RequestBody Employee employee) {

        System.out.println(employee);
        Ship ship = shipRepository.findById((long)employee.getShipKey()).get();
        System.out.println(ship);

        employee.setShip(ship);
        employeeRepository.save(employee);

        return employee.toString();
    }

    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @RequestBody Employee newEmployee) {
        Optional<Employee> updatedEmployee = employeeRepository.findById(id);

        /*
        Optional<Employee> updatedEmployeeId = updatedEmployee.map(employee -> {
            employee.setFirstName(newEmployee.getFirstName());
            employee.setLastName(newEmployee.getLastName());
            employee.setCountry(newEmployee.getCountry());
            employee.setDateOfBirth(newEmployee.getDateOfBirth());
            employee.setGender(newEmployee.getGender());
            employee.setDescription(newEmployee.getDescription());

            return employeeRepository.save(employee);
        });
        */

        newEmployee.setId(id);
        employeeRepository.save(newEmployee);

        return "Updated";
    }
}

