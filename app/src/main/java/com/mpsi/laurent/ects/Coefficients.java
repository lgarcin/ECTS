package com.mpsi.laurent.ects;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;


public class Coefficients extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new CoefficientsFragment()).commit();
    }

    public static class CoefficientsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(getActivity());

            String[] subjects = getResources().getStringArray(R.array.subjects);
            for (String subject : subjects) {
                MyTextPreference coeff = new MyTextPreference(getActivity());
                coeff.setKey(subject);
                coeff.setTitle(subject);
                coeff.setSummary("%s");
                coeff.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                screen.addPreference(coeff);
            }
            setPreferenceScreen(screen);
        }

    }

}
