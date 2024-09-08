package app.parvin.weatherappp.ui

import app.parvin.weatherappp.R

enum class WeatherType(
    val id: Int,
    val iconRes: Int
) {
    ClearSky(0, R.drawable.ic_sun),
    MainlyClear(1, R.drawable.ic_sun),
    PartlyCloudy(2, R.drawable.ic_sun_cloud),
    Overcast(3, R.drawable.ic_cloudy),
    Fog(45, R.drawable.ic_fog),
    DepositingRimeFog(48, R.drawable.ic_fog),
    LightDrizzle(51, R.drawable.ic_drizzle_slight),
    ModerateDrizzle(53, R.drawable.ic_drizzle_heavy),
    DenseDrizzle(55, R.drawable.ic_drizzle_heavy),
    LightFreezingDrizzle(56, R.drawable.ic_drizzle_frozen),
    DenseFreezingDrizzle(57, R.drawable.ic_drizzle_frozen),
    SlightRain(61, R.drawable.ic_rain_light),
    ModerateRain(63, R.drawable.ic_rain_moderate),
    HeavyRain(65, R.drawable.ic_rain_heavy),
    LightFreezingRain(66, R.drawable.ic_rain_snow),
    HeavyFreezingRain(67, R.drawable.ic_rain_snow),
    SlightSnow(71, R.drawable.ic_snow_slight),
    ModerateSnow(73, R.drawable.ic_snow_moderate),
    HeavySnow(75, R.drawable.ic_snow_intense),
    SnowGrains(77, R.drawable.ic_snow_grain),
    SlightRainShowers(80, R.drawable.ic_rain_light),
    ModerateRainShowers(81, R.drawable.ic_rain_moderate),
    ViolentRainShowers(82, R.drawable.ic_rain_heavy),
    SlightSnowShowers(85, R.drawable.ic_snow_moderate),
    HeavySnowShowers(86, R.drawable.ic_snow_intense);


    companion object {
        fun from(id: Int) = entries.find { it.id == id }
    }
}