package murmur.cubeout;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import murmur.cubeout.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;
    private Handler mHandler;
    private Runnable mTurnRunnable;
    private View mLeft, mRight;
    private boolean mTurnDir;
    private boolean mClock;

    private void handleCube(float place){
        final float DEGREE = 30f;
        int width, pivotY;
        if(mTurnDir){
            width = mLeft.getWidth();
            pivotY = mLeft.getHeight() >> 1;
        }
        else{
            width = mRight.getWidth();
            pivotY = mRight.getHeight() >> 1;
        }
        mRight.setTranslationX(width * place);
        mRight.setPivotX(0);
        mRight.setPivotY(pivotY);
        mRight.setRotationY(DEGREE * place);
        if(mRight.getVisibility() != View.VISIBLE) {
            mRight.setVisibility(View.VISIBLE);
        }

        mLeft.setTranslationX(width * (place - 1));
        mLeft.setPivotX(width);
        mLeft.setPivotY(pivotY);
        mLeft.setRotationY(DEGREE * (place -1));
        if(mLeft.getVisibility() != View.VISIBLE) {
            mLeft.setVisibility(View.VISIBLE);
        }
        if(mTurnDir){
            if(place < 0.001f){
                mLeft.setVisibility(View.GONE);
                mLeft = mRight = null;
            }
        }
        else{
            if(place > 0.999f){
                mRight.setVisibility(View.GONE);
                mLeft = mRight = null;
            }
        }
    }


    private void handleTurn(View left, View right, boolean dir){
        if(mLeft == left && mRight == right){
            return;
        }
        if(mLeft != null && mRight != null){
            mHandler.removeCallbacks(mTurnRunnable);
            if(mTurnDir){
                mRight.bringToFront();
                mRight.setRotationY(0);
                mRight.setTranslationX(0);
                mLeft.setVisibility(View.GONE);
            }
            else{
                mLeft.bringToFront();
                mLeft.setRotationY(0);
                mLeft.setTranslationX(0);
                mRight.setVisibility(View.GONE);
            }
        }

        mLeft = left;
        mRight = right;
        mTurnDir = dir;
        if(dir){
            mTurnRunnable = new Runnable() {
                float place = 1f;
                @Override
                public void run() {
                    place -= 0.1f;
                    handleCube(place);
                    if(!(place < 0)) {
                        mHandler.postDelayed(this, 50);
                    }
                }
            };
            mTurnRunnable.run();

        }
        else{
            mTurnRunnable = new Runnable() {
                float place = 0f;
                @Override
                public void run() {
                    place += 0.1f;
                    handleCube(place);
                    if(place < 1) {
                        mHandler.postDelayed(this, 50);
                    }
                }
            };
            mTurnRunnable.run();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mHandler = new Handler();

        mActivityMainBinding.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClock = false;
            }
        });

        mActivityMainBinding.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClock = true;
            }
        });



        mActivityMainBinding.page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClock){
                    handleTurn(mActivityMainBinding.page3, mActivityMainBinding.page1, false);
                }
                else {
                    handleTurn(mActivityMainBinding.page1, mActivityMainBinding.page2, true);
                }
            }
        });
        mActivityMainBinding.page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClock){
                    handleTurn(mActivityMainBinding.page1, mActivityMainBinding.page2, false);
                }
                else {
                    handleTurn(mActivityMainBinding.page2, mActivityMainBinding.page3, true);
                }            }
        });
        mActivityMainBinding.page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClock){
                    handleTurn(mActivityMainBinding.page2, mActivityMainBinding.page3, false);
                }
                else {
                    handleTurn(mActivityMainBinding.page3, mActivityMainBinding.page1, true);
                }            }
        });

    }
}
