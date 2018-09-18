package com.kotlin.test.presentation.movies

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.kotlin.test.R
import com.kotlin.test.core.exception.Failure
import com.kotlin.test.core.exception.Failure.NetworkConnection
import com.kotlin.test.core.exception.Failure.ServerError
import com.kotlin.test.core.extension.*
import com.kotlin.test.core.navigation.Navigator
import com.kotlin.test.core.platform.BaseFragment
import com.kotlin.test.domain.model.MovieFailure.ListNotAvailable
import com.kotlin.test.presentation.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MoviesFragment : BaseFragment() {

    private val outputDateFormat = "yyyy-MM-dd"

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private lateinit var moviesViewModel: MoviesViewModel
    private var pageCalled = 1
    private var filter: String = String.empty()

    override fun layoutId() = R.layout.fragment_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        moviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadMoviesList()
    }

    private fun initializeView() {
        movieFilterButton.setOnClickListener { showDatePicker() }

        val layoutManager = GridLayoutManager(context, 3)
        movieList.layoutManager = layoutManager
        movieList.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing)))
        movieList.adapter = moviesAdapter

        moviesAdapter.clickListener = { movie, navigationExtras ->
            activity?.let {
                navigator.showMovieDetails(it, movie, navigationExtras)
            }
        }

        movieList.addOnScrollListener(
                object : EndlessRecyclerViewScrollListener(layoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int) {
                        showProgress()
                        pageCalled += 1
                        moviesViewModel.loadMoviesByDate(pageCalled, filter)
                    }
                })
    }

    private fun loadMoviesList() {
        emptyView.invisible()
        movieList.visible()
        showProgress()
        moviesViewModel.loadMoviesByDate(pageCalled, filter)
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.collection.addAll(movies.orEmpty())
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        movieList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        context?.let {
            val datePickerDialog = DatePickerDialog(
                    it, onDateSetListener, mYear, mMonth, mDay)
            datePickerDialog.show()
        }
    }

    private val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                setFilterByDate(year, monthOfYear, dayOfMonth)
            }

    @SuppressLint("SimpleDateFormat")
    fun setFilterByDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        showProgress()

        val calendar = Calendar.getInstance()

        calendar.set(year, monthOfYear, dayOfMonth)

        val date = calendar.time
        val outputFormat = SimpleDateFormat(outputDateFormat)

        pageCalled = 1
        moviesAdapter.collection = arrayListOf()

        filter = outputFormat.format(date)
        moviesViewModel.loadMoviesByDate(pageCalled, filter)
    }
}
