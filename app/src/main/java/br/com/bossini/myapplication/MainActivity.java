package br.com.bossini.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText loginEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText =
                findViewById(R.id.loginEditText);
    }

    public void fazerLogin(View view) {
        String login = loginEditText.getText().toString();
        Intent tela2 = new Intent(this,Main2Activity.class);
        Bundle variaveis = new Bundle();
        variaveis.putString("nick", login);
        tela2.putExtras(variaveis);
        startActivity(tela2);
    }
}
