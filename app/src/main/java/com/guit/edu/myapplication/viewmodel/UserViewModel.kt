package com.guit.edu.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.guit.edu.myapplication.entity.History
import com.guit.edu.myapplication.entity.User
import com.guit.edu.myapplication.util.RetrofitUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel: ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user:StateFlow<User?> = _user.asStateFlow()
    private val _history = MutableStateFlow<List<History>?>(null)
    val history : StateFlow<List<History>?> = _history.asStateFlow()

    private var token:String? = null


    init {
        // fetch user info
    }

    fun fetchUserInfo(token:String){
        this.token = token
        RetrofitUtil.api.getUserInfo(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _user.value = result.data
            },{})
    }
    fun fetchUserHistory(username: String,date:String){
        RetrofitUtil.api.getUserHistory(username,date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ historyList ->
                _history.value = historyList.toList()
            },{})
    }




    fun updateNickname(nickname:String){
        _user.value = _user.value?.copy(nickname = nickname)
        updateUserInfo()
    }

    fun updateSignature(signature:String){
        _user.value = _user.value?.copy(signature = signature)
        updateUserInfo()

    }

    fun updateGender(gender:String){
        _user.value = _user.value?.copy(gender = gender)
        updateUserInfo()
    }

    fun updateHeight(height:Int){
        _user.value = _user.value?.copy(height = height)
        updateUserInfo()
    }
    fun updateWeight(weight:Int){
        _user.value = _user.value?.copy(weight = weight)
        updateUserInfo()
    }

    fun updateCupCapacity(cupcapacity:Int){
        _user.value = _user.value?.copy(cupcapacity = cupcapacity)
        updateUserInfo()
    }

    fun updateAssignment(assignment:Int){
        _user.value = _user.value?.copy(assignment = assignment)
        updateUserInfo()
    }
    private fun updateUserInfo(){
        _user.value?.let {
            token?.let { token ->
                RetrofitUtil.api.updateUserInfo(it.id, it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {user -> _user.value = user},
                        {}
                    )
            }

        }

    }
}
