package staysafepi.staysafepi.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import staysafepi.staysafepi.DAO.ConfiguracaoFirebase;
import staysafepi.staysafepi.Entidades.Usuarios;
import staysafepi.staysafepi.Helper.Base64Custom;
import staysafepi.staysafepi.Helper.Preferencias;
import staysafepi.staysafepi.R;


public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail;
    private EditText edtCadSenha;
    private EditText edtCadConfirmaSenha;
    private EditText edtCadNome;
    private EditText edtcadSobrenome;
    private EditText edtcadEndereco;
    private EditText edtcadContato1;
    private EditText edtcadContato2;
    private EditText edtcadContato3;
    private Button btnSalvar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadEmail = (EditText) findViewById(R.id.edtEmail);
        edtCadSenha = (EditText) findViewById(R.id.edtSenha);
        edtCadConfirmaSenha = (EditText) findViewById(R.id.edtCadConfirmarSenha);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtcadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtcadEndereco = (EditText) findViewById(R.id.edtCadEndereco);
        edtcadContato1 = (EditText) findViewById(R.id.edtCadContato1);
        edtcadContato2 = (EditText) findViewById(R.id.edtCadContato2);
        edtcadContato3 = (EditText) findViewById(R.id.edtCadContato3);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString())) {

                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtcadSobrenome.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());
                    usuarios.setEnredeco(edtcadEndereco.getText().toString());
                    usuarios.setContato1(edtcadContato1.getText().toString());
                    usuarios.setContato2(edtcadContato2.getText().toString());
                    usuarios.setContato3(edtcadContato3.getText().toString());

                    cadastrarUsuario();

                } else {
                    Toast.makeText(CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        private void cadastrarUsuario() {
            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuarios.getEmail(),
                    usuarios.getSenha()
            ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                        String identificadorUsuario = Base64Custom.codificaBase64(usuarios.getEmail());

                        FirebaseUser usuarioFirebase = task.getResult().getUser();
                        usuarios.setId(identificadorUsuario);
                        usuarios.salvar();

                        Preferencias preferencias = new Preferencias(CadastroActivity.this);
                        preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                        abrirLoginUsuario();
                    }else{
                        String erroExcessao = "";

                        try{
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e){
                            erroExcessao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                        }
                        catch (FirebaseAuthInvalidCredentialsException e){
                            erroExcessao = "O e-mail digitado é inválido, digite um novo e-mail";
                        }
                        catch (FirebaseAuthUserCollisionException e){
                            erroExcessao = "Esse e-mail já está cadastrado no sistema";
                        }
                        catch (Exception e){
                            erroExcessao = "Erro ao efetuar o cadastro";
                            e.printStackTrace();
                        }
                        Toast.makeText(CadastroActivity.this, "Erro: "+erroExcessao, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public void abrirLoginUsuario(){
            Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

