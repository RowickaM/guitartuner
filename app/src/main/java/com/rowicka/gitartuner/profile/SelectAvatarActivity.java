package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.user.UsersCollection;
import com.rowicka.gitartuner.utility.IcoUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectAvatarActivity extends Activity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ImageView[] ico_table;
    ArrayList<IcoUser> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        ico_table = new ImageView[]{
                (ImageView) findViewById(R.id.ico_1),
                (ImageView) findViewById(R.id.ico_2),
                (ImageView) findViewById(R.id.ico_3),
                (ImageView) findViewById(R.id.ico_4),
                (ImageView) findViewById(R.id.ico_5),
                (ImageView) findViewById(R.id.ico_6),
                (ImageView) findViewById(R.id.ico_7),
                (ImageView) findViewById(R.id.ico_8),
                (ImageView) findViewById(R.id.ico_9),
                (ImageView) findViewById(R.id.ico_10),
                (ImageView) findViewById(R.id.ico_11),
                (ImageView) findViewById(R.id.ico_12),
                (ImageView) findViewById(R.id.ico_13),
                (ImageView) findViewById(R.id.ico_14),
                (ImageView) findViewById(R.id.ico_15),
                (ImageView) findViewById(R.id.ico_16),
                (ImageView) findViewById(R.id.ico_17),
                (ImageView) findViewById(R.id.ico_18),
                (ImageView) findViewById(R.id.ico_19),
                (ImageView) findViewById(R.id.ico_20),
                (ImageView) findViewById(R.id.ico_21),
                (ImageView) findViewById(R.id.ico_22),
                (ImageView) findViewById(R.id.ico_23),
                (ImageView) findViewById(R.id.ico_24),
        };

        array.add(new IcoUser("ico_1", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-1.png?alt=media&token=8ebc6572-dd71-4e6a-8b7f-abe4d12a11c8", R.id.ico_1));
        array.add(new IcoUser("ico_2", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-2.png?alt=media&token=dc3fe4c2-8cbb-48d1-9ffc-5d3b9ae058de", R.id.ico_2));
        array.add(new IcoUser("ico_3", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-3.png?alt=media&token=aeababf9-02ca-470d-ab3a-7f1cd422ff50", R.id.ico_3));
        array.add(new IcoUser("ico_4", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-4.png?alt=media&token=e4685749-a409-4cc6-9199-84a7d6d6e157", R.id.ico_4));
        array.add(new IcoUser("ico_5", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-5.png?alt=media&token=652814b1-2e80-426d-9341-b3dc6bc66194", R.id.ico_5));
        array.add(new IcoUser("ico_6", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-6.png?alt=media&token=4e4177b5-c45f-45c4-8471-b4d8ce54b592", R.id.ico_6));
        array.add(new IcoUser("ico_7", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-7.png?alt=media&token=2a43d955-fa78-4be4-a6fe-adb75c1ce18d", R.id.ico_7));
        array.add(new IcoUser("ico_8", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-8.png?alt=media&token=876cf9e5-3e73-4bd1-a52c-8733f2aaf5f9", R.id.ico_8));
        array.add(new IcoUser("ico_9", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-9.png?alt=media&token=d6d79769-48fe-47a6-98c0-50d8330dad30", R.id.ico_9));
        array.add(new IcoUser("ico_10", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-10.png?alt=media&token=4b56463d-8ff9-4d92-8f51-6fbf70097d19", R.id.ico_10));
        array.add(new IcoUser("ico_11", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-11.png?alt=media&token=4f0af0a7-0a9b-41bd-bd60-ef15c6f6af84", R.id.ico_11));
        array.add(new IcoUser("ico_12", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-12.png?alt=media&token=14f9c920-e90c-4aa8-b6d4-ec2e297c5938", R.id.ico_12));
        array.add(new IcoUser("ico_13", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-13.png?alt=media&token=a5e32761-d8ad-44fa-ab7a-bbaf8f58716b", R.id.ico_13));
        array.add(new IcoUser("ico_14", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-14.png?alt=media&token=71ccf533-8474-4e7a-84ff-1ab29f710381", R.id.ico_14));
        array.add(new IcoUser("ico_15", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-15.png?alt=media&token=8a60d3da-601c-4594-8a57-2779e90db3d1", R.id.ico_15));
        array.add(new IcoUser("ico_16", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-16.png?alt=media&token=9caf75d5-d614-49ec-84f7-f35aea59e289", R.id.ico_16));
        array.add(new IcoUser("ico_17", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-17.png?alt=media&token=f58aa2aa-e462-4950-9ae0-1253fe081b55", R.id.ico_17));
        array.add(new IcoUser("ico_18", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-18.png?alt=media&token=8ba0104b-5a75-4c88-9104-2c347fefbd6e", R.id.ico_18));
        array.add(new IcoUser("ico_19", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-19.png?alt=media&token=f7cc5720-3f73-4f2c-8be7-67f336517807", R.id.ico_19));
        array.add(new IcoUser("ico_20", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-20.png?alt=media&token=9cb8f67b-9fc3-4211-810c-7a723d469d27", R.id.ico_20));
        array.add(new IcoUser("ico_21", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-21.png?alt=media&token=70ee1f81-abb8-457f-9b87-40b80b4376de", R.id.ico_21));
        array.add(new IcoUser("ico_22", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser-22.png?alt=media&token=e0938cd8-1cc3-4413-bd1e-507561b801a8", R.id.ico_22));
        array.add(new IcoUser("ico_23", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fuser.png?alt=media&token=28fe03c6-90c7-4cbf-9eb8-bb33214a7a67", R.id.ico_23));
        array.add(new IcoUser("ico_24", "https://firebasestorage.googleapis.com/v0/b/guitar-tuna.appspot.com/o/user-ico%2Fman.png?alt=media&token=4b4b9a3b-913a-4b39-8139-296898c74401", R.id.ico_24));


        for (int i = 0; i < ico_table.length; i++) {
            Picasso.with(this)
                    .load(array.get(i).getImgUrl())
                    .placeholder(R.mipmap.ic_guitar_round)
                    .resize(150, 150)
                    .centerCrop()
                    .into(ico_table[i]);
            final int finalI = i;
            ico_table[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final UsersCollection uc = new UsersCollection(firebaseAuth.getCurrentUser().getUid());
                    uc.updateUser(
                            SelectAvatarActivity.this,
                            firebaseAuth.getCurrentUser().getUid(),
                            "",
                            array.get(finalI).getImgUrl());

                    startActivity(new Intent(SelectAvatarActivity.this, ProfileActivity.class));

                }
            });
        }

        Button back = (Button) findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectAvatarActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
