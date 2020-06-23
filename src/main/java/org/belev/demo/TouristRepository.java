package org.belev.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TouristRepository extends CrudRepository<Tourist, Long> {
    List<Tourist> findByShipId(Long shipId);
}
