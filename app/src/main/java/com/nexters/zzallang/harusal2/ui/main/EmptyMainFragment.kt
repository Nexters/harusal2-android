package com.nexters.zzallang.harusal2.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nexters.zzallang.harusal2.R
import com.nexters.zzallang.harusal2.databinding.FragmentEmptyMainBinding

class EmptyMainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentEmptyMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_empty_main, container, false)
        return binding.root
    }
}