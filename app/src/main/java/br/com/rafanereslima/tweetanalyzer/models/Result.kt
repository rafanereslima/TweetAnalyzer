package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("type")
    var type : String
)