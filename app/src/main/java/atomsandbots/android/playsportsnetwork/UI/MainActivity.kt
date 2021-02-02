package atomsandbots.android.playsportsnetwork.UI

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import atomsandbots.android.playsportsnetwork.Adapter.Adapter
import atomsandbots.android.playsportsnetwork.Data.Model
import atomsandbots.android.playsportsnetwork.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }


    private val modelList = ArrayList<Model>()
    var adapter: Adapter? = null

    //Complete GMBN URL
    private val url: String = "${Constants.BASE_URL}${Constants.CHANNEL_ID}&maxResults=30&key=${Constants.API_KEY}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.main_recyclerView)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        isNetworkAvailable(this)

        //Attach Layout manager to recyclerview and load adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = Adapter(modelList)
        recyclerView.adapter = adapter
        parseJson()

    }

    private fun parseJson() {
        val requestQueue = Volley.newRequestQueue(this)
        // Initialize a new JsonObjectRequest instance
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    // Do something with response
                    try {
                        // Get the JSON array
                        val array = response!!.getJSONArray("items")

                        // Loop through array elements
                        for (i in 0 until array.length()) {
                            // Get current json object
                            val jsonObj: JSONObject = array.getJSONObject(i)
                            val jsonObj1: JSONObject = jsonObj.getJSONObject("id")
                            // Video ID
                            val videoID: String = jsonObj1.get("videoId").toString()
                            // Title, date and Description
                            val jsonObj2: JSONObject = jsonObj.getJSONObject("snippet")
                            val videoTitle: String = jsonObj2.get("title").toString()
                            val videoDescription: String = jsonObj2.get("description").toString()
                            //Thumbnail
                            val jsonObj3: JSONObject = jsonObj2.getJSONObject("thumbnails")
                            val jsonObj4: JSONObject = jsonObj3.getJSONObject("high")
                            val videoThumbnail: String = jsonObj4.get("url").toString()

                            modelList.add(
                                    Model(
                                            videoID, videoTitle, videoDescription, videoThumbnail
                                    )
                            )

                        }

                        adapter?.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
        ) { error ->
            // Log error to string
            Log.e("JSON ERROR", error.toString())
        }
        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest)

    }


    // A reference to the ConnectivityManager to check state of network connectivity (Mobile and wifi).
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}