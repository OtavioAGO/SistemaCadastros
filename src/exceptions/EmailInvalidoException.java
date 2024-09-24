package exceptions;

public class EmailInvalidoException extends RuntimeException{
    public EmailInvalidoException(){
        super("Formatação do email invalida.");
    }
    public EmailInvalidoException(String message) {
        super(message);
    }
}
