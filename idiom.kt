class IdiomaticClass(val idiomLastDay: LocalDate) {
    companion object {
        operator fun invoke(): IdiomaticClass =
            IdiomaticClass(idiomLastDay = LocalDate.now())
    }
}
