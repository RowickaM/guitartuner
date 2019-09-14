package com.rowicka.gitartuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

import com.rowicka.gitartuner.tuner.TunerActivity;
import com.rowicka.gitartuner.utility.AudioPermission;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listView);
        String permissions[] = getResources().getStringArray(R.array.permissions);

        ArrayList<String> permissionsL = new ArrayList<String>();
        permissionsL.addAll( Arrays.asList(permissions) );

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, permissionsL);

        list.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Button permissionGrantedButton = (Button) findViewById(R.id.permissionButton);
        final Context context = this;

        if (AudioPermission.checkPermissions(context)){
            Intent intent = new Intent(context, TunerActivity.class);
            startActivity(intent);
        }

        permissionGrantedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPermission.checkPermissions(context);
                if (AudioPermission.checkPermissions(context)){
                    //todo przekierowanie do innej aktywności
                    Intent intent = new Intent(context, TunerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AudioPermission.RECORD_AUDIO){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Pozwolenia nadano", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, TunerActivity.class);
                startActivity(intent);
//            }else {
//                Toast.makeText(this, "Pozwolenia nie nadano", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
