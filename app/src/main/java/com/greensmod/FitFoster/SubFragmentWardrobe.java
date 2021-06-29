package com.greensmod.FitFoster;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SubFragmentWardrobe extends Fragment {

    FragmentManager myFragmentManager;

    public void toPreviousSubFragment() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SubFragmentPet());
        fragmentTransaction.commit();
    }

    public void toNextSubFragment() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SubFragmentAchievements());
        fragmentTransaction.commit();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subfragment_wardrobe, container, false);

        TextView steps_points_amount = view.findViewById(R.id.steps_points_amount);
        TextView events_points_amount = view.findViewById(R.id.events_points_amount);
        ImageButton button_texture_1 = view.findViewById(R.id.button_texture_1);
        ImageButton button_texture_2 = view.findViewById(R.id.button_texture_2);
        ImageButton button_body_0 = view.findViewById(R.id.button_body_0);
        ImageButton button_body_1 = view.findViewById(R.id.button_body_1);
        ImageButton button_body_2 = view.findViewById(R.id.button_body_2);

        PetInfoRoomDatabase dbPetInfo = App.getInstance().getDatabasePetInfo();
        PetInfoDao petInfoDao = dbPetInfo.petInfoDao();
        PetInfo pet_info_db = petInfoDao.getById(1);
        steps_points_amount.setText(pet_info_db.steps_points + "");
        events_points_amount.setText(pet_info_db.events_points + "");

        button_texture_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_info_db.texture_id != 1) {
                    pet_info_db.texture_id = 1;
                    petInfoDao.update(pet_info_db);
                }
            }
        });
        button_texture_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_info_db.texture_id != 2) {
                    pet_info_db.texture_id = 2;
                    petInfoDao.update(pet_info_db);
                }
            }
        });
        button_body_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_info_db.body_id != 0) {
                    pet_info_db.body_id = 0;
                    petInfoDao.update(pet_info_db);
                }
            }
        });
        button_body_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_info_db.body_id != 497) {
                    pet_info_db.body_id = 497;
                    petInfoDao.update(pet_info_db);
                }
            }
        });
        button_body_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pet_info_db.body_id != 498) {
                    pet_info_db.body_id = 498;
                    petInfoDao.update(pet_info_db);
                }
            }
        });

        myFragmentManager = getFragmentManager();
        Button previousSubFragment = view.findViewById(R.id.button_previous_subfragment);
        previousSubFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPreviousSubFragment();
            }
        });
        Button nextSubFragment = view.findViewById(R.id.button_next_subfragment);
        nextSubFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextSubFragment();
            }
        });

        return view;
    }
}
