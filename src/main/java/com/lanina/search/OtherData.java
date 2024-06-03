package com.lanina.search;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class OtherData extends LocationDetailsResponse {
    public OtherData(String region, String wine) {
        this.setRegion(region);
        this.other = wine;
    }

    String other;
}
