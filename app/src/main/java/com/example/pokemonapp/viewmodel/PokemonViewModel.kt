package com.example.pokemonapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    val pokemonData = MutableLiveData<Pokemon?>()
    val errorMessage = MutableLiveData<String?>()

    fun fetchPokemon(name: String) {
        Log.d("PokemonViewModel", "Fetching Pokémon data for: $name")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getPokemon(name)
                if (response.isSuccessful) {
                    response.body()?.let { pokemon ->
                        Log.d("PokemonViewModel", "Fetched Pokémon: ${pokemon.name}, Height: ${pokemon.height}, Weight: ${pokemon.weight}")
                        pokemonData.postValue(pokemon)
                        errorMessage.postValue(null)
                    }
                } else {
                    Log.e("PokemonViewModel", "Error fetching Pokémon: ${response.errorBody()?.string()}")
                    errorMessage.postValue("Pokémon not found")
                    pokemonData.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("PokemonViewModel", "API Error: ${e.message}")
                errorMessage.postValue("API Error: ${e.message}")
                pokemonData.postValue(null)
            }
        }
    }
}
