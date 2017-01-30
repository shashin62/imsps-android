package com.proschoolonline.components;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class AppEditText extends EditText {

    public AppEditText(Context context) {
        super(context);
        init(null,context);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }

    private void init(AttributeSet attrs, Context context) {
        //this.setSingleLine(true);
        this.setEllipsize(TextUtils.TruncateAt.END);
        this.setSingleLine();
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
