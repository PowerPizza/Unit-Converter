package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;


class Converter{
    public double km_to_m(double km){
        return km * 1000;
    }
    public double m_to_km(double m){
        return m / 1000;
    }
    public double inch_to_m(double inch) {return inch / 39.37;}
    public double m_to_inch(double meter) {return meter * 39.37;}
    public double cm_to_m(double cm) {return cm / 100;}
    public double m_to_cm(double m) {return m * 100;}
    public double hour_to_min(double hour) {return hour*60;}
    public double min_to_hour(double min) {return min/60;}
    public double min_to_sec(double min) {return min*60;}
    public double sec_to_min(double sec) {return sec/60;}
    public double sec_to_millisec(double sec) {return sec * 1000;}
    public double millisec_to_sec(double millisec) {return millisec / 1000;}
}

public class MainActivity extends AppCompatActivity {
    static String currentConversion = "CM_TO_METER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText entry_1 = findViewById(R.id.ValueEntry1);
        entry_1.setTag("");
        EditText entry_2 = findViewById(R.id.ValueEntry2);
        entry_2.setTag("");

        entry_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (entry_1.getTag().equals("CHANGING")){
                    return;
                }
                entry_2.setTag("CHANGING");
                try {
                    double value_ = Double.parseDouble(String.format(Locale.ENGLISH, "%s", editable.toString()));
                    calculate_up_to_down(value_);
                }
                catch (Exception e){
                    entry_2.setText("0");
                }
                entry_2.setTag("");
            }
        });

        entry_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (entry_2.getTag() == "CHANGING"){
                    return;
                }

                entry_1.setTag("CHANGING");
                try {
                    double value_ = Double.parseDouble(editable.toString());
                    calculate_down_to_up(value_);
                }
                catch (Exception e) {
                    entry_1.setText("0");
                }
                entry_1.setTag("");
            }
        });
    }

    public void calculate_down_to_up(double value){
        Converter conv = new Converter();
        EditText entry_1 = findViewById(R.id.ValueEntry1);

        switch (MainActivity.currentConversion){
            case "M_TO_KM":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.km_to_m(value)));
                break;
            case "INCH_TO_M":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.m_to_inch(value)));
                break;
            case "HOUR_TO_MIN":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.min_to_hour(value)));
                break;
            case "CM_TO_METER":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.m_to_cm(value)));
                break;
            case "MIN_TO_SEC":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.sec_to_min(value)));
                break;
            case "SEC_TO_MILLISEC":
                entry_1.setText(String.format(Locale.ENGLISH, "%s", conv.millisec_to_sec(value)));
                break;
        }
    }
    public void calculate_up_to_down(double value){
        Converter conv = new Converter();
        EditText entry_2 = findViewById(R.id.ValueEntry2);

        switch (MainActivity.currentConversion) {
            case "M_TO_KM":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.m_to_km(value)));
                break;
            case "INCH_TO_M":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.inch_to_m(value)));
                break;
            case "HOUR_TO_MIN":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.hour_to_min(value)));
                break;
            case "CM_TO_METER":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.cm_to_m(value)));
                break;
            case "MIN_TO_SEC":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.min_to_sec(value)));
                break;
            case "SEC_TO_MILLISEC":
                entry_2.setText(String.format(Locale.ENGLISH, "%s", conv.sec_to_millisec(value)));
                break;
        }
    }

    public void changeLabels(int label_id, int entry_id,  int value_id){
        TextView lbl_value = findViewById(label_id);
        lbl_value.setText(value_id);
        EditText value_entry = findViewById(entry_id);
        value_entry.setHint(value_id);
    }

    public void onClickMeterToKm(View v1){
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.m_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.km_default);
        currentConversion = "M_TO_KM";
    }

    public void onClickInchToMeter(View v1) {
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.inch_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.m_default);
        currentConversion = "INCH_TO_M";
    }

    public void onClickHourToMin(View v1){
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.hour_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.min_default);
        currentConversion = "HOUR_TO_MIN";
    }

    public void onClickCmToMeter(View v1){
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.cm_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.m_default);
        currentConversion = "CM_TO_METER";
    }

    public void onClickMinToSec(View v1){
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.min_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.sec_default);
        currentConversion = "MIN_TO_SEC";
    }

    public void onClickSecToMillisec(View v1){
        this.changeLabels(R.id.ValueLabel1, R.id.ValueEntry1, R.string.sec_default);
        this.changeLabels(R.id.ValueLabel2, R.id.ValueEntry2, R.string.millisec_default);
        currentConversion = "SEC_TO_MILLISEC";
    }
}