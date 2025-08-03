/**
 * This file is part of Breezy Weather.
 *
 * Breezy Weather is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License.
 *
 * Breezy Weather is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.datasharing.provider

object ProviderLocation {
    /**
     * Unique ID of the location
     * The ID can change, so don’t rely on it between different queries
     */
    const val COLUMN_ID = "id"
    const val COLUMN_LATITUDE = "latitude"
    const val COLUMN_LONGITUDE = "longitude"
    const val COLUMN_IS_CURRENT_POSITION = "is_current_position"
    const val COLUMN_TIMEZONE = "timezone"
    const val COLUMN_CUSTOM_NAME = "custom_name"
    const val COLUMN_COUNTRY = "country"
    const val COLUMN_COUNTRY_CODE = "country_code"
    const val COLUMN_ADMIN1 = "admin1"
    const val COLUMN_ADMIN1_CODE = "admin1_code"
    const val COLUMN_ADMIN2 = "admin2"
    const val COLUMN_ADMIN2_CODE = "admin2_code"
    const val COLUMN_ADMIN3 = "admin3"
    const val COLUMN_ADMIN3_CODE = "admin3_code"
    const val COLUMN_ADMIN4 = "admin4"
    const val COLUMN_ADMIN4_CODE = "admin4_code"
    const val COLUMN_CITY = "city"
    const val COLUMN_DISTRICT = "district"
    const val COLUMN_WEATHER = "weather"
}