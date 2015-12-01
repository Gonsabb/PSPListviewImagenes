package com.example.gonzalo.psplistviewimagenes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    public ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ListView listView;
    private android.widget.Button btDescargar;
    private EditText etDescargar;
    private String urlimag;
    private ArrayList<String> listaimagenes= new ArrayList<>();
    private Adaptador adap;
    private DescargadorImagenes di;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.etDescargar = (EditText) findViewById(R.id.etDescargar);
        this.btDescargar = (Button) findViewById(R.id.btDescargar);
        this.listView = (ListView) findViewById(R.id.listView);
        init();
    }

    public void init(){
        listView.setAdapter(adap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                irAFoto(position);
            }
        });
    }

    /**************************************************************************************/
    public void irAFoto(int posicion){
        Intent i = new Intent(this,ImgDescargada.class);
        i.putExtra("imagen", bitmaps.get(posicion));
        startActivity(i);
    }

    public void buscarImagenes(View v) {
        urlimag = etDescargar.getText().toString();
        di = new DescargadorImagenes();
        di.execute(urlimag);
    }

    public ArrayList<String> descargar(String urlpagina) {
        try {
            URL url = new URL(urlpagina);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea;
            ArrayList<String> out = new ArrayList<>();
            urlimag = etDescargar.getText().toString();
            while ((linea = in.readLine()) != null) {
                if (linea.contains("<img src=")) {
                    linea=linea.substring(linea.indexOf("<img src=") + 10);
                    linea=linea.substring(0,linea.indexOf('"'));
                    if(linea.indexOf("/")==0) {
                        linea = urlimag + linea.substring(1);
                    }
                    out.add(linea.trim());
                }
            }
            in.close();
            return out;
        } catch (IOException ex) {
        }
        return null;
    }

    /*******************************************************************************************/
    public class DescargadorImagenes extends AsyncTask<String, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            listaimagenes = descargar(params[0]);
            URL url = null;
            try {
                for(String i : listaimagenes) {
                    url = new URL(i);
                    bitmaps.add(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return listaimagenes;
        }

        @Override
        protected void onPostExecute(ArrayList<String> lista) {
            listView = (ListView) findViewById(R.id.listView);
            adap = new Adaptador(Principal.this, listaimagenes);
            listView.setAdapter(adap);
            adap.notifyDataSetChanged();
        }
    }

}
