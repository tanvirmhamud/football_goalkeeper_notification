package com.example.backgroundservice.Api_Interface.LiveMatch

import com.example.backgroundservice.Model.Live.Livematch
import com.example.footballnotification.Model.playerinfo.singleplayerinfo
import com.example.footballnotification.SingleMatch.SingleMatchDetails
import retrofit2.Response
import retrofit2.http.*

interface Livematchinterface {

//    @GET("fixtures/live")
//    suspend fun getQuotes(@Header("ab") token: String) : Response<Livematch>

    @GET("fixtures/live")
    suspend fun getQuotes(@Header("ab") token: String) : Response<Livematch>

    @GET("players/squads/player={id}")
    suspend fun getplayerinfo(@Path("id") id: String ,@Header("ab") token: String) : Response<singleplayerinfo>


}