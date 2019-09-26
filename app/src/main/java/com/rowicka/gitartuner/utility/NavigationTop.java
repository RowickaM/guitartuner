package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.leaderboard.LeaderboardActivity;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.learning.ListOfChordsActivity;
import com.rowicka.gitartuner.learning.ShowChordActivity;
import com.rowicka.gitartuner.profile.AuthFirebase;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.profile.ProfileActivity;

public class NavigationTop {

    private Activity activity;
    private Button back, logout, leaderboard, profile;


    public NavigationTop(final Activity activity) {
        this.activity = activity;

        back = (Button) activity.findViewById(R.id.backButton);
        logout = (Button) activity.findViewById(R.id.logoutButton);
        leaderboard = (Button) activity.findViewById(R.id.toLeaderboard);
        profile = (Button) activity.findViewById(R.id.toProfile);

        if (activity.getClass().equals(BasicLearningActivity.class)){
            back.setVisibility(View.INVISIBLE);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.getClass().equals(ProfileActivity.class)){
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, LeaderboardActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    activity.startActivity(new Intent(activity, BasicLearningActivity.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthFirebase auth = new AuthFirebase(activity);
                auth.logout();
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }
        });

    }
}
