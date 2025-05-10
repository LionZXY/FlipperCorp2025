package uk.kulikov.flippercorp2025.loading.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsBytes
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.asByteWriteChannel
import io.ktor.utils.io.writeByteArray
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.kotlincrypto.hash.md.MD5
import uk.kulikov.flippercorp2025.loading.LoadingState
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.utils.PlatformAppPath
import uk.kulikov.flippercorp2025.utils.resolve
import kotlin.collections.map

data class ImageLoader(
    private val client: HttpClient,
    private val platformAppPath: PlatformAppPath
) {
    suspend fun loadAllImages(
        onLoadingState: (LoadingState) -> Unit,
        events: List<DayEvents>
    ): List<DayEvents> {
        val allImagesUris = events.map { day ->
            day.events.mapNotNull { it.activity?.imageUri }
        }.flatten().toSet()

        val newImagesUris = mutableMapOf<String, String>()

        allImagesUris.forEachIndexed { index, imageUri ->
            onLoadingState(LoadingState.Loading.WithProgress.LoadingImages(index, allImagesUris.size))
            val processedUri = processUri(imageUri)
            newImagesUris[imageUri] = processedUri
        }

        return events.map { day ->
            day.copy(
                events = replaceActivitiesInEvents(day.events, newImagesUris)
            )
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun processUri(uri: String): String {
        val imageBytes = client.get {
            url(uri)
        }.bodyAsBytes()
        val md5 = MD5().digest(imageBytes).toHexString()

        val imagesFolder = platformAppPath.appPath.resolve("images")
        SystemFileSystem.createDirectories(imagesFolder)

        val filePath = imagesFolder.resolve(md5)
        SystemFileSystem.sink(filePath).use { sink ->
            sink.asByteWriteChannel().writeByteArray(imageBytes)
        }

        return SystemFileSystem.resolve(filePath).toString()
    }

    private fun replaceActivitiesInEvents(
        events: ImmutableList<Event>,
        newUris: Map<String, String>
    ): ImmutableList<Event> {
        return events.map { event ->
            val activity = event.activity
            if (activity != null) {
                event.copy(
                    activity = activity.copy(
                        imageUri = newUris[activity.imageUri]
                            ?: error("Can't find image with uri ${activity.imageUri}")
                    )
                )
            } else {
                event
            }
        }.toPersistentList()
    }
}