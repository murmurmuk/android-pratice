package murmur.circletextview;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private EditText et;
    private int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircleTextView ct = (CircleTextView)findViewById(R.id.test);
        RadioGroup rg = (RadioGroup)findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.blue){
                    mColor = Color.BLUE;
                    ct.setmState(mColor, et.getText().toString());
                }
                else{
                    mColor = Color.RED;
                    ct.setmState(mColor, et.getText().toString());
                }
            }
        });
        et = (EditText)findViewById(R.id.edit);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ct.setmState(mColor, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
