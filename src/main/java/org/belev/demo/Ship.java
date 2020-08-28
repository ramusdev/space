package org.belev.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Entity
public class Ship implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String direction;
    private int price;
    private int seats;
    private int seatsAvailable;
    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm:ss")
    // @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm:ss")
    private LocalDateTime departureDate;
    // @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy HH:mm:ss")
    private LocalDateTime arrivalDate;
    @Transient
    @JsonIgnoreProperties("ship")
    private List<Tourist> touristsAdded = new ArrayList<Tourist>();

    // @JsonManagedReference
    @OneToMany(mappedBy="ship", fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
    @JsonIgnoreProperties("ship")
    private List<Tourist> tourists = new ArrayList<Tourist>();

    public Ship() {}

    public Ship(String direction, int price, int seats, LocalDateTime departureDate, LocalDateTime arrivalDate, List<Tourist> touristsAdded) {
        this.direction = direction;
        this.price = price;
        this.seats = seats;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.touristsAdded = touristsAdded;
    }

    public void addTourist(Tourist tourist) {
        tourist.setShip(this);
        this.tourists.add(tourist);
    }

    public void removeTourist(Tourist tourist) {
        tourist.setShip(null);
        this.tourists.add(tourist);
    }

    public Long getId() {
        return id;
    }

    public String getDirection() {
        return direction;
    }

    public int getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public int getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public List<Tourist> getTourists() {
        return this.tourists;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public List<Tourist> getTouristsAdded() {
        return this.touristsAdded;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setTouristsAdded(List<Tourist> touristsAdded) {
        this.touristsAdded = touristsAdded;
    }

    /*
    public String toString() {
        return "Ship: " +
                "\nid: " + id +
                "\ndirection: " + direction +
                "\nprice: " + price +
                "\nseats: " + seats +
                "\ndepartureDate: " + departureDate +
                "\narrivalDate: " + arrivalDate +
                "\ntourists: " + tourists;
    }
    */
}
