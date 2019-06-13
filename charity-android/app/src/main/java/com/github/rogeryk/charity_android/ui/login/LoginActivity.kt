package com.github.rogeryk.charity_android.ui.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.modal.UserModal
import com.github.rogeryk.charity_android.utils.castTo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var loginViewModel: LoginViewModel
        @Inject set

    lateinit var userModal: UserModal
        @Inject set

    fun writeLoginInfoToLocal() {
        val sharePre = getSharedPreferences("login_info", Context.MODE_PRIVATE)
        sharePre.edit().apply {
            putString("phone", username.text.toString())
            putString("password", password.text.toString())
            apply()
        }
    }

    private fun readLoginInfoFromLocal() {
        val sharePre = getSharedPreferences("login_info", Context.MODE_PRIVATE)
        sharePre.apply {
            val phone: String = getString("phone", "")
            val pd: String = getString("password", "")
            username.text.append(phone)
            password.text.append(pd)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        application.castTo<App>().appComponent.inject(this)
        setContentView(R.layout.activity_login)

        initView()

        readLoginInfoFromLocal()
    }

    private fun initView() {
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.errCode != 0) {
                showLoginFailed(loginResult.msg ?: "未知错误")
            }
            if (loginResult.errCode == 0) {
                Toast.makeText(applicationContext, "登陆成功", Toast.LENGTH_SHORT).show()
                userModal.accessToken = loginResult.data
                userModal.isLogined.value = true
                writeLoginInfoToLocal()
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        launch {
                            loginViewModel.login(
                                    username.text.toString(),
                                    password.text.toString()
                            )
                        }
                }
                false
            }

            login.setOnClickListener  {
                launch {
                    loading.visibility = View.VISIBLE
                    loginViewModel.login(username.text.toString(), password.text.toString())
                }
            }
        }


    }


    private fun showLoginFailed(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function castTo simplify setting an afterTextChanged action castTo EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
