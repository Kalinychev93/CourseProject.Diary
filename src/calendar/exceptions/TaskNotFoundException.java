package calendar.exceptions;

import java.io.IOException;

public class TaskNotFoundException extends IOException {

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
