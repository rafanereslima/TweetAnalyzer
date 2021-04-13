package br.com.rafanereslima.tweetanalyzer.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.rafanereslima.tweetanalyzer.ListViewTweetsAdapter
import br.com.rafanereslima.tweetanalyzer.R
import br.com.rafanereslima.tweetanalyzer.models.Data
import br.com.rafanereslima.tweetanalyzer.models.DataResponse
import br.com.rafanereslima.tweetanalyzer.models.ResultResponse
import br.com.rafanereslima.tweetanalyzer.network.Endpoints
import br.com.rafanereslima.tweetanalyzer.network.NetworkUtils
import br.com.rafanereslima.tweetanalyzer.utils.PrefUtil
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListTweetsActivity : AppCompatActivity() {

    lateinit var data: ArrayList<Data>
    lateinit var userId: String
    lateinit var userName: String
    lateinit var listView: ListView
    lateinit var tvUserName: TextView
    lateinit var tvNoTweets: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        init()
        getData()

    }

    fun init(){
        userId = intent.getStringExtra("userId")!!
        userName = intent.getStringExtra("userName")!!

        listView = findViewById(R.id.recipe_list_view)
        tvUserName = findViewById(R.id.tv_home_tittle_user)
        tvNoTweets = findViewById(R.id.tv_home_no_tweets)

        tvUserName.setText("twitter.com/$userName")
    }

    fun getData() {
        val retrofitClient = NetworkUtils
                .getRetrofitInstance(PrefUtil.TWITTER_BASE_URL)

        val endpoint = retrofitClient.create(Endpoints::class.java)
        val callback = endpoint.getTweets(
            "users/$userId/tweets", PrefUtil.TWITTER_TOKEN
        )

        callback.enqueue(object : Callback<DataResponse> {
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                //Log.d("TAG", t.toString())
                //Toast.makeText(baseContext, "FALHOU", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DataResponse>?, response: Response<DataResponse>?) {
                Log.d("TAG TWEETS", response?.body().toString())

                if (response?.body()?.data == null){
                    tvNoTweets.visibility = View.VISIBLE
                    showAlertError()
                } else {
                    data = response?.body()?.data!!
                    showList()
                }
            }
        })
    }

    fun showAlertError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tweet Analyzer")
        builder.setMessage("Usuário não possui nenhum tweet publicado \uD83D\uDE1E")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }

        builder.show()
    }

    fun showList(){
        var listViewAdapter = ListViewTweetsAdapter(this, data)
        listView.adapter = listViewAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->

            val retrofitClient = NetworkUtils
                .getRetrofitInstance(PrefUtil.SENTIM_API_BASE_URL)

            val endpoint = retrofitClient.create(Endpoints::class.java)
            val callback = endpoint.getSentiment(
                "api/v1/",(createJsonRequestBody(
                    "text" to (data[position]).toString())), PrefUtil.SENTIM_API_HEADER
            )

            callback.enqueue(object : Callback<ResultResponse> {
                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    //Log.d("TAG TWEETS SENTIMENT", t.toString())
                    //Toast.makeText(baseContext, "FALHOU", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ResultResponse>?, response: Response<ResultResponse>?) {
                    //Log.d("TAG SENTIMENT", response?.body().toString())
                    //Log.d("TAG SENTIMENT", response?.body()?.result!!.type)

                    alertFeeling(response?.body()?.result!!.type)
                }
            })

        }
    }

    fun alertFeeling(felling: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tweet Analyzer")
        if (felling == "positive"){
            builder.setMessage("Tweet feliz \uD83D\uDE00")
        } else if (felling == "neutral"){
            builder.setMessage("Tweet neutro \uD83D\uDE10")
        } else if (felling == "negative"){
            builder.setMessage("Tweet triste \uD83D\uDE14")
        }

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }

        builder.show()
    }

    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(mapOf(*params)).toString())

}

