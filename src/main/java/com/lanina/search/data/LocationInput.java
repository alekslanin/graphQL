package com.lanina.search.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationInput{
    private String year, title, country, actors;
    private Integer total;
}