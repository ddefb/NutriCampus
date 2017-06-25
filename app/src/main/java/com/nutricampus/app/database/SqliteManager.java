package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Diego Bezerra on 20/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class SQLiteManager extends SQLiteOpenHelper{
    /*
    http://randomkeygen.com/
    TODO trocar keys a cada versão do app
     */

    /* Nome do Banco de Dados */
    private static final String NOME_BANCO = "NutriCampusBD";
    private static final int VERSAO_BANCO = 1;

    /* Modo de acesso ao banco de dados
     *
     * Configura as permissões de acesso ao banco de dados.
     *
     * 0 - Modo privado (apenas essa aplicação pode usar o banco).
     * 1 - Modo leitura para todos (outras aplicações podem usar o banco).
     * 2 - Modo escrita para todos (outras aplicações podem usar o banco). */
    private final int DATABASE_ACESS = 0;

    protected static Context context;
    public static final String TABELA_USUARIO = "usuario";
    public static final String USUARIO_COL_ID = "_id";
    public static final String USUARIO_COL_CRMV = "crmv";
    public static final String USUARIO_COL_CPF = "cpf";
    public static final String USUARIO_COL_NOME = "nome";
    public static final String USUARIO_COL_EMAIL = "email";
    public static final String USUARIO_COL_SENHA = "senha";

    private final String SQL_CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO + "(" +
            USUARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USUARIO_COL_CRMV + " TEXT NOT NULL UNIQUE, " +
            USUARIO_COL_CPF + " TEXT NOT NULL UNIQUE," +
            USUARIO_COL_NOME + " TEXT NOT NULL, " +
            USUARIO_COL_EMAIL + " TEXT NOT NULL, " +
            USUARIO_COL_SENHA + " TEXT NOT NULL);";

    public SQLiteManager(Context context) {
        super(context, NOME_BANCO, null,VERSAO_BANCO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /*
    public void removerBdVersoesAnteriores() {
        final String bd1 = "nutriCampusDB";
        final String bd2 = "y=03p*48$4Zay;.9NutriCampusDB";

        if (NOME_BANCO.equals(bd1)) {
            this.context.deleteDatabase(bd1);
        } else if (NOME_BANCO.equals(bd2)) {
            this.context.deleteDatabase(bd2);
        }
    }
    */

}
