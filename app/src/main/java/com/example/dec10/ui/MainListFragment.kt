package com.example.dec10.ui

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainListFragment : Fragment() {

    lateinit var popylarMovieData: List<MovieModel>
    lateinit var upcomingMovieData: List<MovieModel>

    //    lateinit var amovie:MovieModel
    lateinit var mainAdapter: MainRecyclerViewAdapter
    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)
//        ActionBar.LayoutParams(R.id.toolbar)
        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toobar, menu)
//
////        val searchView = menu.findItem(R.id.toolbar_search).actionView as SearchView
////
////        searchView.queryHint="검색어를 입력하세요"
////        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
////            override fun onQueryTextSubmit(query: String?): Boolean {
////                return true
////            }
////
//////            var job:Job?=null
////            override fun onQueryTextChange(newText: String?): Boolean {
//////                job?.cancel()
//////                job= MainScope().launch {
//////                    newText?.let {
//////                        if(newText.toString().isNotEmpty()){
//////                            val searchMovie = movieListViewModel.searchMovie(newText.toString())
//////                        }
//////                    }
//////                }
////                return true
////            }
////
////        })
////
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.toolbar_search -> findNavController().navigate(R.id.action_mainListFragment_to_searchFragment)
//            R.id.toolbar_mypage -> findNavController().navigate(R.id.action_mainListFragment_to_detailFragment)
//        }
//        return true
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieApi = RetrofitInstance.getMovieApi()

        val popularResponseCall: Call<MovieSearchResponse> = movieApi.getPopularMovie()
        val upcomingResponseCall: Call<MovieSearchResponse> = movieApi.getUpcomingMovie()

//        view.search_toolbar.setOnMenuItemClickListener {
//            findNavController().navigate(R.id.action_mainListFragment_to_searchFragment)
//            return false
//        }

        toolbar_search.setOnClickListener {
            findNavController().navigate(R.id.action_mainListFragment_to_searchFragment)
        }
        toolbar_mypage.setOnClickListener {
            findNavController().navigate(R.id.action_mainListFragment_to_mypageFragment)
        }


        popularResponseCall.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.code() == 200) {
                    Log.v("Tag", "the response: ${response.body().toString()}")

                    popylarMovieData = response.body()!!.movieList as MutableList<MovieModel>

                    for (m in popylarMovieData) {
                        Log.v("Tag", "The title ${m.title.toString()}")
                    }
                    mainAdapter = MainRecyclerViewAdapter()
                    mainAdapter.differ.submitList(popylarMovieData)
                    view.main_popular_recycler_view.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.main_popular_recycler_view.adapter = mainAdapter

                    mainAdapter.setOnItemClickListener {
                        val bundle = Bundle().apply {
                            putParcelable("movie", it)
                        }
                        findNavController().navigate(
                            R.id.action_mainListFragment_to_detailFragment, bundle
                        )
                    }
                    Glide.with(activity!!)
                        .load("https://image.tmdb.org/t/p/w500${popylarMovieData[0].poster_path}")
                        .into(mainBackground_imageview)
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

        registerForContextMenu(main_popular_recycler_view)
        registerForContextMenu(main_upcoming_recycler_view)

//        main_popular_recycler_view.setOnCreateContextMenuListener(object :
//            View.OnCreateContextMenuListener {
//            override fun onCreateContextMenu(
//                menu: ContextMenu?,
//                view: View?,
//                menuInfo: ContextMenu.ContextMenuInfo?
//            ) {
//                view!!.setOnLongClickListener(object :View.OnLongClickListener{
//                    override fun onLongClick(p0: View?): Boolean {
//                        Toast.makeText(p0!!.context, "${popylarMovieData[p0.verticalScrollbarPosition].title}", Toast.LENGTH_SHORT)
//                            .show()
//                        return true
//                    }
//
//                })
//                menu!!.add("save")
//                    .setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
//                        override fun onMenuItemClick(p0: MenuItem?): Boolean {
//                            Toast.makeText(view!!.context, "저장 버튼 클릭", Toast.LENGTH_SHORT)
//                                .show()
//                            return true
//                        }
//
//                    })
//            }
//
//        })

        upcomingResponseCall.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.code() == 200) {
                    Log.v("Tag", "the response: ${response.body().toString()}")

                    upcomingMovieData = response.body()!!.movieList as MutableList<MovieModel>

                    for (m in upcomingMovieData) {
                        Log.v("Tag", "The title ${m.title.toString()}")
                    }
                    mainAdapter = MainRecyclerViewAdapter()
                    mainAdapter.differ.submitList(upcomingMovieData)
                    view.main_upcoming_recycler_view.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    view.main_upcoming_recycler_view.adapter = mainAdapter


                    mainAdapter.setOnItemClickListener {
                        val bundle = Bundle().apply {
                            putParcelable("movie", it)
                        }
                        findNavController().navigate(
                            R.id.action_mainListFragment_to_detailFragment, bundle
                        )
                    }


                    Glide.with(activity!!)
                        .load("https://image.tmdb.org/t/p/w500${upcomingMovieData[0].poster_path}")
                        .into(mainBackground_imageview)
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


    }

