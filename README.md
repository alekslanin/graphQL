# graphQL

@QueryMapping(name="getLocationByName")
@BatchMapping(typeName = "LocationResponse", field = "regionDetailsData", maxBatchSize = 25)
@SchemaMapping(typeName = "LocationResponse", field = "wineData")

# interfaces
# unions
# fragments

# extended scalars
# directives

testing graphQL features

Query Example:

~~~
query Search ($name: String!) {
  ByName: getLocationByName(filter: WINE, name: $name) {
    year
    title
    country
    total
    actors
    type
    regionDetailsData {
      region
    }
  }
  
  All: getAllLocations(filter: WINE) {
    year
    title
    country
    total
    actors
    type
    regionDetailsData {
      region
      ... on OtherData {
        other
      }
      ... on WineData {
        wine
      }
    }
  }
}

{
  getPaginatedLocation2(page: {after:"bi9hIGhvbWUgcGljcw==", limit:2}, filter: BLAH) {
    pageInfo {
      startCursor
      endCursor
      hasPreviousPage
      hasNextPage
    }
    edges {
      node {
          year
          title
          country
          total
          actors
          mark
          type
          regionDetailsData {
            region
            ... on OtherData {
              other
            }
            ... on BlahData {
              blah
            }
            ... on WineData {
              wine
            }
         }
       }
      cursor
    }
  }
}

VARIABLES:
{
  "name" : "poco"
}
~~~