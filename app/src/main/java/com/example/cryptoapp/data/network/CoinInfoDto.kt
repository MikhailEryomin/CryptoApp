package com.example.cryptoapp.data.network

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoDto (
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromSymbol: String,
    @SerializedName("TOSYMBOL")
    @Expose
    val toSymbol: String?,
    @SerializedName("PRICE")
    @Expose
    val price: String?,
    @SerializedName("LOWDAY")
    @Expose
    val lowDay: String?,
    @SerializedName("HIGHDAY")
    @Expose
    val highDay: String?,
    @SerializedName("LASTMARKET")
    @Expose
    val lastMarket: String?,
    @SerializedName("LASTUPDATE")
    @Expose
    val lastUpdate: Long?,
    @SerializedName("IMAGEURL")
    @Expose
    val imageUrl: String?
)