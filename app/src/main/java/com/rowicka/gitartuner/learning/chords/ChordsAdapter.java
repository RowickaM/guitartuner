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

public class ChordsAdapter extends ArrayAdapter<Chords> {

    private Context context;
    private List<Chords> list = new ArrayList<>();

    public ChordsAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Chords> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.item_chords, parent, false );
        }

        Chords chordsCurrent = list.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.nameChordTV);
        name.setText(chordsCurrent.getName());

        return listItem;


    }
}
