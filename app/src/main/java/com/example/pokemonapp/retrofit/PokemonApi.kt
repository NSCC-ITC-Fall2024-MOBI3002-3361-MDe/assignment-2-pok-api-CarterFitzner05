package com.example.pokemonapp.retrofit

import com.example.pokemonapp.models.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("api/v2/pokemon/{name}") // This should match the PokeAPI endpoint
    suspend fun getPokemon(@Path("name") name: String): Response<Pokemon>
}
