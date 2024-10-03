package com.lanina.search.exception;

import com.lanina.search.data.Response;
import com.lanina.search.data.Status;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@GraphQlExceptionHandler
	public GraphQLError handle (ArithmeticException ex, DataFetchingEnvironment dfe) {
		var map = new HashMap<String, Object>();
		map.put("details", new Response(Status.FAILURE, "math", "calculation exception: " + ex.getLocalizedMessage()));
		return GraphQLError.newError()
				.message("arithmetic exception caught!")
				.errorType(ErrorType.INTERNAL_ERROR)
				.location(dfe.getField().getSourceLocation())
				.path(dfe.getExecutionStepInfo().getPath())
				.extensions(map)
				.build();
	}
	
	@GraphQlExceptionHandler
	public GraphQLError handle (NullPointerException ex, DataFetchingEnvironment dfe) {
		var map = new HashMap<String, Object>();
		map.put("details", new Response(Status.FAILURE, "math", "null pointer exception: " + ex.getLocalizedMessage()));
		return GraphQLError.newError()
				.message("arithmetic exception caught!")
				.errorType(ErrorType.INTERNAL_ERROR)
				.location(dfe.getField().getSourceLocation())
				.path(dfe.getExecutionStepInfo().getPath())
				.extensions(map)
				.build();
	}
	
	@GraphQlExceptionHandler
	public GraphQLError handle (Throwable ex, DataFetchingEnvironment dfe) {
		return GraphQLError.newError()
				.message("zorro:: " + ex.getLocalizedMessage())
				.errorType(ErrorType.INTERNAL_ERROR)
				.location(dfe.getField().getSourceLocation())
				.path(dfe.getExecutionStepInfo().getPath())
				.build();
	}
	
}
