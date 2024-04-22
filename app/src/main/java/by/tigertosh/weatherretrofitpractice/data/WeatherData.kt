package by.tigertosh.weatherretrofitpractice.data

data class WeatherData(
    val location: Location,
    val current: Current,
    val forecast: ForecastData
)


data class Location(
    val name: String,
    val localtime: String
)

data class Current(
    val last_updated: String,
    val temp_c: String,
    val condition: Condition

)

data class Condition(
    val text: String,
    val icon: String
)
