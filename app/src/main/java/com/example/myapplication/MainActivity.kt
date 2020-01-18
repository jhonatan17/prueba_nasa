package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import rx.Observable
import rx.Subscriber
import java.lang.reflect.Array
import java.util.*


class MainActivity : AppCompatActivity() {


    val imageId :ArrayList<String> = ArrayList()
    val title:ArrayList<String> = ArrayList()
    val n:ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      mi()


    }

    fun mi(){
      button.setOnClickListener(View.OnClickListener {
        //  Toast.makeText(this, ""+getUsers(),Toast.LENGTH_SHORT).show();
            //val intent=Intent(this,MapsActivity::class.java)
getUsers().subscribe({



var array= JSONArray(it.toString())
imageId.clear();
    title.clear();
    n.clear();
    for (i in 0 until array.length()) {
        JSONObject(array[i].toString()).get("data");

        try {
            var img=JSONObject(JSONArray(JSONObject(array[i].toString()).get("links").toString())[0].toString()).get("href").toString()
           img = img.replace( oldValue = " ",
                        newValue = "%20")
            imageId.add(img )
            title.add("sd");
            n.add("s");
        } catch (e: JSONException) {

        }

    }
    val myListAdapter = MyListAdapter(this,n,title,imageId)
    listView.adapter = myListAdapter
    listView.setOnItemClickListener(){adapterView, view, position, id ->
        val itemAtPos = adapterView.getItemAtPosition(position)
        val itemIdAtPos = adapterView.getItemIdAtPosition(position)
        Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
    }



})
       // startActivity(intent)
            })
    }

    fun  getUsers():Observable<Any> {



    return Observable.create(object : Observable.OnSubscribe<Any> {
            override fun call(subscriber: Subscriber<in Any>) {
                val my_url = "https://images-api.nasa.gov/search?q="+editText4.text
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
