package com.mpsi.laurent.ects;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class MainActivity extends Activity {
    Hashtable<String, String> mMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMarks = new Hashtable<String, String>();
        TableLayout entries = (TableLayout) findViewById(R.id.entries);
        String[] subjects = getResources().getStringArray(R.array.subjects);
        String[] letters = getResources().getStringArray(R.array.letters);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, letters);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        for (final String subject : subjects) {
            TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.subject, entries, false);
            TextView subject_text = (TextView) getLayoutInflater().inflate(R.layout.label, row, false);
            subject_text.setText(subject);
            row.addView(subject_text);
            Spinner subject_mark = (Spinner) getLayoutInflater().inflate(R.layout.mark, row, false);
            subject_mark.setAdapter(adapter);
            subject_mark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mMarks.put(subject, parent.getItemAtPosition(position).toString());
                    computeMean();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            row.addView(subject_mark);
            entries.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.coeff) {
            Intent coefficients = new Intent(this, Coefficients.class);
            startActivity(coefficients);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        computeMean();
    }

    private void computeMean() {
        Integer sum = 0;
        Integer div = 0;
        String[] subjects = getResources().getStringArray(R.array.subjects);
        SharedPreferences coeffs = PreferenceManager.getDefaultSharedPreferences(this);
        for (String subject : subjects) {
            Integer coeff = Integer.parseInt(coeffs.getString(subject, "0"));
            div += coeff;
            String mark = mMarks.get(subject);
            if (mark != null) {
                switch (mark) {
                    case "A":
                        sum += 1 * coeff;
                        break;
                    case "B":
                        sum += 2 * coeff;
                        break;
                    case "C":
                        sum += 3 * coeff;
                        break;
                    case "D":
                        sum += 4 * coeff;
                        break;
                    case "E":
                        sum += 5 * coeff;
                        break;
                    case "F":
                        sum += 6 * coeff;
                        break;
                    default:
                }
            }
        }
        String mean;
        Float quo = div == 0 ? 0f : sum / div;
        switch (Math.round(quo)) {
            case 1:
                mean = "A";
                break;
            case 2:
                mean = "B";
                break;
            case 3:
                mean = "C";
                break;
            case 4:
                mean = "D";
                break;
            case 5:
                mean = "E";
                break;
            case 6:
                mean = "F";
                break;
            default:
                mean = "N/A";
        }
        TextView global = (TextView) findViewById(R.id.global);
        global.setText(mean);
    }

}
