package com.lanina.search.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
	private Status status;
	private String  title;
	private String message;
}
