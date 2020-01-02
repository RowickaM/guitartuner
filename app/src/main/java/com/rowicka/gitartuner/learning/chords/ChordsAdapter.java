package com.rowicka.gitartuner.learning.chords;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rowicka.gitartuner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa ChordsAdapter służy do utworzenia listy potrzebnej do wyświetlenia listy akordów w danej grupie
 */
public class ChordsAdapter extends ArrayAdapter<Chords> {

    private Context context;
    private List<Chords> list;

    public ChordsAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Chords> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    /**
     * Funkcja łączy dane z miejscem wyświetlania danych
     * @param position pozycja aktualnie sprawdzanej wartości
     * @param convertView obiekt widoku
     * @param parent obiekt rodzica widoku
     * @return zwraca obiekt typu View wyświetlany w oknie aplikacji
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.item_chords, parent, false );
        }

        Chords chordsCurrent = list.get(position);

        TextView name = listItem.findViewById(R.id.nameChordTV);
        name.setText(chordsCurrent.getName());

        return listItem;


    }
}
