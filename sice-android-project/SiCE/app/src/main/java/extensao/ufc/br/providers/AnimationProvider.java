package extensao.ufc.br.providers;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by alan on 11/22/15.
 */
public class AnimationProvider {

    private static final int DEFAULT_DURATION = 500;

    public static Animation doInAnimation(final View view){
        String a = "";
        Animation anim = new AlphaAnimation(0, 1);
        view.setVisibility(View.VISIBLE);
        anim.setDuration(DEFAULT_DURATION);
        anim.setFillAfter(true);

        view.startAnimation(anim);

        return anim;
    }

    public static Animation doOutAnimation(final View view){
        Animation anim = new AlphaAnimation(1, 0);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(DEFAULT_DURATION);
        anim.setFillAfter(true);

        view.startAnimation(anim);
        return anim;
    }

}
