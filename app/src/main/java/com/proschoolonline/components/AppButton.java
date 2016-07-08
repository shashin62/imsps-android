package com.proschoolonline.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class AppButton extends Button {

    public AppButton(Context context) {
        super(context);
        init(null,context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }

    private void init(AttributeSet attrs, Context context) {

        if (attrs != null){
            FontHelper.setCustomFont(this,context,attrs);
        }
//        if (attrs!=null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.customFont);
//            String fontName = a.getString(R.styleable.customFont_fontName);
//            if (fontName!=null) {
//                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
//                setTypeface(myTypeface);
//            }
//            a.recycle();
//        }
    }

}
