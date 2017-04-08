package br.com.agenda.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    ListView list_view_contatos;
    List<String> listaContatos = new ArrayList<>();
    List<Integer> listaIdsContatos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        list_view_contatos= (ListView) findViewById(R.id.list_view_contatos);

        configurarBotaoFlutuante();
        buscarDadosNoBanco();
        preencherAdapter();
        configurarClickLista();
    }

    private void configurarClickLista() {
        list_view_contatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pegar _id na posição clicada
                Integer idContato = listaIdsContatos.get(position);
                Intent intent = new Intent(context,DetalhesActivity.class);
                intent.putExtra("idContato", idContato);
                startActivity(intent);

            }
        });
    }
    private void preencherAdapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                listaContatos
        );

        list_view_contatos.setAdapter(arrayAdapter);
    }
    private void buscarDadosNoBanco() {
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        // inicio do comando select
        String comandoSQL = "SELECT * FROM tblContatos;";
        Cursor cursor = db.rawQuery(comandoSQL,null);

        // verificar se veio alguma registro
        if(cursor.getCount() > 0 ){
            // move o cursor para o primeiro registro
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount() ; i++) {
                listaIdsContatos.add(cursor.getInt(0)); // buscar o _id
                listaContatos.add(cursor.getString(1));// buscar o nome
                cursor.moveToNext();
            }
        }

        cursor.close();
        // termino do comando select
    }
    private void configurarBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context,NovoContatoActivity.class);
                startActivity(intent);
            }
        });
    }

}
