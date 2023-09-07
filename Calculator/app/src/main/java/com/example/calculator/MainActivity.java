package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private StringBuilder currentInput;
    private double operand1;
    private String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        currentInput = new StringBuilder();
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        currentInput.append(buttonText);
        editText.setText(currentInput.toString());
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if (currentInput.length() > 0) {
            operand1 = Double.parseDouble(currentInput.toString());
            operator = buttonText;
            currentInput.setLength(0);
        }
    }

    public void onClearClick(View view) {
        currentInput.setLength(0);
        editText.setText("");
    }

    public void onEqualsClick(View view) {
        if (currentInput.length() > 0 && operator != null) {
            double operand2 = Double.parseDouble(currentInput.toString());
            double result = performOperation(operand1, operand2, operator);
            editText.setText(String.valueOf(result));
            currentInput.setLength(0);
            currentInput.append(result);
            operator = null;
        }
    }

    private double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    return Double.NaN; // Handle division by zero
                }
            default:
                return operand2;
        }
    }
}
