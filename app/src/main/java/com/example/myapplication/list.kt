package com.example.myapplication

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MyListAdapter(private val context: Activity,private val imgid: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.list_view, imgid)  {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_view, null, true)

        val imageView = rowView.findViewById(R.id.icon) as ImageView

        Picasso.with(context).load(imgid[position]).fit().into(imageView)

        return rowView
    }

}
