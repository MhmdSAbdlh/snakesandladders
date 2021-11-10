package com.mhmdsabdlh.snakesandladders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static String p1Name = "", p2Name = "";
    private static final String[] NAMES = new String[]{
            "Mhmd", "Maya", "Hadi", "Ali", "Malak", "Hussein", "Maryam",
            "Hassan", "Serine", "Hala", "Lana", "Fatima", "Yasmine", "Sabine"};
    private Switch toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggle = findViewById(R.id.switch1);
    }

    public void changeNames(View view) {
        //Player names alert in start
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, NAMES);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final AutoCompleteTextView titleBox = new AutoCompleteTextView(this);
        titleBox.setHint("Blue Player Name");
        titleBox.setAdapter(adapter);
        layout.addView(titleBox);
        final AutoCompleteTextView descriptionBox = new AutoCompleteTextView(this);
        descriptionBox.setHint("Red Player Name");
        descriptionBox.setAdapter(adapter);
        layout.addView(descriptionBox);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Player's Name").setMessage("Enter your name")
                .setView(layout)
                .setPositiveButton("Ok", (dialog, whichButton) -> {
                    p1Name = titleBox.getText().toString();
                    p2Name = descriptionBox.getText().toString();
                })
                .show();
    }

    public void singlePlayer(View view) {
        if (!toggle.isChecked()) {
            Intent onePlayer = new Intent(MainActivity.this, Map2_PC.class);
            onePlayer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            onePlayer.putExtra("flag", "modify");
            MainActivity.this.startActivity(onePlayer);
        } else {
            Intent onePlayer = new Intent(MainActivity.this, Map1_PC.class);
            onePlayer.putExtra("NewClicked", true);
            onePlayer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(onePlayer);
        }
    }

    public void multiPlayer(View view) {
        if (p1Name.isEmpty())
            p1Name = "Player 1";
        if (p2Name.isEmpty())
            p2Name = "Player 2";
        if (!toggle.isChecked()) {
            Intent twoPlayer = new Intent(MainActivity.this, Map2.class);
            twoPlayer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            twoPlayer.putExtra("flag", "modify");
            MainActivity.this.startActivity(twoPlayer);
        } else {
            Intent twoPlayer = new Intent(MainActivity.this, Map1.class);
            twoPlayer.putExtra("NewClicked", true);
            twoPlayer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.this.startActivity(twoPlayer);
        }
    }

    public void mapTwo(View view) {
        toggle.setChecked(true);
    }

    public void mapOne(View view) {
        toggle.setChecked(false);
    }
}