package com.greensmod.FitFoster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;


public class ProfileFragment extends Fragment {

    public void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        Fitness.getConfigClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity())).disableFit();
        mGoogleSignInClient.signOut();

        Intent intent = new Intent(getActivity(), GoogleAuth.class);
        startActivity(intent);
        getActivity().finish();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        Button clear_database_button = view.findViewById(R.id.clear_database_button);
//        clear_database_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AchievementsRoomDatabase dbAchievements = App.getInstance().getDatabaseAchievements();
//                AchievementsDao achievementsDao = dbAchievements.achievementsDao();
//                achievementsDao.deleteAll();
//
//                DaysRoomDatabase dbDays = App.getInstance().getDatabaseDays();
//                DaysDao daysDao = dbDays.daysDao();
//                daysDao.deleteAll();
//
//                PetInfoRoomDatabase dbPetInfo = App.getInstance().getDatabasePetInfo();
//                PetInfoDao petInfoDao = dbPetInfo.petInfoDao();
//                petInfoDao.deleteAll();
//
//                StatisticsRoomDatabase dbStatistics = App.getInstance().getDatabaseStatistics();
//                StatisticsDao statisticsDao = dbStatistics.statisticsDao();
//                statisticsDao.deleteAll();
//
//                WardrobeRoomDatabase dbWardrobe = App.getInstance().getDatabaseWardrobe();
//                WardrobeDao wardrobeDao = dbWardrobe.wardrobeDao();
//                wardrobeDao.deleteAll();
//
//                WeeklyMissionsRoomDatabase dbWeeklyMissions = App.getInstance().getDatabaseWeeklyMissions();
//                WeeklyMissionsDao weeklyMissionsDao = dbWeeklyMissions.weeklyMissionsDao();
//                weeklyMissionsDao.deleteAll();
//            }
//        });

        Button updateButton = view.findViewById(R.id.sign_out_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return view;
    }
}
