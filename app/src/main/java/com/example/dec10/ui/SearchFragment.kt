package com.example.dec10.ui

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dec10.MainRecyclerViewAdapter
import com.example.dec10.R
import com.example.dec10.models.MovieModel
import com.example.dec10.request.RetrofitInstance
import com.example.dec10.response.MovieSearchResponse
import com.example.dec10.viewmodels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchFragment : Fragment() {

    lateinit var mainAdapter: MainRecyclerViewAdapter
    private lateinit var movieListViewModel: MovieListViewModel
    lateinit var searchMovieData: List<MovieModel>
    lateinit var searchResponseCall: Call<MovieSearchResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieApi = RetrofitInstance.getMovieApi()
        searchResponseCall=movieApi.getPopularMovie()
        searchResponseCall.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.code() == 200) {
                    Log.v("Tag", "the response: ${response.body().toString()}")

                    searchMovieData = response.body()!!.movieList as MutableList<MovieModel>

                    for (m in searchMovieData) {
                        Log.v("Tag", "The title ${m.title.toString()}")
                    }
                    mainAdapter = MainRecyclerViewAdapter()
                    mainAdapter.differ.submitList(searchMovieData)
                    view.search_recycler_view.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.search_recycler_view.adapter = mainAdapter

                    mainAdapter.setOnItemClickListener {
                        val bundle = Bundle().apply {
                            putParcelable("movie", it)
                        }
                        findNavController().navigate(
                            R.id.action_mainListFragment_to_detailFragment, bundle
                        )
                    }
                } else {
                    try {
                        Log.v("TAG", "Error : ${response.errorBody().toString()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {

            }

        })


        searchfrag_search_view.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchResponseCall = movieApi.searchMovie(query = newText.toString(),page = 1)
                searchResponseCall.enqueue(object : Callback<MovieSearchResponse> {
                    override fun onResponse(
                        call: Call<MovieSearchResponse>,
                        response: Response<MovieSearchResponse>
                    ) {
                        if (response.code() == 200) {
                            Log.v("Tag", "the response: ${response.body().toString()}")

                            searchMovieData = response.body()!!.movieList as MutableList<MovieModel>
                            mainAdapter.differ.submitList(searchMovieData)

                        } else {
                            try {
                                Log.v("TAG", "Error : ${response.errorBody().toString()}")
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {

                    }

                })
                return true
            }

        })


    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toobar, menu)
//
//        val searchView = menu.findItem(R.id.toolbar_search).actionView as SearchView
//
//        searchView.queryHint = "검색어를 입력하세요"
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            var job: Job? = null
//            override fun onQueryTextChange(newText: String?): Boolean {
//                job?.cancel()
//                job = MainScope().launch {
//                    newText?.let {
//                        movieListViewModel.searchMovie(newText)
//                    }
//                }
//                return true
//            }
//
//        })
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.toolbar_search -> {
//
//                Toast.makeText(this.context, "search Click!", Toast.LENGTH_SHORT).show()
//            }
//            R.id.toolbar_mypage -> findNavController().navigate(R.id.action_mainListFragment_to_detailFragment)
//        }
//        return true
//    }
}