package data.features.trains.main.models

import data.ExerciseTimeStampEntity
import kotlinx.serialization.Serializable

data class Exercise(
    val recordId: Long,
    val timestamp: Long,
)

@Serializable
data class ExerciseDto(
    val recordId: Long,
    val timestamp: Long,
)

fun ExerciseTimeStampEntity.asExercise(): Exercise = Exercise(
    recordId = recordId,
    timestamp = timestamp,
)

fun Exercise.asDto(): ExerciseDto = ExerciseDto(
    recordId = recordId,
    timestamp = timestamp,
)
