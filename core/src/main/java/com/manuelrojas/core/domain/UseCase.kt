package com.manuelrojas.core.domain

import io.reactivex.disposables.CompositeDisposable

abstract class UseCase<T> {

    abstract fun executeUseCase(onStatus: (status: T)->Unit )

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun clear() {
        compositeDisposable.clear()
    }
}