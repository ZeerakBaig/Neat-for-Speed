package Main_Package.NEAT.Genome;

public class MutationFailedException extends Exception {

    public MutationFailedException() {
        super();
    }

    public MutationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MutationFailedException(String message) {
        super(message);
    }

    public MutationFailedException(Throwable cause) {
        super(cause);
    }
}
