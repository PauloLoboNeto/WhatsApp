package whatsappandroid.com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import whatsappandroid.com.br.whatsapp.R;
import whatsappandroid.com.br.whatsapp.helper.Preferences;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.edit_codigo_validacao);
        validar = (Button) findViewById(R.id.button_validar);

        SimpleMaskFormatter smfCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtwCodigoValidacao = new MaskTextWatcher(codigoValidacao, smfCodigoValidacao);
        codigoValidacao.addTextChangedListener(mtwCodigoValidacao);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences preferences = new Preferences(ValidadorActivity.this);
                HashMap<String, String> hashMap = preferences.getDadosUsuario();
                String tokenGerado = hashMap.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if(tokenGerado.equals(tokenDigitado)){
                    Toast.makeText(ValidadorActivity.this, "Código válido", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Código inválido", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
