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
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.breezyweather.datasharing.BreezyLocation
import org.breezyweather.datasharing.sample.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We take the first package found, but you could allow the user to choose when more than 1
        val installedPackage = getInstalledBreezyWeatherPackages(this).firstOrNull()

        val contentProvider = installedPackage?.let { BreezyContentProvider(it) }

        val permissionGranted = contentProvider?.let {
            ContextCompat.checkSelfPermission(this, it.readPermission) == PackageManager.PERMISSION_GRANTED
        } ?: false

        val compatibleVersion = contentProvider?.isCompatible(this) ?: false

        val locations = if (compatibleVersion) contentProvider.getLocations(this, 5) else emptyList()

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val layoutDirection = LocalLayoutDirection.current
                    BreezyWeatherDataSharingShowcase(
                        installedPackage = installedPackage,
                        permissionGranted = permissionGranted,
                        compatibleVersion = compatibleVersion,
                        locations = locations,
                        loadWeather = { locationId ->
                            contentProvider?.getLocationWithWeather(this, locationId)
                        },
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding() + 16.dp,
                            bottom = innerPadding.calculateBottomPadding() + 16.dp,
                            start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                            end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp
                        )
                    )
                }
            }
        }
    }

    private fun getInstalledBreezyWeatherPackages(context: Context): List<String> {
        return BreezyContentProvider.SUPPORTED_PACKAGES
            .filter { isPackageInstalled(context, it) }
    }

    private fun isPackageInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0).enabled
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }
    }
}

@Composable
fun BreezyWeatherDataSharingShowcase(
    installedPackage: String?,
    permissionGranted: Boolean,
    compatibleVersion: Boolean,
    locations: List<BreezyLocation>,
    loadWeather: (String) -> BreezyLocation?,
    modifier: Modifier = Modifier,
) {
    val isLoading = remember { mutableStateOf(false) }
    val selectedLocation: MutableState<BreezyLocation?> = remember { mutableStateOf(null) }
    val scope = CoroutineScope(Job() + Dispatchers.Default)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (installedPackage != null) {
                    "Installed package found: $installedPackage"
                } else {
                    "No installed package found!"
                }
            )
        }
        if (installedPackage != null) {
            item {
                Text(
                    text = if (permissionGranted) {
                        "Permission granted"
                    } else {
                        "Missing permission!"
                    }
                )
            }
            if (permissionGranted) {
                item {
                    Text(
                        text = if (compatibleVersion) {
                            "Compatible version"
                        } else {
                            "Incompatible version!"
                        }
                    )
                }
                if (compatibleVersion) {
                    item {
                        Text(text = "Found ${locations.size} locations")
                    }
                    items(locations) {
                        TextButton(
                            onClick = {
                                selectedLocation.value = null
                                isLoading.value = true
                                scope.launch {
                                    selectedLocation.value = loadWeather(it.id)
                                    isLoading.value = false
                                }
                            }
                        ) {
                            Text(text = it.customName ?: it.city)
                        }
                    }

                    if (isLoading.value) {
                        // Just print the object for the example
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }

                    if (selectedLocation.value != null) {
                        // Just print the object for the example
                        item {
                            Text(
                                text = "${selectedLocation.value}"
                            )
                        }
                    }
                }
            }
        }
    }
}
