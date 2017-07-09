package murmur.dualimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class DualImageView extends ImageView {
    private int mAngelres;
    private int mEvilres;
    public DualImageView(Context context) {
        super(context);
    }

    public DualImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DualImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DualImageView, 0, 0);
        mAngelres = a.getResourceId(R.styleable.DualImageView_Dual_Angel, -1);
        mEvilres = a.getResourceId(R.styleable.DualImageView_Dual_Evil, -1);
        a.recycle();
    }
    public void changeType(boolean flag){
        if(flag){
            this.setImageResource(mEvilres);
        }
        else{
            this.setImageResource(mAngelres);
        }
    }

}
