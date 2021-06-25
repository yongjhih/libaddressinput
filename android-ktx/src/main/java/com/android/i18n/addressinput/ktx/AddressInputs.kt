package com.android.i18n.addressinput.ktx

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.android.i18n.addressinput.AddressDataMapLoader
import com.google.i18n.addressinput.common.*
import com.google.i18n.addressinput.isValidPostalCode
import com.google.i18n.addressinput.validatePostalCode
import java.util.*

@Suppress("DEPRECATION")
fun Configuration.firstLocale(default: Locale = Locale.US): Locale =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales.get(0) ?: default
    else locale ?: default

fun Context.isValidPostalCode(
    code: String,
    locale: Locale = resources.configuration.firstLocale(),
    dataSource: DataSource = AddressVerificationData(AddressDataMapLoader(this).loadImmutableDataMap()),
): Boolean =
    locale.isValidPostalCode(this, code, dataSource = dataSource)

fun Locale.isValidPostalCode(
    context: Context,
    code: String,
    default: Locale = Locale.US,
    dataSource: DataSource = AddressVerificationData(AddressDataMapLoader(context).loadImmutableDataMap()),
): Boolean =
    code.isValidPostalCode(
        country = country,
        default = default.country,
        dataSource = dataSource,
    )

fun Locale.validatePostalCode(
    context: Context,
    code: String,
    default: Locale = Locale.US,
    dataSource: DataSource = AddressVerificationData(AddressDataMapLoader(context).loadImmutableDataMap())
): AddressProblemType? =
    code.validatePostalCode(
        country = country,
        default = default.country,
        dataSource = dataSource,
    )
