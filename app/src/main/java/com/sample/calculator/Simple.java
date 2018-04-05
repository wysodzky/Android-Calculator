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
    TextView textView;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("firstNumber", firstNumber);
        outState.putString("currentOperation", currentOperation);
        outState.putString("temp", temp);
        outState.putString("secondNumber", secondNumber);
        outState.putBoolean("operationFlag", operationFlag);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            firstNumber = savedInstanceState.getString("firstNumber");
            currentOperation = savedInstanceState.getString("currentOperation");
            temp = savedInstanceState.getString("temp");
            secondNumber = savedInstanceState.getString("secondNumber");
            operationFlag = savedInstanceState.getBoolean("operationFlag");
        }

        setContentView(R.layout.simple);


        textView = (TextView) findViewById(R.id.textView);
        coma = (Button) findViewById(R.id.coma);
        textView.setText("");
        temp = "";
        coma.setEnabled(false);
    }


    public void backspace(View view) {
        String text = textView.getText().toString();
        if (text != "" && text != null && text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            textView.setText(text);
        }
        comaCheck();
    }

    public void clear(View view) {
        coma.setEnabled(true);
        textView.setText("");
        firstNumber = "";
        secondNumber = "";
        temp = "";
        isMinus = false;
        equal = 0;
    }

    public void symbolChange(View view) {
        if (isMinus == false) {
            textView.setText("-" + textView.getText());
            isMinus = true;
        } else if (isMinus == true) {
            String text = textView.getText().toString();
            if (text != "" && text != null && text.length() > 0 || text.substring(0, 0) == "-") {
                text = text.substring(1, text.length());
                textView.setText(text);

            } else if (text == "0") {
                textView.setText("");
            }
            isMinus = false;
        }
    }

    public void addComa(View view) {
        if (textView.getText().toString().equals("")) {
            textView.setText("0" + ".");
        } else {
            textView.setText(textView.getText() + ".");
        }
        coma.setEnabled(false);
    }

    public void equal(View view) {


        if (firstNumber == null) {
            showToast();
        } else if (firstNumber.equals("")) {
            showToast();
        } else {
            if (temp == null) {
                temp = secondNumber;
            } else if (temp.equals("")) {
                temp = secondNumber;
            }
            secondNumber = textView.getText().toString();

            if (currentOperation == "sum") {

                if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                    if (operationFlag == false && !temp.isEmpty()) {
                        equal = Double.parseDouble(secondNumber) + Double.parseDouble(temp);
                    } else {
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
                    if (operationFlag == false && !temp.isEmpty()) {
                        equal = Double.parseDouble(secondNumber) * Double.parseDouble(temp);
                    } else {
                        equal = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
                    }
                } else {
                    if (equal == 0) {
                        equal = Double.parseDouble(firstNumber);
                    }

                }

                coma.setEnabled(false);
                textView.setText(Double.toString(equal));

            } else if (currentOperation == "subtraction") {

                if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                    if (operationFlag == false && !temp.isEmpty()) {
                        equal = Double.parseDouble(secondNumber) - Math.abs(Double.parseDouble(temp));
                    } else {
                        equal = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
                    }
                } else {

                    equal += Math.abs(Double.parseDouble(firstNumber));
                }
                coma.setEnabled(false);
                textView.setText(Double.toString(equal));


            } else if (currentOperation == "division") {
                if (!secondNumber.equals("") && !secondNumber.isEmpty()) {
                    if (operationFlag == false && !temp.isEmpty()) {
                        equal = Double.parseDouble(secondNumber) / Double.parseDouble(temp);
                        coma.setEnabled(false);
                        textView.setText(Double.toString(equal));
                    } else {
                        if (Double.parseDouble(secondNumber) != 0) {
                            equal = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                            coma.setEnabled(false);
                            textView.setText(Double.toString(equal));
                        } else {
                            showToastForZero();
                        }
                    }

                } else {
                    if (equal == 0) {
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


    public void threeArguments(String currentOperation) {
        if (firstNumber == null) {
            firstNumber = "0";
        } else if (firstNumber.isEmpty()) {
            firstNumber = "0";
        }
        double fNumber = Double.parseDouble(firstNumber);

        switch (currentOperation) {

            case "sum":
                fNumber += Double.parseDouble(textView.getText().toString());
                break;
            case "multiplication":
                fNumber *= Double.parseDouble(textView.getText().toString());
                break;
            case "subtraction":
                fNumber -= Double.parseDouble(textView.getText().toString());
                break;
            case "division":
                if (Double.parseDouble(textView.getText().toString()) == 0) {
                    showToastForZero();
                } else {
                    fNumber /= Double.parseDouble(textView.getText().toString());
                }
                break;
        }

        firstNumber = String.valueOf(fNumber);
    }


    public void setOperation(String currentOperation) {
        coma.setEnabled(true);
        if (operationFlag == true) {
            this.currentOperation = currentOperation;
            textView.setText("");
        } else {
            isMinus = false;
            operationFlag = true;
            this.currentOperation = currentOperation;
            firstNumber = textView.getText().toString();
            textView.setText("");
        }
    }


    public void sum(View view) {
        if (currentOperation != null) {
            threeArguments(currentOperation);
        }
        setOperation("sum");
    }

    public void division(View view) {
        if (currentOperation != null) {
            threeArguments(currentOperation);
        }
        setOperation("division");
    }

    public void multiplication(View view) {
        if (currentOperation != null) {
            threeArguments(currentOperation);
        }
        setOperation("multiplication");
    }

    public void subtraction(View view) {
        if (currentOperation != null) {
            threeArguments(currentOperation);
        }
        setOperation("subtraction");
    }


    public void comaCheck() {
        String checkText = textView.getText().toString();
        if (checkText.indexOf('.') > -1) {
            coma.setEnabled(false);
        } else {
            coma.setEnabled(true);
        }
    }


    public void setNumber(String number) {
        textView.setText(textView.getText() + number);
        if (oneComaFlag == true) {
        } else {
            coma.setEnabled(true);
            oneComaFlag = true;
        }
    }


    //numbers
    public void addNumber_0(View view) {
        setNumber("0");
    }

    public void addNumber_1(View view) {
        setNumber("1");
    }

    public void addNumber_2(View view) {
        setNumber("2");
    }

    public void addNumber_3(View view) {
        setNumber("3");
    }

    public void addNumber_4(View view) {
        setNumber("4");
    }

    public void addNumber_5(View view) {
        setNumber("5");
    }

    public void addNumber_6(View view) {
        setNumber("6");
    }

    public void addNumber_7(View view) {
        setNumber("7");
    }

    public void addNumber_8(View view) {
        setNumber("8");
    }

    public void addNumber_9(View view) {
        setNumber("9");
    }

    public void showToast() {
        Context context = getApplicationContext();
        CharSequence text = "You didn't give the other argument!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void showToastForZero() {
        Context context = getApplicationContext();
        CharSequence text = "You can't divide by zero!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
