package Exception;

public class ListIsEmptyException extends Exception{

    /**
     * schreibt einen ERROR Nachricht, wenn die Liste leer ist
     * @param message, den man bekommt
     */
    public ListIsEmptyException(String message) {
        super(message);
    }

}
