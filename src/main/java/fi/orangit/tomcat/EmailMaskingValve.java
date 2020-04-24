package fi.orangit.tomcat;

import org.apache.catalina.valves.AccessLogValve;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class EmailMaskingValve extends AccessLogValve {

    public EmailMaskingValve() {}

    @Override
    public void log(String message) {
        try {
            logEntry(maskEmail(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logEntry(String maskedMessage) {
        super.log(maskedMessage);
    }

    private String maskEmail(String message) throws IOException {
        StringBuilder sb = new StringBuilder(message);
        Pattern pattern = Pattern.compile("(\\w+([-+.']\\w+)*(@|%40)\\w+\\.\\w+)");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    int start = matcher.start(group);
                    int indexOfAt = sb.indexOf("%40", start);
                    if (indexOfAt < 0) {
                        indexOfAt = sb.indexOf("@", start);
                    }
                    IntStream.range(start+1, indexOfAt-1)
                            .forEach(i -> sb.setCharAt(i, '*')); // replace each character with asterisk
                }
            });
        }
        return sb.toString();
    }
}
