package by.tigertosh.weatherretrofitpractice.data

data class ForecastData(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>

)

data class Day(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val condition: Condition
)

data class Hour(
    val time: String,
    val temp_c: Float,
    val condition: Condition
)
