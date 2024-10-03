package com.lanina.search.interceptor;

import jakarta.validation.constraints.NotNull;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class RequestInterceptor implements WebGraphQlInterceptor {

	@Override
	public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, @NotNull Chain chain) {
		String headerValue = request.getHeaders().getFirst("testHeader");
		if (headerValue != null) {
			if(headerValue.equalsIgnoreCase("sashok"))	request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(Collections.singletonMap("testHeader", headerValue)).build());
			else request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(Collections.singletonMap("testHeader", "warning: wrong dude")).build());
		}
		return chain.next(request);
	}

}
