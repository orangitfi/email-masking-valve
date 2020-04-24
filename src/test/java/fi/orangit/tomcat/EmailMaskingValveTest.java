package fi.orangit.tomcat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.verification.PrivateMethodVerification;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doNothing;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EmailMaskingValve.class)
public class EmailMaskingValveTest {

    private EmailMaskingValve emailMaskingValve;

    @Before
    public void setUp() {
        emailMaskingValve = new EmailMaskingValve();
        emailMaskingValve = PowerMockito.spy(emailMaskingValve);
    }

    @Test
    public void testLogCall() throws Exception {
        String message = "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=john.doe@example.com&organizationId=example";
        doNothing().when(emailMaskingValve, "logEntry", "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e@example.com&organizationId=example");
        emailMaskingValve.log(message);
        PrivateMethodVerification privateMethodInvocation = PowerMockito.verifyPrivate(emailMaskingValve);
        privateMethodInvocation.invoke("logEntry", "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e@example.com&organizationId=example");
    }

    @Test
    public void testLogCallForUnicodeCharacter() throws Exception {
        String message = "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=john.doe%40example.com&organizationId=example";
        doNothing().when(emailMaskingValve, "logEntry", "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e%40example.com&organizationId=example");
        emailMaskingValve.log(message);
        PrivateMethodVerification privateMethodInvocation = PowerMockito.verifyPrivate(emailMaskingValve);
        privateMethodInvocation.invoke("logEntry", "127.0.0.1 - - [24/Apr/2020:00:12:37 +0300] \"GET /app/rest/v1//request?userId=j******e%40example.com&organizationId=example");
    }
}
