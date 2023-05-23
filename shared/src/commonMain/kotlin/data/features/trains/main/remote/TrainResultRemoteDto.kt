package data.features.trains.main.remote

import kotlinx.serialization.Serializable

@Serializable
data class TrainResultRemoteDto(val result: List<String>)