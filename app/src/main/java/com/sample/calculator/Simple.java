package com.sample.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bartłomiej on 2018-03-12.
 */

public class Simple extends AppCompatActivity {
    TextView textView ;
    Button coma;

    String currentOperation;
    String firstNumber;
    String secondNumber;
    String temp;

    boolean operationFlag = false;
    boolean isMinus = false;
    boolean oneComaFlag = false;
    double equal = 0;



    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("firstNumber",firstNumber);
        outState.putString("currentOperation",currentOperation);
        outState.putString("temp",temp);
        outState.putString("secondNumber",secondNumber);
        outState.putBoolean("operationFlag",operationFlag);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState != null) {
            firstNumber = savedInstanceState.getString("firstNumber");
            currentOperation = savedInstanceState.getString("currentOperation");
            temp = savedInstanceState.getString("temp");
            secondNumber = savedInstanceState.getString("secondNumber");
            operationFlag = savedInstanceState.getBoolean("operationFlag");
        }

        setContentView(R.layout.simple);


        textView = (TextView) findViewById(R.id.textView);
        coma = (Button)findViewById(R.id.coma);
        textView.setText("");
        temp = "";
        coma.setEnabled(false);
    }



    public void backspace(View view){
        String text = textView.getText().toString();
        if(text != "" && text !=null && text.length() > 0){
            text = text.substring(0,text.length()-1);
            textView.setText(text);
        }
    }

    public void clear(View view){
        coma.setEnabled(true);
        textView.setText("");
        firstNumber = "";
        secondNumber = "";
        temp = "";
        isMinus = false;
        equal = 0;
    }

    public void symbolChange(View view){
        if(isMinus == false) {
            textView.setText("-" + textView.getText());
            isMinus = true;
        }else if(isMinus == true) {
            String text = textView.getText().toString();
            if(text != "" && text !=null && text.length() > 0 || text.substring(0,0) == "-" ){
                text = text.substring(1,text.length());
                textView.setText(text);

            }else if(text == "0"){
                textView.setText("");
            }
            isMinus = false;
        }
    }

    public void addComa(View view) {
        if(textView.getText().toString().equals("")){
            textView.setText("0"+".");
        }else{
            textView.setText(textView.getText() + ".");
        }
        coma.setEnabled(false);
    }

    public void equal(View view){



            if (firstNumber == null) {
               showToast();
            }else if(firstNumber.equals("")){
                showToast();
            }
            else {
                if(temp == null) {
                    temp = secondNumber;
                }else if(temp.equals("")){
                    temp = secondNumber;
                }
                secondNumber = textView.getText().toString();

                if (currentOperation == "sum") {

                    if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                        if(operationFlag == false && !temp.isEmpty()){
                            equal = Double.parseDouble(secondNumber) + Double.parseDouble(temp);
                        }else {
                            equal = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
                        }
                    } else {
                        equal += Double.parseDouble(firstNumber);
                        //equal = Double.parseDouble(firstNumber) + Double.parseDouble(firstNumber);
                    }

                    coma.setEnabled(false);
                    textView.setText(Double.toString(equal));

                } else if (currentOperation == "multiplication") {

                    if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                        if(operationFlag == false && !temp.isEmpty()) {
                            equal = Double.parseDouble(secondNumber) * Double.parseDouble(temp);
                        }else {
                            equal = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                        }
                    } else {
                        if(equal == 0){
                            equal = Double.parseDouble(firstNumber);
                        }

                    }

                    coma.setEnabled(false);
                    textView.setText(Double.toString(equal));

                } else if (currentOperation == "subtraction") {

                    if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                        if(operationFlag== false && !temp.isEmpty()) {
                            equal = Double.parseDouble(secondNumber) - Math.abs(Double.parseDouble(temp));
                        }else {
                            equal = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                        }
                    } else {

                        equal +=  Math.abs(Double.parseDouble(firstNumber));
                    }
                    coma.setEnabled(false);
                    textView.setText(Double.toString(equal));


                } else if (currentOperation == "division") {
                    if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                        if(operationFlag == false && !temp.isEmpty()) {
                            equal = Double.parseDouble(secondNumber) / Double.parseDouble(temp);
                            coma.setEnabled(false);
                            textView.setText(Double.toString(equal));
                        }else {
                            if(Double.parseDouble(secondNumber) != 0) {
                                equal = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                                coma.setEnabled(false);
                                textView.setText(Double.toString(equal));
                            }else {
                                Context context = getApplicationContext();
                                CharSequence text = "You can't divide by zero!";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }

                    } else {
                        if(equal ==0){
                            equal = Double.parseDouble(firstNumber);
                        }
                        //equal = Double.parseDouble(firstNumber) / Double.parseDouble(firstNumber);
                        coma.setEnabled(false);
                        textView.setText(Double.toString(equal));
                    }
                }
            }
            if (equal < 0) {
                isMinus = true;
            }
            operationFlag = false;

    }

    //operations

    public void sum(View view){
        coma.setEnabled(true);
        if(operationFlag == true) {
            currentOperation = "sum";
            textView.setText("");
        }else {
            isMinus = false;
            operationFlag = true;
            currentOperation = "sum";
            firstNumber = textView.getText().toString();
            textView.setText("");
        }
    }

    public void division(View view){
        coma.setEnabled(true);
        if(operationFlag == true) {
            currentOperation="division";
            textView.setText("");
        }else {
            isMinus = false;
            operationFlag = true;
            currentOperation = "division";
            firstNumber = textView.getText().toString();
            textView.setText("");
        }
    }

    public void multiplication(View view){
        coma.setEnabled(true);
        if(operationFlag == true) {
            currentOperation="multiplication";
            textView.setText("");
        }else {
            isMinus = false;
            operationFlag = true;
            currentOperation = "multiplication";
            firstNumber = textView.getText().toString();
            textView.setText("");
        }
    }

    public void subtraction(View view){
        coma.setEnabled(true);
        if(operationFlag == true){
            currentOperation = "subtraction";
            textView.setText("");
        }else {
            isMinus = false;
            operationFlag = true;
            currentOperation = "subtraction";
            firstNumber = textView.getText().toString();
            textView.setText("");
        }
    }


    //numbers
    public void addNumber_0(View view){
        textView.setText(textView.getText()+"0");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_1(View view){
        textView.setText(textView.getText()+"1");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_2(View view){
        textView.setText(textView.getText()+"2");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_3(View view){
        textView.setText(textView.getText()+"3");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_4(View view){
        textView.setText(textView.getText()+"4");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }
    public void addNumber_5(View view){
        textView.setText(textView.getText()+"5");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_6(View view){
        textView.setText(textView.getText()+"6");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_7(View view){
        textView.setText(textView.getText()+"7");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_8(View view){
        textView.setText(textView.getText()+"8");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void addNumber_9(View view){
        textView.setText(textView.getText()+"9");
        if(oneComaFlag == true){
        }else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }

    public void showToast() {
        Context context = getApplicationContext();
        CharSequence text = "You didn't give the other argument!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
