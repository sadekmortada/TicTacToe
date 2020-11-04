package com.smco.tictactoe;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.smco.tictactoe.Game.sharedTag;
import static com.smco.tictactoe.MainActivity.sharedPreferences;

public class Sharing {
    public static boolean checked1=true,checked2=true;
    private static AlertDialog.Builder alertDialog;
    public static void settings(final Context context) {
        alertDialog=new AlertDialog.Builder(context);
        LinearLayout linearLayout=new LinearLayout(context);
        TextView textView1=new TextView(context),textView2=new TextView(context),breakLine1=new TextView(context),breakLine2=new TextView(context);
        breakLine1.setText("");
        breakLine2.setText("");
        textView1.setText("Player1 name:");
        textView2.setText("Player2 name:");
        textView1.setTextColor(Color.WHITE);
        textView2.setTextColor(Color.WHITE);
        final EditText editText1=new EditText(context),editText2=new EditText(context);
        editText1.setText(sharedPreferences.getString("player1","player1"));
        editText2.setText(sharedPreferences.getString("player2","player2"));
        editText1.setHint("player1");
        editText2.setHint("player2");
        editText1.setTextSize(20f);
        editText2.setTextSize(20f);
        final RadioButton radioButton3=new RadioButton(context),radioButton4=new RadioButton(context), radioButton5=new RadioButton(context);
        radioButton3.setText("Single Player Reset Score");
        radioButton3.setTextColor(Color.WHITE);
        radioButton4.setText("Multi Player Reset Score");
        radioButton4.setTextColor(Color.WHITE);
        radioButton5.setText("Apply changes to names");
        radioButton5.setTextColor(Color.WHITE);
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton3.setChecked(false);
                if(!MainActivity.onMain&&!MainActivity.fromAbout&&sharedTag.equals("1")) {
                    Game.score1.setText("0");
                    Game.score2.setText("0");
                }
                sharedPreferences.edit().putString("score1Single","0").putString("scoreBot","0").apply();
                Toast.makeText(context,"Done!",Toast.LENGTH_SHORT).show();
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton4.setChecked(false);
                if(!MainActivity.onMain&&!MainActivity.fromAbout&&sharedTag.equals("2")) {
                    Game.score1.setText("0");
                    Game.score2.setText("0");
                }
                sharedPreferences.edit().putString("score1","0").putString("score2","0").apply();
                Toast.makeText(context,"Done!",Toast.LENGTH_SHORT).show();
            }
        });
        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton5.setChecked(false);
                String temp1=editText1.getText().toString(),temp2=editText2.getText().toString(),v="";
                if(temp1.equals(v))
                    temp1="player1";
                if(temp2.equals(v))
                    temp2="player2";
                if(temp1.length()>12)
                    temp1=temp1.substring(0,12);
                if(temp2.length()>12)
                    temp2=temp2.substring(0,12);
                if(!MainActivity.onMain&&!MainActivity.fromAbout) {
                    Game.textView1.setText(temp1);
                    if(sharedPreferences.getString("mode","1").toString().equals("2"))
                        Game.textView2.setText(temp2);
                }
                sharedPreferences.edit().putString("player1",temp1).putString("player2",temp2).apply();
                MainActivity.textView.setText("Welcome "+temp1);
                Toast.makeText(context,"Names have been changed",Toast.LENGTH_SHORT).show();
            }
        });
        CheckBox checkBox1=new CheckBox(context),checkBox2=new CheckBox(context);
        checkBox1.setChecked(checked1);
        checkBox2.setChecked(checked2);
        checkBox1.setText("Music");
        checkBox1.setTextColor(Color.WHITE);
        checkBox2.setText("Game sounds");
        checkBox2.setTextColor(Color.WHITE);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked1=!checked1;
                if(checked1) {
                    if (MainActivity.onMain || MainActivity.fromAbout)
                        MainActivity.introMusic.start();
                }
                else {
                    MainActivity.introMusic.seekTo(0);
                    MainActivity.introMusic.pause();
                }
                sharedPreferences.edit().putBoolean("music",checked1).apply();
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked2=!checked2;
                sharedPreferences.edit().putBoolean("sound",checked2).apply();
            }
        });
        linearLayout.addView(checkBox1);
        linearLayout.addView(checkBox2);
        linearLayout.addView(breakLine1);
        linearLayout.addView(radioButton3);
        linearLayout.addView(radioButton4);
        linearLayout.addView(breakLine2);
        linearLayout.addView(textView1);
        linearLayout.addView(editText1);
        linearLayout.addView(textView2);
        linearLayout.addView(editText2);
        linearLayout.addView(radioButton5);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.woodborder);
        linearLayout.setPadding(150,150,150,150);
        LinearLayout linearLayout1=new LinearLayout(context);
        linearLayout1.addView(linearLayout);
        linearLayout1.setBackgroundResource(R.drawable.imgbg);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        alertDialog.setView(linearLayout1);
        alertDialog.show();
    }
    public static void help(Context context) {
        alertDialog=new AlertDialog.Builder(context);
        TextView editText=new TextView(context),editText2=new TextView(context);
        LinearLayout linearLayout=new LinearLayout(context);
        editText.setText("\n  The famous pencil-and-paper game on your device ;) .\n\n  " +
                "The aim is to complete (before your opponent do) a row, a column, or a diagonal with either three O's or three X's drawn in the spaces of nine squares grid, otherwise it will be a tie.\n\n  " +
                "Are you excited to try it? Ohh you must be ^.^ .\n\n  Game modes:\n  -Single Player: meet our friend King-XO, it's not going to be easy with him.\n  " +
                "-Multi Player: two players can challenge on the same device.\n\n  You can change names, control music and sounds, and reset scores with the settings.");
        editText.setTextColor(Color.RED);
        editText.setTextSize(16f);
        editText2.setText("\"TIC TAC TOE\"");
        editText2.setTextSize(20f);
        editText2.setTextColor(Color.RED);
        editText2.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT));
        editText2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linearLayout.setPadding(80,80,80,100);
        linearLayout.setBackgroundResource(R.drawable.woodwriting);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(editText2);
        linearLayout.addView(editText);
        alertDialog.setView(linearLayout);
        alertDialog.show();
    }
}

