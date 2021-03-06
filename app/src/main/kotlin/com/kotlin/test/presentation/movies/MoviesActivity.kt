
package com.kotlin.test.presentation.movies

import android.content.Context
import android.content.Intent
import com.kotlin.test.core.platform.BaseActivity

class MoviesActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }

    override fun fragment() = MoviesFragment()
}
