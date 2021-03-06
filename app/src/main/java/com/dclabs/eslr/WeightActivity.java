package com.dclabs.eslr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WeightActivity extends AppCompatActivity {

    private static final Map<Double, List<Double>> CACHE = new HashMap<Double, List<Double>>();
    private static final double [] WEIGHTS = new double[]{45,35,25,10,5,2.5};  //plates of weights that are used to calculate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
    }


    /**   Button action event used for calculating plates based of weight input */
    public void submitWeight(View view) {
        TextView textView = (TextView) this.findViewById(R.id.textView2);
        EditText editText = (EditText) this.findViewById(R.id.editText);
        String targetWeight = editText.getText().toString();

        
        if (isInteger(targetWeight,10)) {
            double targetValue = Integer.parseInt(targetWeight);
            List<Double> weights = getWeights((targetValue)/2);  //divide by two since we are displaying plates on one side
            if(weights.size() ==  0) {
                textView.setText("Can't match input value with given weights");
            } else {
                textView.setText(Arrays.toString(weights.toArray()));
            }

        } else {
            textView.setText("Invalid Input");
        }
    }


    /** Algorithm used for calculating which weights to use to add up to target input.  Returns list of weights */

    public List<Double> getWeights(double targetWeight) {

        if (targetWeight == 0) {
            return new ArrayList<Double>();
        }
        if (CACHE.containsKey(targetWeight) ) {
            return CACHE.get(targetWeight);
        }

        List<Double> min = new ArrayList<Double>();
        List<Double> newMin = null;
        double newAmount = 0;


        for (double weight: WEIGHTS) {
            newAmount = targetWeight - weight;

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

        CACHE.put(targetWeight, min);
        return min;
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
