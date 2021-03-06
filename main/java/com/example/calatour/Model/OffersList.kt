package com.example.calatour.Model

import com.example.calatour.R

class OffersList{
fun getOffers(): ArrayList<Offers>{
    val offers = ArrayList<Offers>()
    offers.add(Offers("Barcelona, 3 days","description for barcelona offer",300, R.drawable.offer_1))
    offers.add(Offers("Maldive, 7 days","description for maldive offer",1050, R.drawable.offer_2))
    offers.add(Offers("Thailand, 10 nights","description for thailand offer",1250, R.drawable.offer_3))
return offers
}


}


