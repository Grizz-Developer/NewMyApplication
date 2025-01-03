package com.guit.edu.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guit.edu.myapplication.RetrofitUtil
import com.guit.edu.myapplication.entity.LoginResult
import com.guit.edu.myapplication.net.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel: ViewModel() {

    private val _loginResult = MutableStateFlow<Result<LoginResult>>(Result.Idle)
    val loginResult: StateFlow<Result<LoginResult>> = _loginResult


    fun login(username: String,password:String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            RetrofitUtil.api.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {result ->
                    _loginResult.value = Result.Success(result)
                },{e ->
                    if (e is HttpException) {
                        _loginResult.value = Result.Error("HttpException")
                    }else if(e is IOException){
                        _loginResult.value = Result.Error("IOException")
                    }
                })
        }
    }
}