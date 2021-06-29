package com.greensmod.FitFoster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubFragmentAchievements extends Fragment {

    FragmentManager myFragmentManager;
    ArrayList<AchievementElement> achievements = new ArrayList<AchievementElement>();

    public void toPreviousSubFragment() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new SubFragmentWardrobe());
        fragmentTransaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subfragment_achievements, container, false);

        myFragmentManager = getFragmentManager();
        Button previousSubFragment = view.findViewById(R.id.button_previous_subfragment);
        previousSubFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPreviousSubFragment();
            }
        });

        setInitialData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        AchievementElementAdapter adapter = new AchievementElementAdapter(getActivity(), achievements);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setInitialData() {
        AchievementsRoomDatabase dbAchievements = App.getInstance().getDatabaseAchievements();
        AchievementsDao achievementsDao = dbAchievements.achievementsDao();

        List<Achievements> achievementsGetAll = achievementsDao.getAll();

        for (int i = 0; i < achievementsGetAll.size(); i++) {
            Achievements achievement = achievementsGetAll.get(i);
            if (achievement.type_id != -1 && achievement.completed_type_id != 1) {
                achievements.add(new AchievementElement(achievement.label, achievement.name, achievement.description, achievement.completed_type_id, achievement.type_id));
            }
        }
    }
}
