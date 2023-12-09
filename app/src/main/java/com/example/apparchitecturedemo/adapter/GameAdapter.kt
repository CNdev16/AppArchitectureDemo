package com.example.apparchitecturedemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.apparchitecturedemo.R
import com.example.apparchitecturedemo.service.response.GameResponse
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class GameAdapter : RecyclerView.Adapter<GameAdapter.ItemViewHolder>() {
    var gameItems: List<GameResponse> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var callBack: ((GameResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
            .let(::ItemViewHolder)
    }

    override fun getItemCount(): Int {
        return gameItems.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setup(gameItems[position], callBack)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var item: GameResponse

        private var rootItem: ConstraintLayout
        private var thumbnail: ShapeableImageView
        private var title: MaterialTextView
        private var shortDescription: MaterialTextView
        private var genre: MaterialTextView

        private var callBack: ((GameResponse) -> Unit)? = null

        init {
            rootItem = itemView.findViewById(R.id.root_item)
            thumbnail = itemView.findViewById(R.id.image_view_thumbnail)
            title = itemView.findViewById(R.id.text_view_title)
            shortDescription = itemView.findViewById(R.id.text_view_short_description)
            genre = itemView.findViewById(R.id.text_view_genre)

            itemView.setOnClickListener {
                callBack?.invoke(item)
            }
        }

        fun setup(item: GameResponse, callBack: ((GameResponse) -> Unit)?) {
            this.item = item
            this.callBack = callBack

            thumbnail.load(item.thumbnail) {
                crossfade(true)
            }

            title.text = item.title
            shortDescription.text = item.shortDescription
            genre.text = item.genre
        }
    }
}
