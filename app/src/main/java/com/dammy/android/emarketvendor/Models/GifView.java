package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

public class GifView extends View {
    private Movie movie;
    private long moviestart;
    public GifView(Context context) throws IOException {
        super(context);
        movie = Movie.decodeStream(getResources().getAssets().open("loading.gif"));
    }
    public GifView(Context context, AttributeSet attributeSet) throws IOException {
        super(context,attributeSet);
        movie = Movie.decodeStream(getResources().getAssets().open("loading.gif"));

    }
    public GifView(Context context,AttributeSet attributeSet,int defstyle) throws IOException {
        super(context,attributeSet,defstyle);
        movie = Movie.decodeStream(getResources().getAssets().open("loading.gif"));
    }

    @Override
    protected void onDraw(Canvas canvas){
       super.onDraw(canvas);
       long now = android.os.SystemClock.uptimeMillis();
        Paint p = new Paint();
        p.setAntiAlias(true);
        if(moviestart == 0){
            moviestart = now;
            int realtime;
            realtime = (int)((now-moviestart)%movie.duration());
            movie.setTime(realtime);
            movie.draw(canvas,0,0);
            this.invalidate();
        }

    }
}
