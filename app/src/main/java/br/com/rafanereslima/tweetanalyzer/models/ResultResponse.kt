package br.com.rafanereslima.tweetanalyzer.models

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("result")
    var result : Result
)