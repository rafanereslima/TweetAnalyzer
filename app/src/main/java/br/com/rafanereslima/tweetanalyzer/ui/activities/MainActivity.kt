package br.com.rafanereslima.tweetanalyzer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.rafanereslima.tweetanalyzer.R
import br.com.rafanereslima.tweetanalyzer.models.DataUserResponse
import br.com.rafanereslima.tweetanalyzer.network.Endpoints
import br.com.rafanereslima.tweetanalyzer.network.NetworkUtils
import br.com.rafanereslima.tweetanalyzer.utils.PermissionManager
import br.com.rafanereslima.tweetanalyzer.utils.PrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    lateinit var etUser: EditText
    lateinit var btAnalyze: Button

    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        //getUserId()
        //getData()
    }

    fun init(){

        etUser = findViewById(R.id.et_home_user)
        btAnalyze = findViewById(R.id.bt_analyze)


        etUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(!s.toString().startsWith("twitter.com/")){
                    if (s != null) {
                        s.insert(0, "twitter.com/")
                    };
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        btAnalyze.setOnClickListener {
            getUserId()
        }
    }

    fun getUserId() {
        val retrofitClient = NetworkUtils
                .getRetrofitInstance(PrefUtil.TWITTER_BASE_URL)

        val endpoint = retrofitClient.create(Endpoints::class.java)
        val callback = endpoint.getUserIdTwitter("users/by/username/" + etUser.text.toString().replace("twitter.com/","") , PrefUtil.TWITTER_TOKEN)

        callback.enqueue(object : Callback<DataUserResponse> {
            override fun onFailure(call: Call<DataUserResponse>, t: Throwable) {
                //Log.d("TAG USER ID", t.toString())
                //Toast.makeText(baseContext, "FALHOU", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DataUserResponse>?, response: Response<DataUserResponse>?) {
                response?.body()?.data?.id?.let { Log.d("TAG USER ID", it) }
                val intent = Intent(this@MainActivity, ListTweetsActivity::class.java)
                if (response?.body()?.data?.id == null){
                    showAlertError()
                } else {
                    intent.putExtra("userId", response?.body()?.data?.id)
                    intent.putExtra("userName", response?.body()?.data?.username)
                    startActivity(intent)
                }

            }
        })
    }

    fun showAlertError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tweet Analyzer")
        builder.setMessage("Usuário Twitter não encontrado, verifique e tente novamente \uD83D\uDE1E")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }

        builder.show()

    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode ->{
                val isPermissionsGranted = managePermissions
                        .processPermissionsResult(requestCode,permissions,grantResults)

                if(isPermissionsGranted){
                    // Do the task now
                    //toast("Permissions granted.")
                }else{
                    //toast("Permissions denied.")
                }
                return
            }
        }
    }

}