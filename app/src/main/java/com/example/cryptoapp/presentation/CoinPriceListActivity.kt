package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.AppClass
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.CoinInfo
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CoinViewModelFactory

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private var mainContainerView: FragmentContainerView? = null

    private lateinit var binding: ActivityCoinPriceListBinding

    private val component by lazy {
        (application as AppClass).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainContainerView = binding.mainContainer
        val adapter = CoinInfoAdapter(this)
        setupRecyclerView(adapter)
        observeViewModel(adapter)
    }

    private fun setupRecyclerView(adapter: CoinInfoAdapter) {
        setupOnCoinClickListener(adapter)
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
    }

    private fun observeViewModel(adapter: CoinInfoAdapter) {
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
