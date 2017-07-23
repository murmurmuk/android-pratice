package murmur.cubeout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import murmur.cubeout.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;
    private ValueAnimator mTurnAnimator;
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
    }

    private void getAnimator(){
        mTurnAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mTurnAnimator.setInterpolator(new LinearInterpolator());
        mTurnAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if(mTurnDir) {
                    handleCube(1 - mTurnAnimator.getAnimatedFraction());
                }
                else{
                    handleCube(mTurnAnimator.getAnimatedFraction());
                }
            }
        });
        mTurnAnimator.setDuration(500);
        mTurnAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mTurnDir){
                    mRight.setTranslationX(0);
                    mLeft.setVisibility(View.GONE);
                }
                else{
                    mLeft.setTranslationX(0);
                    mRight.setVisibility(View.GONE);
                }
                mLeft = mRight = null;
                mTurnAnimator.setInterpolator(null);
                mTurnAnimator.removeAllUpdateListeners();
                mTurnAnimator.removeAllListeners();
                mTurnAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    private void handleTurn(View left, View right, boolean dir){
        if(mLeft == left && mRight == right){
            return;
        }
        if(mLeft != null && mRight != null){
            if(mTurnAnimator != null) {
                mTurnAnimator.removeAllUpdateListeners();
                mTurnAnimator.removeAllListeners();
                mTurnAnimator.end();
            }
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
        getAnimator();
        mTurnAnimator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
                    handleTurn(mActivityMainBinding.page1, mActivityMainBinding.page2, true);
                }
                else {
                    handleTurn(mActivityMainBinding.page3, mActivityMainBinding.page1, false);
                }
            }
        });
        mActivityMainBinding.page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClock){
                    handleTurn(mActivityMainBinding.page2, mActivityMainBinding.page3, true);
                }
                else {
                    handleTurn(mActivityMainBinding.page1, mActivityMainBinding.page2, false);

                }
            }
        });
        mActivityMainBinding.page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClock){
                    handleTurn(mActivityMainBinding.page3, mActivityMainBinding.page1, true);
                }
                else {
                    handleTurn(mActivityMainBinding.page2, mActivityMainBinding.page3, false);
                }
            }
        });

    }
}
