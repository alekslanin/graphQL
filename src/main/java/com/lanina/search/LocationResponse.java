package com.lanina.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse{
  private String year, title, country, actors;
  private Integer total;
  private MemberType type;

}
