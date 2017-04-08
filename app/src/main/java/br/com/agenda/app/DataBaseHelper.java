package br.com.agenda.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15251365 on 08/02/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    // final : uma vez definida não pode mais mudar o nome, SEMPRE COLOCAR EM MAIUSCULO
    private static final String NOME_BANCO="agendaContatos.db";
    private static final int VERSAO=1;

    //construtor
    public DataBaseHelper(Context context){
        super(context,NOME_BANCO,null,VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblContatos(" +
                "_id INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "telefone TEXT," +
                "email TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
