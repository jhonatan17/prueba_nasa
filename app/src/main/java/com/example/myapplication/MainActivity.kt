package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rx.Observable
import rx.Subscriber
import java.util.*
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "mindorks-welcome"

    lateinit var  sharedPref: SharedPreferences ;
    lateinit var  editor:SharedPreferences.Editor
    val historial :ArrayList<String> = ArrayList()
    val imageId :ArrayList<String> = ArrayList()
    val data:ArrayList<JSONObject> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPref.edit()
      mi()
       // Toast.makeText(this,sharedPref.getString("historial", ""),Toast.LENGTH_LONG).show()
       if(sharedPref.getString("historial", null)!=null){
        var h= JSONArray(sharedPref.getString("historial", ""))
        for(i in 0 until h.length()){
            historial.add(h[i].toString())
       }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, historial)
        input.setAdapter(adapter)}
        close.setOnClickListener(View.OnClickListener {
            contentDes.visibility=View.GONE
        })
    }

    fun mi() {
        button.setOnClickListener(View.OnClickListener {
            if(!input.text.toString().equals("")){
            if (!historial.contains(input.text.toString())) {
                historial.add(input.text.toString())
                editor.putString("historial", historial.toString())
                editor.apply()
                val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, historial)
                input.setAdapter(adapter)
            }
            //  Toast.makeText(this, ""+getUsers(),Toast.LENGTH_SHORT).show();
            //val intent=Intent(this,MapsActivity::class.java)
            getUsers().subscribe({


                input.setText("")
                var array = JSONArray(it.toString())
                imageId.clear();
                data.clear()
                for (i in 0 until array.length()) {
                    JSONObject(array[i].toString()).get("data");

                    try {
                        var img =
                            JSONObject(JSONArray(JSONObject(array[i].toString()).get("links").toString())[0].toString()).get(
                                "href"
                            ).toString()
                        var datap =
                            JSONObject(JSONArray(JSONObject(array[i].toString()).get("data").toString())[0].toString());

                        img = img.replace(
                            oldValue = " ",
                            newValue = "%20"
                        )
                        imageId.add(img)
                        data.add(datap)
                    } catch (e: JSONException) {

                    }

                }
                val myListAdapter = MyListAdapter(this, imageId)
                grid.adapter = myListAdapter
                grid.setOnItemClickListener() { adapterView, view, position, id ->
                    web.loadUrl(imageId[position])
                    val itemAtPos = data[position]
                    contentDes.visibility = View.VISIBLE
                    titleimg.setText(itemAtPos.get("title").toString())
                    descripcion.setText(itemAtPos.get("description").toString())
                    fecha.setText(itemAtPos.get("date_created").toString())
                 //   Toast.makeText(this, "ds", Toast.LENGTH_LONG).show()

                }


            })}else
                Toast.makeText(this,"El campo de busqueda no puede estar vacio",Toast.LENGTH_SHORT).show()
            // startActivity(intent)
        })

    }

    fun  getUsers():Observable<Any> {



    return Observable.create(object : Observable.OnSubscribe<Any> {
            override fun call(subscriber: Subscriber<in Any>) {
                val my_url = "https://images-api.nasa.gov/search?q="+input.text
                var respuesta="";

                val requ = object : JsonObjectRequest(Method.GET, my_url, null,
                    Response.Listener {

                        try {

                            var strResp = it.toString();
                            respuesta=it.toString()
                            val jsonArray  = it.get("collection");


                            subscriber.onNext( JSONObject(jsonArray.toString()).get("items"))
                            subscriber.onCompleted()
                        }catch (e:JSONException){

                        }


                    },
                    Response.ErrorListener {



                    }
                ) {


                }
//Toast.makeText(this,respuesta,Toast.LENGTH_SHORT).show()
MySingleton.getInstance(applicationContext).addToRequestQueue(requ)
//Instantiate the RequestQueue.
 //val queue = Volley.newRequestQueue(applicationContext)

        }})




}


}
