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

    private Activity activity;
    private AuthFirebase auth;

    public NavigationTop(final Activity activity) {
        this.activity = activity;

        Button back = activity.findViewById(R.id.backButton);
        Button logout = activity.findViewById(R.id.logoutButton);
        Button leaderboard = activity.findViewById(R.id.toLeaderboard);
        Button help = activity.findViewById(R.id.toHelp);
        Button profile = activity.findViewById(R.id.toProfile);

        this.auth = new AuthFirebase(activity);

        if(auth.checkUserLogin()){
            logout.setText(R.string.logout);
        }else {
            logout.setText(R.string.login);
            profile.setVisibility(View.INVISIBLE);
            leaderboard.setVisibility(View.INVISIBLE);
        }

        if (activity.getClass().equals(BasicLearningActivity.class) || activity.getClass().equals(HelpActivity.class)){
            back.setVisibility(View.INVISIBLE);
        }

        addListener(help, HelpActivity.class);
        addListener(profile, ProfileActivity.class);
        addListener(leaderboard, LeaderboardActivity.class);
        addListener(back, BasicLearningActivity.class);
        addListener(logout, LoginActivity.class);

    }

    private void addListener(final Button btn, final Class to){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.getClass().equals(to)){
                    if(btn.getText().toString().equalsIgnoreCase(activity.getString(R.string.logout))){
                        auth.logout();
                    }
                    changeTo(to);
                }
            }
        });
    }

    private void changeTo(Class cls){
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(new Intent(activity, cls));
        activity.overridePendingTransition(0, 0);
    }
}
