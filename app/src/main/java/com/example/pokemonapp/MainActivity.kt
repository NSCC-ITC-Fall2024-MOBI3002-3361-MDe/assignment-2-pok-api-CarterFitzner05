package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.viewmodel.PokemonViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonScreen(viewModel) // Pass the ViewModel to the screen
        }
    }
}

@Composable
fun PokemonScreen(viewModel: PokemonViewModel) {
    var pokemonName by remember { mutableStateOf("") }
    var pokemonData by remember { mutableStateOf<Pokemon?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Collect data from the ViewModel's state flow
    LaunchedEffect(Unit) {
        viewModel.pokemonData.observeForever { data ->
            pokemonData = data
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the Pokémon logo
        Image(
            painter = painterResource(R.drawable.pokemon_logo),
            contentDescription = "Pokemon Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )

        // Text field for Pokémon name input
        OutlinedTextField(
            value = pokemonName,
            onValueChange = { pokemonName = it },
            label = { Text("Enter Pokémon name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to fetch Pokémon data
        Button(
            onClick = {
                if (pokemonName.isNotBlank()) {
                    viewModel.fetchPokemon(pokemonName.lowercase())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display error message if available
        if (errorMessage.isNotBlank()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        // Display Pokémon details and image
        pokemonData?.let { pokemon ->
            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(205.dp)
            )

            Text(text = pokemon.name)
            Text(text = "Types: ${pokemon.types.joinToString { it.type.name }}")
            Text(text = "Height: ${pokemon.height}ft ")
            Text(text = "Weight: ${pokemon.weight}lbs")

        } ?: Text(text = "No Pokémon data available")
    }
}
