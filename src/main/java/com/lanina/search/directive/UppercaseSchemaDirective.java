package com.lanina.search.directive;

import graphql.language.StringValue;
import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

import java.util.Objects;

public class UppercaseSchemaDirective implements SchemaDirectiveWiring {

	@Override
	public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
		GraphQLFieldDefinition field = environment.getElement();
		GraphQLFieldsContainer parentType = environment.getFieldsContainer();
		String conditionValue = ((StringValue) Objects.requireNonNull(environment.getAppliedDirective().getArgument("condition").getArgumentValue().getValue())).getValue();

		DataFetcher<?> existingDF = environment.getCodeRegistry().getDataFetcher(FieldCoordinates.coordinates(parentType, field), field);
		DataFetcher<?> updatedDF = DataFetcherFactories.wrapDataFetcher(existingDF, ((datafetchingEnv, value) -> {
			if (value instanceof String stringValue && stringValue.toLowerCase().contains(conditionValue)) {
				return stringValue.toUpperCase();
			}
			return value;
		}));

		environment.getCodeRegistry().dataFetcher(FieldCoordinates.coordinates(parentType, field), updatedDF);
		return field;
	}
}
