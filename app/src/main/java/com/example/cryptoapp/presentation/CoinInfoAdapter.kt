package com.example.cryptoapp.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoAdapter.CoinInfoViewHolder>(CoinItemCallBack()) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = currentList[position]
        val binding = holder.binding
        with(coin) {
            val symbolsTemplate = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
            binding.tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
            binding.tvPrice.text = price
            binding.tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
            Picasso.get().load(imageUrl).into(binding.ivLogoCoin)
            binding.root.setOnClickListener {
                onCoinClickListener?.onCoinClick(this)
            }
        }
    }

    inner class CoinInfoViewHolder(
        val binding: ItemCoinInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coinInfo: CoinInfo)
    }
}