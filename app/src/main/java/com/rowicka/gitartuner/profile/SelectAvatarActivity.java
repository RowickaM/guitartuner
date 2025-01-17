package com.rowicka.gitartuner.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rowicka.gitartuner.R;
import com.rowicka.gitartuner.collections.user.UsersCollection;
import com.rowicka.gitartuner.utility.IcoUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class SelectAvatarActivity extends Activity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ArrayList<IcoUser> array = new ArrayList<>();

    /**
     * Funkcja potrzebna do stworzenia okna. Jest ona nadpisywana z klasy Activity.
     * Jest jedną z kliku dostępnych stanów z cyku życia aktywności.
     *
     * @param savedInstanceState zawiera informacje o poprzednim stanie
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        array.add(
                new IcoUser(
                        this,
                        "user-1.png?alt=media&token=8ebc6572-dd71-4e6a-8b7f-abe4d12a11c8",
                        R.id.ico_1)
        );

        array.add(
                new IcoUser(
                        this,
                        "user-2.png?alt=media&token=dc3fe4c2-8cbb-48d1-9ffc-5d3b9ae058de",
                        R.id.ico_2)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-3.png?alt=media&token=aeababf9-02ca-470d-ab3a-7f1cd422ff50",
                        R.id.ico_3)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-4.png?alt=media&token=e4685749-a409-4cc6-9199-84a7d6d6e157",
                        R.id.ico_4)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-5.png?alt=media&token=652814b1-2e80-426d-9341-b3dc6bc66194",
                        R.id.ico_5)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-6.png?alt=media&token=4e4177b5-c45f-45c4-8471-b4d8ce54b592",
                        R.id.ico_6)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-7.png?alt=media&token=2a43d955-fa78-4be4-a6fe-adb75c1ce18d",
                        R.id.ico_7)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-8.png?alt=media&token=876cf9e5-3e73-4bd1-a52c-8733f2aaf5f9",
                        R.id.ico_8)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-9.png?alt=media&token=d6d79769-48fe-47a6-98c0-50d8330dad30",
                        R.id.ico_9)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-10.png?alt=media&token=4b56463d-8ff9-4d92-8f51-6fbf70097d19",
                        R.id.ico_10)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-11.png?alt=media&token=4f0af0a7-0a9b-41bd-bd60-ef15c6f6af84",
                        R.id.ico_11)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-12.png?alt=media&token=14f9c920-e90c-4aa8-b6d4-ec2e297c5938",
                        R.id.ico_12)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-13.png?alt=media&token=a5e32761-d8ad-44fa-ab7a-bbaf8f58716b",
                        R.id.ico_13)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-14.png?alt=media&token=71ccf533-8474-4e7a-84ff-1ab29f710381",
                        R.id.ico_14)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-15.png?alt=media&token=8a60d3da-601c-4594-8a57-2779e90db3d1",
                        R.id.ico_15)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-16.png?alt=media&token=9caf75d5-d614-49ec-84f7-f35aea59e289",
                        R.id.ico_16)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-17.png?alt=media&token=f58aa2aa-e462-4950-9ae0-1253fe081b55",
                        R.id.ico_17)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-18.png?alt=media&token=8ba0104b-5a75-4c88-9104-2c347fefbd6e",
                        R.id.ico_18)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-19.png?alt=media&token=f7cc5720-3f73-4f2c-8be7-67f336517807",
                        R.id.ico_19)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-20.png?alt=media&token=9cb8f67b-9fc3-4211-810c-7a723d469d27",
                        R.id.ico_20)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-21.png?alt=media&token=70ee1f81-abb8-457f-9b87-40b80b4376de",
                        R.id.ico_21)
        );
        array.add(
                new IcoUser(
                        this,
                        "user-22.png?alt=media&token=e0938cd8-1cc3-4413-bd1e-507561b801a8",
                        R.id.ico_22)
        );
        array.add(
                new IcoUser(
                        this,
                        "user.png?alt=media&token=28fe03c6-90c7-4cbf-9eb8-bb33214a7a67",
                        R.id.ico_23)
        );
        array.add(
                new IcoUser(
                        this,
                        "man.png?alt=media&token=4b4b9a3b-913a-4b39-8139-296898c74401",
                        R.id.ico_24)
        );


        for (int i = 0; i < array.size(); i++) {
            Picasso.with(this)
                    .load(array.get(i).getImgUrl())
                    .placeholder(R.mipmap.ic_guitar_round)
                    .resize(150, 150)
                    .centerCrop()
                    .into(array.get(i).getDisplay());
            final int finalI = i;
            array.get(i).getDisplay().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final UsersCollection uc = new UsersCollection(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                    uc.updateUser(
                            SelectAvatarActivity.this,
                            "",
                            array.get(finalI).getImgUrl());
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(SelectAvatarActivity.this, ProfileActivity.class));
                    overridePendingTransition(0, 0);

                }
            });
        }

        Button back = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(new Intent(SelectAvatarActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

    }

    /**
     * Funkcja potrzebna do nadpisania działania aplikacji po naciśnięciu przycisku cofnij
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
