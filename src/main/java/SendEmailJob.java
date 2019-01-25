import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailJob implements Job {
    Logger logger = LoggerFactory.getLogger(SendEmailJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Sending email...");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "" + App.SMTP_PORT);

        Session session = Session.getInstance(props);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("test@example.com"));
            message.setSubject("Once In A Lifetime Opportunity");
            message.setText("Dear Sir / Madam,\n\nI am the reigning Prince of Wakanda. I have inherited a large sum of " +
                    "money ($500 000 000 to be exact). Due to new banking legislation by the Black Panther in Wakanda I " +
                    "am in need of your assistance to transfer the funds out of Wakanda.\n\n" +
                    "I am willing to give you 5% of the total funds in exchange for your timely assistance. Please " +
                    "transfer $100 to account number WA-43V3R-12984751 to confirm your interest in this exciting offer.\n\n" +
                    "Wakanda Forever!\n" +
                    "Matt \"KillMonger\" Van Der Westhuizen");

            Transport.send(message);

            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
