package com.example.calatour.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.calatour.Model.Offers
import com.example.calatour.R

class OffersAdapter (private val context: Context, private var dataSource: ArrayList<Offers>) :
    BaseAdapter() {
    // get a reference to the LayoutInflater service
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, containerView: View?, viewGroupParent: ViewGroup?): View {
        // reuse OR expand the view for one entry into the list
        val rowView = containerView ?: inflater.inflate(R.layout.offers_list_element, viewGroupParent, false)
        // get the visual elements and update them with the information from the model
        // for example:
         val titleTextView = rowView.findViewById <TextView> ( R.id.offerTitleTextView )
         titleTextView.text = getItem(position).title

        val priceTextView = rowView.findViewById <TextView> ( R.id.offerPriceTextView )
        priceTextView.text = getItem(position).price.toString()

        val descriptionTextView = rowView.findViewById <TextView> ( R.id.offerDescriptionTextView )
        descriptionTextView.text = getItem(position).description

        val imageView = rowView.findViewById <ImageView> ( R.id.offerImageImageView )
        imageView.setImageResource(getItem(position).image)
        return rowView
    }

    override fun getItem(position: Int):Offers {
        return dataSource.elementAt(position)
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    fun addOffer(position: Int, offer:Offers){
        dataSource.add(position, offer)
    }

    fun removeOffer(position:Int){
        dataSource.removeAt(position)
    }

    fun resetDataSource(offers:ArrayList<Offers>){
        dataSource = offers
    }
}