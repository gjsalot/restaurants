package com.grantsutcliffe.restaurants.search.presenter

import com.grantsutcliffe.restaurants.core.PresenterView
import com.grantsutcliffe.restaurants.core.model.*
import com.grantsutcliffe.restaurants.core.repository.RestaurantsRepository
import com.grantsutcliffe.restaurants.search.model.RestaurantUI
import com.grantsutcliffe.restaurants.search.viewmodel.SearchViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import java.io.IOException

class SearchPresenterTest {

    private val restaurantsRepository: RestaurantsRepository = mock()
    private val schedulers = object: com.grantsutcliffe.restaurants.core.rx.Schedulers {
        override val ui: Scheduler = Schedulers.trampoline()
        override val io: Scheduler = Schedulers.trampoline()
        override val computation: Scheduler = Schedulers.trampoline()
    }
    private val underTest = SearchPresenter(restaurantsRepository, schedulers)
    private val uiEvents = PublishSubject.create<SearchUiEvent>()
    private val view: PresenterView<SearchViewModel, SearchAction, SearchUiEvent> = mock {
        whenever(mock.uiEvents).thenReturn(uiEvents)
    }

    @Test
    fun testGetRestaurants() {
        whenever(restaurantsRepository.getRestaurants()).thenReturn(Observable.just(
            listOf(
                Restaurant(
                    "1",
                    "Restaurant 1",
                    "Address 1",
                    Icons(
                        IconEntry("icon.png"),
                        "badge.png"
                    ),
                    PrimaryAction("tel:911"),
                    listOf(
                        Category("category 1"),
                        Category("category 2")
                    )
                )
            )
        ))
        val expectedResult = SearchViewModel.Result(
            listOf(
                RestaurantUI(
                    "1",
                    "Restaurant 1",
                    "Address 1",
                    "icon.png",
                    "badge.png",
                    "tel:911",
                    "category 1 • category 2"
                )
            )
        )

        underTest.start(view)

        inOrder(view) {
            verify(view).render(SearchViewModel.Loading)
            verify(view).render(expectedResult)
        }
    }

    @Test
    fun testGetRestaurantsError() {
        whenever(restaurantsRepository.getRestaurants()).thenReturn(Observable.error(IOException("No internet")))

        underTest.start(view)

        inOrder(view) {
            verify(view).render(SearchViewModel.Loading)
            verify(view).render(SearchViewModel.Error)
        }
    }

    @Test
    fun testGetRestaurantsErrorRetryError() {
        whenever(restaurantsRepository.getRestaurants()).thenReturn(Observable.error(IOException("No internet")))

        underTest.start(view)

        uiEvents.onNext(SearchUiEvent.TryAgainClicked)

        inOrder(view) {
            verify(view).render(SearchViewModel.Loading)
            verify(view).render(SearchViewModel.Error)
            verify(view).render(SearchViewModel.Loading)
            verify(view).render(SearchViewModel.Error)
        }
    }
}