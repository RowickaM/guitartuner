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

    /**
     * Konstruktoir klasy CheckConnection ustawiający kontekst aplikacji
     * @param context aktualny kontekst aplikacji
     */
    public CheckConnection(Context context) {
        this.context = context;
        setInstanceOfNetwork();
    }

    /**
     * Funkcja dodająca do activeNetwork aktualne informacje o sieci
     */
    private void setInstanceOfNetwork() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activeNetwork = cm.getActiveNetworkInfo();
    }

    /**
     * Funkcja sparawdzająca połączenie z Internetem
     * @return trur - jeśli połączenie z Internetem istnieje false - jeśli połączenie z Internetem nie istnieje
     */
    public boolean isConnected() {
        return this.activeNetwork != null && this.activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Funkcja tworząca okno informujące o braku interetu oraz pozwalająca na ponowne sprawdzenie statusu połączenia
     * oraz na przejście do ustawień
     * @return obiekt typu AlertDialog.Builer zawierający zachowanie i wygląd okna dialogowego informującego o braku dostępu do sieci
     */
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
