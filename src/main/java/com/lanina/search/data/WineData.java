package com.lanina.search.data;

import com.lanina.search.data.LocationDetailsResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class WineData  extends LocationDetailsResponse {
    public  WineData( String region, String wine) {
        this.setRegion(region);
        this.wine = wine;
    }

    String wine;
}

