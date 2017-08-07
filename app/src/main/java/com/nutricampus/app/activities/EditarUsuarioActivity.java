package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

@java.lang.SuppressWarnings("squid:S1172")
public class EditarUsuarioActivity extends CadastrarUsuarioActivity {

    SharedPreferencesManager session;
    RepositorioUsuario repositorioUsuario;
    Usuario usuario;
    boolean isLogoff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(this);
        repositorioUsuario = new RepositorioUsuario(EditarUsuarioActivity.this);
        usuario = repositorioUsuario.buscarUsuario(session.getCrmvNC(), session.getSenha());

        inicializarCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            voltarActivity();
        else if(item.getItemId() == R.id.acao_delete)
            deletarUsuario();


        return super.onOptionsItemSelected(item);
    }

    private void deletarUsuario() {

        new AlertDialog.Builder(this)
                .setMessage("Excluir usuário")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(EditarUsuarioActivity.this);
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(EditarUsuarioActivity.this);
                        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(EditarUsuarioActivity.this);
                        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(EditarUsuarioActivity.this);
                        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(EditarUsuarioActivity.this);
                        RepositorioProle repositorioProle = new RepositorioProle(EditarUsuarioActivity.this);

                        ArrayList<Propriedade> propriedades = (ArrayList<Propriedade>)
                                repositorioPropriedade.buscarPropriedadesPorUsuario(usuario.getId());

                        int result = repositorioUsuario.removerUsuario(usuario);

                        if (result > 0) {
                            Toast.makeText(EditarUsuarioActivity.this, "Usuário excluido com sucesso", Toast.LENGTH_LONG).show();

                            if (!(propriedades.isEmpty())) {
                                for (Propriedade p : propriedades) {
                                    List<Animal> listAnimal = repositorioAnimal.buscarPorPropridade(p.getId());
                                    for(Animal a : listAnimal) {
                                        repositorioDadosComplAnimal.removerDadosCompl(a.getId());
                                        repositorioProle.removerProle(a.getId());
                                        repositorioProducaoDeLeite.removerProducaoDeLeite(a.getId());
                                        repositorioAnimal.removerAnimal(a);
                                    }

                                    int idProprietario = p.getIdProprietario();
                                    repositorioPropriedade.removerPropriedadePorProprietario(idProprietario);
                                    repositorioProprietario.removerProprietario(idProprietario);
                                    repositorioPropriedade.removerPropriedade(p);
                                }
                            }

                            EditarUsuarioActivity.this.finish();
                            session.logoutUser();

                        }
                        else{
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "Não foi possivel excuir", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void inicializarCampos() {

        edtNome.setText(usuario.getNome());
        edtCpf.setText(usuario.getCpf());
        edtRegistro.setText(usuario.getCrmv());
        edtEmail.setText(usuario.getEmail());
        edtSenha.setText(usuario.getSenha());

        edtCpf.setEnabled(false);
        edtRegistro.setEnabled(false);
        txtAlterSenha.setVisibility(View.VISIBLE);
        edtSenha.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        btnSalvar.setText(R.string.atualizar);

    }

    @Override
    public void criarUsuario(View v) {
        if (!validarDados()) {
            Toast.makeText(EditarUsuarioActivity.this, "Campos inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(senha.equals(usuario.getSenha())))
            isLogoff = true;

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        boolean isAtualizado = repositorioUsuario.atualizarUsuario(usuario);

        if (isAtualizado) {
            if(isLogoff)
                session.logoutUser();
            else
                EditarUsuarioActivity.this.finish();

            Toast.makeText(EditarUsuarioActivity.this,
                    getString(R.string.msg_sucesso_atualizar, usuario.getNome(), ""),
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(EditarUsuarioActivity.this,
                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancelarCriarUsuario(View v) {
        startActivity(new Intent(
                EditarUsuarioActivity.this, ConfigActivity.class));
    }


    @Override
    public void onBackPressed(){
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it = new Intent(
                EditarUsuarioActivity.this, ConfigActivity.class);

        startActivity(it);
        finish();
    }
}
