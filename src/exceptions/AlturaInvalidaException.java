package exceptions;

public class AlturaInvalidaException extends RuntimeException{
    public AlturaInvalidaException(){
        super("A altura deve ser escrita em numeros com virgula.");
    }
    public AlturaInvalidaException(String message) {
        super(message);
    }
}
