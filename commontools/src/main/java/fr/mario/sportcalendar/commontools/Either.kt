package fr.mario.sportcalendar.commontools

sealed class Either<out SuccessTemplate, out FailureTemplate> {
    data class Success<out SuccessTemplate>(val value: SuccessTemplate) :
        Either<SuccessTemplate, Nothing>()

    data class Failure<out FailureTemplate>(val value: FailureTemplate) :
        Either<Nothing, FailureTemplate>()

    inline fun <ResultTemplate> fold(
        foldSuccess: (SuccessTemplate) -> ResultTemplate,
        foldFailure: (FailureTemplate) -> ResultTemplate
    ): ResultTemplate = when (this) {
        is Success -> foldSuccess(value)
        is Failure -> foldFailure(value)
    }

    inline fun <MapSuccessTemplate> mapSuccess(
        map: (SuccessTemplate) -> MapSuccessTemplate
    ): Either<MapSuccessTemplate, FailureTemplate> = when (this) {
        is Success -> Success(map(value))
        is Failure -> Failure(value)
    }

    inline fun <MapFailureTemplate> mapFailure(
        map: (FailureTemplate) -> MapFailureTemplate
    ): Either<SuccessTemplate, MapFailureTemplate> = when (this) {
        is Success -> Success(value)
        is Failure -> Failure(map(value))
    }

    inline fun <MapSuccessTemplate, MapFailureTemplate> map(
        mapSuccess: (SuccessTemplate) -> MapSuccessTemplate,
        mapFailure: (FailureTemplate) -> MapFailureTemplate
    ): Either<MapSuccessTemplate, MapFailureTemplate> = when (this) {
        is Success -> Success(mapSuccess(value))
        is Failure -> Failure(mapFailure(value))
    }

    inline fun onSuccess(block: (SuccessTemplate) -> Unit): Either<SuccessTemplate, FailureTemplate> {
        when (this) {
            is Success -> block(this.value)
            is Failure -> {}
        }
        return this
    }

    inline fun onFailure(block: (FailureTemplate) -> Unit): Either<SuccessTemplate, FailureTemplate> {
        when (this) {
            is Failure -> block(this.value)
            is Success -> {}
        }
        return this
    }

    fun isSuccess(): Boolean =
        this is Success

    fun isFailure(): Boolean =
        this is Failure

    fun getSuccessValueOrThrow(): SuccessTemplate =
        (this as Success).value

    fun getFailureValueOrThrow(): FailureTemplate =
        (this as Failure).value

    fun getSuccessValueOrNull(): SuccessTemplate? =
        (this as? Success)?.value

    fun getFailureValueOrNull(): FailureTemplate? =
        (this as? Failure)?.value

    companion object {
        inline fun <SuccessTemplate> catch(body: () -> SuccessTemplate): Either<SuccessTemplate, Throwable> =
            try {
                Success(body())
            } catch (t: Throwable) {
                Failure(t)
            }
    }
}

inline fun <SuccessTemplate, FailureTemplate, MapSuccessTemplate>
        Either<SuccessTemplate, FailureTemplate>.mapSuccessEither(
    map: (SuccessTemplate) -> Either<MapSuccessTemplate, FailureTemplate>
): Either<MapSuccessTemplate, FailureTemplate> = when (this) {
    is Either.Success -> map(value)
    is Either.Failure -> Either.Failure(value)
}