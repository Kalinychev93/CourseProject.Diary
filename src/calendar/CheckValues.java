package calendar;

import calendar.exceptions.WrongInputException;

public class CheckValues {

    public static String CheckString(String string) throws WrongInputException {
        if (string == null || string.isBlank()) {
            throw new WrongInputException("Некорректный ввод");
        } else {
            return string;
        }
    }
}
