package br.com.agenda.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetalhesActivity extends AppCompatActivity {
    TextView txt_nome_contato,txt_telefone_contato,txt_email_contato;
    Integer idContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txt_nome_contato = (TextView) findViewById(R.id.txt_nome_contato);
        txt_telefone_contato = (TextView) findViewById(R.id.txt_telefone_contato);
        txt_email_contato = (TextView) findViewById(R.id.txt_email_contato);

        Intent intent = getIntent();
        if(intent != null){
            idContato = intent.getIntExtra("idContato",0);// o 0 é um numero padrão para caso nao retorne nada
            buscarContatoBanco(idContato);

        }

    }

    //criação do menu de detalhes (tres pontinhos do lado)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // esse codigo diz: busca aquele xml como menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes,menu);
        return true;

    }

    // processar os clicks do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_editar:
                //clicou no editar
                Intent intent = new Intent(this,EditarActivity.class);
                // precisar pegar o _id para editar oq o user clicou
                intent.putExtra("idContato", idContato);
                startActivity(intent );
                break;
            case R.id.menu_excluir:
                confirmarExcluir();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void confirmarExcluir(){
        //Builder vai criar a caixinha
        new AlertDialog.Builder(this)
                .setTitle("EXCLUIR") // titulo da caixa
                .setIcon(android.R.drawable.ic_delete) // imagem da caixa
                .setMessage("Tem certeza que deseja deletar esse item?") // mensagem que vai aparecer na caixa
                //se a resposta for positiva vai chamar o metodo de excluir atraves do listener
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluirContato();
                    }
                })
                //se a resposta for negativa
                .setNegativeButton("Não",null)
                .show();
    }
    private void excluirContato() {
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
        // modo escrita: para modificar a estrutura do banco com este comando
        //nome da tabela, o parametro
        db.delete("tblContatos","_id = ?",new String[]{idContato.toString() } );
        Toast.makeText(this,"EXCLUIDO COM SUCESSO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
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

            txt_nome_contato.setText(nomeContato);
            txt_telefone_contato.setText(telefoneContato);
            txt_email_contato.setText(emailContato);
            cursor.close();
        }
    }

}
