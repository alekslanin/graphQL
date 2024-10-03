package com.lanina.search.controller;

import com.lanina.search.data.*;
import com.lanina.search.services.ITravelPhotoService;
import com.lanina.search.utils.EncoderDecoder;
import graphql.GraphQLContext;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QueryController {

	@Autowired
	private ITravelPhotoService service;
//	private FluxSink<Response> locationStream;
//	private ConnectableFlux<Response> locationPublisher;

	/*
	curl 'http://localhost:8091/graphql' -H 'Content-Type: application/json' --data-binary '{"query":"query _Welcome_to_Altair_G127 { getAllLocations { year title country total region actors wine}}","variables":{}}'
    curl 'http://localhost:8091/graphql' -H 'Content-Type: application/json' --data-binary '{"query":"{getAllLocations {year title country total region actors wine}}","variables":{}}' --compressed

	query test_gql($fn: String!, $ln: String) {
		firstQuery
		secondQuery(firstName: $fn, lastName: $ln)
	}

    # variables
	{
  		"fn": "Blah",
  		"ln": "La"
	}
	 */

	@PostConstruct
	public void initSubscription() {
//		Flux<Response> publisher = Flux.create(emitter -> locationStream = emitter);
//		locationPublisher = publisher.publish();
//		locationPublisher.connect();
//		locationPublisher.log();
//		locationPublisher.replay();
	}

//	@SubscriptionMapping("locationSubscription")
//	public Publisher<Response> addMemberSubscription() {
//		return locationPublisher;
//	}

	@QueryMapping
	public String firstQuery() {
		return "First GQL Query successful :: " + "EOQ";
	}

//	@QueryMapping
//	public String firstQuery(@ContextValue String testHeader, GraphQLContext context) {
//		context.put("setHeader1", "test value 1");
//		//dfe.getGraphQlContext().put("setHeader2", "test value 2");
//		return "First GQL Query successful :: " + testHeader;
//	}

//	@QueryMapping
//	public String queryWithInterceptor(DataFetchingEnvironment dfe, @ContextValue String testHeader, GraphQLContext context) {
//		//String value = dfe.getGraphQlContext().get("testHeader");
//		context.put("setHeader1", "test1");
//		dfe.getGraphQlContext().put("setHeader2", "test2");
//		return "Hello "+testHeader;
//	}

	@QueryMapping
	public String secondQuery(@Argument String firstName, @Argument String lastName) {
		return firstName + " " + lastName;
	}

	/*
	@SchemaMapping(typeName = "LocationResponse", field = "wineData")
	public List<WineData> getWineData(LocationResponse response) {
		log.info("LocationResponse get wine data executed");
		return List.of(new WineData("region 1", "tbd"), new WineData("region 2", "tbd"));
	}
	*/

/*
	@BatchMapping(typeName = "LocationResponse", field = "wineData", maxBatchSize = 25)
	public Map<LocationResponse, List<WineData>> getAllWineData(List<LocationResponse> responses) {
		log.info("LocationResponse get ALL wine data executed");
		var map = new HashMap<LocationResponse, List<WineData>>();
		responses.forEach(x -> map.put(x, List.of(new WineData("region 1", "tbd"), new WineData("region 2", "tbd")) ));
		return map;
	}

	@QueryMapping(name="getAllLocations")
	public List<LocationResponse> locations() {
		var locations = service.getLocations();
		return locations
				.stream()
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getTotal(), x.getActors(), null))
				.toList();
	}
*/

	@QueryMapping(name="getLocationByName")
	public List<LocationResponse> locationsByName(@Argument("filter") MemberType type, @NotBlank @Argument("name") String name) {
		var locations = service.getLocations();
		return locations
				.stream()
				.filter(x -> x.getId().toLowerCase().contains(name.toLowerCase()))
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getActors(), x.getTotal(), new BigDecimal("12345.12345"), type))
				.toList();
	}

	@QueryMapping(name="getAllLocations")
	public List<LocationResponse> locations(@Argument("filter") MemberType type) {
		var locations = service.getLocations();
		return locations
				.stream()
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getActors(), x.getTotal(), new BigDecimal("12345.12345"), type))
				.toList();
	}

	@BatchMapping(typeName = "LocationResponse", field = "regionDetailsData", maxBatchSize = 25)
	public Map<LocationResponse, List<?>> getAllWineData(List<LocationResponse> responses) {
		log.info("LocationResponse get ALL wine data executed");
		var map = new HashMap<LocationResponse, List<?>>();
		responses.forEach(x -> {
			if(x.getType() == MemberType.WINE) map.put(x, List.of(new WineData("region 1", "wine tbd"), new WineData("region 2", "wine tbd")) );
			else if(x.getType() == MemberType.OTHER) map.put(x, List.of(new OtherData("region 1", "other tbd"), new OtherData("region 2", "other tbd")) );
			else if(x.getType() == MemberType.BLAH) map.put(x, List.of(new BlahData("region 001", "blah tbd"), new BlahData("region 2", "other tbd")) );
		});
		return map;
	}

	@QueryMapping(name = "getPaginatedLocation")
	public List<LocationResponse> getPaginatedLocation(@Argument PageInput page, @Argument("filter") MemberType type) {
		// fetch all students records
		log.info(":: fetching all members ::");
		if (page.getAfter() != null) {
			String decodedValue = EncoderDecoder.decode(page.getAfter());
			int offset = Integer.parseInt(decodedValue)/page.getLimit();
			page.setOffset(offset);
		}
		var locations = service.getPaginatedLocations(page);

		return locations
				.stream()
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getActors(), x.getTotal(), new BigDecimal("12345.12345"), type))
				.toList();
	}

	@QueryMapping(name = "getPaginatedLocation2")
	public Connection<LocationResponse> getPaginatedLocation2(@Argument PageInput page, @Argument("filter") MemberType type) {
		// fetch all students records
		log.info(":: fetching all members ::");

		// naive after implementation
		if (page.getAfter() != null) {
			String decodedValue = EncoderDecoder.decode(page.getAfter());
			log.info("decoded value: " + decodedValue);
			int offset = 5; //Integer.parseInt(decodedValue)/page.getLimit();
			page.setOffset(offset);
		}
		var locations = service.getPaginatedLocations(page);

		List<Edge<LocationResponse>> edges = locations
				.stream()
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getActors(), x.getTotal(), new BigDecimal("12345.12345"), type))
				.map(location ->
						(Edge<LocationResponse>) new DefaultEdge<LocationResponse>(location, new DefaultConnectionCursor(EncoderDecoder.encode(id(location)))))
				.toList();

		ConnectionCursor startCursor = edges.isEmpty() ? null : edges.get(0).getCursor();
		ConnectionCursor endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
		boolean hasPrev = page.getOffset() > 0;
		boolean hasNext = page.getLimit() == locations.size();

		PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, hasPrev, hasNext);
		return new DefaultConnection<>(edges, pageInfo);
	}

	private static String id(LocationResponse location) {
		return location.getTitle();
	}

	@MutationMapping("addLocation")
	public Response add(ArgumentValue<LocationInput> input) {
		if(input.isOmitted()) {
			return new Response(Status.FAILURE, "n/a", "invalid input");
		}
		var meta = service.add(input.value());
		var response = new Response(Status.SUCCESS, meta.getId(), "saved new location :: " + meta);
		//locationStream.next(response);
		return response;
	}

	@MutationMapping("removeLocation")
	public Response removeMember(@Argument String title) {
		var result = service.delete(title);
		var response =  new Response(Status.SUCCESS,  result.getId(), "location deleted :: " + result);
		//locationStream.next(response);
		return response;
	}
}
