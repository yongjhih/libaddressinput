package com.android.i18n.addressinput;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.google.i18n.addressinput.common.AddressData;
import com.google.i18n.addressinput.common.AddressField;
import com.google.i18n.addressinput.common.AddressProblemType;
import com.google.i18n.addressinput.common.AddressProblems;
import com.google.i18n.addressinput.common.AddressVerificationData;
import com.google.i18n.addressinput.common.FieldVerifier;
import com.google.i18n.addressinput.common.StandardAddressVerifier;
import com.google.i18n.addressinput.common.StandardChecks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class AddressDataMapLoaderTest {
    @Test
    public void test() {
        final Context context = ApplicationProvider.getApplicationContext();
        final AddressDataMapLoader addressDataMapLoader = new AddressDataMapLoader(context);
        final Map<String, String> dataMap = addressDataMapLoader.loadImmutableDataMap();
        assertThat(dataMap).isNotEmpty();

        final AddressProblems problems = new AddressProblems();
        new StandardAddressVerifier(
                new FieldVerifier(new AddressVerificationData(dataMap)),
                StandardChecks.PROBLEM_MAP
        ).verify(AddressData.builder()
                .setCountry("US")
                .setPostalCode("94025")
                .build(), problems);
        assertThat(problems.getProblem(AddressField.POSTAL_CODE)).isNull();

        final AddressProblems problems2 = new AddressProblems();
        new StandardAddressVerifier(
                new FieldVerifier(new AddressVerificationData(dataMap)),
                StandardChecks.PROBLEM_MAP
        ).verify(AddressData.builder()
                .setCountry("US")
                .setPostalCode("94")
                .build(), problems2);
        assertThat(problems2.getProblem(AddressField.POSTAL_CODE))
            .isEqualTo(AddressProblemType.INVALID_FORMAT);

    }

}