# graphQL
testing graphQL features

Query Example:

~~~
{
    getAllLocations(filter: WINE) {
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
~~~