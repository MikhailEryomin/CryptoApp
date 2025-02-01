package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private var mainContainerView: FragmentContainerView? = null

    private lateinit var binding: ActivityCoinPriceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainContainerView = binding.mainContainer
        val adapter = CoinInfoAdapter(this)
        setupOnCoinClickListener(adapter)
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupOnCoinClickListener(adapter: CoinInfoAdapter) {
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                if (isLandscapeOrientation()) {
                    launchCoinDetailFragment(coinInfo.fromSymbol)
                } else {
                    launchCoinDetailActivity(coinInfo.fromSymbol)
                }
            }
        }
    }

    private fun launchCoinDetailFragment(fromSymbol: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CoinDetailFragment.newInstance(fromSymbol))
            .commit()
    }

    private fun launchCoinDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun isLandscapeOrientation(): Boolean =
        binding.mainContainer != null
}
