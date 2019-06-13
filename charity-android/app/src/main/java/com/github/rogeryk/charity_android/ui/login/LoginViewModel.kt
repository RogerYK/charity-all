package com.github.rogeryk.charity_android.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.api.LoginForm
import com.github.rogeryk.charity_android.data.Response

class LoginViewModel(val api: Api) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Response<String>>()
    val loginResult: LiveData<Response<String>> = _loginResult

    suspend fun  login(username: String, password: String) {
        val form = LoginForm(username, password)
        Log.i("form", form.toString())
        val res = api.auth.login(form).await()

        _loginResult.value = res
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5;
    }
}
