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

package org.breezyweather.datasharing.sample

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import org.breezyweather.datasharing.BreezyLocation
import org.breezyweather.datasharing.provider.ProviderUri
import org.breezyweather.datasharing.provider.ProviderVersion

class BreezyContentProvider(
    requestedPackage: String,
) {
    val readPermission: String
    private val versionUri: Uri
    private val locationsUri: Uri
    private val weatherUri: Uri

    init {
        if (requestedPackage !in SUPPORTED_PACKAGES) {
            throw Exception("Unsupported package!")
        }

        readPermission = "$requestedPackage.READ_PROVIDER"
        val authority = "$requestedPackage.provider.weather"
        versionUri = "content://$authority/${ProviderUri.VERSION_PATH}".toUri()
        locationsUri = "content://$authority/${ProviderUri.LOCATIONS_PATH}".toUri()
        weatherUri = "content://$authority/${ProviderUri.WEATHER_PATH}".toUri()
    }

    fun isCompatible(context: Context): Boolean {
        val contentResolver = context.contentResolver
        try {
            contentResolver.query(versionUri, null, null, null, null).use { cursor ->
                if (cursor == null || cursor.count == 0) {
                    Log.w(TAG, "Content provider version not found")
                    return false
                }
                cursor.moveToNext()
                val major = cursor.getInt(cursor.getColumnIndexOrThrow(ProviderVersion.COLUMN_MAJOR))
                val minor = cursor.getInt(cursor.getColumnIndexOrThrow(ProviderVersion.COLUMN_MINOR))

                Log.i(TAG, "Got content provider version: $major.$minor")

                // We support versions 0.x
                return major == 0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get content provider version", e)
        }

        return false
    }

    fun getLocations(context: Context, limit: Int?): List<BreezyLocation> {
        val locations = mutableListOf<BreezyLocation>()
        val contentResolver = context.contentResolver
        try {
            val uri = if (limit != null && limit > 0) {
                locationsUri.buildUpon().appendQueryParameter("limit", limit.toString()).build();
            } else {
                locationsUri
            }

            contentResolver
                .query(
                    uri,
                    null,
                    null,
                    null,
                    null
                ).use { cursor ->
                    if (cursor == null || cursor.count == 0) {
                        Log.d(TAG, "No locations found")
                        return locations
                    }
                    while (cursor.moveToNext()) {
                        locations.add(BreezyLocation.Companion.toBreezyLocation(cursor))
                    }
                }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to list locations", e)
            return locations
        }

        return locations
    }

    fun getLocationWithWeather(context: Context, locationId: String): BreezyLocation? {
        val contentResolver = context.contentResolver
        try {
            contentResolver.query(weatherUri, null, "id=$locationId", null, null).use { cursor ->
                if (cursor == null || cursor.count == 0) {
                    Log.d(TAG, "No matching location found")
                    return null
                }
                cursor.moveToNext()
                return BreezyLocation.Companion.toBreezyLocation(cursor)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get weather for a location", e)
            return null
        }
    }

    companion object {
        private const val TAG = "BreezyContentProvider"
        val SUPPORTED_PACKAGES = arrayOf(
            "org.breezyweather.debug",
            "org.breezyweather"
        )
    }
}