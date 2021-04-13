package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("data")
    var data : ArrayList<Data>
)