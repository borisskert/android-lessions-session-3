package de.adorsys.android.androidsession3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity() : AppCompatActivity() {

    private val pokemonCards: ArrayList<PokemonCard> = ArrayList<PokemonCard>()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    private val restService = retrofit.create(PokemonCardRestService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadPokemonCardsAndShowThem()
    }

    private fun loadPokemonCardsAndShowThem() {
        pokemon_cards_loading_progress_bar.visibility = View.VISIBLE

        GlobalScope.launch {
            pokemonCards.addAll(loadPokemonCards())

            GlobalScope.launch(Dispatchers.Main) {
                pokemon_cards_listView.adapter = PokemonListViewAdapter(this@MainActivity, pokemonCards)
                pokemon_cards_loading_progress_bar.visibility = View.GONE
            }
        }

        pokemon_cards_listView.setOnItemClickListener { parent, view, position, id ->
            val pokemonCard: PokemonCard = pokemonCards[position]

            val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
            intent.putExtra(pokemonCardExtra, pokemonCard)

            startActivity(intent)
        }
    }

    private fun loadPokemonCards(): List<PokemonCard> {
        val pokemonCardResponse = restService.getAll().execute()
        val pokemonCards: List<PokemonCard>

        if (pokemonCardResponse.isSuccessful) {
            pokemonCards = filterAndSortPokemonCards(pokemonCardResponse)
        } else {
            pokemonCards = ArrayList<PokemonCard>()
            Toast.makeText(this@MainActivity, "Error while loading pokemon cards", Toast.LENGTH_SHORT).show()
        }

        return pokemonCards
    }

    private fun filterAndSortPokemonCards(pokemonCardResponse: Response<PokemonCardWrapper>): List<PokemonCard> {
        return pokemonCardResponse
                .body()?.cards
                .orEmpty()
                .filter { pokemonSuperType.equals(it.supertype) }
                .sortedBy { it.nationalPokedexNumber }
    }

    companion object {
        const val pokemonSuperType = "Pokémon"
        const val pokemonCardExtra = "pokemon card extra intent extra key"
    }
}
