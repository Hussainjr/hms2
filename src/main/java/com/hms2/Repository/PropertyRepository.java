package com.hms2.Repository;

import com.hms2.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByNameAndCityIdAndCountryId(String name, long cityId, long countryId);

}