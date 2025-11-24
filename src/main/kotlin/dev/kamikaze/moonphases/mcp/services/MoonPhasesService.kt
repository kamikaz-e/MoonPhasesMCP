package dev.kamikaze.moonphases.mcp.services

import dev.kamikaze.moonphases.mcp.models.ApiVerveResponse
import dev.kamikaze.moonphases.mcp.models.MoonPhaseResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoonPhasesService {
    
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    private val apiKey = "04b8d5a8-96b6-4926-a2ed-17ba06cd33af"
    private val baseUrl = "https://api.apiverve.com/v1/moonphases"
    
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 10_000
        }
        
        defaultRequest {
            header("x-api-key", apiKey)
        }
    }
    
    /**
     * Получить фазу луны для указанной даты
     * @param date дата в формате MM-DD-YYYY
     */
    suspend fun getMoonPhaseByDate(date: String): Result<MoonPhaseResponse> {
        return try {
            logger.info("Fetching moon phase for date: $date")
            
            val response: ApiVerveResponse = client.get(baseUrl) {
                parameter("date", date)
            }.body()
            
            if (response.status == "ok" && response.data != null) {
                logger.info("Successfully fetched moon phase: ${response.data.phase}")
                Result.success(
                    MoonPhaseResponse(
                        phase = response.data.phase,
                        phaseEmoji = response.data.phaseEmoji,
                        waxing = response.data.waxing,
                        waning = response.data.waning,
                        lunarAge = response.data.lunarAge,
                        lunarAgePercent = response.data.lunarAgePercent,
                        lunationNumber = response.data.lunationNumber,
                        lunarDistance = response.data.lunarDistance,
                        nextFullMoon = response.data.nextFullMoon,
                        lastFullMoon = response.data.lastFullMoon
                    )
                )
            } else {
                val errorMsg = response.error ?: "Unknown error"
                logger.error("API returned error: $errorMsg")
                Result.failure(Exception(errorMsg))
            }
            
        } catch (e: Exception) {
            logger.error("Failed to fetch moon phase", e)
            Result.failure(e)
        }
    }
    
    /**
     * Получить текущую фазу луны
     */
    suspend fun getCurrentMoonPhase(): Result<MoonPhaseResponse> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val dateString = today.format(formatter)
        
        return getMoonPhaseByDate(dateString)
    }
    
    /**
     * Закрыть HTTP клиент
     */
    fun close() {
        client.close()
    }
}
