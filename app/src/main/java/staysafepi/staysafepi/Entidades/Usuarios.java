package staysafepi.staysafepi.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import staysafepi.staysafepi.DAO.ConfiguracaoFirebase;

public class Usuarios {
    private String id;
    private String email;
    private String senha;
    private String confirmaSenha;
    private String nome;
    private String sobrenome;
    private String contato1;
    private String contato2;
    private String contato3;
    private String enredeco;

    public Usuarios() {

    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("sobrenome",getSobrenome());
        hashMapUsuario.put("contato1",getContato1());
        hashMapUsuario.put("contato2",getContato2());
        hashMapUsuario.put("contato3",getContato3());
        hashMapUsuario.put("endereco",getEnredeco());

        return hashMapUsuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getContato1() {
        return contato1;
    }

    public void setContato1(String contato1) {
        this.contato1 = contato1;
    }

    public String getContato2() {
        return contato2;
    }

    public void setContato2(String contato2) {
        this.contato2 = contato2;
    }

    public String getContato3() {
        return contato3;
    }

    public void setContato3(String contato3) {
        this.contato3 = contato3;
    }

    public String getEnredeco() {
        return enredeco;
    }

    public void setEnredeco(String enredeco) {
        this.enredeco = enredeco;
    }
}



