package com.example.what.smartmeeting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.severcontroler.LoadListHistoryMeetingFromSever;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class HistoryFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView recyclerView;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(int sectionNumber) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvHistory);
        LandingAnimator animator = new LandingAnimator();
        animator.setMoveDuration(1000);
        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private void loadData() {
        LoadListHistoryMeetingFromSever lll = new LoadListHistoryMeetingFromSever(HistoryFragment.this);
        lll.execute("http://hungntph04073.esy.es/get-list-meetings-by-account-id.php?id=hungnt");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            loadData();
        }
    }
}
