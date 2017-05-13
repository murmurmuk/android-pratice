package murmur.circletextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by murmurmuk on 2017/5/13.
 */

public class CircleTextView extends View {
    private Paint mPaint;
    private TextPaint mTextPaint;
    private int mColor = Color.BLUE;
    private String mString = "K";
    private float mTextOffsetY;
    private int mRadius;

    public CircleTextView(Context context) {
        super(context);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        if(getWidth() == 0 || getHeight() == 0){
            return;
        }

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        if(getWidth() != 0 && getHeight() != 0){
            initPaint();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(getWidth() == 0 || getHeight() == 0){
            return;
        }
        int baseX = getWidth() / 2;
        int baseY = getHeight() / 2;
        if(baseX > baseY){
            mRadius = baseY;
        }
        else{
            mRadius = baseX;
        }
        int tSize = (mRadius * 3) >> 1;
        mTextPaint.setTextSize(tSize);
        mTextOffsetY = (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        canvas.drawCircle(baseX, baseY, mRadius, mPaint);
        canvas.drawText(mString, baseX, baseY - mTextOffsetY, mTextPaint);
    }
    public void setmState(int color, String string){
        mColor = color;
        if(string.length() > 1){
            mString = string.substring(0, 1);
        }
        else{
            mString = string;
        }
        mString = mString.toUpperCase();
        initPaint();
    }



}
