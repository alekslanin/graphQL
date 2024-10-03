package com.lanina.search.services;

import com.lanina.search.data.LocationInput;
import com.lanina.search.storage.MetaImage;
import com.lanina.search.storage.MetaLocation;
import com.lanina.search.data.PageInput;
import org.javatuples.Pair;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface ITravelPhotoService {
    List<MetaLocation> getLocations();

    List<MetaLocation> getPaginatedLocations(PageInput pageInput);

    Long countLocations();

    List<MetaLocation> getLocations(int page, int size) throws HttpClientErrorException.NotAcceptable;

    MetaLocation getLocationById(String id);

    MetaImage getImageById(String location, Integer id);

    MetaLocation add(LocationInput input);

    MetaLocation delete(String title);

    Pair<Long, Integer> imagesStat();
}
