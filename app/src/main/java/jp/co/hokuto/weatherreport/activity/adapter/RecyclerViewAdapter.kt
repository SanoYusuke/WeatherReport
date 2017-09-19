package jp.co.hokuto.weatherreport.activity.adapter

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import jp.co.hokuto.weatherreport.R


/**
 * Created by Sano on 2017/09/06.
 */
class RecyclerViewAdapter(val items: ArrayList<Bitmap>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val vh: ViewHolder = ViewHolder(v)

        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.imageView.setImageBitmap(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.list_item_image) as ImageView

        companion object Factory {
            fun create(v: ImageView): ViewHolder = ViewHolder(v)
        }
    }
}