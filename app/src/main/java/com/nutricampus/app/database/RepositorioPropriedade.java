package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Propriedade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 05/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RepositorioPropriedade {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioPropriedade(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirPropriedade(Propriedade propriedade) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(propriedade);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PROPRIEDADE, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<Propriedade> getListaPropriedades(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Propriedade> propriedades = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Propriedade p = new Propriedade();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)));
                    p.setNome(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)));
                    p.setTelefone(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)));
                    p.setLogradouro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)));
                    p.setNumero(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)));
                    p.setBairro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)));
                    p.setCidade(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)));
                    p.setEstado(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)));
                    p.setCep(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)));
                    p.setIdProprietario(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)));
                    p.setIdUsuario(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_USUARIO)));

                    //Verificar o atributo id_proprietario
                    propriedades.add(p);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioPropriedade", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return propriedades;
    }

    public Propriedade buscarPropriedade(String nome) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.PROPRIEDADE_COL_NOME + "= ?";
        String[] valoresWhere = new String[]{String.valueOf(nome)};


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PROPRIEDADE, new String[]{
                        SQLiteManager.PROPRIEDADE_COL_ID,
                        SQLiteManager.PROPRIEDADE_COL_NOME,
                        SQLiteManager.PROPRIEDADE_COL_TELEFONE,
                        SQLiteManager.PROPRIEDADE_COL_LOGRADOURO,
                        SQLiteManager.PROPRIEDADE_COL_NUMERO,
                        SQLiteManager.PROPRIEDADE_COL_BAIRRO,
                        SQLiteManager.PROPRIEDADE_COL_CIDADE,
                        SQLiteManager.PROPRIEDADE_COL_ESTADO,
                        SQLiteManager.PROPRIEDADE_COL_CEP,
                        SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO,
                        SQLiteManager.PROPRIEDADE_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return new Propriedade(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_USUARIO)));
        }
        cursor.close();
        return null;
    }

    public Propriedade buscarPropriedade(int id) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.PROPRIEDADE_COL_ID + "= ?";
        String[] valoresWhere = new String[]{String.valueOf(id)};


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PROPRIEDADE, new String[]{
                        SQLiteManager.PROPRIEDADE_COL_ID,
                        SQLiteManager.PROPRIEDADE_COL_NOME,
                        SQLiteManager.PROPRIEDADE_COL_TELEFONE,
                        SQLiteManager.PROPRIEDADE_COL_LOGRADOURO,
                        SQLiteManager.PROPRIEDADE_COL_NUMERO,
                        SQLiteManager.PROPRIEDADE_COL_BAIRRO,
                        SQLiteManager.PROPRIEDADE_COL_CIDADE,
                        SQLiteManager.PROPRIEDADE_COL_ESTADO,
                        SQLiteManager.PROPRIEDADE_COL_CEP,
                        SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO,
                        SQLiteManager.PROPRIEDADE_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return new Propriedade(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_USUARIO)));
        }
        cursor.close();
        return null;
    }

    public List<Propriedade> buscarTodasPropriedades() {
        return this.getListaPropriedades(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIEDADE);
    }

    /**
     * Busca pelas propriedadesBD que contem 'o valor de <b>nome</b> e pertencem ao usuario <b>usuario</b>
     *
     * @param nome      Nome da propriedade
     * @param idUsuario id do usuario logado no app
     * @return List
     */
    public List<Propriedade> buscarPropriedadesPorNome(String nome, int idUsuario) {
        return this.getListaPropriedades(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIEDADE +
                " WHERE (" + SQLiteManager.PROPRIEDADE_COL_ID_USUARIO + " = " + idUsuario + ") AND (" +
                SQLiteManager.PROPRIEDADE_COL_NOME + " LIKE '%" + nome + "%')");
    }

    public List<Propriedade> propriedadesOfProprietario(int idProprietario) {
        return this.getListaPropriedades(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIEDADE +
                " WHERE " + SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO + " = " + idProprietario);
    }

    public List<Propriedade> buscarPropriedadesPorUsuario(int idUsuario) {
        return this.getListaPropriedades(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_PROPRIEDADE +
                " WHERE " + SQLiteManager.PROPRIEDADE_COL_ID_USUARIO + " = " + idUsuario);
    }


    public boolean atualizarPropriedade(Propriedade propriedade) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(propriedade);

        int retorno = bancoDados.update(SQLiteManager.TABELA_PROPRIEDADE,
                dados, SQLiteManager.PROPRIEDADE_COL_ID + " = ?",
                new String[]{String.valueOf(propriedade.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    public int removerPropriedade(Propriedade propriedade) {
        return excluirRegistros(propriedade.getId(), 1);
    }

    public int removerPropriedadePorProprietario(int idProprietario) {
        return excluirRegistros(idProprietario, 2);
    }

    private int excluirRegistros(int id, int tipo) {
        bancoDados = gerenciador.getWritableDatabase();
        String coluna;

        //tipo = 1 (ID propriedade) | tipo = 2 (ID Usuario) | tipo = 3 (ID Proprietário)
        if (tipo == 1)
            coluna = SQLiteManager.PROPRIEDADE_COL_ID;
        else
            coluna = SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO;


        int result = bancoDados.delete(SQLiteManager.TABELA_PROPRIEDADE,
                coluna + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return result;
    }

    public void removerTodos() {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_PROPRIEDADE,
                SQLiteManager.PROPRIEDADE_COL_ID + " > ? ",
                new String[]{String.valueOf("-1")});

        bancoDados.close();

    }


    private ContentValues getContentValues(Propriedade propriedade) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PROPRIEDADE_COL_NOME, propriedade.getNome());
        dados.put(SQLiteManager.PROPRIEDADE_COL_TELEFONE, propriedade.getTelefone());
        dados.put(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO, propriedade.getLogradouro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_NUMERO, propriedade.getNumero());
        dados.put(SQLiteManager.PROPRIEDADE_COL_BAIRRO, propriedade.getBairro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CIDADE, propriedade.getCidade());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ESTADO, propriedade.getEstado());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CEP, propriedade.getCep());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO, propriedade.getIdProprietario());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ID_USUARIO, propriedade.getIdUsuario());

        return dados;
    }


}
