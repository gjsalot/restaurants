package com.grantsutcliffe.restaurants.search.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grantsutcliffe.restaurants.search.R
import com.grantsutcliffe.restaurants.search.model.RestaurantUI
import com.grantsutcliffe.restaurants.search.presenter.SearchUiEvent
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlin.properties.Delegates

class RestaurantAdapter(
    private val uiEvents: PublishSubject<SearchUiEvent>,
    resources: Resources
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val imageCornerRadius = resources.getDimensionPixelOffset(R.dimen.image_corner_radius)
    private val cardviewMargin = resources.getDimensionPixelOffset(R.dimen.cardview_margin)

    var items: List<RestaurantUI> by Delegates.observable(emptyList()) { _, oldItems, newItems ->
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldItems.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition].id == newItems[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldItems[oldItemPosition] == newItems[newItemPosition]
        })

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder =
        RestaurantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_restaurant_item, parent, false))

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        private val iconImageView = itemView.findViewById<ImageView>(R.id.iconImageView)
        private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        private val subtitleTextView = itemView.findViewById<TextView>(R.id.subtitleTextView)
        private val categoriesTextView = itemView.findViewById<TextView>(R.id.categoriesTextView)
        private val callButton = itemView.findViewById<Button>(R.id.callButton)

        fun bind(restaurantUI: RestaurantUI) = restaurantUI.run {
            Picasso.get()
                .load(imageUrl)
                .transform(RoundedCornersTransformation(imageCornerRadius, 0))
                .into(imageView)
            Picasso.get()
                .load(iconUrl)
                .into(iconImageView)
            titleTextView.text = title
            subtitleTextView.text = subtitle
            categoriesTextView.text = categories

            callButton.setOnClickListener { uiEvents.onNext(SearchUiEvent.CallClicked(this)) }

            itemView.layoutParams = (itemView.layoutParams as RecyclerView.LayoutParams).apply {
                bottomMargin = if (adapterPosition == items.size - 1) cardviewMargin else 0
            }
        }

    }
}