package org.example;

public class DropDeadlinePassedException extends Exception {
    // Constructors
    public DropDeadlinePassedException() {
        super();
    }

    public DropDeadlinePassedException(String message) {
        super(message);
    }

    public DropDeadlinePassedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropDeadlinePassedException(Throwable cause) {
        super(cause);
    }
}
