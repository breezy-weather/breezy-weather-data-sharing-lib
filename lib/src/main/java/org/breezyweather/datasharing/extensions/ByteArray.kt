package org.breezyweather.datasharing.extensions

import java.io.ByteArrayInputStream
import java.util.zip.GZIPInputStream

/**
 * Decompress a byte array using GZIP.
 *
 * @return an UTF-8 encoded string.
 */
fun ByteArray.gzipDecompress(): String {
    val bais = ByteArrayInputStream(this)
    return GZIPInputStream(bais).bufferedReader(Charsets.UTF_8).use { it.readText() }
}
