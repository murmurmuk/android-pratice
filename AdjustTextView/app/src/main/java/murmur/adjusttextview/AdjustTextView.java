package murmur.adjusttextview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by murmurmuk on 2017/5/27.
 */

public class AdjustTextView extends TextView {

    public AdjustTextView(Context context) {
        super(context);
    }

    public AdjustTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getWidth() != 0 && getHeight() != 0) {
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            if (layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT
                    && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                Paint.FontMetrics fontMetrics = this.getPaint().getFontMetrics();
                float fontHeight = Math.abs(fontMetrics.leading) + Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);
                int maxLine = (int)Math.floor(getHeight()/fontHeight);
                this.setMaxLines(maxLine);
                invalidate();
            }

        }
    }
}
