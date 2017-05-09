package com.dclabs.eslr;

import android.support.annotation.NonNull;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WeightActivity extends AppCompatActivity {

    private final Map<Double, ArrayList<Double>> cache = new HashMap<Double, ArrayList<Double>>();
    private final double [] weights = new double[]{45,35,25,10,5,2.5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
    }



    public void submitWeight(View view) {
        TextView textView = (TextView) this.findViewById(R.id.textView2);
        EditText editText = (EditText) this.findViewById(R.id.editText);
        String text = editText.getText().toString();

        if (isInteger(text,10)) {
            double weightValue = Integer.parseInt(text);
            ArrayList<Double> weights = getWeights((weightValue-45)/2);  //take off bar value and do for one side only
            if(weights.size() ==  0) {
                textView.setText("Can't match input value with given weights");
            } else {
                textView.setText(Arrays.toString(weights.toArray()));
            }


        } else {
            textView.setText("Invalid Input");
        }
    }

    public ArrayList<Double> getWeights(double goalWeight) {

        if(goalWeight == 0) {
            return new ArrayList<Double>();
        }
        if(cache.containsKey(goalWeight) ) {
            return cache.get(goalWeight);
        }

        ArrayList<Double> min = new ArrayList<Double>();
        ArrayList<Double> newMin = null;
        double newAmount = 0;


        for(double weight: weights) {
            newAmount = goalWeight - weight;

            if(newAmount >= 0){
                newMin = getWeights(newAmount);

                if((newMin.size() < min.size() - 1 ||  min.size() == 0) &&
                        (newMin.size() > 0 || newAmount == 0 )) {

                    min  = new ArrayList<Double>() {};
                    min.add(weight);
                    min.addAll(newMin);
                }
            }
        }

        cache.put(goalWeight, min);
        return (ArrayList<Double>)min.clone();
    }


    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }



}
