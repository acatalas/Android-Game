package user.example.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    public static MagatzemPuntuacions magatzem = new MagatzemPuntuacionsArray();
    private Button bJoc;
    private Button bAcercaDe;
    private Button bSortida;
    private Button bConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bJoc = findViewById(R.id.button_play);
        bJoc.setOnClickListener(this);

        bAcercaDe = findViewById(R.id.button_about);
        bAcercaDe.setOnClickListener(this);

        bSortida = findViewById(R.id.button_exit);
        bSortida.setOnClickListener(this);
        bSortida.setOnLongClickListener(this);

        bConfiguracion = findViewById(R.id.button_config);
        bConfiguracion.setOnClickListener(this);
        bConfiguracion.setOnLongClickListener(this);

    }

    //Mostra el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Crea l'objecte en java que representa el menú
        MenuInflater infl = getMenuInflater();
        //Associa el menu creat en XML a l'objecte Java
        infl.inflate(R.menu.menu_main, menu);
        //Indica que es vol visualitzar el menú
        return true;
    }

    //Cada vegada que es selecciona el menú es crida el següent mètode
    //per que tracti els esdeveniments capturats
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.configuracio){
            llancarPreferencies(null);
            return true;
        }
        if(id==R.id.acercaDe){
            llancarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void llancarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }

    public void llancarPreferencies(View view){
        Intent i = new Intent(this, Preferencies.class);
        startActivity(i);
    }

    public void mostrarPreferencies(View view){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Map sharedPreferences = pref.getAll();
        String text = "";
        Iterator it = sharedPreferences.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            text = text + pair.getKey() + ": " + pair.getValue() + "\n";
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void llancarPuntuacions(View view){
        Intent i = new Intent(this, Puntuacions.class);
        startActivity(i);
    }

    public void llancarJoc(View view){
        Intent i = new Intent(this, Joc.class);
        startActivity(i);
    }

    private void tancarAplicacio(){
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_play){
            llancarJoc(v);
        } else if(v.getId() == R.id.button_config){
            llancarPreferencies(v);
        } else if(v.getId() == R.id.button_about){
            llancarAcercaDe(v);
        } else if(v.getId() == R.id.button_exit){
            tancarAplicacio();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId() == R.id.button_config){
            mostrarPreferencies(v);

        } else if(v.getId() == R.id.button_exit) {
            llancarPuntuacions(v);
        }

        return true;
    }
}
