package screens.detail.models

import kotlinx.datetime.Instant

sealed class DetailEvent {
    object DeleteItem : DetailEvent()
    object CloseScreen : DetailEvent()
    object SaveChanges : DetailEvent()
    object ActionInvoked : DetailEvent()
    data class StartDateSelected(val value: Instant) : DetailEvent()
    data class EndDateSelected(val value: Instant) : DetailEvent()
}