package com.codemaker.rajatroy.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CardsActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    LinearLayout newsLayout;
    LinearLayout videosLayout;
    LinearLayout moviesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home");
        setContentView(R.layout.activity_cards);

        firebaseAuth = FirebaseAuth.getInstance();

        newsLayout = (LinearLayout)findViewById(R.id.newsLayout);

        newsLayout.setOnClickListener(this);
        videosLayout = (LinearLayout)findViewById(R.id.videosLayout);
        videosLayout.setOnClickListener(this);
        moviesLayout = (LinearLayout)findViewById(R.id.moviesLayout);
        moviesLayout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:
                //logout code
                firebaseAuth.signOut();
                Toast.makeText(CardsActivity.this,"Bye...",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
            if(v == newsLayout){
                finish();
                startActivity(new Intent(CardsActivity.this,NewsActivity.class));
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }
            if(v == videosLayout){
                finish();
                startActivity(new Intent(CardsActivity.this,VideosActivity.class));
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }
            if(v== moviesLayout){
                finish();
                startActivity(new Intent(CardsActivity.this,MoviesActivity.class));
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }
    }
}
