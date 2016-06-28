package com.xamoom.android.htl.htldemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xamoom.android.xamoomcontentblocks.XamoomContentFragment;
import com.xamoom.android.xamoomsdk.APICallback;
import com.xamoom.android.xamoomsdk.APIListCallback;
import com.xamoom.android.xamoomsdk.EnduserApi;
import com.xamoom.android.xamoomsdk.Resource.Content;

import java.util.ArrayList;
import java.util.List;

import at.rags.morpheus.Error;


public class ListFragment extends Fragment implements StudentAdapter.StudentListInteractionListener {
    private RecyclerView mRecyclerView;
    private List<Content> mStudentList = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.student_recycler_view);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mStudentList.size() == 0) {
            downloadStudents();
        }
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StudentAdapter studentAdapter = new StudentAdapter(mStudentList, this, getContext());
        mRecyclerView.setAdapter(studentAdapter);
    }

    private void downloadStudents() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("student");
        EnduserApi.getSharedInstance().getContentsByTags(tags, 40, null, null, new APIListCallback<List<Content>, List<Error>>() {
            @Override
            public void finished(List<Content> result, String cursor, boolean hasMore) {
                mStudentList.addAll(result);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void error(List<Error> error) {
                Log.e("TAG", "Error: " + error);
            }
        });
    }

    @Override
    public void onStudentClicked(String id) {
        EnduserApi.getSharedInstance().getContent(id, new APICallback<Content, List<Error>>() {
            @Override
            public void finished(Content result) {
                XamoomContentFragment contentFragment = XamoomContentFragment.newInstance("AIzaSyDjv9vZq-9zAjxh2ifXFHl0sfhrDHQT-3s");
                contentFragment.setEnduserApi(EnduserApi.getSharedInstance());
                contentFragment.setContent(result);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, contentFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void error(List<Error> error) {
                Log.e("TAG", "Error: " + error);
            }
        });
    }
}
