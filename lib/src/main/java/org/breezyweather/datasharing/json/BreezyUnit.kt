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

/**
 * Class that provides a weather data in the user preferred value
 */
@Serializable
data class BreezyUnit(
    /**
     * Example: 75.2
     */
    val value: Double? = null,
    /**
     * Example: f
     */
    val unit: String? = null,
    /**
     * Some units have a description
     * Examples:
     * - Gentle breeze
     * - Partly cloudy
     * - Very poor (visibility)
     */
    val description: String? = null,
    /**
     * Some units have a color (UV index, etc)
     * In hexadecimal format
     */
    val color: String? = null,
)
