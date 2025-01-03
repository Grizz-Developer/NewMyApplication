package com.guit.edu.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.guit.edu.myapplication.RetrofitUtil
import com.guit.edu.myapplication.entity.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val disposables = CompositeDisposable()

    fun fetchUserInfo(token: String) {
        val disposable = RetrofitUtil.api.getUserInfo(token) // 去掉 Bearer 前缀
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _user.value = user
            }, { error ->
                // 处理错误情况
                Log.e("UserViewModel", "Error fetching user info", error)
            })

        disposables.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
