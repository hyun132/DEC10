package com.example.dec10.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dec10.MainRecyclerViewAdapter
import com.example.dec10.MovieApplication
import com.example.dec10.R
import com.example.dec10.SavedMovieAdapter
import com.example.dec10.models.MovieModel
import com.example.dec10.viewmodels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {
    lateinit var movieViewModel: MovieListViewModel
    lateinit var movieAdapter: MainRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = (activity as MainActivity).movieViewModel

        movieAdapter = MainRecyclerViewAdapter()
        mypage_recycler_view.adapter = movieAdapter
        mypage_recycler_view.layoutManager = GridLayoutManager(activity, 3)

        movieViewModel.getSavedMovies().observe(viewLifecycleOwner, Observer { movie ->
            movieAdapter.differ.submitList(movieViewModel.getSavedMovies().value)
        })

        savedmovie_search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                movieAdapter.differ.submitList(movieViewModel.getSavedMovies().value?.filter {
                    it.title.toString().contains(p0.toString())
                })
                return true

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("TAG","$p0")
//                movieAdapter.differ.submitList(movieViewModel.getSavedMovies().value?.filter {
//                    it.title.toString().contains(p0.toString())
//                })
                movieViewModel.getSavedMovies().observe(viewLifecycleOwner, Observer { movie ->
                    movieAdapter.differ.submitList(movieViewModel.getSavedMovies().value?.filter {
                        it.title.toString().toLowerCase().contains(p0.toString().toLowerCase())
                    })
                })
                return true
            }

        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = movieAdapter.differ.currentList[position]
                movieViewModel.deleteSavedMovie(movie)
                Toast.makeText(activity, "저장목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(mypage_recycler_view)
        }

    }


}