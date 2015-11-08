package com.dk.sudoku;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<LinearLayout> cellLayouts;
    private Context context;

    public GridViewAdapter(Context context) {
        this.context = context;
    }

    public void updateAdapter(int position, View view) {

        Cell cell = GameActivity.game.getCell(position);
        int value = cell.getValue();

        TextView textView = ((TextView) view.findViewById(R.id.value_view));
        GridView gridView = (GridView) view.findViewById(R.id.pencil_view);


        if (cell.getPencil().size() > 1) {
            gridView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }

        textView.setText(value == 0 ? "" : Integer.toString(value));
        ((PencilViewAdapter) (gridView.getAdapter())).updateCell(cell);
        notifyDataSetChanged();
    }

    public int getCount() {
        return 81;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int value;
        Cell cell;
        ArrayList<String> list;

        cell = GameActivity.game.getCell(position);
        value = cell.getValue();
        list = cell.asArrayList();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_layout, null);
            ((GridView) convertView.findViewById(R.id.pencil_view)).setAdapter(new PencilViewAdapter(context, cell));
        }

        TextView textView = ((TextView) convertView.findViewById(R.id.value_view));
        GridView gridView = (GridView) convertView.findViewById(R.id.pencil_view);

        textView.setText(value == 0 ? "" : Integer.toString(value));
        ((PencilViewAdapter) gridView.getAdapter()).updateCell(cell);

        if (cell.getPencil().size() > 1) {
            gridView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
        else {
            textView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }

        if(cell.isGiven())
            ((TextView) convertView.findViewById(R.id.value_view)).setTypeface(null, Typeface.ITALIC);;

        return convertView;
    }

}
