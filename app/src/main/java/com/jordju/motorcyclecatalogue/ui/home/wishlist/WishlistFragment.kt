package com.jordju.motorcyclecatalogue.ui.home.wishlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jordju.motorcyclecatalogue.R

class WishlistFragment : Fragment() {

    companion object {
        fun newInstance() = WishlistFragment()
    }

    private lateinit var viewModel: WishlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WishlistViewModel::class.java)
        // TODO: Use the ViewModel
    }

}