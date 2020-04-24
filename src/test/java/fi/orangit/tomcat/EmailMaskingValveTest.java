package fi.orangit.tomcat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EmailMaskingValveTest {

    private final EmailMaskingValve emailMaskingValve = new EmailMaskingValve();

    @Test
    void testEmailIsMaskedForUnicodeCharacter() throws IOException {
        String message = "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=john.doe%40example.com&organizationId=example HTTP/1.1\" 404 - \"https://help.service.com/\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36\"";
        String masked = emailMaskingValve.maskEmail(message);
        assertEquals("127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e%40example.com&organizationId=example HTTP/1.1\" 404 - \"https://help.service.com/\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36\"", masked);
    }

    @Test
    void testEmailIsMasked() throws IOException {
        String message = "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=john.doe@example.com&organizationId=example HTTP/1.1\" 404 - \"https://help.service.com/\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36\"";
        String masked = emailMaskingValve.maskEmail(message);
        assertEquals("127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e@example.com&organizationId=example HTTP/1.1\" 404 - \"https://help.service.com/\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36\"", masked);
    }

}
