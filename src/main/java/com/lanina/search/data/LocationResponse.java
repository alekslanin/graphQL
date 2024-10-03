package com.lanina.search.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse{
  private String year, title, country, actors;
  private Integer total;
  private BigDecimal mark;
  private MemberType type;

}
