package com.rowicka.gitartuner.utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    private String[] permissions ={
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET

    };
    private Activity activity;

    public Permission(Activity activity) {
        this.activity = activity;
    }

    public void permissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!arePermissionsEnabled()){
                requestMultiplePermissions();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean arePermissionsEnabled(){
        for(String permission : permissions){
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestMultiplePermissions(){
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        activity.requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), 101);
    }

}
