package com.javahelps.arcore_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.javahelps.arcore_project.R;

import java.io.InputStream;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    //Variable
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ModelAnimator animator;
    private int nextAnimation;
    private FloatingActionButton btn_anim;
    private ModelRenderable animationAR;
    private TransformableNode transformableNode;
    private MediaPlayer mediaPlayer1=null;
    private ImageView button_play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_play = findViewById(R.id.start);
        button_play.setOnClickListener( new View.OnClickListener()   {
            @Override
            public void onClick(View view) {

                if(mediaPlayer1 == null || !mediaPlayer1.isPlaying()) {
                    music_start();
                } else {
                    music_stop();
                }
            }
        });

        arFragment = (ArFragment)getSupportFragmentManager()
                .findFragmentById(R.id.ar_fragment);
        //Tap on plane event
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if(animationAR ==null)
                    return;
                //Create the Anchor
                Anchor anchor = hitResult.createAnchor();
                if(anchorNode == null)
                {
                    anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    transformableNode = new TransformableNode(arFragment.getTransformationSystem());

                    transformableNode.getScaleController().setMinScale(0.01f);
                    transformableNode.getScaleController().setMaxScale(1.5f);
                    transformableNode.setParent(anchorNode);
                    transformableNode.setRenderable(animationAR);
                }
            }
        });

        //Add frame update to control state of button
        arFragment.getArSceneView().getScene()
                .addOnUpdateListener(new Scene.OnUpdateListener(){
                    public void onUpdate(FrameTime frameTime){
                        if (anchorNode == null)
                        {
                            if (btn_anim.isEnabled())
                            {
                                btn_anim.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                                btn_anim.setEnabled(false);
                            }
                        }
                        else
                        {
                            if (!btn_anim.isEnabled())
                            {
                                btn_anim.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this,R.color.colorAccent));
                                btn_anim.setEnabled(true);
                            }
                        }
                    }
                });
        btn_anim = (FloatingActionButton)findViewById(R.id.btn_anim);
        btn_anim.setEnabled(false);
        btn_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animator == null || !animator.isRunning())
                {
                    AnimationData data = animationAR.getAnimationData(nextAnimation);
                    nextAnimation = (nextAnimation+1)%animationAR.getAnimationDataCount();
                    animator = new ModelAnimator(data,animationAR);
                    animator.start();
                }
                else{
                    animator.pause();
                }
                if(mediaPlayer1 == null || !mediaPlayer1.isPlaying()) {
                    music_start();
                } else {
                    music_stop();
                }
            }
        });
        setupModel(
        );
    }
    private void music_start() {

        mediaPlayer1 = MediaPlayer.create(getBaseContext(), R.raw.music);
        mediaPlayer1.seekTo(0);
        animator.resume();
        mediaPlayer1.start();
        Drawable myDrawable = getResources().getDrawable(R.drawable.stop);
        button_play.setImageDrawable(myDrawable);
    }
    private void music_stop() {

        if(mediaPlayer1 == null) return;
        mediaPlayer1.stop();
        mediaPlayer1.release();
        mediaPlayer1 = null;
        Drawable myDrawable = getResources().getDrawable(R.drawable.start);
        button_play.setImageDrawable(myDrawable);
    }

//    private void stopAnimation(ValueAnimator valueAnimator){
//        if (valueAnimator.getCurrentPlayTime() <= valueAnimator.getDuration()) {
//            float fraction = valueAnimator.getAnimatedFraction();
//            if (mListener != null) {
//                mListener.onViewAnimationUpdate(mView, valueAnimator, fraction);
//            }
//        }
//    }
//
//    private void startAnimation() {
//        mRotateAntiClockwiseAnimator.start();
//        mRotateAntiClockwiseAnimator.setCurrentPlayTime(mCurrentPlayTime);
//    }
        private void setupModel() {
//        res = getResource();
//        InputStream is = res.openRawResource(R.raw.dancing);
        ModelRenderable.builder()
                .setSource(this,R.raw.dancing)
                .build()
                .thenAccept(renderable -> animationAR = renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    return null;
                });
    }
}