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
data class BreezyDailyUnit(
    /**
     * Average value of the day. Some sources may provide a mean value here
     */
    val avg: BreezyUnit? = null,
    /**
     * Maximum value of the day
     */
    val max: BreezyUnit? = null,
    /**
     * Minimum value of the day
     */
    val min: BreezyUnit? = null,
    /**
     * A textual representation of the daily values
     * Always null for the moment
     */
    val summary: String? = null,
)
