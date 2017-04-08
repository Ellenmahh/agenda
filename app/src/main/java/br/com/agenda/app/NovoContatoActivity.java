package br.com.agenda.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoContatoActivity extends AppCompatActivity {
    Button btn_salvar;
    Context context;
    EditText edit_nome_contato,edit_telefone_contato,edit_email_contato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        configurarBotaoSalvar();

        edit_nome_contato=(EditText) findViewById(R.id.edit_nome_contato);
        edit_telefone_contato=(EditText) findViewById(R.id.edit_telefone_contato);
        edit_email_contato=(EditText) findViewById(R.id.edit_email_contato);


    }

    private void configurarBotaoSalvar() {
        btn_salvar = (Button) findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inserirDadosBanco();


            }
        });
    }

    private void inserirDadosBanco() {
        //banco de dados de leitura
        SQLiteDatabase db = new DataBaseHelper(context).getWritableDatabase();
        //valores
        ContentValues contentValues = new ContentValues();
        //coluna e nome da caixa que o user vai digitar
        contentValues.put("nome",edit_nome_contato.getText().toString());
        contentValues.put("telefone",edit_telefone_contato.getText().toString());
        contentValues.put("email",edit_email_contato.getText().toString());
        //INSERT NO BANCO: db.insert(nomeDaColuna,null,contentValues)
        db.insert("tblContatos",null,contentValues);

        Toast.makeText(context,"Salvo com sucesso",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }

}
