package murmur.pagetransition;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import murmur.pagetransition.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainBinding.pagetmp.startTransition(mainBinding.page1, mainBinding.page3, true, 10);
            }
        });

        mainBinding.prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mainBinding.pagetmp.startTransition(mainBinding.page3, mainBinding.page1, false, 10);
            }
        });
        mainBinding.tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("kanna", "touch");
            }
        });

    }
}
