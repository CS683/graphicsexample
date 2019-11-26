package edu.bu.metcs.graphicsexample;


import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {

    private ImageView animationIV;
    private AnimationDrawable frameAnimation;

    private Handler animationHandler;
    private Button startAnim, endAnim;
    private ImageButton addAnim;
    private TransitionDrawable transitionDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_animation);

        //set up the animation through xml file
        ImageView image = (ImageView) findViewById (R.id.chatimageview);

        Animation hyperspaceJump = AnimationUtils.loadAnimation (this, R.anim.animateexample);
        image.startAnimation (hyperspaceJump);


        startAnim = findViewById (R.id.startAnim);
        endAnim = findViewById (R.id.endAnim);
        addAnim = findViewById (R.id.addAnim);
        transitionDrawable = (TransitionDrawable) addAnim.getDrawable();

    }



    public void addAnimation(View v) {


        transitionDrawable.startTransition (3000);
        Handler newHandler = new Handler();
        newHandler.postDelayed (new Runnable () {
            @Override
            public void run() {
                transitionDrawable.reverseTransition (3000);
            }
        },3000);

        if (animationIV != null)
            return;

        LinearLayout mLinearLayout = (LinearLayout) findViewById (R.id.animLinearLayout);

        animationIV = new ImageView (this);
        animationIV.setBackgroundResource (R.drawable.animationlist);
        animationIV.setLayoutParams (new LinearLayout.LayoutParams // width / height
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mLinearLayout.addView (animationIV);

        startAnim.setEnabled (true);
        startAnim.setVisibility (View.VISIBLE);

        endAnim.setEnabled (false);
        endAnim.setVisibility (View.VISIBLE);



    }

    public void startAnimation(View v) {
        frameAnimation =
                (AnimationDrawable) animationIV.getBackground ();

        // the animation will start after 1s
        animationHandler = new Handler ();
        animationHandler.postDelayed (new Runnable () {
            @Override
            public void run() {
                frameAnimation.start ();
                startAnim.setEnabled (false);
                endAnim.setEnabled (true);
            }
        },1000);



    }

    public void endAnimation(View v) {

        frameAnimation.stop ();
        startAnim.setEnabled (true);
    }

}
