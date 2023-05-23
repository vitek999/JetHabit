package data.features.trains.main.remote

import data.features.trains.main.models.RecordDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import utils.NetworkUtils

class TrainsRemoteDataSource(val httpClient: HttpClient) {
  suspend fun predictResults(recordDto: RecordDto): TrainResultRemoteDto? {
    return runCatching {
      val requestJsonBody = Json.encodeToString(ListSerializer(RecordDto.serializer()), listOf(recordDto))
      println(requestJsonBody)
      val response = httpClient.post("${NetworkUtils.BASE_URL}/predict") {
        contentType(ContentType.Application.Json)
        setBody(requestJsonBody)
      }
      Json.decodeFromString(TrainResultRemoteDto.serializer(),  response.bodyAsText())
    }.getOrElse {
      println("Error sending results to prediction")
      it.printStackTrace()
      null
    }
  }
}