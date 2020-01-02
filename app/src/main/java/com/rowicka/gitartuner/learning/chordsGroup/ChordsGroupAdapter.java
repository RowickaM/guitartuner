package com.rowicka.gitartuner.learning.chordsGroup;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.rowicka.gitartuner.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa ChordsAdapter służy do utworzenia listy potrzebnej do wyświetlenia listy grup akordów i ich zawartości
 */
public class ChordsGroupAdapter extends ArrayAdapter<ChordsGroup> {

    private Context context;
    private List<ChordsGroup> list;

    public ChordsGroupAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ChordsGroup> list) {
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
            listItem = LayoutInflater.from(context).inflate(R.layout.item_chords_group, parent, false );
        }

        ChordsGroup chordsGroupCurrent = list.get(position);

        TextView name = listItem.findViewById(R.id.nameGroupTV);
        name.setText(chordsGroupCurrent.getName());

        TextView set = listItem.findViewById(R.id.includeTV);
        StringBuilder sb = new StringBuilder();
        for (String str: chordsGroupCurrent.getInclude()){
            sb.append(str);
            sb.append(" ");
        }
        set.setText(sb.toString());

        return listItem;


    }
}
