package com.mpsi.laurent.ects;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class MyTextPreference extends EditTextPreference {
    public MyTextPreference(Context context) {
        super(context);
    }

    // Override getSummary to return the current value formatted using the default summary string
    @Override
    public CharSequence getSummary() {
        return String.format(super.getSummary().toString(), getText());
    }
}
