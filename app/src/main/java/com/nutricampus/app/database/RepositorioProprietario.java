package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Proprietario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 05/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RepositorioProprietario {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioProprietario(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirProprietario(Proprietario proprietario) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PROPRIETARIO_COL_CPF, proprietario.getCpf());
        dados.put(SQLiteManager.PROPRIETARIO_COL_NOME, proprietario.getNome());
        dados.put(SQLiteManager.PROPRIETARIO_COL_EMAIL, proprietario.getEmail());
        dados.put(SQLiteManager.PROPRIETARIO_COL_TELEFONE, proprietario.getTelefone());

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PROPRIETARIO, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    public Proprietario buscarProprietario(String cpf) {
        bancoDados = gerenciador.getReadableDatabase();


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PROPRIETARIO, new String[]{
                        SQLiteManager.PROPRIETARIO_COL_ID,
                        SQLiteManager.PROPRIETARIO_COL_CPF,
                        SQLiteManager.PROPRIETARIO_COL_NOME,
                        SQLiteManager.PROPRIETARIO_COL_EMAIL,
                        SQLiteManager.PROPRIETARIO_COL_TELEFONE},
                SQLiteManager.PROPRIETARIO_COL_CPF + "= ?",
                new String[]{String.valueOf(cpf)}, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return new Proprietario(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_CPF)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_TELEFONE)));

        }
        cursor.close();
        return null;
    }

    public Proprietario buscarProprietario(Integer id) {
        bancoDados = gerenciador.getReadableDatabase();

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PROPRIETARIO, new String[]{
                        SQLiteManager.PROPRIETARIO_COL_ID,
                        SQLiteManager.PROPRIETARIO_COL_CPF,
                        SQLiteManager.PROPRIETARIO_COL_NOME,
                        SQLiteManager.PROPRIETARIO_COL_EMAIL,
                        SQLiteManager.PROPRIETARIO_COL_TELEFONE},
                SQLiteManager.PROPRIETARIO_COL_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return new Proprietario(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_CPF)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_TELEFONE)));

        }
        cursor.close();
        return null;
    }


    private List<Proprietario> getListaProprietarios(String query) {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Proprietario> proprietarios = new ArrayList<>();

        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Proprietario p = new Proprietario();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_ID)));
                    p.setCpf(c.getString(c.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_CPF)));
                    p.setNome(c.getString(c.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_NOME)));
                    p.setEmail(c.getString(c.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_EMAIL)));
                    p.setTelefone(c.getString(c.getColumnIndex(SQLiteManager.PROPRIETARIO_COL_TELEFONE)));
                    proprietarios.add(p);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioProprietario", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return proprietarios;

    }

    public List<Proprietario> buscarTodosProprietarios(String nome) {
        return this.getListaProprietarios(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIETARIO +
                " WHERE " + SQLiteManager.PROPRIETARIO_COL_NOME + " LIKE '%" + nome + "%'" +
                SQLiteManager.ORDER_BY + SQLiteManager.PROPRIETARIO_COL_NOME);
    }


    public List<Proprietario> buscarTodosProprietarios() {
        return getListaProprietarios(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIETARIO +
                SQLiteManager.ORDER_BY + SQLiteManager.PROPRIETARIO_COL_NOME);
    }

    public boolean atualizarProprietario(Proprietario proprietario) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.PROPRIETARIO_COL_NOME, proprietario.getNome());
        dados.put(SQLiteManager.PROPRIETARIO_COL_EMAIL, proprietario.getEmail());
        dados.put(SQLiteManager.PROPRIETARIO_COL_TELEFONE, proprietario.getTelefone());

        int retorno = bancoDados.update(SQLiteManager.TABELA_PROPRIETARIO,
                dados, SQLiteManager.PROPRIETARIO_COL_ID + " = ?",
                new String[]{String.valueOf(proprietario.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    public int removerProprietario(Proprietario proprietario) {
        return excluirRegistros(proprietario.getId());
    }

    public int removerProprietario(int id) {
        return excluirRegistros(id);
    }

    private int excluirRegistros(int id) {
        bancoDados = gerenciador.getWritableDatabase();

        int resultado = bancoDados.delete(SQLiteManager.TABELA_PROPRIETARIO,
                SQLiteManager.PROPRIETARIO_COL_ID + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return resultado;
    }

    public void removerTodos() {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_PROPRIETARIO,
                SQLiteManager.PROPRIETARIO_COL_ID + " > ? ",
                new String[]{String.valueOf("-1")});

        bancoDados.close();

    }


}
