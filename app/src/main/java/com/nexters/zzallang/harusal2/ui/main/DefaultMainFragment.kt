package com.nexters.zzallang.harusal2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.FragmentDefaultMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DefaultMainFragment : Fragment() {
    lateinit var binding: FragmentDefaultMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_default_main, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@DefaultMainFragment
        }

        return binding.root
    }
}