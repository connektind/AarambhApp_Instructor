package com.example.aarambhapp_instructor.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.aarambhapp_instructor.R;
import com.example.aarambhapp_instructor.multipath.AarambhSharedPreference;

public class SplashScreenAcivity extends AppCompatActivity {
    String teacher_id;
    public final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_acivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
         init();
    }

    private void init() {

        teacher_id= AarambhSharedPreference.loadTeacherIdFromPreference(this);

        if (!(teacher_id.equalsIgnoreCase("NA"))) {
            boolean b = new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    actionBox();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashScreenAcivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
    }
    private void actionBox() {

        if (!teacher_id.equalsIgnoreCase("NA")) {
            try {
                Intent intent = new Intent(SplashScreenAcivity.this, DashboardActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //  getChannelPlayListVideos();
            //getChannelVideos();
            //getYoutubeDataUrl();
//            switch (student_class_id) {
//                case "1":
//                    Intent intent = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                    startActivity(intent);
//                    finishAffinity();
//                    break;
//                case "2":
//                    Intent intent1 = new Intent(SpaleshScreenActivity.this, DashBoardSaventhStandardActivity.class);
//                    startActivity(intent1);
//                    finishAffinity();
//                    break;
//                case "3":
//                    Intent intent2 = new Intent(SpaleshScreenActivity.this, DashBoardEighthStandardActivity.class);
//                    startActivity(intent2);
//                    finishAffinity();
//                    break;
//                default:
//                    Intent intent3 = new Intent(SpaleshScreenActivity.this, DashBoardSixthStandardActivity.class);
//                    startActivity(intent3);
//                    finishAffinity();
//                    break;
//            }
        }
        else {
            Intent intent = new Intent(SplashScreenAcivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        finish();
    }

    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}