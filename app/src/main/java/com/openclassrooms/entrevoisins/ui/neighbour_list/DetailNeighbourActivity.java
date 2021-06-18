package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNeighbourActivity extends AppCompatActivity {

    private Neighbour mNeighbour;
    private NeighbourApiService mApiService;


    //Constants
    public static final String NEIGHBOUR_ID_KEY = "NEIGHBOUR_ID_KEY";

    //Views
    @BindView(R.id.activity_detail_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_detail_collapsing) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.activity_detail_avatar) ImageView mAvatar;
    //@BindView(R.id.activity_detail_name) TextView mName;
    @BindView(R.id.activity_detail_card_name) TextView mCardName;
    @BindView(R.id.activity_detail_location) TextView mCardLocation;
    @BindView(R.id.activity_detail_phone_number) TextView mCardPhoneNumber;
    @BindView(R.id.activity_detail_website) TextView mCardWebSite;
    @BindView(R.id.activity_detail_about_me) TextView mAboutMe;
    @BindView(R.id.activity_detail_fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        mApiService = DI.getNeighbourApiService();

        if (intent != null) {
            mNeighbour = (Neighbour) intent.getSerializableExtra(NEIGHBOUR_ID_KEY);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNeighbour.isFavoris()){
                    fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                } else {
                    fab.setImageResource(R.drawable.ic_star_yellow_24dp);
                }
                mApiService.setFavoriteNeighbour(mNeighbour);
                mNeighbour.setFavoris(!mNeighbour.isFavoris());
            }
        });

        //Writing information
        setSupportActionBar(mToolbar);
        mCollapsingToolbarLayout.setTitle(mNeighbour.getName());
        //mName.setText(mNeighbour.getName());
        mCardName.setText(mNeighbour.getName());
        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .centerCrop()
                .into(mAvatar);
        mCardLocation.setText(mNeighbour.getAddress());
        mCardPhoneNumber.setText(mNeighbour.getPhoneNumber());
        mAboutMe.setText(mNeighbour.getAboutMe());
        mCardWebSite.setText("www.facebook.fr/"+  mNeighbour.getName().toLowerCase());
        if (mNeighbour.isFavoris()) {
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }
    }

}