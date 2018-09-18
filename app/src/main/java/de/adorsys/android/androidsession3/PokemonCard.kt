package de.adorsys.android.androidsession3

data class PokemonCard(
        val id: String,
        val imageUrl: String?,
        val imageUrlHiRes: String?,
        val name: String?,
        val nationalPokedexNumber: Int?,
        val types: List<String>,
        val superType: String?,
        val number: Int?,
        val rarity: String?,
        val subtype: String?
) {
}
