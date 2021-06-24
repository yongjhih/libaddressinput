package com.android.i18n.addressinput;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddressDataMapLoader {
    private static final String DATA_PATH = "com/android/i18n/addressinput/countryinfo.txt";

    @NonNull
    private final Context context;

    public AddressDataMapLoader(@NonNull final Context context) {
        this.context = context;
    }

    @NonNull
    public Map<String, String> loadImmutableDataMap() {
        final HashMap<String, String> map = new HashMap<>();
        try (final BufferedReader br = new BufferedReader(
                new InputStreamReader(context.getAssets().open(DATA_PATH), "UTF-8"))) {
            String line;
            while (null != (line = br.readLine())) {
                line = line.trim();
                if (line.length() == 0 || line.charAt(0) == '#') {
                    continue;
                }
                int x = line.indexOf('=');
                map.put(line.substring(0, x), line.substring(x + 1));
            }
        } catch (IOException e) {
            System.err.println("unable to create map: " + e.getMessage());
        }
        return Collections.unmodifiableMap(map);
    }
}
