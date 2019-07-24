package com.example.cotizacionesvisenar;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cotizacionesvisenar.Adapter.MyAdapter;
import com.example.cotizacionesvisenar.Listener.IFirebaseLoadDone;
import com.example.cotizacionesvisenar.Model.Movie;
import com.example.cotizacionesvisenar.Transformer.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, IFirebaseLoadDone, ValueEventListener {
    private Toolbar toolbar;
    private ProgressBar progress;

    ////////////////////////////////
    ViewPager viewPager;
    MyAdapter adapter;

    DatabaseReference movies;

    IFirebaseLoadDone iFirebaseLoadDone;

    ///////////////////////////
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    ///////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        ///////////////////init firebase
        movies = FirebaseDatabase.getInstance().getReference("Movies");

        //init event
        iFirebaseLoadDone = this;

        loadMovie();

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadMovie() {
        movies.addValueEventListener(this);
    }

    // Menú
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // Item
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.id_cerrar:
                //para cerrar sesion
                Toast.makeText(this,"Hola",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btn_porteria:
                //loginUser();
                //mostrar progres
                Intent intent = new Intent(HomeActivity.this, CotizacionActivity.class);
                startActivity(intent);
                finish();
                progress.setVisibility(View.VISIBLE);
                //ocultar progress progress.setVisibility(View.INVISIBLE);
                break;
                */
        }
    }

    @Override
    public void onFirebaseLoadSuccess(List<Movie> movieList) {
        adapter = new MyAdapter(this, movieList);
        viewPager.setAdapter(adapter);
        ////////////////////////////////////////////////////////////
        viewPager.setPadding(0,0,0,10);
        /*
        Integer[] colors_temp = {
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3)
        };

        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }
                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }
            @Override
            public void onPageSelected(int i) {}

            @Override
            public void onPageScrollStateChanged(int i) {}
        });*/


    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Movie> movieList = new ArrayList<>();
        for (DataSnapshot movieSnapShot:dataSnapshot.getChildren())
            movieList.add(movieSnapShot.getValue(Movie.class));
        iFirebaseLoadDone.onFirebaseLoadSuccess(movieList);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
    }

    @Override
    protected void onDestroy() {
        movies.removeEventListener(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        movies.addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        movies.removeEventListener(this);
        super.onStop();
    }
}
