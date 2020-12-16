package com.example.dec10

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_list_view_practice.*
import kotlinx.android.synthetic.main.listview_item.view.*

val userArray= arrayListOf<String>("김길동","이길동","박길동","홍길돋","황길동","진길동","유길동","최길동")

class ListViewPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_practice)

        myListview.adapter=myListViewAdapter(this, userArray)
    }
}


class myListViewAdapter(val context:Context, val userArray:ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return userArray.size
    }

    override fun getItem(p0: Int): Any {
        return userArray[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View {    //뷰를 재사용하기때문에 스위치가 사라짐. 사라지지않게하려면 그 데이터 객체에 설정값 저장해서 보여주도록 하면될듯.
        val view = LayoutInflater.from(context).inflate(R.layout.listview_item,container,false)
        view.list_item_name.text=userArray[position]
        return view
    }

}