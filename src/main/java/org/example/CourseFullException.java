package org.example;

public class CourseFullException extends Exception {
    // Constructors
    public CourseFullException() {
        super();
    }

    public CourseFullException(String message) {
        super(message);
    }

    public CourseFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseFullException(Throwable cause) {
        super(cause);
    }
}
