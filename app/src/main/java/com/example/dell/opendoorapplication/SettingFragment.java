package com.example.dell.opendoorapplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
Button logout;
    Button disturb;
    private showPro mCallback;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        logout=(Button)view.findViewById(R.id.logout);
        //disturb=(Button)view.findViewById(R.id.disturb);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mCallback.showProByName("logout");
            }

        });
        return view;

    }

    public interface showPro {
        public void showProByName(String name);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mCallback = (showPro) activity;
        }
    }
}
