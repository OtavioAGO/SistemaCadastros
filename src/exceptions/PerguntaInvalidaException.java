package exceptions;

public class PerguntaInvalidaException extends RuntimeException{
    public PerguntaInvalidaException(){
        super("Não é possivel remover as perguntas originais.");
    }
    public PerguntaInvalidaException(String message) {
        super(message);
    }
}
