package com.example.dec10.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dec10.R
import com.example.dec10.models.MovieModel
import com.example.dec10.viewmodels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    //    val args:DetailFragmentArgs by navArgs()
    lateinit var movieViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireArguments().getParcelable<MovieModel>("movie")
        if (movie != null) {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(detail_poster)
            detail_description.text = movie.overview
            var range = IntRange(0,3)
            release_year.text = movie.release_date!!.slice(range)
        }
        movieViewModel = (activity as MainActivity).movieViewModel

        detail_save.setOnClickListener {
            if (movie != null) {
                Toast.makeText(context,"저장되었습니다.",Toast.LENGTH_SHORT).show()

                movieViewModel.saveMovie(movie)
            }
        }


    }

}