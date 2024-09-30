package com.example.uicalc;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;  // Import for Button
import com.example.uicalc.databinding.ActivityMainBinding;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;
    private boolean operatorSet = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#.########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());

        // Set up number and operator listeners
        setNumberButtonListeners();
        setOperatorButtonListeners();
    }

    private void setNumberButtonListeners() {
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;  // Cast view to Button
                currentInput += button.getText().toString();
                binding.input.setText(currentInput);
            }
        };

        binding.btn0.setOnClickListener(numberClickListener);
        binding.btn1.setOnClickListener(numberClickListener);
        binding.btn2.setOnClickListener(numberClickListener);
        binding.btn3.setOnClickListener(numberClickListener);
        binding.btn4.setOnClickListener(numberClickListener);
        binding.btn5.setOnClickListener(numberClickListener);
        binding.btn6.setOnClickListener(numberClickListener);
        binding.btn7.setOnClickListener(numberClickListener);
        binding.btn8.setOnClickListener(numberClickListener);
        binding.btn9.setOnClickListener(numberClickListener);

        binding.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    binding.input.setText(currentInput);
                }
            }
        });
    }

    private void setOperatorButtonListeners() {
        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;  // Cast view to Button
                if (!operatorSet && !currentInput.isEmpty()) {
                    firstValue = Double.parseDouble(currentInput);
                    operator = button.getText().toString();
                    operatorSet = true;
                    currentInput = "";
                    binding.input.setText("");
                }
            }
        };

        binding.btnPlus.setOnClickListener(operatorClickListener);
        binding.btnSubtract.setOnClickListener(operatorClickListener);
        binding.btnMultiply.setOnClickListener(operatorClickListener);
        binding.btnDivide.setOnClickListener(operatorClickListener);

        binding.btnPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput) / 100;
                    currentInput = decimalFormat.format(value);
                    binding.input.setText(currentInput);
                }
            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                firstValue = 0;
                operatorSet = false;
                binding.input.setText("");
                binding.output.setText("");
            }
        });

        binding.btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    binding.input.setText(currentInput);
                }
            }
        });

        binding.btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatorSet && !currentInput.isEmpty()) {
                    double secondValue = Double.parseDouble(currentInput);
                    double result = 0;

                    switch (operator) {
                        case "+":
                            result = firstValue + secondValue;
                            break;
                        case "-":
                            result = firstValue - secondValue;
                            break;
                        case "*":
                            result = firstValue * secondValue;
                            break;
                        case "/":
                            if (secondValue != 0) {
                                result = firstValue / secondValue;
                            } else {
                                binding.output.setText("Error");
                                return;
                            }
                            break;
                    }

                    currentInput = decimalFormat.format(result);
                    binding.output.setText(currentInput);
                    operatorSet = false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
