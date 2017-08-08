package whatsappandroid.com.br.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Paulo on 08/08/2017.
 */

public class Preferences {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String NOME_ARQUIVO = "whatsapp.preferences";
    private SharedPreferences.Editor editorPreferences;
    private String CHAVE_NOME = "nomeUsuario";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN = "token";


    public Preferences(Context contextParameter){
        context = contextParameter;
        sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        editorPreferences = sharedPreferences.edit();
    }

    public void salvarPreferences(String nomeUsuario, String telefone, String token ){
        editorPreferences.putString(CHAVE_NOME, nomeUsuario);
        editorPreferences.putString(CHAVE_TELEFONE, telefone);
        editorPreferences.putString(CHAVE_TOKEN, token);
        editorPreferences.commit();
    }

    public HashMap<String, String> getDadosUsuario(){
        HashMap<String, String> dadosUsuario = new HashMap<>();
        dadosUsuario.put(CHAVE_NOME, sharedPreferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, sharedPreferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, sharedPreferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
