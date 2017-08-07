package whatsappandroid.com.br.whatsapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import whatsappandroid.com.br.whatsapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone;
    private EditText codigoInternacional;
    private EditText codigoEstadual;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telefone = (EditText) findViewById(R.id.edit_telefone);
        codigoInternacional = (EditText) findViewById(R.id.edit_codigo_internacional);
        codigoEstadual = (EditText) findViewById(R.id.edit_codigo_estadual);
        cadastrar = (Button) findViewById(R.id.button_cadastrar);


        SimpleMaskFormatter smfCodigoInternacional = new SimpleMaskFormatter("+NN");
        MaskTextWatcher mtwCodigoInternacional = new MaskTextWatcher(codigoInternacional, smfCodigoInternacional);
        codigoInternacional.addTextChangedListener(mtwCodigoInternacional);


        SimpleMaskFormatter smfCodigoEstadual = new SimpleMaskFormatter("NN");
        MaskTextWatcher mtwCodigoEstadual = new MaskTextWatcher(codigoEstadual, smfCodigoEstadual);
        codigoEstadual.addTextChangedListener(mtwCodigoEstadual);

        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("N NNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(telefone, smfTelefone);
        telefone.addTextChangedListener(mtwTelefone);
    }

}


