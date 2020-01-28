package com.grantsutcliffe.restaurants.core

import io.reactivex.Observable

interface PresenterView<VM : ViewModel, A : PresenterAction, E : UiEvent> {

    val uiEvents: Observable<E>

    val presenter: Presenter<VM, A, E>

    fun render(viewModel: VM)

    fun performAction(action: A)

}