package Exception;

public class DasElementExistiertException extends Exception{

    /**
     * schreibt einen ERROR Nachricht, wenn das Element nicht in der Liste ist
     * @param message, den man bekommt
     */
    public DasElementExistiertException(String message) {
        super(message);
    }

}
