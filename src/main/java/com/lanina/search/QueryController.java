package com.lanina.search;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@AllArgsConstructor
@Slf4j
public class QueryController {

	private ITravelPhotoService service;

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
	@QueryMapping
	public String firstQuery() {
		return "First GQL Query successful";
	}

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

	@QueryMapping(name="getAllLocations")
	public List<LocationResponse> locations(@Argument("filter") MemberType type) {
		var locations = service.getLocations();
		return locations
				.stream()
				.map(x -> new LocationResponse(x.getYearAsString(), x.getId(), x.getCountries(), x.getActors(), x.getTotal(), type))
				.toList();
	}

	@BatchMapping(typeName = "LocationResponse", field = "regionDetailsData", maxBatchSize = 25)
	public Map<LocationResponse, List<LocationDetailsResponse>> getAllWineData(List<LocationResponse> responses) {
		log.info("LocationResponse get ALL wine data executed");
		var map = new HashMap<LocationResponse, List<LocationDetailsResponse>>();
		responses.forEach(x -> {
			if(x.getType() == MemberType.WINE) map.put(x, List.of(new WineData("region 1", "wine tbd"), new WineData("region 2", "wine tbd")) );
			else if(x.getType() == MemberType.TRAIL) map.put(x, List.of(new TrailData("region 1", "trail tbd"), new TrailData("region 2", "trail tbd")) );
			else if(x.getType() == MemberType.OTHER) map.put(x, List.of(new OtherData("region 1", "trail tbd"), new OtherData("region 2", "trail tbd")) );
		});
		return map;
	}
}
