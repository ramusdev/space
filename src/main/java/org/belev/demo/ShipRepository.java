package org.belev.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Timestamp;

interface ShipRepository extends CrudRepository<Ship, Long> {
    @Query(value = "SELECT * FROM ship WHERE direction LIKE %:direction% AND departure_date = :departure", nativeQuery = true)
    // List<Ship> findShipsByDirectionAndDeparture(@Param("departure") LocalDateTime departure);
    List<Ship> findShipsByDirectionAndDeparture(@Param("direction") String direction,
                                                @Param("departure") LocalDateTime departure);
}
