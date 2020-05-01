package com.manuelrojas.core.domain

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


abstract class UseCase2<T, Params> {

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    abstract fun executeUseCase(result: (result: T)->Unit)

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    abstract fun buildUseCaseObservable(params: Params): Observable<T>

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun clear() {
        compositeDisposable.clear()
    }
}