//    override fun onCreateContextMenu(
//        menu: ContextMenu,
//        v: View,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        var inflater =this.menuInflater()
//        when(v.id){
//            R.id.main_popular_recycler_view->{
//                menu.setHeaderTitle("저장하시겠습니까?")
//                inf
//            }
//        }
//    }

//    override fun onContextItemSelected(item: MenuItem): Boolean {
//
//        when(item?.itemId){
//            R.id.main_context_save -> {
//                val position = view?.verticalScrollbarPosition
//                movieListViewModel.saveMovie(popylarMovieData[position!!])
//            }
//        }
//
//        return super.onContextItemSelected(item)
//    }


}


//    private fun setUpRecyclerView(){
//        mainAdapter=MainRecyclerViewAdapter()
//        recyclerView.apply {
//            adapter=mainAdapter
//            layoutManager=LinearLayoutManager(activity)
//        }
//        recyclerView=view.findViewById(R.id.main_recycler_view)
//        recyclerView.layoutManager=LinearLayoutManager(activity)
//        mainAdapter= MainRecyclerViewAdapter(movieData)
//        recyclerView.adapter=mainAdapter
//    }

//    private fun getRetrofitResponse(){
//        val movieApi = RetrofitInstance.getMovieApi()
//
//        val responseCall: Call<MovieSearchResponse> = movieApi.getPopularMovie()
//
//        responseCall.enqueue(object :Callback<MovieSearchResponse>{
//            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
//                if (response.code()==200){
//                    Log.v("Tag","the response: ${response.body().toString()}")
//
//                    movieData = response.body()!!.movieList as MutableList<MovieModel>
//
//                    for (m in  movieData){
//                        Log.v("Tag","The title ${m.title.toString()}")
//                    }
//                    mainAdapter=MainRecyclerViewAdapter(movieData)
//                    main_recycler_view.apply {
//                        adapter=mainAdapter
//                        layoutManager=LinearLayoutManager(activity)
//                    }
//
//                }else{
//                    try {
//                        Log.v("TAG","Error : ${response.errorBody().toString()}")
//                    }catch (e:IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
//
//            }
//
//        })
//    }

//    private fun getRetrofitResponseAccordingToId() { //리싸이클러뷰에서 아이템 클릭했을 때 상세페이지 이동
//        val aMovieApi = getRetrofitInstance()
//        val aResponseCall: Call<AMovieResponse> = aMovieApi.getMovie(movie_id = 550)
//
//        aResponseCall.enqueue(object : Callback<AMovieResponse> {
//            override fun onResponse(
//                call: Call<AMovieResponse>,
//                response: Response<AMovieResponse>
//            ) {
//                if (response.code() == 200) {
//                    Log.v("Tag", "TheResponse : ${response}")
//                    amovie = response.body()!!.movie
//                    Log.v("Tag", "TheResponse : ${amovie.title}")
//                } else {
//                    try {
//                        Log.v("TAG", "ERROF : ${response.errorBody()}")
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<AMovieResponse>, t: Throwable) {
//
//            }
//
//        })
//    }

