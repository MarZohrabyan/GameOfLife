public class PatternFormatException extends Exception {


    public PatternFormatException() {
        super("Incorrect format");
    }

    public PatternFormatException(String messege) {
        super(messege);

    }

}
