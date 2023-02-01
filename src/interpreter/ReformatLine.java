package interpreter;

import java.util.List;

public class ReformatLine {

    public static String reformat(List<String> parsedCommandSequence) {

        StringBuffer out = new StringBuffer();

        for(String token : parsedCommandSequence) {
            out.append(token);
            out.append(" ");
        }
        return out.toString().trim();
    }
}
