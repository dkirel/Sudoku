package com.dk.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static Sudoku game;
    public static int[] cellSelected = new int[2];
    private GridView gameView;
    private GridViewAdapter gridViewAdapter;
    private int positionSelected;
    private View viewSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        int givens;

        if (extras != null) {
            givens = extras.getInt("GIVENS");
        }
        else {
            givens = Sudoku.EASY_GIVENS;
        }

        try {
            game = new Sudoku(getResources().openRawResource(R.raw.solution1), givens);
        }
        catch(Exception e) {
            setContentView(R.layout.activity_main);

            View errorMessage = (View) findViewById(R.id.error_message);
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        gameView = (GridView) findViewById(R.id.sudoku_grid);
        gridViewAdapter = new GridViewAdapter(this);
        gameView.setAdapter(gridViewAdapter);

        positionSelected = -1;

        gameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (viewSelected != null) {
                    viewSelected.setBackgroundResource(R.color.white);
                    viewSelected.postInvalidate();
                    viewSelected = null;
                }

                positionSelected = position;
                viewSelected = v;

                viewSelected.setBackgroundResource(R.drawable.selected_cell_border);
                viewSelected.postInvalidate();
            }

        });
    }

    public void initMainActivity(View view) {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    public void updateGrid(View view) {

        Cell cell = game.getCell(positionSelected);
        int buttonVal = 0;

        if (positionSelected == -1 || cell.isGiven()) {
            Toast.makeText(this, "Please select a valid cell", Toast.LENGTH_SHORT).show();
        }

        switch(view.getId()) {
            case R.id.number_button_1: buttonVal = 1;
                break;
            case R.id.number_button_2: buttonVal = 2;
                break;
            case R.id.number_button_3: buttonVal = 3;
                break;
            case R.id.number_button_4: buttonVal = 4;
                break;
            case R.id.number_button_5: buttonVal = 5;
                break;
            case R.id.number_button_6: buttonVal = 6;
                break;
            case R.id.number_button_7: buttonVal = 7;
                break;
            case R.id.number_button_8: buttonVal = 8;
                break;
            case R.id.number_button_9: buttonVal = 9;
                break;
            case R.id.number_button_10: buttonVal = 10;
                break;
        };

        //Update values
        game.setCell(positionSelected, buttonVal);
        gridViewAdapter.updateAdapter(positionSelected, viewSelected);

        if (game.isLegal(true)) {
            setContentView(R.layout.win_layout);
        }
    }

}
