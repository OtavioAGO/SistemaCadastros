package exceptions;

public class UsuarioInvalidoException extends RuntimeException{
    public UsuarioInvalidoException(){
        super("O nome do usuario deve possuir no minimo 10 caractéres.");
    }
    public UsuarioInvalidoException(String message) {
        super(message);
    }
}
