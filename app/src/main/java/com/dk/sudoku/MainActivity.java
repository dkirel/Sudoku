package com.dk.sudoku;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadGame(View view) {

        int givens;
        View errorMessage;

        errorMessage = (View) findViewById(R.id.error_message);
        errorMessage.setVisibility(View.GONE);

        try {

            givens = Sudoku.EASY_GIVENS;

            switch(view.getId()) {
                case R.id.button_EASY: givens = Sudoku.EASY_GIVENS;
                    break;
                case R.id.button_MEDIUM: givens= Sudoku.MEDIUM_GIVENS;
                    break;
                case R.id.button_HARD: givens=Sudoku.HARD_GIVENS;
                    break;
                case R.id.button_EXPERT: givens=Sudoku.EXPERT_GIVENS;
                    break;
            }

            Sudoku sudoku = new Sudoku(getResources().openRawResource(R.raw.solution1), givens);
            setContentView(R.layout.sudoku_grid);

        }
        catch(Exception e) {
            setContentView(R.layout.activity_main);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

}
