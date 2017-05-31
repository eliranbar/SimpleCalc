package developndesign.eb.simplecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView displayPanel;
    private boolean isInMiddleOfTyping;
    private boolean isFirstOperation;
    private TextView operatorTxt;
    private boolean isLastTypeIsNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        displayPanel = (TextView)findViewById(R.id.displayWindow);
        operatorTxt = (TextView)findViewById(R.id.operatorTxt);
        isInMiddleOfTyping = false;
        isFirstOperation = true;
        isLastTypeIsNumber = true;
    }

    public void AddNumbersToDesplay(View v){
        Button currenButton = (Button)v;
        if(isInMiddleOfTyping) {
            displayPanel.setText(displayPanel.getText() + currenButton.getText().toString());
        }
        else {
            if(currenButton.getText().toString().indexOf(".")>-1){
                displayPanel.setText("0" + currenButton.getText().toString());
            }
            else {
                operatorTxt.setText("");
                displayPanel.setText(currenButton.getText().toString());
            }
        }
        isInMiddleOfTyping = true;
        isLastTypeIsNumber = true;
        CalcModel.UpdateCurrenOperand(Double.valueOf(displayPanel.getText().toString()));
    }
    public void EnterPI(View v){
        try {
            isInMiddleOfTyping = true;
            CalcModel.UpdateCurrenOperand(Math.PI);
            displayPanel.setText(Double.toString(Math.PI));
        }
        catch (Exception e){
            Log.i("MainActivity", "e");
        }

    }
    public void Calculate(View v){
        try {
            Log.i("MainActivity", "start calculate");
            Button currenButton = (Button) v;
            operatorTxt.setText(currenButton.getText());
            CalcResult result = new CalcResult();
            if (!isFirstOperation && isLastTypeIsNumber) {
                result = CalcModel.Calculate();
                if (result.success) {
                    Log.i("MainActivity", "result calc: " + (Math.floor(result.result) - result.result) );
                    if (Math.floor(result.result) - result.result < 0) {
                        Log.i("MainActivity", "inset to double result");
                        displayPanel.setText(result.result.toString());
                    } else {
                        if(result.result.toString().indexOf("E")>-1){
                            displayPanel.setText(result.result.toString());
                        }
                        else {
                            displayPanel.setText(result.result.toString().substring(0, result.result.toString().length() - 2));
                        }
                    }
                } else {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show();
                }
            }

            if (currenButton.getText().toString().indexOf("=") > -1) {

                isFirstOperation = true;
            } else {
                CalcModel.UpdateLastOperation(currenButton.getText().toString());
                isFirstOperation = false;
            }
            isInMiddleOfTyping = false;
            isLastTypeIsNumber = false;
            CalcModel.UpdateLastOperand(Double.valueOf(displayPanel.getText().toString()));
        }
        catch (Exception e) {
            Log.e("MainActivity", "error on Calculate(). exeption: " + e.getMessage());
        }
    }

    public void AllClear(View v){
        isInMiddleOfTyping = false;
        isFirstOperation = true;
        displayPanel.setText("0");
        operatorTxt.setText("");
        CalcModel.UpdateLastOperand(0.0);
        CalcModel.UpdateCurrenOperand(0.0);
    }
    public void DelOneCharFormDesplay(View v){
        operatorTxt.setText("");
        String currentDisplayStr = displayPanel.getText().toString();
        if (currentDisplayStr.length() > 1) {
            String newStr = currentDisplayStr.substring(0, currentDisplayStr.length() - 1);
            displayPanel.setText(newStr);
        } else {
            AllClear(new View(getApplicationContext()));
        }

    }
}
