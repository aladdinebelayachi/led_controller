package com.led.led_controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class consom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consom);
        BarChart barChart =findViewById(R.id.barChart);
        ArrayList<BarEntry> conso =new ArrayList<>();
        conso.add(new BarEntry(1,1500));
        conso.add(new BarEntry(2,1700));
        conso.add(new BarEntry(3,1889));
        conso.add(new BarEntry(4,1950));
        conso.add(new BarEntry(5,1550));
        conso.add(new BarEntry(6,1120));
        conso.add(new BarEntry(7,1135));
        conso.add(new BarEntry(8,1490));
        conso.add(new BarEntry(9,1320));
        conso.add(new BarEntry(10,1470));
        conso.add(new BarEntry(11,2100));
        conso.add(new BarEntry(12,2150));
        BarDataSet barDataSet =new BarDataSet(conso,"Consomation mensuelle");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData=new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("mWh");
        barChart.animateY(2000);


    }
}
