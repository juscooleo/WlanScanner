package com.example.wlanscanner;

import com.example.wlanscanner.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import android.util.Log;

import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    
    private DoScanning scanner = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fullscreen);
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final TextView contentView = (TextView)findViewById(R.id.fullscreen_content);
        scanner = new DoScanning( this, contentView );
        
        // Set up the user interaction to manually show or hide the system UI.
        /* contentView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        }); */

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnClickListener(mDelayHideClickListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnClickListener mDelayHideClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view/*, MotionEvent motionEvent*/) {
            try {
                scanner.StartScan();
            } catch(Throwable e) {
                Log.e( "WlanScanner", "Scanning failed !", e );
            }
            //return true;
        }
    };
}