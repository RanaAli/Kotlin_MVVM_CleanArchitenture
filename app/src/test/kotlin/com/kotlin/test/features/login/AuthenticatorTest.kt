
package com.kotlin.test.features.login

import com.kotlin.test.UnitTest
import com.kotlin.test.presentation.login.Authenticator
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }
}
