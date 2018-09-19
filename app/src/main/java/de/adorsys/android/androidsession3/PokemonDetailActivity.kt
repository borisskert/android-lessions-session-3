package de.adorsys.android.androidsession3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_detail.*

class PokemonDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val pokemonCard = intent.extras.getParcelable<PokemonCard>(MainActivity.pokemonCardExtra)

        Picasso.get().load(pokemonCard.imageUrlHiRes).into(pokemon_card_detail_image_view)
    }
}
