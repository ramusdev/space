package org.belev.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface ShipRepository extends CrudRepository<Ship, Long> {
    @Query(value="SELECT * FROM ship WHERE direction LIKE %:direction%", nativeQuery = true)
    List<Ship> findShipsByDirection(String direction);
}
