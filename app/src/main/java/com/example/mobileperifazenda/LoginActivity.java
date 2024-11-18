package com.example.mobileperifazenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mobileperifazenda.api.ApiService;
import com.example.mobileperifazenda.api.RetrofitClient;
import com.example.mobileperifazenda.models.LoginRequest;
import com.example.mobileperifazenda.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonLogar = findViewById(R.id.buttonEntrar);
        EditText usuarioEditText = findViewById(R.id.editTextUsuario);
        EditText senhaEditText = findViewById(R.id.editTextSenha);



        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = usuarioEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString();

                ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                LoginRequest loginRequest = new LoginRequest(usuario, senha);

                Call<LoginResponse> call = api.verificarLogin(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse loginResponse = response.body();
                            String mensagem = loginResponse.getMessage();
                            Integer fkCliente = loginResponse.getFkCliente();
                            Integer fkFuncionario = loginResponse.getFkFuncionario();

                            //Se o cliente for nulo (usuario de funcionario.)
                            if(fkCliente == null){
                                Toast.makeText(LoginActivity.this, "Você está tentando entrar com o login de funcionário.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_LONG).show();
                                getSharedPreferences("AppPrefs", MODE_PRIVATE)
                                        .edit()
                                        .putInt("fkCliente", fkCliente) // Armazena o fkCliente com a chave "fkCliente"
                                        .apply();
                                Intent intent = new Intent(LoginActivity.this, ProdutosActivity.class);
                                startActivity(intent);
                            }
                        } else{
                            Toast.makeText(LoginActivity.this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }
}