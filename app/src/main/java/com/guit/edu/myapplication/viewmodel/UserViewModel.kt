package com.guit.edu.myapplication.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guit.edu.myapplication.RetrofitUtil
import com.guit.edu.myapplication.entity.History
import com.guit.edu.myapplication.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Date


class UserViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _history = MutableStateFlow<List<History>?>(null)
    val history : StateFlow<List<History>?> = _history


    fun fetchUserInfo(token: String) {
        viewModelScope.launch{
            RetrofitUtil.api.getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: User ->
                    _user.value = result
                }, {e ->
                    println("error"+e.message)
                })
        }

    }

    fun fetchUserHistory(username: String,date:String) {
        viewModelScope.launch {
            RetrofitUtil.api.getUserHistory(username, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ historyList ->
                    _history.value = historyList
                }, {e ->
                    println("error"+e.message)
                })
        }
    }


    fun updateUserInfo(user: User){
        viewModelScope.launch {
            _user.value?.let {
                RetrofitUtil.api.updateUserInfo(it.id,user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user : User -> _user.value = user },{ e -> println("error"+e.message)})
                return@let Unit
            }
        }
    }
}