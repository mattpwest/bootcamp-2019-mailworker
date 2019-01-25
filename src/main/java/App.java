import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class App {
    public final static int BASE_PORT = 3000;
    public final static int SMTP_PORT = BASE_PORT + 25;

    public static void main(String[] args) throws SchedulerException {
        App.startTestMailServer();

        App.startScheduler();
    }


    private static void startTestMailServer() {
        ServerSetupTest.setPortOffset(BASE_PORT);
        GreenMail greenMail = new GreenMail(
                ServerSetupTest.ALL
        );

        greenMail.setUser("test@example.com", "test");
        greenMail.start();
    }

    private static void startScheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail job = JobBuilder
                .newJob(SendEmailJob.class)
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("test1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
                .build();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();
    }
}
