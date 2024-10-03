package com.lanina.search.storage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WinoMetaImageRepository extends JpaRepository<MetaImage, ImageId> {
    MetaImage findImageById(ImageId id);
}
