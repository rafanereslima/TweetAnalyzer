package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class DataUserResponse(
        @SerializedName("data")
        var data : DataUser
)