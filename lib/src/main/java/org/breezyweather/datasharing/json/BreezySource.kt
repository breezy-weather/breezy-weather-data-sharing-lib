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

package org.breezyweather.datasharing.json

import kotlinx.serialization.Serializable

@Serializable
data class BreezySource(
    /**
     * The type of data provided by the source, translated
     * Example: "Air quality"
     */
    val type: String? = null,
    /**
     * Credits to display
     * Example: "Open-Meteo (CC BY 4.0)"
     */
    val text: String? = null,
    /**
     * Links to insert in the text
     * Example: {"CC BY 4.0" to "https://creativecommons.org/licenses/by/4.0/"}
     */
    val links: Map<String, String>? = null,
)
