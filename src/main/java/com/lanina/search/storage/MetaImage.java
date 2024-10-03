package com.lanina.search.storage;

import com.lanina.search.storage.ImageId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity
@Table(name="photo")
public class MetaImage {
    @EmbeddedId
    private ImageId id;

    private String name;

    @Lob
    private byte[] image;
}
