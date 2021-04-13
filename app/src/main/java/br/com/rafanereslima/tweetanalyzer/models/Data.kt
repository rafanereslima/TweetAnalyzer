package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    var id : String,
    @SerializedName("text")
    var text : String
)