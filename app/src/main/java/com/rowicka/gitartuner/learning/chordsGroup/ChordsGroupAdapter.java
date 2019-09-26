package com.rowicka.gitartuner.learning.chordsGroup;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rowicka.gitartuner.R;

import java.util.ArrayList;
import java.util.List;

public class ChordsGroupAdapter extends ArrayAdapter<ChordsGroup> {

    private Context context;
    private List<ChordsGroup> list = new ArrayList<>();

    public ChordsGroupAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ChordsGroup> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.item_chords_group, parent, false );
        }

        ChordsGroup chordsGroupCurrent = list.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.nameGroupTV);
        name.setText(chordsGroupCurrent.getName());

        TextView set = (TextView) listItem.findViewById(R.id.includeTV);
        StringBuilder sb = new StringBuilder();
        for (String str: chordsGroupCurrent.getInclude()){
            sb.append(str);
            sb.append(" ");
        }
        set.setText(sb.toString());

        return listItem;


    }
}
