package jp.co.hokuto.weatherreport.activity.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import jp.co.hokuto.weatherreport.R
import jp.co.hokuto.weatherreport.activity.data.ImageData


/**
 * RecyclerViewAdapter
 *
 * Created by 佐野 on 2017/09/06.
 */
class RecyclerViewAdapter(val items: ArrayList<ImageData>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val vh: ViewHolder = ViewHolder(v)

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.imageView.setImageBitmap(items[position].imageBitmap)
		holder.cardView.setCardBackgroundColor(items[position].imageColor)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val cardView : CardView = view.findViewById(R.id.card_view) as CardView
		val imageView : ImageView = view.findViewById(R.id.list_item_image) as ImageView
    }
}