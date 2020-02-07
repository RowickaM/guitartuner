package com.rowicka.gitartuner.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class CheckConnection {

    private Context context;
    private NetworkInfo activeNetwork;

    public CheckConnection(Context context) {
        this.context = context;
        setInstanceOfNetwork();
    }

    private void setInstanceOfNetwork() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activeNetwork = cm.getActiveNetworkInfo();
    }

    public boolean isConnected() {
        return this.activeNetwork != null && this.activeNetwork.isConnectedOrConnecting();
    }

    public AlertDialog.Builder notConnectedAlertDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle("Brak połączenia")
                .setMessage("Twoje urządzenie prawdopodobie nie ma połączenia z Internetem. Proszę sprawdzić ustawienia i spróbować jeszcze raz")
                .setPositiveButton(
                        "Spróbuj jeszcze raz",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        })
                .setNeutralButton(
                        "Przejdź do ustawień",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            }
                        }
                );
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                setInstanceOfNetwork();
                if (!isConnected()) {
                    dialog.show();
                }
            }
        });
        return dialog;
    }
}
