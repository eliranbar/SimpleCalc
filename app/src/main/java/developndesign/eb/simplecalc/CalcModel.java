package developndesign.eb.simplecalc;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by eliran on 19/05/2017.
 */

public class CalcModel {
    private static Double lastOperand = 0.0;
    private static Double currenOperand = 0.0;
    private static String lastOperation = "";
    public static void UpdateLastOperand(Double num){
        lastOperand = num;
    }
    public static void UpdateCurrenOperand(Double num){
        currenOperand = num;
    }
    public static void UpdateLastOperation(String operation){
        if(operation.indexOf("=")<0) {
            lastOperation = operation;
        }
    }
    public static CalcResult Calculate(){
        CalcResult calcResult = new CalcResult();
        try {
            calcResult.success = true;
            Log.i("CalcModel", "lastOperand: " + lastOperand
                    + ", last operation: " + lastOperation
                    + "currenOperand: " + currenOperand
            );
            switch (lastOperation) {
                case "+": {
                    lastOperand = lastOperand + currenOperand;
                    break;
                }
                case "-": {
                    lastOperand = lastOperand - currenOperand;
                    break;
                }
                case "X": {
                    Log.i("CalcModel", "mult");
                    lastOperand = lastOperand * currenOperand;
                    break;
                }
                case "/": {
                    if (currenOperand != 0.0) {
                        lastOperand = lastOperand / currenOperand;
                    } else {
                        calcResult.success = false;
                        calcResult.error = "Cannot divide by zero";
                    }
                    break;
                }
                case "EXP": {
                    lastOperand = Math.exp(currenOperand);
                    break;
                }
                case "cos": {
                    lastOperand = Math.cos(currenOperand);
                    break;
                }
                case "sin": {
                    lastOperand = Math.sin(currenOperand);
                    break;
                }
                case "tan": {
                    lastOperand = Math.tan(currenOperand);
                    break;
                }
                case "√": {
                    lastOperand = Math.sqrt(currenOperand);
                    break;
                }
                case "X²": {
                    lastOperand = Math.pow(currenOperand, 2);
                    break;
                }


            }
            calcResult.result = lastOperand;
            Log.i("CalcModel", "result: " + lastOperand);
        }
        catch (Exception e){
            Log.e("CalcModel", "exeption on CalcResult(). Exeption: " + e.getMessage() );
        }
        return calcResult;
    }
    public static CalcResult CalculateEqualResult(){
        CalcResult calcResult = new CalcResult();
        try {
            if (lastOperation != "=")
                return Calculate();
            else {

                calcResult.result = lastOperand;
                calcResult.success = true;

            }
        }
        catch (Exception e){
            Log.e("CalcModel", "exeption on Calculate(). Exeption: " + e.getMessage() );
        }
        return calcResult;
    }

}
