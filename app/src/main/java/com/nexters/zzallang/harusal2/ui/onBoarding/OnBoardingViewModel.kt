package com.nexters.zzallang.harusal2.ui.onBoarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.zzallang.harusal2.base.BaseViewModel

class OnBoardingViewModel: BaseViewModel() {
    private val _text = MutableLiveData("")
    private val _title = MutableLiveData("")
    val text: LiveData<String> get() = _text
    val title: LiveData<String> get() = _title

    fun setText(text: String) {
        _text.postValue(text)
    }

    fun setTitle(title: String){
        _title.postValue(title)
    }
}