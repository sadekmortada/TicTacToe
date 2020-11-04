package com.smco.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {
    private boolean p1turn,p2turn;//determen turn for each player
    private int player1,player2;//chosen sign for each player
    private int tag;
    private int[] checkTag={0,0,0,0,0,0,0,0,0};
    public static String sharedTag;
    private final int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private CountDownTimer countDownTimer;
    private ImageView image,imageView1,imageView2;
    private MediaPlayer click;
    private GridLayout grid;
    private LinearLayout linear,linearLayout1,linearLayout2;
    private Random random;
    public static TextView textView1,textView2,score1,score2;
    public void play(int player)
    {
        if(player==1)
            image.setImageResource(R.drawable.o);
        else
            image.setImageResource(R.drawable.x);
        checkTag[tag] = player;
        p1turn=p2turn;
        p2turn=!p2turn;
        image.animate().alpha(1f).rotation(360).setDuration(400);
        if(p1turn) {
            imageView2.setAlpha(0f);
            imageView1.setAlpha(1f);
        }
        else
        {
            imageView1.setAlpha(0f);
            imageView2.setAlpha(1f);
        }
    }
    public void chosen_Turn_Sign()
    {
        int turn=new Random().nextInt(2);
        if(turn==1)
            p1turn=true;
        else
            p1turn=false;
        p2turn=!p1turn;
        player1=new Random().nextInt(2)+1;
        player2=3-player1;
    }
    public void dropIn(View v)
    {
        image=(ImageView) v;
        tag=Integer.parseInt(v.getTag().toString());
        if(checkTag[tag]!=0)
        { Toast.makeText(getApplicationContext(),"invalid move",Toast.LENGTH_SHORT).show(); return; }
        if(sharedTag.equals("1")){
            if(!p1turn)
                return;
            play(player1);
            if(checkWinner()) {
                imageView1.setAlpha(0f);
                imageView2.setAlpha(0f);
                return;
            }
            if(checkDraw()){
                imageView1.setAlpha(0f);
                imageView2.setAlpha(0f);
                return;
            }
            countDownTimer.start();
        }
        else {
            if (p1turn)
                play(player1);
            else
                play(player2);
            if (checkWinner()){
                imageView1.setAlpha(0f);
                imageView2.setAlpha(0f);
                return;
            }
            if (checkDraw()){
                imageView1.setAlpha(0f);
                imageView2.setAlpha(0f);
            }
        }
    }
    public boolean checkDraw()
    {
        for (int i = 0; i < checkTag.length; i++)
            if (checkTag[i] == 0)
                return false;
        for (int i = 0; i < checkTag.length; i++)
            grid.getChildAt(i).setClickable(false);
        ((TextView) linear.getChildAt(0)).setText("It's a draw!");
        (linear.getChildAt(1)).setClickable(true);
        linear.setAlpha(1f);
        return true;
    }
    public boolean checkWinner()
    {
        int j=tag-tag%3;
        if(checkTag[j]==checkTag[j+1]&&checkTag[j]==checkTag[j+2]){}
        else {
            j = tag % 3;
            if (checkTag[j] == checkTag[j + 3] && checkTag[j] == checkTag[j + 6]){}
            else {
                if (checkTag[4] != 0 && checkTag[0] == checkTag[4] && checkTag[4] == checkTag[8]){}
                else {
                    if (checkTag[4] != 0 && checkTag[2] == checkTag[4] && checkTag[4] == checkTag[6]){}
                    else
                        return false;
                }
            }
        }
        for(int i=0;i<9;i++)
            if(checkTag[i]!=checkTag[tag]&&checkTag[i]!=0)
                grid.getChildAt(i).animate().alpha(0.6f).setDuration(500);
        String winner;
        if(checkTag[tag]==player1) {
            score1.setText("" + (Integer.parseInt(score1.getText().toString()) + 1));
            winner=MainActivity.sharedPreferences.getString("player1","player1");
            if (sharedTag.equals("1"))
                MainActivity.sharedPreferences.edit().putString("score1Single", score1.getText().toString()).apply();
            else
                MainActivity.sharedPreferences.edit().putString("score1",score1.getText().toString()).apply();
        }
        else {
            score2.setText("" + (Integer.parseInt(score2.getText().toString()) + 1));
            if (sharedTag.equals("1")) {
                MainActivity.sharedPreferences.edit().putString("scoreBot", score2.getText().toString()).apply();
                winner="King-XO";
            }
            else{
                winner=MainActivity.sharedPreferences.getString("player2","player2");
                MainActivity.sharedPreferences.edit().putString("score2",score2.getText().toString()).apply();
            }
        }
        for (int i = 0; i < checkTag.length; i++)
            grid.getChildAt(i).setClickable(false);
        ((TextView) linear.getChildAt(0)).setText(winner + " has won!");
        (linear.getChildAt(1)).setClickable(true);
        linear.setAlpha(1f);
        return true;
    }
    public void restart(View v)
    {
        if(Sharing.checked2) {
            click.start();
            click.seekTo(150);
        }
        linear.setAlpha(0);
        v.setClickable(false);
        chosen_Turn_Sign();
        for(int i=0;i<9;i++) {
            v=grid.getChildAt(i);
            checkTag[i]=0;
            v.animate().alpha(0f).rotation(0f).setDuration(500);
            v.setClickable(true);
        }
        if(p1turn)
        {
            imageView2.setAlpha(0f);
            imageView1.setAlpha(1f);
        }
        else
        {
            imageView1.setAlpha(0f);
            imageView2.setAlpha(1f);
        }
        if(sharedTag.equals("1")) {
            random=new Random();
            random.setSeed(System.currentTimeMillis());
            if (p2turn)
                countDownTimer.start();
        }
    }
    public View botInt() {
        int j = 0, count = 0;
        for (; j < 8; j++,count=0) {
            for (int k = 0; k < 3; k++)
                if (player2 == checkTag[winningPositions[j][k]])
                    count++;
            if (count == 2)
                for (int i = 0; i < 3; i++) {
                    tag = winningPositions[j][i];
                    if (checkTag[tag] == 0)
                        return grid.getChildAt(tag);
                }
        }
        for (j=0,count=0; j < 8; j++,count=0) {
            for (int k = 0; k < 3; k++)
                if (player1 == checkTag[winningPositions[j][k]])
                    count++;
            if (count == 2)
                for (int i = 0; i < 3; i++) {
                    tag= winningPositions[j][i];
                    if (checkTag[tag] == 0)
                        return grid.getChildAt(tag);
                }
        }
        int rand = random.nextInt(9);
        if (checkTag[rand] == 0 &&   rand % 2!=0){
            tag = rand;
            return grid.getChildAt(rand);
        }
        else
        if (checkTag[rand] == 0) {
            tag = rand;
            return grid.getChildAt(rand);
        }
        else
        if(checkTag[4]==0){
            tag=4;
            return grid.getChildAt(4);
        }
        for(tag=0;tag<9;tag++)
            if(checkTag[tag]==0)
                return grid.getChildAt(tag);
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedTag= MainActivity.sharedPreferences.getString("mode","2");
        grid=findViewById(R.id.grid);
        linear=findViewById(R.id.linear);
        click=MediaPlayer.create(this,R.raw.click);
        click.seekTo(150);
        linearLayout1=findViewById(R.id.linearLayout1);
        linearLayout2=findViewById(R.id.linearLayout);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        score1=findViewById(R.id.score1);
        score2=findViewById(R.id.score2);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        chosen_Turn_Sign();
        if(p1turn)
            imageView1.setAlpha(1f);
        else
            imageView2.setAlpha(1f);
        random=new Random();
        random.setSeed(System.currentTimeMillis());
        countDownTimer=new CountDownTimer(1000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                image=(ImageView)botInt();

                play(player2);
                if (checkWinner()){
                    imageView1.setAlpha(0f);
                    imageView2.setAlpha(0f);
                    return;
                }
                if(checkDraw()){
                    imageView1.setAlpha(0f);
                    imageView2.setAlpha(0f);
                }
            }
        };
        textView1.setText(MainActivity.sharedPreferences.getString("player1","player1"));
        if(sharedTag.equals("1")) {
            setTitle("Single Player");
            score1.setText(MainActivity.sharedPreferences.getString("score1Single","0"));
            score2.setText(MainActivity.sharedPreferences.getString("scoreBot","0"));
            textView2.setText("King-XO");
            if (p2turn)
                countDownTimer.start();
        }
        else{
            setTitle("Multi Player");
            score1.setText(MainActivity.sharedPreferences.getString("score1","0"));
            score2.setText(MainActivity.sharedPreferences.getString("score2","0"));
            textView2.setText(MainActivity.sharedPreferences.getString("player2","player2"));
        }
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
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage("Current game progress will be lost");
        builder.setNeutralButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.onMain=true;
                Game.super.onBackPressed();
                finish();
            }
        });
        builder.show();
    }
}
