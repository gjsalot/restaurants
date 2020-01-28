package com.grantsutcliffe.restaurants.core

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Presenter<VM : ViewModel, A: PresenterAction, E : UiEvent> {

    protected var view: PresenterView<VM, A, E>? = null

    private val disposables = CompositeDisposable()

    protected open fun onStart(view: PresenterView<VM, A, E>) = Unit

    protected open fun onStop() = Unit

    protected fun Disposable.disposeOnStop() {
        disposables.add(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun start(view: PresenterView<*, *, *>) {
        this.view = view as PresenterView<VM, A, E>
        onStart(view)
    }

    fun stop() {
        onStop()
        disposables.clear()
        this.view = null
    }

}