package calendar.exceptions;

import java.io.IOException;

public class WrongInputException extends IOException {
    public WrongInputException() {
    }

    public WrongInputException(String message) {
        super(message);
    }
}
