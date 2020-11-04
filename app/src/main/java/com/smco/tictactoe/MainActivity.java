package com.smco.tictactoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected static boolean animation = true,fromAbout=false,onMain=true,home=false;
    private String player1, string = "";
    private int j = 0;
    public static TextView textView;
    private Button button1, button2, button3;
    public static SharedPreferences sharedPreferences;
    public static MediaPlayer button,introMusic,welcoming;
    public void animate(final int i) {
        final String welcome = "Welcome " + player1;
        if (animation) {
            animation=false;
            if(Sharing.checked2)
                welcoming.start();
            new CountDownTimer(welcome.length() * 120, 120) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int progress = (int) millisUntilFinished / 120;
                    if (j < welcome.length() - progress)
                        string += welcome.charAt(j);
                    j++;
                    textView.setText(string);
                }
                @Override
                public void onFinish() {
                    textView.setText(welcome);
                    button1.animate().translationX(-i).alpha(1).setDuration(400);
                    button2.animate().translationX(i).alpha(1).setDuration(400);
                    button3.animate().translationX(-i).alpha(1).setDuration(400);
                    if(Sharing.checked2){
                        button.start();
                        welcoming.stop();}
                    findViewById(R.id.introMusic).animate().alpha(0.7f).setDuration(1000);
                }
            }.start();
        }
        else {
            if(Sharing.checked2)
                button.start();
            textView.setText("Welcome "+sharedPreferences.getString("player1","player1"));
            new CountDownTimer(300, 300) {
                @Override
                public void onTick(long millisUntilFinished) {
                    button1.animate().translationX(0).alpha(1).setDuration(400);
                    button2.animate().translationX(0).alpha(1).setDuration(400);
                    button3.animate().translationX(0).alpha(1).setDuration(400);
                }
                @Override
                public void onFinish() {
                    MainActivity.fromAbout = false;
                    findViewById(R.id.introMusic).animate().alpha(0.7f).setDuration(1000);
                }
            }.start();
        }
    }
    public void play(View v) {
        sharedPreferences.edit().putString("mode", v.getTag().toString()).apply();
        new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(Sharing.checked2)
                    button.start();
                onMain=false;
                button1.animate().translationX(-1000).alpha(0).setDuration(400);
                button2.animate().translationX(1000).alpha(0).setDuration(400);
                button3.animate().translationX(-1000).alpha(0).setDuration(400);
                findViewById(R.id.introMusic).animate().alpha(0f).setDuration(500);
            }
            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), Game.class));
            }
        }.start();
    }
    public void aboutApp(View v)
    {
        onMain=false;
        fromAbout=true;
        if(Sharing.checked2)
            button.start();
        new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
                button1.animate().translationX(-1000).alpha(0).setDuration(400);
                button2.animate().translationX(1000).alpha(0).setDuration(400);
                button3.animate().translationX(-1000).alpha(0).setDuration(400);
                findViewById(R.id.introMusic).animate().alpha(0f).setDuration(500);
            }
            @Override
            public void onFinish() {
                startActivityIfNeeded(new Intent(getApplicationContext(),Info.class),0);
            }
        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("com.example.connect3", Context.MODE_PRIVATE);
        Sharing.checked1=sharedPreferences.getBoolean("music",true);
        Sharing.checked2=sharedPreferences.getBoolean("sound",true);
        introMusic = MediaPlayer.create(this, R.raw.summer);
        button= MediaPlayer.create(this,R.raw.button);
        welcoming=MediaPlayer.create(this,R.raw.keyboardtapping);
        introMusic = MediaPlayer.create(this, R.raw.summer);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(Sharing.checked1&&!fromAbout)
            introMusic.start();
        player1 = sharedPreferences.getString("player1", "");
        if (player1.equals("")) {
            final EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            editText.setHint("player1");
            AlertDialog builder = new AlertDialog.Builder(this).create();
            builder.setCanceledOnTouchOutside(false);
            builder.setIcon(android.R.drawable.ic_menu_edit);
            builder.setTitle("So... What's your name?");
            builder.setMessage("12 characters at maximum will be taken.");
            builder.setView(editText);
            builder.setButton(-3,"Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    player1 = editText.getText().toString();
                    if (player1.equals(""))
                        player1 = editText.getHint().toString();
                    if(player1.length()>12)
                        player1=player1.substring(0,11);
                    sharedPreferences.edit().putString("player1", player1).apply();
                    animate(0);
                }

            });
            builder.show();
        }
        else
            animate(0);
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
    protected void onPause() {
        super.onPause();
        if(!fromAbout&&Sharing.checked1) {
            introMusic.seekTo(0);
            introMusic.pause();
        }
        welcoming.stop();
        button1.animate().translationX(-1000).alpha(0).setDuration(0);
        button2.animate().translationX(1000).alpha(0).setDuration(0);
        button3.animate().translationX(-1000).alpha(0).setDuration(0);
    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}
