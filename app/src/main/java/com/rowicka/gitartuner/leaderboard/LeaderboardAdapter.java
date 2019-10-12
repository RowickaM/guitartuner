package com.rowicka.gitartuner.leaderboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.leaderboard.Leaderboard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<Leaderboard> {
    private static final String TAG = "LeaderboardAdapter";
    private Context context;
    private List<Leaderboard> list = new ArrayList<>();
    private String group;

    public LeaderboardAdapter(@NonNull Context context,
                              @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Leaderboard> list,
                                String group) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.group = group;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.item_leaderboard, parent, false );
        }

        Leaderboard current = list.get(position);

        TextView nick = (TextView) listItem.findViewById(R.id.nick);
        TextView place = (TextView) listItem.findViewById(R.id.place);
        TextView points = (TextView) listItem.findViewById(R.id.points);
        ImageView avk = (ImageView) listItem.findViewById(R.id.avk);

        place.setText(String.valueOf(position + 1));
        if (current.getUser() != null) {
            Picasso.with(context)
                    .load(current.getUser().getImgUrl())
                    .placeholder(R.mipmap.ic_guitar_round)
                    .resize(150, 150)
                    .centerCrop()
                    .into(avk);

            nick.setText(current.getUser().getNick());
            Log.d(TAG, "adapter:" + current.getUser().getNick());
        }
        if (group == null){
            points.setText(String.valueOf(current.getAllPoints()));
        }else {
            points.setText(String.valueOf(current.getPointsByGroup(this.group)));
        }
        return listItem;
    }
}
