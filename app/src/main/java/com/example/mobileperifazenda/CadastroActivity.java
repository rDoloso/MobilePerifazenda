package com.example.mobileperifazenda;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.mobileperifazenda.models.Cliente;
import com.example.mobileperifazenda.models.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nomeEditText = findViewById(R.id.nome);
        EditText logradouroEditText = findViewById(R.id.logradouro);
        EditText numeroEditText = findViewById(R.id.numero);
        EditText bairroEditText = findViewById(R.id.bairro);
        EditText cidade_ufEditText = findViewById(R.id.cidade_uf);
        EditText complementoEditText = findViewById(R.id.complemento);
        EditText emailEditText = findViewById(R.id.email);
        EditText usuarioEditText = findViewById(R.id.usuario);
        EditText senhaEditText = findViewById(R.id.senha);
        EditText confirmacaoEditText = findViewById(R.id.confirmacao);

        //Pegar cpf e colocar pontuacao.
        EditText cpfEditText = findViewById(R.id.cpf);
        cpfEditText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[.-]", "");


                // Limitar o CPF a no máximo 11 dígitos
                if (str.length() > 11) {
                    str = str.substring(0, 11);
                }

                String formattedText = "";
                if (str.length() > 3) {
                    formattedText += str.substring(0, 3) + ".";
                    if (str.length() > 6) {
                        formattedText += str.substring(3, 6) + ".";
                        if (str.length() > 9) {
                            formattedText += str.substring(6, 9) + "-";
                            formattedText += str.substring(9);
                        } else {
                            formattedText += str.substring(6);
                        }
                    } else {
                        formattedText += str.substring(3);
                    }
                } else {
                    formattedText = str;
                }

                isUpdating = true;
                cpfEditText.setText(formattedText);
                cpfEditText.setSelection(formattedText.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //Pegar cep e colocar pontuacao
        EditText cepEditText = findViewById(R.id.cep);
        cepEditText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("-", "");

                // Limitar o CEP a no máximo 8 dígitos
                if (str.length() > 8) {
                    str = str.substring(0, 8);
                }

                if (str.length() > 5) {
                    str = str.substring(0, 5) + "-" + str.substring(5);
                }

                isUpdating = true;
                cepEditText.setText(str);
                cepEditText.setSelection(str.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Data de nascimento
        EditText datanascimentoEditText = findViewById(R.id.data_nascimento);
        datanascimentoEditText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[/]", ""); // Remove caracteres existentes de formatação
                String formattedText = "";

                // Limitar a data de nascimento a 8 caracteres sem formatação
                if (str.length() > 8) {
                    str = str.substring(0, 8);
                }

                if (str.length() > 2) {
                    formattedText += str.substring(0, 2) + "/";
                    if (str.length() > 4) {
                        formattedText += str.substring(2, 4) + "/";
                        formattedText += str.substring(4);
                    } else {
                        formattedText += str.substring(2);
                    }
                } else {
                    formattedText = str;
                }

                isUpdating = true;
                datanascimentoEditText.setText(formattedText);
                datanascimentoEditText.setSelection(formattedText.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        Button buttonCadastrar = findViewById(R.id.buttonCadastrar);


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = nomeEditText.getText().toString().trim();
                String logradouro = logradouroEditText.getText().toString().trim();
                String numero = numeroEditText.getText().toString().trim();
                String bairro = bairroEditText.getText().toString().trim();
                String cidadeUf = cidade_ufEditText.getText().toString().trim();
                String complemento = complementoEditText.getText().toString().trim(); // Opcional
                String dataNascimento = datanascimentoEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String usuario = usuarioEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString();
                String confirmacaoSenha = confirmacaoEditText.getText().toString().trim();
                String cpf = cpfEditText.getText().toString().trim();
                String cep = cepEditText.getText().toString().trim();

                //converter data para formato do banco
                String dataNascimentoInput = datanascimentoEditText.getText().toString().trim();
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dataFormatada = "";

                try {
                    // Converte a entrada do usuário para o formato esperado pela API
                    Date date = inputFormat.parse(dataNascimentoInput);
                    dataFormatada = outputFormat.format(date); // Formato yyyy-MM-dd
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(CadastroActivity.this, "Data de nascimento inválida", Toast.LENGTH_SHORT).show();
                    return; // Impede que continue se a data for inválida
                }

                boolean camposValido = verificarCamposObrigatorios(nome, logradouro, numero, bairro,
                        cidadeUf, dataNascimento, email,
                        usuario, senha, confirmacaoSenha, cpf, cep);

                boolean confirmaSenha = confirmaSenha(senha, confirmacaoSenha);

                int idCliente = 0;

                if(camposValido && confirmaSenha){
                    ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                    //Cadastrar cliente
                    Cliente cliente = new Cliente(1, cpf, nome, dataFormatada, cep, logradouro, numero, bairro, cidadeUf, complemento);

                    Call<Cliente> callCliente = api.cadastrarCliente(cliente);
                    callCliente.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> callCliente, Response<Cliente> response) {
                            if(response.isSuccessful() && response.body() != null){
                                int idCliente = response.body().getId();

                                //Cadastrar usuario
                                Usuario user = new Usuario(usuario,email, senha, 1, idCliente);

                                Call<Usuario> callUsuario = api.criarUsuario(user);
                                callUsuario.enqueue(new Callback<Usuario>() {
                                    @Override
                                    public void onResponse(Call<Usuario> callUsuario, Response<Usuario> response) {
                                        Toast.makeText(CadastroActivity.this, "Cliente cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Usuario> callUsuario, Throwable throwable) {
                                        Toast.makeText(CadastroActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else{
                                Toast.makeText(CadastroActivity.this, "Erro ao cadastrar o cliente.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable throwable) {
                            Toast.makeText(CadastroActivity.this, "Sem acesso a internet. Conecte-se à internet e tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    });



                }

            }
        });


    }
    private boolean verificarCamposObrigatorios(String... campos) {
        for (String campo : campos) {
            if (campo.toString().trim().isEmpty()) {
                Toast.makeText(CadastroActivity.this, "Preencha todos os campos obrigatorios!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean confirmaSenha(String senha, String confirmacao){
        if (!senha.equals(confirmacao)) {
            Toast.makeText(CadastroActivity.this, "A confirmação de senha não coincide com a senha.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

}