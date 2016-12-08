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
import com.example.what.smartmeeting.severcontroler.LoadListIsComingMeetingFromSever;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class IsComingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView recyclerView;
    public IsComingFragment() {
    }

    public static IsComingFragment newInstance(int sectionNumber) {
        IsComingFragment fragment = new IsComingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_is_coming, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvIsComing);
        LandingAnimator animator = new LandingAnimator();
        animator.setMoveDuration(1000);
        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private void loadData() {
        LoadListIsComingMeetingFromSever lll = new LoadListIsComingMeetingFromSever(IsComingFragment.this);
        lll.execute("http://hungntph04073.esy.es/get-list-meetings-by-account-id.php?id=hungnt");
//        try {
//            lll.get(3000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE).setTitleText(e.toString()).show();
//            e.printStackTrace();
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            loadData();
        }
    }
}
