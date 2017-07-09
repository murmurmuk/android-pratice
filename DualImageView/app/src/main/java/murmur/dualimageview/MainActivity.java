package murmur.dualimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton ib = (ImageButton)findViewById(R.id.button);
        ib.setOnClickListener(new View.OnClickListener() {
            private boolean flag = true;
            @Override
            public void onClick(View v) {
                DualImageView dl = (DualImageView)findViewById(R.id.test);
                dl.changeType(flag);
                flag = !flag;
            }
        });
    }
}
