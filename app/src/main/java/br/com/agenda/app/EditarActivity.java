package br.com.agenda.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {
    Integer idContato;
    EditText edit_nome_contato,edit_telefone_contato,edit_email_contato;
    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_nome_contato = (EditText) findViewById(R.id.edit_nome_contato);
        edit_telefone_contato = (EditText) findViewById(R.id.edit_telefone_contato);
        edit_email_contato = (EditText) findViewById(R.id.edit_email_contato);
        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        Intent intent = getIntent();
        if(intent != null){
            idContato = intent.getIntExtra("idContato",0);// o 0 é um numero padrão para caso nao retorne nada
            buscarContatoBanco(idContato);
        }
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEdicao();
            }

            
        });
    }

    private void salvarEdicao() {
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase(); // editar tbn é escrita
        // colocar os valores da edittext na tabela
        ContentValues valores = new ContentValues();
        valores.put("nome",edit_nome_contato.getText().toString());
        valores.put("telefone",edit_telefone_contato.getText().toString());
        valores.put("email",edit_email_contato.getText().toString());

        // nome da tabela, valores(preenchidos acima), id
        db.update("tblContatos",valores,"_id=?", new String[] {idContato.toString()});

        Toast.makeText(this,"editado com sucesssssss!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DetalhesActivity.class);

        //enviar id denovo para a tela de detalhes atraves do intent.putExtra
        intent.putExtra("idContato",idContato);
        startActivity(intent);
    }

    private void buscarContatoBanco(Integer idContato) {
        // select contato
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase(); // leitura
        Cursor cursor = db.rawQuery("SELECT * FROM tblContatos WHERE _id=?", // ? significa passagem de parametro
                new String[] {idContato.toString()}
        );
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            String nomeContato = cursor.getString(1);
            String telefoneContato = cursor.getString(2);
            String emailContato = cursor.getString(3);

            edit_nome_contato.setText(nomeContato);
            edit_telefone_contato.setText(telefoneContato);
            edit_email_contato.setText(emailContato);
            cursor.close();
        }
    }

}
