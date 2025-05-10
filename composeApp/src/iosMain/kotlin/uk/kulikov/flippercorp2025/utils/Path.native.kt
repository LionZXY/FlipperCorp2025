package uk.kulikov.flippercorp2025.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.io.files.Path
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

private const val appName = "uk.kulikov.flippercorp2025"

@OptIn(ExperimentalForeignApi::class)
fun getAppPath(): Path = Path(
    NSSearchPathForDirectoriesInDomains(NSApplicationSupportDirectory.convert(), NSUserDomainMask.convert(), true)
        .firstOrNull()?.toString() ?: error("Cannot get SystemSupportDir"),
).resolve(appName)