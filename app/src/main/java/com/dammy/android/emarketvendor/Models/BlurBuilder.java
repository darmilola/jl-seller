package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

public class BlurBuilder {

    private static final float BITMAP_SCALE = 0.6f;
    private static final float BLUR_RADIUS = 10.0f;

    public static Bitmap blur(Context context, Bitmap bitmap){
       // int width = Math.round(bitmap.getWidth() *BITMAP_SCALE);
        //int height = Math.round(bitmap.getHeight() * BITMAP_SCALE);

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation tmpin = Allocation.createFromBitmap(renderScript,bitmap);
        Allocation tmpout = Allocation.createFromBitmap(renderScript,outBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpin);
        theIntrinsic.forEach(tmpout);
        tmpout.copyTo(outBitmap);
        bitmap.recycle();
        renderScript.destroy();
        return outBitmap;
    }
}
