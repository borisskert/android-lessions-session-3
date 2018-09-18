package de.adorsys.android.androidsession3

import retrofit2.Call
import retrofit2.http.GET

interface PokemonCardRestService {

    @GET("v1/cards")
    fun getAll(): Call<PokemonCardWrapper>
}