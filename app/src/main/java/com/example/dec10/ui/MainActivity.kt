package com.example.dec10.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dec10.R
import com.example.dec10.SavedMovieAdapter
import com.example.dec10.viewmodels.MovieListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var movieViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        movieViewModel = MovieListViewModel(application)
        movieViewModel.getSavedMovies()

        home_bottom_nav.setupWithNavController(movieNavHostFragment.findNavController())
//        home_bottom_nav.setOnNavigationItemSelectedListener(homeBottomNavListener)
//        replaceFragment(MainListFragment()) // 처음 앱 켜졌을때 보여줄 화면.

    }

    val homeBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.mainListFragment -> {
                replaceFragment(MainListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.mypageFragment -> {
                replaceFragment(MypageFragment())
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_frame_layout, fragment)
        fragmentTransaction.commit()    // 커밋 꼭 해줘야함.
    }
}
