package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding: ActivityCoinDetailBinding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fromSymbol = parseIntent()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            bindViews(it)
        }
    }

    private fun parseIntent(): String {
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return EMPTY_SYM
        }
        return intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYM
    }

    private fun bindViews(coin: CoinInfo) {
        binding.tvPrice.text = coin.price
        binding.tvMinPrice.text = coin.lowDay
        binding.tvMaxPrice.text = coin.highDay
        binding.tvLastMarket.text = coin.lastMarket
        binding.tvLastUpdate.text = coin.lastUpdate
        binding.tvFromSymbol.text = coin.fromSymbol
        binding.tvToSymbol.text = coin.toSymbol
        Picasso.get().load(coin.imageUrl).into(binding.ivLogoCoin)
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYM = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
