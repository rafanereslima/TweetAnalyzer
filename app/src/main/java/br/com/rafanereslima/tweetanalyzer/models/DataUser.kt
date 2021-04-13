package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class DataUser(
        @SerializedName("id")
        var id : String,
        @SerializedName("name")
        var name : String,
        @SerializedName("username")
        var username : String
)