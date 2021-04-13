package br.com.rafanereslima.tweetanalyzer.network

import br.com.rafanereslima.tweetanalyzer.models.DataResponse
import br.com.rafanereslima.tweetanalyzer.models.DataUserResponse
import br.com.rafanereslima.tweetanalyzer.models.ResultResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @GET()
    fun getTweets(@Url url: String, @Header("Authorization") token:String) : Call<DataResponse>

    @GET()
    fun getUserIdTwitter(@Url url: String, @Header("Authorization") token:String) : Call<DataUserResponse>

    @POST()
    fun getSentiment(@Url url: String, @Body requestBody: RequestBody, @Header("Accept") header: String) : Call<ResultResponse>
}