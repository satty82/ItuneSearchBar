package satyam.andyprojects.itunesearchbar

import android.os.AsyncTask
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject



public open class MainActivity : AppCompatActivity() {

    var searchItem : SpannableStringBuilder? = null
    var SearchBar: SearchView ? = null
    var gridView : GridView? = null
    var adapter : ArrayAdapter<String>? = null
    var songs : ArrayList<String> = ArrayList()
    var str1 : String? =null
    var str2 : String? =null
    var str3 : String? =null
    var str4 : String? =null
   // var progressBar : ProgressBar ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // progressBar  = findViewById(R.id.progressBar)
        gridView = findViewById(R.id.gridView) as GridView
        SearchBar = findViewById(R.id.searchBar)
        SearchBar!!.setIconifiedByDefault(false)
        searchItem = SearchBar!!.query as SpannableStringBuilder
        adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, songs)
        //main.gridView?.setAdapter(main.adapter)
        gridView?.adapter = adapter


        SearchBar!!.setOnQueryTextListener(object : SearchView.
            OnQueryTextListener
            {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    songs.clear()
                    // enable
//                    progressBar?.setVisibility(ProgressBar.VISIBLE);
//                    progressBar?.setProgress(0);
                    getData(query.toString());

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    return true
                }

        })

    }
    fun getData(query: String){
        val url = "https://itunes.apple.com/search?term=" + query


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("Response",response.toString() )
                var jsonObject = JSONObject(response.toString())


                var Info2 = jsonObject.getString("results")

                var array = JSONArray(Info2)

                for (i in 0..(array.length() - 1))
                {

                    var json_objectdetail: JSONObject = array.getJSONObject(i)

                    if (json_objectdetail.has("artistName")){
                        str1 = json_objectdetail.getString("artistName")

                    }else{
                        str1 = " "
                    }

                    if (json_objectdetail.has("collectionName"))
                    {
                        str2 = json_objectdetail.getString("collectionName")

                    }else{
                        str2 = " "
                    }

                    if (json_objectdetail.has("collectionCensoredName"))
                    {
                        str3 = json_objectdetail.getString("collectionCensoredName")

                    }else{
                        str3 = " "
                    }

                    if (json_objectdetail.has("collectionCensoredName"))
                    {
                        str3 = json_objectdetail.getString("collectionCensoredName")

                    }else{
                        str3 = " "
                    }

                    if (json_objectdetail.has("trackCensoredName"))
                    {
                        str4 = json_objectdetail.getString("trackCensoredName")

                    }else{
                        str4 = " "
                    }

//                    Log.i("parsed" , str2 + str3)
//                    //  main.addDetails(str1,str2,str3,str4)

                    songs.add(str1!!)
                    songs.add(str2!!)
                    songs.add(str3!!)
                    songs.add(str4!!)

                }
                //disable
//                progressBar?.setVisibility(ProgressBar.GONE);



            },
            Response.ErrorListener { error ->
                // TODO: Handle error
          }
    )

// Access the RequestQueue through your singleton class.
        VolleySingleton.instance?.addToRequestQueue(jsonObjectRequest)
    }

}

