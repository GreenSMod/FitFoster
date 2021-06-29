package com.greensmod.FitFoster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    FragmentManager myFragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myFragmentManager = getFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = myFragmentManager
                    .beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new SubFragmentPet());
            fragmentTransaction.commit();
        }

        return view;
    }
}
