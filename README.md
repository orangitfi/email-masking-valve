# email-masking-valve
Email masking valve for Tomcat access log email masking

# Usage info
This project is for masking emails in Tomcat access log from syntax john.doe@example.com to j******e@example.com

## Build
Build the maven project and copy the generated email-masking-valve.jar file from target directory and paste it to tomcat/lib directory.

## Change the default valve configuration
Change the default valve configuration in tomcat/conf/server.xml for example as follows,

        <Valve className="fi.orangit.tomcat.EmailMaskingValve" directory="logs"
               prefix="access_log." suffix=".out"
               pattern="combined" rotatable="true"
               maxDays="14" />

## Restart
Restart the tomcat server.
