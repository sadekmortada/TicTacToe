package com.smco.tictactoe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    TextView textView;
    ImageView googlePlay;
    public void googlePlay(View v)
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.smco.tictactoe");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_info);
        setTitle("About The App");
        textView=findViewById(R.id.textView3);
        googlePlay=findViewById(R.id.googlePlay);
        textView.setText("   Introducing my first android app. It's nothing more than a simple, made with passion, game. Yet it required continuous hours of work for weeks, to get accomplished.\n\n"
                +"   The app between your hands is to be the first and not the last of enjoying, relaxing, simple and easy control apps.\n\n"
                +"   Any suggestion or evaluation is a complete please. Feel free to offer your opinion and to rate the app on google play.\n\n   Thank you ..");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getTitle().equals("Help"))
            Sharing.help(this);
        else
            Sharing.settings(this);
        return true;
    }
    @Override
    public void onBackPressed()
    {
        new CountDownTimer(400,400) {
            @Override
            public void onTick(long millisUntilFinished) {
                googlePlay.animate().alpha(0f).setDuration(500);
                textView.animate().alpha(0f).setDuration(500);
            }
            @Override
            public void onFinish() {
                MainActivity.onMain=true;
                Info.super.onBackPressed();
            }
        }.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (!MainActivity.onMain) {
            MainActivity.introMusic.pause();
            MainActivity.introMusic.seekTo(0);
        }
        googlePlay.setAlpha(0);
        textView.setAlpha(0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        googlePlay.animate().alpha(1f).setDuration(500);
        textView.animate().alpha(1f).setDuration(500);
        if(Sharing.checked1)
            MainActivity.introMusic.start();
    }
}
