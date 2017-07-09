package murmur.pagetransition;


import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

public class PageTransition extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mPageNum;
    private int mDiffLeft = 15;
    private int mDiffBottom = 10;
    private int mFakeX;
    private View mTop;
    private View mBottom;

    private Bitmap mFakeView;
    private BeforeRemove mBeforeRemove;
    private AfterPutBack mAfterPutBack;
    private boolean isFinished = true;
    private boolean mState;

    private class BeforeRemove implements Runnable{
        int num;
        int now;
        public void start(int pagenum){
            setVisibility(VISIBLE);
            num = pagenum;
            now = 1;
            run();
        }

        @Override
        public void run() {
            if(now <= num){
                setPageNum(now);
                now++;
                postDelayed(this, 50);
            }
            else{
                mTop.setVisibility(GONE);
                removeAnimation();
            }
        }
    }

    private class AfterPutBack implements Runnable{
        int num;
        public void start(int pagenum){
            num = pagenum - 1;
            run();
        }

        @Override
        public void run() {
            if(num > 0){
                setPageNum(num);
                num--;
                postDelayed(this, 50);
            }
            else{
                mTop.setVisibility(View.VISIBLE);
                setVisibility(GONE);
                removeFakeView();
            }
        }
    }



    public PageTransition(Context context) {
        this(context, null);
    }

    public PageTransition(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageTransition(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        mFakeX = metrics.widthPixels + 20;
    }

    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh){
        mHeight = h;
        mWidth = w + mDiffBottom;
        initPaint();
    }
    private void putBackAnimation(final int pagenum){
        setTranslationX(mFakeX);
        setRotation(10);
        setVisibility(VISIBLE);
        animate().setDuration(500);
        animate().translationX(0);
        animate().rotation(0);
        animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animate().setListener(null);
                mBottom.setVisibility(GONE);
                if(mAfterPutBack == null){
                    mAfterPutBack = new AfterPutBack();
                }
                mAfterPutBack.start(pagenum);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    private void removeAnimation(){
        mBottom.setVisibility(VISIBLE);
        setTranslationX(0);
        setRotation(0);
        animate().setDuration(500);
        animate().translationX(mFakeX);
        animate().rotation(10);
        animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(GONE);
                animate().setListener(null);
                removeFakeView();
                setTranslationX(0);
                setRotation(0);
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    private void removeFakeView(){
        if(mFakeView != null){
            mFakeView.recycle();
            mFakeView = null;
        }
        if(mBottom != null){
            mBottom = null;
        }
        if(mTop != null){
            mTop = null;
        }
        isFinished = true;
    }

    private void updateFakeView(){
        mFakeView = Bitmap.createBitmap(mTop.getWidth(), mTop.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mFakeView);
        mTop.draw(canvas);
    }


    private int checkPageNum(int pagenum){
        if(pagenum > 5){
            return 5;
        }
        else if(pagenum < 1){
            return 1;
        }
        return pagenum;
    }

    private void setPageNum(int num){
        mPageNum = num;
        invalidate();
    }

    private void handleRude(){
        animate().setListener(null);
        animate().cancel();
        if(mAfterPutBack != null){
            removeCallbacks(mAfterPutBack);
        }
        if(mBeforeRemove != null){
            removeCallbacks(mBeforeRemove);
        }
        if(getVisibility() == VISIBLE){
            setVisibility(GONE);
        }
        if(mState){
            mBottom.setVisibility(VISIBLE);
            mTop.setVisibility(GONE);
        }
        else{
            mTop.setVisibility(VISIBLE);
            mBottom.setVisibility(GONE);
        }
        removeFakeView();
    }


    /**
     *
     * @param top
     * @param bottom
     * @param state true: remove, false: put back
     * @param pagenum
     */
    public void startTransition(View top, View bottom, boolean state, final int pagenum){

        if(!isFinished){
            //Log.d("kanna","not finish need handle");
            handleRude();
        }
        isFinished = false;
        mState = state;
        bringToFront();
        mTop = top;
        mBottom = bottom;
        if(state){
            updateFakeView();
            if(mBeforeRemove == null){
                mBeforeRemove = new BeforeRemove();
            }
            mBeforeRemove.start(checkPageNum(pagenum));
        }
        else{
            if(mTop.getHeight() == 0){
                mBottom.bringToFront();
                mTop.setVisibility(VISIBLE);
                setVisibility(VISIBLE);
                mTop.getViewTreeObserver().addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener(){

                            @Override
                            public void onGlobalLayout() {
                                // gets called after layout has been done but before display
                                // so we can get the height then hide the view
                                mTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                mTop.setVisibility(GONE);
                                bringToFront();
                                updateFakeView();
                                setPageNum(checkPageNum(pagenum));
                                putBackAnimation(checkPageNum(pagenum));
                            }

                        });
            }
            else {
                updateFakeView();
                setPageNum(checkPageNum(pagenum));
                putBackAnimation(checkPageNum(pagenum));
            }
        }
    }



    protected void onDraw(Canvas canvas){

        int i;
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < mPageNum; i++) {
            canvas.drawRect(mDiffLeft * i, -mDiffBottom, mWidth, mHeight - mDiffBottom * i, mPaint);
        }
        i--;
        canvas.save();
        canvas.translate(mDiffLeft * i + 2, -mDiffBottom * i - 2);
        canvas.drawBitmap(mFakeView, 0, 0, null);
        canvas.restore();
    }

}
