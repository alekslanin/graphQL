package com.lanina.search;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class TrailData  extends LocationDetailsResponse {
    public  TrailData( String region, String arg) {
        this.setRegion(region);
        this.trail = arg;
    }

    String trail;
}

