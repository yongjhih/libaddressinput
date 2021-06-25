package com.android.i18n.addressinput.ktx

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.i18n.addressinput.AddressDataMapLoader
import com.google.common.truth.Truth
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23], qualifiers = "en-rUS")
class ExampleUnitTest {
    @Test
    fun test() {
        val context: Context = ApplicationProvider.getApplicationContext()
        Truth.assertThat(AddressDataMapLoader(context).loadImmutableDataMap()).isNotEmpty()
        Truth.assertThat(Locale.getDefault().country).isEqualTo("US")
        Truth.assertThat(Locale.getDefault().isValidPostalCode(context, "")).isFalse()
        Truth.assertThat(Locale.getDefault().isValidPostalCode(context, "00000")).isTrue()
        Truth.assertThat(Locale.getDefault().isValidPostalCode(context, "000000")).isFalse()
        Truth.assertThat(Locale.getDefault().isValidPostalCode(context, "00000-1234")).isTrue()
        Truth.assertThat(Locale.getDefault().isValidPostalCode(context, "00000-12345")).isFalse()
        Truth.assertThat(context.isValidPostalCode("k1a0b1", Locale.CANADA)).isTrue()
        Truth.assertThat(context.isValidPostalCode("k1a 0b1", Locale.CANADA)).isTrue()
        Truth.assertThat(context.isValidPostalCode("k1a-0b1", Locale.CANADA)).isFalse()
        Truth.assertThat(context.isValidPostalCode("000000", Locale.CANADA)).isFalse()
        Truth.assertThat(context.isValidPostalCode("000 000", Locale.CANADA)).isFalse()
        Truth.assertThat(context.isValidPostalCode("", Locale.CANADA)).isFalse()
        Truth.assertThat(context.isValidPostalCode("00000", Locale.CANADA)).isFalse()

        Truth.assertThat(context.isValidPostalCode("236", Locale.TAIWAN)).isTrue()
        Truth.assertThat(context.isValidPostalCode("23669", Locale.TAIWAN)).isTrue()
        // not supported 3+3
        Truth.assertThat(context.isValidPostalCode("236034", Locale.TAIWAN)).isFalse()
        Truth.assertThat(context.isValidPostalCode("236-69", Locale.TAIWAN)).isFalse()
        Truth.assertThat(context.isValidPostalCode("236-034", Locale.TAIWAN)).isFalse()
        Truth.assertThat(context.isValidPostalCode("", Locale.TAIWAN)).isFalse()
    }
}