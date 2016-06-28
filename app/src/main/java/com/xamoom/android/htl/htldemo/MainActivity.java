package com.xamoom.android.htl.htldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xamoom.android.xamoomcontentblocks.XamoomContentFragment;
import com.xamoom.android.xamoomcontentblocks.XamoomContentFragment.OnXamoomContentFragmentInteractionListener;
import com.xamoom.android.xamoomsdk.APICallback;
import com.xamoom.android.xamoomsdk.EnduserApi;
import com.xamoom.android.xamoomsdk.Resource.Content;

import java.util.List;

import at.rags.morpheus.Error;

public class MainActivity extends AppCompatActivity implements OnXamoomContentFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, ListFragment.newInstance()).commit();

        EnduserApi.getSharedInstance("7d2ded21-7756-41c5-b668-d0cd2cd5944e");
    }

    @Override
    public void clickedContentBlock(Content content) {

    }

    @Override
    public void clickedSpotMapContentLink(String contentId) {

    }
}
