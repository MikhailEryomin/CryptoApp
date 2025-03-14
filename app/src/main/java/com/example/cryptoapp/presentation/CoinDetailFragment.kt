package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.AppClass
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CoinViewModelFactory

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as AppClass).component
    }

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding = null")

    private lateinit var fromSymbol: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fromSymbol = requireArguments().getString(FROM_SYM_KEY, EMPTY_SYMBOL)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            bindViews(it)
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

        private const val FROM_SYM_KEY = "from_sym"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fromSym: String): CoinDetailFragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYM_KEY, fromSym)
                }
            }
        }
    }
}