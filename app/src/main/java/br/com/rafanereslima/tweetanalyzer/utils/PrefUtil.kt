package br.com.rafanereslima.tweetanalyzer.utils

import android.app.Application

public class PrefUtil : Application() {
    companion object {
        @JvmField
        var TWITTER_TOKEN: String = "Bearer AAAAAAAAAAAAAAAAAAAAAPPuOQEAAAAAAV8GuN23m0WZ5P6SQzfmcqAQwPo%3DYSe9Mw0Y0lzm3Bx465a8d4imAU8pFXxqMVOelIH3cjOXumKFcA"
        var TWITTER_BASE_URL: String = "https://api.twitter.com/2/"
        var SENTIM_API_BASE_URL: String = "https://sentim-api.herokuapp.com/"
        var SENTIM_API_HEADER: String = "\"application/json\", \"Content-Type\": \"application/json\""
    }
}