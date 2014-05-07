package com.example.wlanscanner;

import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import java.util.List;
import android.widget.TextView;
import android.util.TypedValue;

import android.util.Log;
import android.view.Gravity;

/**
 * @author 佳欢
 * 
 * Usage: Initialize by new DoScanning(context, Debugger);
 *     Then perform StartScan() method to obtain results.
 */
public class DoScanning {
    private WifiManager manager=null;
    private BroadcastReceiver receiver;
    protected TextView debugger;
    protected Context context;
    
    /*
     * @param Context context    A context from active Activity, usually the Activity itself.
     * @TextView Debugger    The TextView element for output.
     */
    public DoScanning( Context context, TextView Debugger ) {
        debugger = Debugger; this.context = context;
        try {
            receiver = new BroadcastReceiver() {
                public void onReceive( Context context, Intent intent ) {
                    List<ScanResult> results = manager.getScanResults();
                    ResultProcess(results);
                }
            };
            try {
                context.registerReceiver( receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) );
                manager = (WifiManager)context.getSystemService( Context.WIFI_SERVICE );
            } catch(Throwable e) {
                Log.e("WlanScanner", "Error registering scanning receiver.", e);
            }
        } catch(Throwable e) {
            Log.e("WlanScanner", "Error occurred while creating receiver.", e);
        }
    }
    
    public void StartScan() {
        try {
            debugger.setText("Scaning\n......");
            debugger.setTypeface(Typeface.DEFAULT_BOLD);
            debugger.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            debugger.setGravity(Gravity.CENTER);
        } catch(Throwable e) {}
        
        try {
            manager.startScan();
        } catch(Throwable err) {
            Log.e("WlanScanner", "Scan wifi action failed.", err);
            debugger.setText("Scan\nFailed!");
        }
    }
    
    protected void ResultProcess(List<ScanResult> list) {
        try {
            debugger.setText("Found Following APs: \n\n");
            debugger.setTypeface(Typeface.DEFAULT);
            debugger.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            debugger.setGravity(Gravity.CENTER_VERTICAL);
            debugger.setText("          BSSID               (SSID)             Level\n");
        } catch(Throwable e) {}
        
        for (ScanResult AP : list) {
            try {
                debugger.append( AP.BSSID+'('+AP.SSID+"): "+AP.level+"dBm\n" );
            } catch(Throwable e) {}
        }
        
    }
}
