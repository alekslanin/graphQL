package com.lanina.search.data;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class BlahData extends LocationDetailsResponse {
    public BlahData(String region, String trail) {
        this.setRegion(region);
        this.blah = trail;
    }

    String blah;
}

