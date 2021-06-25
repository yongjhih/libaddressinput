package com.google.i18n.addressinput

import com.google.i18n.addressinput.common.*

fun String.isValidPostalCode(
    country: String = "US",
    dataSource: DataSource,
    default: String = "US"
) = validatePostalCode(country, dataSource, default) == null


/**
 * @dataSource AddressDataMapLoader(context).loadImmutableDataMap()
 */
fun String.validatePostalCode(
    country: String = "US",
    dataSource: DataSource,
    default: String = "US"
): AddressProblemType? =
    AddressProblems().also {
        StandardAddressVerifier(
            FieldVerifier(dataSource),
            StandardChecks.PROBLEM_MAP
        ).verify(AddressData.builder()
            .setCountry(country.ifEmpty { default })
            .setPostalCode(this)
            .build(), it)
    }.getProblem(AddressField.POSTAL_CODE)
