package com.nexters.zzallang.harusal2.ui.onBoarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel

class OnBoardingViewModel: BaseViewModel() {
    private val _text = MutableLiveData("")
    val text: LiveData<String> get() = _text

    fun setText(message: String) {
        _text.postValue(message)
    }
}