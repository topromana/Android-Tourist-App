package com.example.calatour.Model

import java.io.Serializable

class Offers(
val title: String,
val description: String,
val price: Int,
val image: Int,
var isFavourite: Boolean = false
):Serializable