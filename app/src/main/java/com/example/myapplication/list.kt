package com.example.myapplication

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso

class MyListAdapter(private val context: Activity, private val title: ArrayList<String>, private val description: ArrayList<String>, private val imgid: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.list_view,imgid) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_view, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView
        Picasso.with(context).load(imgid[position]).into(imageView)
        titleText.text = title[position]
        subtitleText.text = description[position]

        return rowView
    }
}