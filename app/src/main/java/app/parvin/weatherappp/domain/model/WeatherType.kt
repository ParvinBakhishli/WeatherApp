package app.parvin.weatherappp.domain.model

import app.parvin.weatherappp.R

enum class WeatherType(
    val id: Int,
    val iconDay: Int,
    val iconNight: Int
) {
    ClearSky(0, R.drawable.ic_sun, R.drawable.ic_moon),
    MainlyClear(1, R.drawable.ic_sun, R.drawable.ic_moon),
    PartlyCloudy(2, R.drawable.ic_sun_cloud, R.drawable.ic_moon_cloud),
    Overcast(3, R.drawable.ic_sun_cloud, R.drawable.ic_moon_cloud),
    Fog(45, R.drawable.ic_fog, R.drawable.ic_fog_night),
    DepositingRimeFog(48, R.drawable.ic_fog, R.drawable.ic_fog_night),
    LightDrizzle(51, R.drawable.ic_drizzle_slight, R.drawable.ic_drizzle_slight_night),
    ModerateDrizzle(53, R.drawable.ic_drizzle_heavy, R.drawable.ic_drizzle_heavy_night),
    DenseDrizzle(55, R.drawable.ic_drizzle_heavy, R.drawable.ic_drizzle_heavy_night),
    LightFreezingDrizzle(56, R.drawable.ic_drizzle_frozen, R.drawable.ic_drizzle_slight_night),
    DenseFreezingDrizzle(57, R.drawable.ic_drizzle_frozen, R.drawable.ic_drizzle_heavy_night),
    SlightRain(61, R.drawable.ic_rain_light, R.drawable.ic_rain_night),
    ModerateRain(63, R.drawable.ic_rain_moderate, R.drawable.ic_rain_night),
    HeavyRain(65, R.drawable.ic_rain_heavy, R.drawable.ic_rain_heavy),
    LightFreezingRain(66, R.drawable.ic_rain_snow, R.drawable.ic_rain_night),
    HeavyFreezingRain(67, R.drawable.ic_rain_snow, R.drawable.ic_rain_heavy),
    SlightSnow(71, R.drawable.ic_snow_slight, R.drawable.ic_snow_slight_night),
    ModerateSnow(73, R.drawable.ic_snow_moderate, R.drawable.ic_snow_slight_night),
    HeavySnow(75, R.drawable.ic_snow_intense, R.drawable.ic_snow_intense),
    SnowGrains(77, R.drawable.ic_snow_grain, R.drawable.ic_snow_grain_night),
    SlightRainShowers(80, R.drawable.ic_rain_light, R.drawable.ic_rain_night),
    ModerateRainShowers(81, R.drawable.ic_rain_moderate, R.drawable.ic_rain_night),
    ViolentRainShowers(82, R.drawable.ic_rain_heavy, R.drawable.ic_rain_heavy),
    SlightSnowShowers(85, R.drawable.ic_snow_moderate, R.drawable.ic_snow_slight_night),
    HeavySnowShowers(86, R.drawable.ic_snow_intense, R.drawable.ic_snow_intense);


    companion object {
        fun from(id: Int) = entries.find { it.id == id }
    }
}