package com.example.usuario.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btn_main_cambiar_a_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn_main);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Modelo1("Enviando mensaje desde MainActivity"));
            }
        });

        btn_main_cambiar_a_fragment = (Button)findViewById(R.id.btn_main_cambiar_a_fragment);
        btn_main_cambiar_a_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushFragment();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void escuchandoEnActivity(Modelo2 modelo2){
        Log.e("MainActivity", "Escuchando desde mainactivity: " + modelo2.msg);
    }

    private void pushFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment f = new BlankFragment();
        ft.replace(R.id.main, f);
        ft.addToBackStack("MainActivity");
        ft.commit();
    }

}
