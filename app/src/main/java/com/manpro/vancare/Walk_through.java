package com.manpro.vancare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Walk_through extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button startButton;
    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        //hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        startButton = findViewById(R.id.start_button);

        //call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void start(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void next(View view){
        viewPager.setCurrentItem(currentPos+1);
    }

    private void addDots(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0 ){
            dots[position].setTextColor(getResources().getColor(R.color.main_colour));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPos = position;

            if(position == 0){
                startButton.setVisibility(View.INVISIBLE);
            }
            else if (position == 1){
                startButton.setVisibility(View.INVISIBLE);
            }
            else {
                animation = AnimationUtils.loadAnimation(Walk_through.this,R.anim.bottom_anim);
                startButton.setAnimation(animation);
                startButton.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}