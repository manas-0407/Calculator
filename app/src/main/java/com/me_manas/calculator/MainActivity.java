package com.me_manas.calculator;

/*
By Manas
This Projects LinearLayout in XML file, in case of poor performance due to nested weight usage,
alternative RelativeLayout code can be use from->
Link :   https://drive.google.com/file/d/1ydUstfetXRNWmrLNSoYKsdr_gHKwwELE/view?usp=sharing
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result,solution;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton clear,open,close,div,mult,add,sub,equls,dot,allclear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resultTV);
        solution=findViewById(R.id.solutionTV);
        result.setText("0");
        solution.setText("0");
        assignId(button0,R.id.zerobutton);
        assignId(button1,R.id.onebutton);
        assignId(button2,R.id.twobutton);
        assignId(button3,R.id.threebutton);
        assignId(button4,R.id.fourbutton);
        assignId(button5,R.id.fivebutton);
        assignId(button6,R.id.sixbutton);
        assignId(button7,R.id.sevenbutton);
        assignId(button8,R.id.eightbutton);
        assignId(button9,R.id.ninebutton);
        assignId(clear,R.id.clearbutton);
        assignId(open,R.id.openbutton);
        assignId(close,R.id.closebutton);
        assignId(div,R.id.dividebutton);
        assignId(mult,R.id.multbutton);
        assignId(add,R.id.plusbutton);
        assignId(sub,R.id.minusbutton);
        assignId(equls,R.id.equalsbutton);
        assignId(dot,R.id.dotbutton);
        assignId(allclear,R.id.acbutton);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tag St","manas started app");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("Tag Pause","manas Paused app");

    }
*/
    // Activity Lifecycle Methods to just test my app

    @Override
    public void onClick(View view) {

        MaterialButton btn= (MaterialButton) view;
        String btntext = btn.getText().toString();
        String datatocal = solution.getText().toString();
        if(btntext.equals("AC")){
            solution.setText("0");
            result.setText("0");
            return;
        }
        if(btntext.equals("=")){
            solution.setText(result.getText());
            return;
        }
        if(btntext.equals("C")){
            if(datatocal.length()>1)datatocal = datatocal.substring(0,datatocal.length()-1);
            else datatocal="0";
        }

        else {
            if(datatocal.length()==1 && (datatocal.charAt(0)=='0'||isOperator(datatocal.charAt(0)))) datatocal="";
            else if(isOperator(datatocal.charAt(datatocal.length()-1)) && isOperator(btntext.charAt(0))) datatocal = datatocal.substring(0,datatocal.length()-1);

            datatocal = datatocal.concat(btntext);
        }
        solution.setText(datatocal);
        String ans= getRes(datatocal);

        if(!ans.equals("Err")){
            int z=ans.indexOf(".");
            if(z!=-1 && ans.length()>z+5) ans=ans.substring(0,z+5);
            result.setText(ans);
        }
    }

    boolean isOperator(char c){
        return c=='*'||c=='/'||c=='+'||c=='-';
    }

    String getRes(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e){
            return "Err";
        }
    }
}