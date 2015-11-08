package com.dk.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/7/2015.
 */
public class PencilViewAdapter extends BaseAdapter {

    private Cell cell;
    private final Context context;

    public PencilViewAdapter(Context context) {
        this.context = context;
    }

    public PencilViewAdapter(Context context, Cell cell) {
        this.context = context;
        this.cell = cell;
    }

    public void updateCell(Cell cell) {
        ThreadPreconditions.checkOnMainThread();
        this.cell = cell;
        notifyDataSetChanged();
    }

    public int getCount() {
        return cell.getPencil().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pencil_layout, parent, false);
        }

        ArrayList<String> pencil = cell.asArrayList();
        ((TextView) convertView).setText(pencil.get(position));

        return convertView;
    }

}
