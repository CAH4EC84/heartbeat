package golikov.alexander.backend;

import golikov.alexander.backend.Notifiers.EmailSender;
import golikov.alexander.backend.entity.TrackApps;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.InternetAddress;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TrackingComponent {
    private final Logger logger = LoggerFactory.getLogger(TrackingComponent.class);

    private Session session;
    private TrackApps app;
    private ErrorLevels errorLevel;

    public TrackingComponent(Session session, TrackApps app) {
        this.session = session;
        this.app = app;
    }

    void check() {
        Boolean isAlive;
        //Время проверки =  последняя УСПЕШНАЯ проверка + интервал заданный для отслеживаемого компонента умноженное на порядок ошибки
        errorLevel = ErrorLevels.valueOf(app.getErrorLevel());
        Integer errIndex = errorLevel.ordinal()== 0 ? 1 : errorLevel.ordinal();

        LocalDateTime checkUpTime = LocalDateTime.ofInstant(
                app.getLastSuccessCheck().toInstant(),ZoneId.systemDefault())
                .plusMinutes(Long.parseLong(app.getCheckInterval()) *errIndex);


        //Если пришло время провери и оно входит в интервал между 3 и 23 часами
        if (LocalDateTime.now().isAfter(checkUpTime)
                && (LocalTime.now().isAfter(LocalTime.parse("03:00:00") )
                && LocalTime.now().isBefore(LocalTime.parse("23:00:00")))) {
            //Если компонент отслеживается
            if (app.getEnableTrack() == 1) {
                try {
                    Class checker = Class.forName("golikov.alexander.backend.chekers." + app.getCheker());
                    isAlive = (Boolean) checker.getMethod("isAlive",String.class, LocalDateTime.class)
                            .invoke(null,app.getPath(),LocalDateTime.ofInstant(app.getLastSuccessCheck().toInstant(),ZoneId.systemDefault()));

                    if (isAlive) update();
                    else {
                        switch (errorLevel) {
                            case NO:
                                alert(app.getName()+" : First check fails"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        ,errorLevel.getRecipients());break;
                            case TRACE:
                                alert(app.getName()+" : TRACE("+errorLevel.ordinal() * Integer.parseInt(app.getCheckInterval()) +") Minutes"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        ,errorLevel.getRecipients());break;
                            case INFO:
                                alert(app.getName()+" : INFO("+errorLevel.ordinal()* Integer.parseInt(app.getCheckInterval()) +") Minutes"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        , errorLevel.getRecipients()); break;
                            case WARN:
                                alert(app.getName()+" : WARN("+errorLevel.ordinal()* Integer.parseInt(app.getCheckInterval()) +") Minutes"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        , errorLevel.getRecipients()); break;
                            case ERROR:
                                alert(app.getName()+" : ERROR("+errorLevel.ordinal()* Integer.parseInt(app.getCheckInterval()) +") Minutes"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        , errorLevel.getRecipients()); break;
                            case FATAL:
                                alert(app.getName()+" : FATAL("+errorLevel.ordinal()* Integer.parseInt(app.getCheckInterval()) +") Minutes"
                                        ,app.getPath() + "\t" + app.getLastSuccessCheck()
                                        , errorLevel.getRecipients()); break;
                        }
                        errorLevel = errorLevel.getNextLevel(errorLevel.ordinal());
                    }
                } catch (ClassNotFoundException | NoSuchMethodException |  IllegalAccessException | InvocationTargetException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }


    private void update() {
        app.setLastSuccessCheck(Timestamp.valueOf(LocalDateTime.now()));
        app.setErrorLevel(ErrorLevels.NO.toString());
        session.beginTransaction();
        session.save(app);
        session.flush();
        session.getTransaction().commit();
    }
    private void alert(String msgSubject, String msgBody, InternetAddress[] recipients) {
        app.setErrorLevel(errorLevel.toString());
        session.beginTransaction();
        session.save(app);
        session.flush();
        session.getTransaction().commit();

        new EmailSender(msgSubject,msgBody,recipients).sendMessage();

    }

    public TrackApps getApp() {
        return app;
    }


}
