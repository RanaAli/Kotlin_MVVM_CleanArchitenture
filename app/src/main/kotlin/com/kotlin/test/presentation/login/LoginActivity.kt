
package com.kotlin.test.presentation.login

import android.content.Context
import android.content.Intent
import com.kotlin.test.core.platform.BaseActivity

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()
}
