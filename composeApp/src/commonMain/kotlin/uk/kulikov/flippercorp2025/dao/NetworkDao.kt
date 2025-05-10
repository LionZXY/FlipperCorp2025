package uk.kulikov.flippercorp2025.dao

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import uk.kulikov.flippercorp2025.model.DayEvents
import uk.kulikov.flippercorp2025.model.api.Event
import uk.kulikov.flippercorp2025.model.api.Question

private const val HOST_URL = "https://cultural-flip.online"

object NetworkDao {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun getQuestions(): List<Question> {
        return client.get("$HOST_URL/api/questions/getAll").body()
    }

    suspend fun getAllData(): List<DayEvents> = withContext(Dispatchers.Default) {
        val dates = getDates()

        return@withContext dates.map {
            val dayEvents = getDayEvents(it)
            dayEvents.copy(
                events = dayEvents
                    .events
                    .map { event ->
                        val activity = event.activity
                        if (activity != null) {
                            event.copy(
                                activity = activity.copy(
                                    imageUri = "${HOST_URL}/images/${activity.imageUri}"
                                )
                            )
                        } else {
                            event
                        }
                    }.toPersistentList()
            )

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