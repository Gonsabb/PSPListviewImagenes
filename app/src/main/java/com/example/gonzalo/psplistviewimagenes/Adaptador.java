package com.example.gonzalo.psplistviewimagenes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Gonzalo on 30/11/2015.
 */
public class Adaptador extends ArrayAdapter<String> {

    private Context contexto;
    private ArrayList<String> lista;
    private LayoutInflater i;

    public Adaptador(Context contexto, ArrayList<String> lista) {
        super(contexto, R.layout.item, lista);
        this.contexto=contexto;//actividad
        this.lista=lista;//
        i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView tv;
        ViewHolder vh;

        if(convertView==null){
            convertView = i.inflate(R.layout.item, null);
            tv = (TextView) convertView.findViewById(R.id.tv);

            vh = new ViewHolder();
            vh.tv =tv;
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }

        vh.tv.setText(lista.get(position));
        return convertView;
    }

    static class ViewHolder{
        public TextView tv;
    }

}
