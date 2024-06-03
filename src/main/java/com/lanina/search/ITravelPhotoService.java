package com.lanina.search;

import org.javatuples.Pair;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface ITravelPhotoService {
    List<MetaLocation> getLocations();

    Long countLocations();

    List<MetaLocation> getLocations(int page, int size) throws HttpClientErrorException.NotAcceptable;

    MetaLocation getLocationById(String id);

    MetaImage getImageById(String location, Integer id);

    Pair<Long, Integer> imagesStat();
}
