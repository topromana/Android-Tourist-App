package com.example.calatour

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import com.example.calatour.Adapters.OffersAdapter
import com.example.calatour.Model.Offers
import com.example.calatour.Model.OffersList
import com.example.calatour.adapters.ChatAdapter
import java.util.*
import kotlin.concurrent.schedule

class OffersActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private  lateinit var offersAdapter: OffersAdapter
    private lateinit var chatAdapter: ChatAdapter
    private val detailsId = 1001
    private val chatId = 1002
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        offersAdapter = OffersAdapter(this, OffersList().getOffers())

        val offersListView = findViewById<ListView>(R.id.offersListView)

        offersListView.adapter = offersAdapter

        registerForContextMenu(offersListView)
        
        
        
        val ProgressBar = findViewById<ProgressBar>(R.id.progressBar)

        ProgressBar.visibility = View.VISIBLE
        offersListView.visibility = View.GONE
        Timer().schedule(3000){
            Handler(mainLooper).post {
                ProgressBar.visibility = View.GONE
                offersListView.visibility = View.VISIBLE
            }
        }

        offersListView.setOnItemClickListener (this)
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu ( menu, v, menuInfo )
        // check if the menu is created for the targeted list
        if (v!!.id == R.id.offersListView)
        {
            // identify the item selected from the list
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            menu!!.setHeaderTitle ( offersAdapter.getItem(info.position).title)
            // load the visual structure of the menu
            getMenuInflater().inflate ( R.menu.offers_contextual_menu, menu )
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info =item.menuInfo as AdapterView.AdapterContextMenuInfo
        if(item.itemId == R.id.offerMenuAddOffer){
            val offer = Offers("new offer","new description",500, R.drawable.offer_2)
            offersAdapter.addOffer(info.position,offer)
        }else  if(item.itemId == R.id.fferMenuRemoveOffer){
            offersAdapter.removeOffer(info.position)
        }

        offersAdapter.notifyDataSetChanged()
        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent2 = Intent ( this, ChatActivity::class.java )
        if(item.itemId == R.id.offersOptionsSignOut){
            onBackPressed()
        }
        else if(item.itemId == R.id.offersOptionsResetList){
            offersAdapter.resetDataSource(OffersList().getOffers())
            offersAdapter.notifyDataSetChanged()
        }
        else if(item.itemId == R.id.offersOptionsClearFavourites){

        } else if(item.itemId == R.id.chatWithUs) {
            startActivity(intent2)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder ( this )
        builder.setTitle ( "please confirm" )
            .setMessage ( "are you sure you want to sign out?" )
            .setPositiveButton ( "yes", DialogInterface.OnClickListener { dialog, which ->finish()  } )
            .setNegativeButton ( "no", null )
        builder.create().show()
    }

    override fun onCreateOptionsMenu ( menu: Menu? ):Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate ( R.menu.offers_options_menu, menu )
        // change programmatically some menu items
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent ( this, OfferDetailsActivity::class.java )
        val offer = offersAdapter.getItem(position) as Offers
        intent.putExtra("offer",offer)
        intent.putExtra("position",position)
        startActivityForResult ( intent,detailsId )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == detailsId ){
            if(resultCode == Activity.RESULT_OK){
                val isFavourite = data!!.getBooleanExtra("isFav",false)
                val position = data!!.getIntExtra("position",-1)
                if(position > -1){
                    offersAdapter.getItem(position).isFavourite=isFavourite
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}