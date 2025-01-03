package com.guit.edu.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guit.edu.myapplication.RetrofitUtil
import com.guit.edu.myapplication.entity.RegisterResult
import com.guit.edu.myapplication.net.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterViewModel : ViewModel() {

    private val _registerResult = MutableStateFlow<Result<RegisterResult>>(Result.Idle)
    val registerResult: StateFlow<Result<RegisterResult>> = _registerResult

    fun register(username: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerResult.value = Result.Loading
            if(password != confirmPassword){
                _registerResult.value = Result.Error("两次密码不一致")
                return@launch
            }
            RetrofitUtil.api.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {result ->
                    _registerResult.value = Result.Success(result)
                },{e ->
                    if (e is HttpException) {
                        _registerResult.value = Result.Error("HttpException")
                    }else if(e is IOException){
                        _registerResult.value = Result.Error("IOException")
                    }
                })

        }
    }
}
