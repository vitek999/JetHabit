package screens.train.detailed.model

sealed class TrainDetailedAction {
    object NavigateBack: TrainDetailedAction()
    data class NavigateRecord(val trainId: Long, val userId: Long): TrainDetailedAction()
    data class OpenRecordPrediction(val recordId: Long): TrainDetailedAction()
}