package com.lanina.search.data;

import lombok.Data;

@Data
public class PageInput {
	private int offset;
	private int limit;
	private String after;
	private String before;
}
