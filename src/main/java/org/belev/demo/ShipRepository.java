package org.belev.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.sql.Timestamp;

interface ShipRepository extends CrudRepository<Ship, Long> {
    // @Query(value="SELECT * FROM ship WHERE direction LIKE %:direction%", nativeQuery = true)
    // List<Ship> findShipsByDirection(String direction);
    // @Query(value = "SELECT * FROM ship WHERE direction LIKE :direction AND departure_date = :departure", nativeQuery = true)
    // @Query(value = "SELECT * FROM ship WHERE departure_date = :departure", nativeQuery = true)

    @Query(value = "SELECT u FROM Ship u WHERE u.departure_date = ?1")
    List<Ship> findShipsByDirectionAndDeparture(@Param("departure") Timestamp departure);
}
