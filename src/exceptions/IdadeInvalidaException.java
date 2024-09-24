package exceptions;

public class IdadeInvalidaException extends RuntimeException{
    public IdadeInvalidaException(){
        super("O usuario deve possuir pelo menos 18 anos para ser cadastrado.");
    }
    public IdadeInvalidaException(String message) {
        super(message);
    }
}
