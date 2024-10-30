package com.example.pokemonapp.models

data class Pokemon(
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val sprites: Sprites
)

data class Sprites(
    val front_default: String?
)

data class Type(
    val type: TypeDetail
)

data class TypeDetail(
    val name: String
)


