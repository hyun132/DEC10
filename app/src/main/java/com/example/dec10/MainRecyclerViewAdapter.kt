package com.example.dec10

import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dec10.db.MovieDatabase
import com.example.dec10.models.MovieModel
import com.example.dec10.ui.MainActivity
import com.example.dec10.utils.Constants.Companion.BASE_URL
import kotlinx.android.synthetic.main.movie_item.view.*

class MainRecyclerViewAdapter(): RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    private var savedMovieList= emptyList<MovieModel>()

    private val differCallback = object : DiffUtil.ItemCallback<MovieModel>() {
        //둘이 같은 객체인지
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.movie_id == newItem.movie_id
        }

        //둘이 같은 아이템인지
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

//        private val title = itemView.title
        private val poster = itemView.poster_image_iew
//        private val release_date = itemView.release_date

        fun bind(item:MovieModel){
//            title.text=item.title
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${item.poster_path}").into(poster)
//            release_date.text=item.release_date
        }

    }

//    internal fun saveMovie(movieList:List<MovieModel>){
//        this.savedMovieList = movieList
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return MainViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(movie) }
        }
        holder.itemView.setOnCreateContextMenuListener(object :View.OnCreateContextMenuListener{
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                p1: View?,
                p2: ContextMenu.ContextMenuInfo?
            ) {
                menu!!.add("getInfo").setOnMenuItemClickListener(object :MenuItem.OnMenuItemClickListener{
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
//                        (holder.itemView.context.applicationContext as MainActivity).movieViewModel.saveMovie(movie)
                        Toast.makeText(holder.itemView.context,"${movie.title}",Toast.LENGTH_SHORT).show()
                        return true
                    }

                })
            }

        })
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((MovieModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieModel) -> Unit) {
        onItemClickListener = listener
    }

}