package br.com.rafanereslima.tweetanalyzer

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rafanereslima.tweetanalyzer.ui.activities.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BasicInstrumentedTest {

    @Test(expected = NullPointerException::class)
    fun nullStringTest() {
        val str: String? = null
        assertTrue(str!!.isEmpty())
    }

    @RunWith(AndroidJUnit4::class)
    class MainActivityUiTest {

        @Rule
        @JvmField
        var mActivityTestRule: BasicInstrumentedTest<MainActivity> = BasicInstrumentedTest(MainActivity::class.java, true, false)

        @Before
        fun setUp() {
            val intent = Intent()
            //Customize intent if needed (maybesome extras?)
            mActivityTestRule.launchActivity(intent)
        }
    }

    @Test
    fun whenButtonIsClickedTheUseCaseTextIsShown(){
        onView(withId(R.id.bt_analyze)).perform((click()))
        onView(withId(R.id.tvTitle)).check(ViewAssertions.matches(withText("Titulo da home do app!")))
    }
}