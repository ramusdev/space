package org.belev.demo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String gender;
    private String country;
    private String dateOfBirth;
    int shipKey;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ship_id", nullable = false)
    private Ship ship;

    private Employee() {}

    public Employee(String firstName, String lastName, String description, String gender, String country, String dateOfBirth, int shipKey, Ship ship) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.shipKey = shipKey;
        this.ship = ship;
    }

    public Employee(String firstName, String lastName, String description, String gender, String country, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(description, employee.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Ship getShip() {
        return this.ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getShipKey() {
        return this.shipKey;
    }

    public void setShipKey(int shipKey) {
        this.shipKey = shipKey;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id = " + id +
                ", firstName = " + firstName + '\'' +
                ", lastName = " + lastName + '\'' +
                ", description = " + description + '\'' +
                ", ship = " + ship + " " +
                '}';
    }


}
