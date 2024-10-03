package com.lanina.search.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class OtherData extends LocationDetailsResponse {
    public OtherData(String region, String other) {
        this.setRegion(region);
        this.other = other;
    }

    String other;
}
