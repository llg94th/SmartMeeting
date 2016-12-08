package com.example.what.smartmeeting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.severcontroler.LoadDepartmentInfo;
import com.example.what.smartmeeting.views.DepartmentActivity;
import com.example.what.smartmeeting.views.ProfileMemberInRom;

/**
 * Created by ngoc on 3/23/2016.
 */
public class InformationFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public InformationFragment() {
    }

    public static InformationFragment newInstance(int sectionNumber) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_department_info, container, false);
        Button btnView = (Button) rootView.findViewById(R.id.btnViewAllAccount);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), ProfileMemberInRom.class));
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadDepartmentInfo(this).execute("2");
    }
}
