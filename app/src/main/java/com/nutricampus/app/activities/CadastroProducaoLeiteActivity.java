package com.nutricampus.app.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.utils.Mascara;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroProducaoLeiteActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    @BindView(R.id.input_lactose)
    EditText inputLactose;
    @BindView(R.id.input_quantidade_leite)
    EditText inputQuantidadeLeite;
    @BindView(R.id.input_gordura)
    EditText inputGordura;
    @BindView(R.id.input_proteina_bruta)
    EditText inputProteinaBruta;
    @BindView(R.id.input_proteina_verdadeira)
    EditText inputProteinaVerdadeira;
    @BindView(R.id.input_data)
    EditText inputData;

    private Calendar data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_producao_leite);

        ButterKnife.bind(this);
        inicializaCampoData();
    }

    private void inicializaCampoData() {
        Calendar calendario = Calendar.getInstance();
        data = Calendar.getInstance();
        data.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE));
        Log.w("YEAR", String.valueOf(calendario.get(Calendar.YEAR)));
        // O valor do mês pelo Calendar varia entre 0 e 11, por isso soma +1
        if (inputData.getText().toString().equals(""))
            inputData.setText(Mascara.dataFormatada(data));
    }

    public void showDatePickerDialog(View v) {
        Calendar cDefault = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)
        );

        Calendar cMax = Calendar.getInstance();
        cMax.set(cMax.get(Calendar.YEAR), cMax.get(Calendar.MONTH), cMax.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMaxDate(cMax.getTime().getTime());

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
        data.set(ano, mes, dia);

        inputData.setText(Mascara.dataFormatada(data));
        inputData.setError(null);
    }

    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        ProducaoDeLeite producao = new ProducaoDeLeite(this.data,
                1,
                Float.valueOf(inputQuantidadeLeite.getText().toString()),
                Float.valueOf(inputLactose.getText().toString()),
                Float.valueOf(inputProteinaVerdadeira.getText().toString()),
                Float.valueOf(inputProteinaBruta.getText().toString()),
                Float.valueOf(inputGordura.getText().toString()));

        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(this);
        int result = repositorio.inserirProducaoDeLeite(producao);


        if (result > 0) {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }

    public boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(inputData);
        campos.add(inputQuantidadeLeite);
        campos.add(inputProteinaVerdadeira);
        campos.add(inputProteinaBruta);
        campos.add(inputLactose);
        campos.add(inputGordura);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }


        return valido;
    }

}