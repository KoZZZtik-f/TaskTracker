package tasktracker.util;

import tasktracker.config.Config;
import tasktracker.exceptions.InvalidFileException;

public class Validator {

    private static final String firstFileStroke = "id,type,name,status,description,epic";

    public static boolean checkFileFirstStroke(String s) throws InvalidFileException {
        if (s.hashCode() != firstFileStroke.hashCode()) {
            throw new InvalidFileException(Config.invalidFileMessage);
        } else {
            return true;
        }
    }

//    public static boolean checkHistoryStroke(String s) {
//        if (s.charAt(1) == ',' || s.charAt(2) == ',' || s.charAt(3) == ',') {
//            return true;
//        } else {
//
//        }
//    }

}
