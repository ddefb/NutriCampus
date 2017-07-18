package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kellison on 21/06/2017.
 * For project NutriCampus.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    SharedPreferencesManager session;

    // Pega a referência para as views

    @BindView(R.id.input_usuario) EditText editTextUsuario;
    @BindView(R.id.input_senha) EditText editTextSenha;
    @BindView(R.id.btn_login)   Button buttonEntrar;
    @BindView(R.id.link_cadastro) TextView linkCadastro;
    @BindView(R.id.link_esqueceu_senha) TextView linkEsqueceuSeha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SharedPreferencesManager(this);

        if (session.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void cadastroOnClick(View view) {
        Intent intent = new Intent(this, CadastrarUsuarioActivity.class);

        startActivity(intent);
    }

    public void recuperarSenhaOnClick(View view) {
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    public void entrarOnClick(View view) {
        Log.d(TAG, "Login");

        if (!validaDados()) {
            falhaLogin("");
            return;
        }

        buttonEntrar.setEnabled(false);

        String usuario = this.editTextUsuario.getText().toString();
        String senha = this.editTextSenha.getText().toString();
        Usuario usuarioLogado = buscarUsuario(usuario, senha);

        if (usuarioLogado != null) {
            session.createLoginSession(usuarioLogado.getId(), usuarioLogado.getNome(), usuarioLogado.getEmail(), usuarioLogado.getSenha());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            falhaLogin(getString(R.string.msg_dados_login_invalido));
            buttonEntrar.setEnabled(true);
        }

    }

    public Usuario buscarUsuario(String usuarioValor, String senhaValor) {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());

        return repositorioUsuario.buscarUsuario(usuarioValor,senhaValor);
    }

    public void falhaLogin(String mensagem) {
        String msg = mensagem.isEmpty() ? "" : (", " + mensagem);
        Toast.makeText(LoginActivity.this, getString(R.string.msg_falha_login) + msg, Toast.LENGTH_LONG).show();

        buttonEntrar.setEnabled(true);
    }

    public boolean validaDados() {
        boolean valido = true;

        String usuario = this.editTextUsuario.getText().toString();
        String password = this.editTextSenha.getText().toString();

        if (usuario.isEmpty()){
            this.editTextUsuario.setError(getString(R.string.msg_erro_campo));
            valido = false;}
        else if(usuario.length() < 4) {
            this.editTextUsuario.setError(getString(R.string.msg_erro_crz));
            valido = false;
        } else {
            this.editTextUsuario.setError(null);
        }

        if (password.isEmpty()){
            this.editTextSenha.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }
        else if(password.length() < 5) {
            this.editTextSenha.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this.editTextSenha.setError(null);
        }

        return valido;
    }


}