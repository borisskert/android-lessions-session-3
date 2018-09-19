package de.adorsys.android.androidsession3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PokemonListViewAdapter(
        context: Context,
        private val pokemonCards: List<PokemonCard>
): ArrayAdapter<PokemonCard>(context, R.layout.item_pokemon_card) {

    override fun getCount(): Int {
        return this.pokemonCards.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertedView: View? = convertView
        val pokemonCard: PokemonCard = pokemonCards[position]

        if (convertedView == null) {
            convertedView = LayoutInflater.from(context).inflate(R.layout.item_pokemon_card, parent, false)
        }

        setLoadedTextIntoView(convertedView, pokemonCard)
        setLoadedImageIntoView(convertedView, pokemonCard)

        return convertedView!!
    }

    private fun setLoadedTextIntoView(convertedView: View?, pokemonCard: PokemonCard) {
        val pokemonCardItemTextView = convertedView!!.findViewById<TextView>(R.id.pokemon_card_text_view)
        pokemonCardItemTextView.text = pokemonCard.name
    }

    private fun setLoadedImageIntoView(convertedView: View?, pokemonCard: PokemonCard) {
        val pokemonCardItemImageView = convertedView!!.findViewById<ImageView>(R.id.pokemon_card_icon_image_view)

        Picasso.get()
                .load(pokemonCard.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(pokemonCardItemImageView)
    }
}
