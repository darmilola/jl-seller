package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.dammy.android.emarketvendor.R;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

public class Fresco {
    private static Fresco loadImage;
    private SimpleDraweeView imageView;
    private Uri uri;
    private boolean roundAsCircle = false;
    private boolean withprogressbar = true;
    private int PLACE_HOLDER_IMAGE = R.drawable.logincameraiiii;

    public static Fresco newBuilder(@NonNull SimpleDraweeView imageView){
        loadImage = new Fresco(imageView);
        return loadImage;

    }
    private Fresco(@NonNull SimpleDraweeView imageView){
        this.imageView = imageView;
    }

    public Fresco setPlaceHolderImage(@DrawableRes int placeHolderImage){
        this.PLACE_HOLDER_IMAGE = placeHolderImage;
        return loadImage;
}
 public Fresco roundAsCircle(boolean roundAsCircle){
        this.roundAsCircle = roundAsCircle;
        return loadImage;
 }

 public Fresco withProgressBar(boolean withProgress){
        this.withprogressbar = withProgress;
        return loadImage;
 }

public void build(@NonNull Context context){
        if(imageView != null){
            imageView.getHierarchy().setPlaceholderImage(PLACE_HOLDER_IMAGE);
            if(uri != null){
                imageView.setImageURI(uri);
            }
        }
        if(roundAsCircle){
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(6f);
            roundingParams.setRoundAsCircle(true);
            imageView.getHierarchy().setRoundingParams(roundingParams);
        }
        if(withprogressbar){
            imageView.getHierarchy().setProgressBarImage(R.drawable.womanimage);
        }

    }

    public Fresco setUri(Uri uri){
        this.uri = uri;
        return loadImage;
    }
    public static void clearCache(){
        ImagePipeline imagePipeline =  com.facebook.drawee.backends.pipeline.Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
        imagePipeline.clearCaches();
    }
}
