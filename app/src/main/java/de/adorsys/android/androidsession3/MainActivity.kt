package de.adorsys.android.androidsession3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val restService = retrofit.create(PokemonCardRestService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result_pokemon_textView.movementMethod = ScrollingMovementMethod()

        loadPokemonCardsAndShowThem()
    }

    private fun loadPokemonCardsAndShowThem() {
        launch {
            val pokemonCardResponse = restService.getAll().execute()

            launch(UI) {
                showPokemonCards(pokemonCardResponse)
            }
        }
    }

    private fun showPokemonCards(pokemonCardResponse: Response<PokemonCardWrapper>) {
        if (pokemonCardResponse.isSuccessful) {
            val pokemonCards = filterAndSortPokemonCards(pokemonCardResponse)
            result_pokemon_textView.text = buildResultText(pokemonCards)
        } else {
            result_pokemon_textView.text = "Error while getting pokemon cards"
        }
    }

    private fun buildResultText(pokemonCards: List<PokemonCard>): String {
        var resultString = ""

        pokemonCards.forEach {
            resultString += " " + it.nationalPokedexNumber + " " + it.name + "\n"
        }

        return resultString
    }

    private fun filterAndSortPokemonCards(pokemonCardResponse: Response<PokemonCardWrapper>): List<PokemonCard> {
        val pokemonCards = pokemonCardResponse
                .body()?.cards
                .orEmpty()
                .filter { "Pokémon".equals(it.supertype) }
                .sortedBy { it.nationalPokedexNumber }
        return pokemonCards
    }
}
