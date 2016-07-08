package com.proschoolonline.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.proschoolonline.mob.R;

import java.util.Hashtable;


public class FontHelper {

    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.customFont);
            String font = a.getString(R.styleable.customFont_fontName);
            setCustomFont(textview, font, context);
            a.recycle();
        }
    }

    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
            textview.setPaintFlags(textview.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    static class FontCache {
        private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

        public static Typeface get(String name, Context context) {
            Typeface tf = fontCache.get(name);
            if(tf == null) {
                try {
                    tf = Typeface.createFromAsset(context.getAssets(), name);
                   // LTLogger.i("FontCache", "---------create fontname :" + name);
                }
                catch (Exception e) {
                    return null;
                }
                if (tf != null) {
                    fontCache.put(name, tf);
                }
            }
            return tf;
        }
    }
}
