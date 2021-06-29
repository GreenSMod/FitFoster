package com.greensmod.FitFoster;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SubFragmentPet extends Fragment {

    FragmentManager myFragmentManager;

    public void toNextSubFragment() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SubFragmentWardrobe());
        fragmentTransaction.commit();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subfragment_pet, container, false);

        TextView steps_points_amount = view.findViewById(R.id.steps_points_amount);
        TextView events_points_amount = view.findViewById(R.id.events_points_amount);

        PetInfoRoomDatabase dbPetInfo = App.getInstance().getDatabasePetInfo();
        PetInfoDao petInfoDao = dbPetInfo.petInfoDao();
        PetInfo pet_info_db = petInfoDao.getById(1);
        steps_points_amount.setText(pet_info_db.steps_points + "");
        events_points_amount.setText(pet_info_db.events_points + "");

        WardrobeRoomDatabase dbWardrobe = App.getInstance().getDatabaseWardrobe();
        WardrobeDao wardrobeDao = dbWardrobe.wardrobeDao();
        ImageView texture_image = view.findViewById(R.id.texture_image);
        ImageView body_image = view.findViewById(R.id.body_image);
        if (pet_info_db.texture_id != 0) {
            texture_image.setImageResource(wardrobeDao.getById(pet_info_db.texture_id).resource_id);
        }

        if (pet_info_db.body_id != 0) {
            body_image.setImageResource(wardrobeDao.getById(pet_info_db.body_id).resource_id);
        }

        myFragmentManager = getFragmentManager();
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
