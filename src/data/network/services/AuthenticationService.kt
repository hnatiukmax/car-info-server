package com.sectumsempra.data.network.services

import com.sectumsempra.data.network.entity.authentication.FacebookAuthInfo
import com.sectumsempra.data.network.entity.authentication.GoogleAuthInfo
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AuthenticationService {

    @GET("https://graph.facebook.com/me?fields=id,name,email,picture")
    suspend fun getFacebookUserAuthInfo(@Query("access_token") token: String) : FacebookAuthInfo

    @GET("https://www.googleapis.com/oauth2/v3/tokeninfo")
    suspend fun getGoogleUserAuthInfo(@Query("id_token") token: String) : GoogleAuthInfo
}