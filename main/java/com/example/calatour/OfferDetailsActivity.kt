package com.example.calatour

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.calatour.Model.Offers


class OfferDetailsActivity : AppCompatActivity() {
    private lateinit var offer: Offers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)
         offer = intent.getSerializableExtra("offer") as Offers
        val titleTextView = findViewById<TextView>(R.id.offerDetailsTitleTextView)
        titleTextView.text = offer.title

        val detailsTextView = findViewById<TextView>(R.id.offerDetailsDetailsTextView)
        detailsTextView.text = offer.description
        val priceTextView = findViewById<TextView>(R.id.offerDetailsPriceTextView)
        priceTextView.text = offer.price.toString()
        val imageView = findViewById<ImageView>(R.id.offerDetailsImageImageView)
        imageView.setImageResource(offer.image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.offer_details_options_menu,menu)
        if(offer.isFavourite == false){
            menu?.getItem(0)?.title = "Add to favourites";
        }else{
            menu?.getItem(0)?.title = "Remove from favourites";
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        offer.isFavourite = !(offer.isFavourite);
        if(offer.isFavourite == false){
           item.title = "Add to favourites";
            Toast.makeText(this,"added to faves",Toast.LENGTH_LONG );
        }else{
            item.title = "Remove from favourites";
            Toast.makeText(this,"removed from faves",Toast.LENGTH_LONG );
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("isFav", offer.isFavourite)
        intent.putExtra("position",this.intent.getIntExtra("position",-1))
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
        finish()
    }

}