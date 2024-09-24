import exceptions.AlturaInvalidaException;
import exceptions.EmailInvalidoException;
import exceptions.IdadeInvalidaException;
import exceptions.UsuarioInvalidoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pessoa {
    private String nome;
    private String email;
    private int idade;
    private double altura;
    public Pessoa(String nome, String email, int idade, double altura) throws UsuarioInvalidoException, EmailInvalidoException, IdadeInvalidaException{
        String regex = "([a-zA-Z0-9\\._-])+@([a-zA-Z])+(\\.([a-zA-Z])+)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        this.nome = nome;
        if (nome.length() < 10){
            throw new UsuarioInvalidoException();
        }
        this.email = email;
        if (!matcher.find()){
            throw  new EmailInvalidoException();
        }
        this.idade = idade;
        if (idade < 18){
            throw new IdadeInvalidaException();
        }
        this.altura = altura;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return getNome()+"\n"+getEmail()+"\n"+getIdade()+"\n"+getAltura();
    }
}
