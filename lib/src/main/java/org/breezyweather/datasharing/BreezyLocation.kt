/*
 * This file is part of Breezy Weather.
 *
 * Breezy Weather is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 *
 * Breezy Weather is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.datasharing

import android.database.Cursor
import kotlinx.serialization.json.Json
import org.breezyweather.datasharing.extensions.getStringOrNull
import org.breezyweather.datasharing.json.BreezyWeather
import org.breezyweather.datasharing.provider.ProviderLocation

data class BreezyLocation(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val isCurrentPosition: Boolean = false,
    val timeZone: String,

    val customName: String?,
    val country: String,
    val countryCode: String?,
    val admin1: String?,
    val admin1Code: String?,
    val admin2: String?,
    val admin2Code: String?,
    val admin3: String?,
    val admin3Code: String?,
    val admin4: String?,
    val admin4Code: String?,
    val city: String,
    val district: String?,

    val weather: BreezyWeather?,

) {
    companion object {
        fun toBreezyLocation(cursor: Cursor): BreezyLocation {
            val json = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                isLenient = true // TODO: Make false in debug mode
            }

            return BreezyLocation(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ID)),
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_LATITUDE)),
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_LONGITUDE)),
                isCurrentPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_IS_CURRENT_POSITION)) > 0,
                timeZone = cursor.getString(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_TIMEZONE)),
                customName = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_CUSTOM_NAME)),
                country = cursor.getString(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_COUNTRY)),
                countryCode = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_COUNTRY_CODE)),
                admin1 = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN1)),
                admin1Code = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN1_CODE)),
                admin2 = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN2)),
                admin2Code = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN2_CODE)),
                admin3 = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN3)),
                admin3Code = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN3_CODE)),
                admin4 = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN4)),
                admin4Code = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_ADMIN4_CODE)),
                city = cursor.getString(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_CITY)),
                district = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_DISTRICT)),
                weather = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(ProviderLocation.COLUMN_WEATHER))?.let {
                    json.decodeFromString<BreezyWeather>(it)
                }
            )
        }
    }
}