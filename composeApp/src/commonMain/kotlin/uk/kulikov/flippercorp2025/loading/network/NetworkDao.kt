package uk.kulikov.flippercorp2025.loading.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import uk.kulikov.flippercorp2025.loading.LoadingState
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.model.LoadedAppState
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.Question

internal const val HOST_URL = "https://cultural-flip.online"

object NetworkDao {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun load(
        onLoadingState: (LoadingState) -> Unit
    ): Result<LoadedAppState> = runCatching {
        withContext(Dispatchers.Default) {
            val events = getAllData(onLoadingState)
            onLoadingState(LoadingState.Loading.LoadingQuestions)
            val questions = getQuestions()

            return@withContext LoadedAppState(
                questions.toImmutableList(),
                events.toImmutableList()
            )
        }
    }

    private suspend fun getQuestions(): List<Question> {
        return client.get("$HOST_URL/api/questions/getAll").body()
    }

    private suspend fun getAllData(onLoadingState: (LoadingState) -> Unit): List<DayEvents> =
        withContext(Dispatchers.Default) {
            onLoadingState(LoadingState.Loading.LoadingDates)
            val dates = getDates()

            return@withContext dates.mapIndexed { index, date ->
                val dayEvents = getDayEvents(date)
                onLoadingState(LoadingState.Loading.WithProgress.LoadingEvents(index, dates.size))
                dayEvents.copy(
                    events = dayEvents
                        .events
                        .map { event ->
                            processEvent(event)
                        }.toPersistentList()
                )
            }
        }

    private fun processEvent(event: Event): Event {
        val activity = event.activity
        return if (activity != null) {
            event.copy(
                activity = activity.copy(
                    imageUri = "${HOST_URL}/images/${activity.imageUri}"
                )
            )
        } else {
            event
        }
    }

    private suspend fun getDates(): List<LocalDate> {
        return client.get {
            url("${HOST_URL}/api/schedule/dates")
        }.body()
    }

    private suspend fun getDayEvents(date: LocalDate): DayEvents {
        val events = client.get {
            url("${HOST_URL}/api/schedule/${date.toString()}")
        }.body<List<Event>>()
        return DayEvents(date, events.toImmutableList())
    }
}