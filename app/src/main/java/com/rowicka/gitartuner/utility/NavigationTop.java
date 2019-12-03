package com.rowicka.gitartuner.utility;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.leaderboard.LeaderboardActivity;
import com.rowicka.gitartuner.learning.BasicLearningActivity;
import com.rowicka.gitartuner.profile.AuthFirebase;
import com.rowicka.gitartuner.profile.LoginActivity;
import com.rowicka.gitartuner.profile.ProfileActivity;

public class NavigationTop {


    public NavigationTop(final Activity activity) {

        Button back = (Button) activity.findViewById(R.id.backButton);
        Button logout = (Button) activity.findViewById(R.id.logoutButton);
        Button leaderboard = (Button) activity.findViewById(R.id.toLeaderboard);
        Button help = (Button) activity.findViewById(R.id.toHelp);
        Button profile = (Button) activity.findViewById(R.id.toProfile);

        if (activity.getClass().equals(BasicLearningActivity.class) || activity.getClass().equals(HelpActivity.class)){
            back.setVisibility(View.INVISIBLE);
        }

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.getClass().equals(HelpActivity.class)){
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(new Intent(activity, HelpActivity.class));
                    activity.overridePendingTransition(0, 0);
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.getClass().equals(ProfileActivity.class)){
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                    activity.overridePendingTransition(0, 0);
                }
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                activity.overridePendingTransition(0, 0);
                activity.startActivity(new Intent(activity, LeaderboardActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                activity.overridePendingTransition(0, 0);
                activity.startActivity(new Intent(activity, BasicLearningActivity.class));
                activity.overridePendingTransition(0, 0);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthFirebase auth = new AuthFirebase(activity);
                auth.logout();
                activity.finish();
                activity.overridePendingTransition(0, 0);
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });

    }
}
