package com.lanina.search.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WinoMetaLocationRepository extends JpaRepository<MetaLocation, String> {
    MetaLocation getLocationById(String id);
}
