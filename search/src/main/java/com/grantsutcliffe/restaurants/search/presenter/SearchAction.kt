package com.grantsutcliffe.restaurants.search.presenter

import com.grantsutcliffe.restaurants.core.PresenterAction

sealed class SearchAction : PresenterAction {

    data class Call(val phoneNumberUri: String) : SearchAction()

    data class ShowDetail(val id: String) : SearchAction()

}