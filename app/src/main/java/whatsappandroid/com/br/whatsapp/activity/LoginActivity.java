package whatsappandroid.com.br.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import whatsappandroid.com.br.whatsapp.R;
import whatsappandroid.com.br.whatsapp.helper.Permissao;
import whatsappandroid.com.br.whatsapp.helper.Preferences;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codigoInternacional;
    private EditText codigoEstadual;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        nome = (EditText) findViewById(R.id.edit_nome);
        telefone = (EditText) findViewById(R.id.edit_telefone);
        codigoInternacional = (EditText) findViewById(R.id.edit_codigo_internacional);
        codigoEstadual = (EditText) findViewById(R.id.edit_codigo_estadual);
        cadastrar = (Button) findViewById(R.id.button_cadastrar);


        SimpleMaskFormatter smfCodigoInternacional = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter smfCodigoEstadual = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("N NNNN-NNNN");

        MaskTextWatcher mtwCodigoInternacional = new MaskTextWatcher(codigoInternacional, smfCodigoInternacional);
        MaskTextWatcher mtwCodigoEstadual = new MaskTextWatcher(codigoEstadual, smfCodigoEstadual);
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(telefone, smfTelefone);

        codigoInternacional.addTextChangedListener(mtwCodigoInternacional);
        codigoEstadual.addTextChangedListener(mtwCodigoEstadual);
        telefone.addTextChangedListener(mtwTelefone);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomToken;
                Random random = new Random();
                String token;

                String telefoneSemFormatacao;
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                            codigoInternacional.getText().toString() +
                            codigoEstadual.getText().toString() +
                            telefone.getText().toString();

                telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace(" ", "");

                //GerarToken
                /**TODO: REALIZAR O GERENCIAMENTO DE TOKEN VIA WEBSERVICE
                **Importante ressaltar que não é recomendado deixar o gerenciamento da geração de
                **token por meio do próprio app. O correto é realizar via WS.
                **Farei essa atualização quando eu integrar o WhatsMobile com o Web.**/
                randomToken = random.nextInt(9999 - 1000) + 1000;
                token = String.valueOf(randomToken);
                String mensagemEnvio = "Whatsapp! Código de confirmação: " + token;


                //Salvar os dados para validação
                Preferences preferences = new Preferences(LoginActivity.this);
                preferences.salvarPreferences(nomeUsuario, telefoneSemFormatacao, token);


              //EnviaSMS
               telefoneSemFormatacao = "5554";
               boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);

                if(enviadoSMS) {
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente!", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

            //Envio de SMS
            private boolean enviaSMS(String telefone, String mensagem) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telefone, null, mensagem, null, null);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            for(int resultado : grantResults){
                if(resultado == getPackageManager().PERMISSION_DENIED){
                    alertaValidacaoPermissao();
                }
            }
        }

        private void alertaValidacaoPermissao(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permissão foi negada");
            builder.setMessage("Para utilizar o whatsapp, é necessário aceitar a permissão!");

            builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

}


