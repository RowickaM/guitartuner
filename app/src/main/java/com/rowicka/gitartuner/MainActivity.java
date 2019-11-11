package com.rowicka.gitartuner;

import androidx.annotation.NonNull;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import com.rowicka.gitartuner.tuner.TunerActivity;
import com.rowicka.gitartuner.utility.Permission;


public class MainActivity extends Activity {
    Permission permission;

    //todo wyświetlam dany schemat i kazuje pociągnąć po kolei struny - moze?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listView);
        String[] permissions = getResources().getStringArray(R.array.permissions);

        ArrayList<String> permissionsL = new ArrayList<String>(Arrays.asList(permissions));

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, permissionsL);

        list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        permission = new Permission(this);

        Button permissionGrantedButton = (Button) findViewById(R.id.permissionButton);

        //sprawdza czy zostały nadane już pozwolenia
        permission.permissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permission.arePermissionsEnabled()){
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, TunerActivity.class));
            overridePendingTransition(0, 0);
        }

        permissionGrantedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.permissions();
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        new AlertDialog.Builder(this)
                                .setMessage("Nie nadano wszystkich potrzebnych pozwoleń")
                                .setPositiveButton("Ponów", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        permission.requestMultiplePermissions();
                                    }
                                })
                                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                    return;
                }
            }
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, TunerActivity.class));
            overridePendingTransition(0, 0);
        }
    }
}
