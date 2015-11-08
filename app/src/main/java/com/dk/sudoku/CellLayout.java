package com.dk.sudoku;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Layout for each cell
 */
public class CellLayout extends View implements AdapterView.OnItemSelectedListener {

    private Context context;
    private Cell cell;
    TextView valueView;
    GridView pencilView;
    public static int positionSelected;

    public CellLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CellLayout(Context context, Cell cell) {
        super(context);
        this.context = context;
        this.cell = cell;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int[] coordinates = Sudoku.getCoordinates(position);

        Toast.makeText(this.context,
                String.format("Item at {%d, %d} was selected", coordinates[0], coordinates[1]),
                Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected (AdapterView<?> av) {}

}
