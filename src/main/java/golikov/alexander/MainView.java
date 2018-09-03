package golikov.alexander;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import golikov.alexander.backend.HeartBeatTimer;
import golikov.alexander.backend.entity.TrackApps;
import golikov.alexander.backend.TrackingComponent;
import golikov.alexander.backend.utils.HibernateSessionfactory;
import golikov.alexander.frontend.LogsTabView;
import golikov.alexander.frontend.SummaryView;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * The main view contains a button and a template element.
 */
@HtmlImport("frontend://styles/shared-styles.html")
@Route("")
@Push()
public class MainView extends VerticalLayout {
    private final Logger logger = LoggerFactory.getLogger(MainView.class);

    private Session session;
    SummaryView summaryView;
    LogsTabView logsTab;
    Button refreshBtn = new Button();

    private static LinkedList<TrackingComponent> trackingComponentList = new LinkedList<>();

    public MainView() {
        logger.debug("app start at {}",LocalDateTime.now().toString().substring(0,19));

        //Получаем список отслеживаемых сервисов
        try {
            session = HibernateSessionfactory.getSession();

            Query query = session.createQuery("from TrackApps order by name");

            //Создаем пул отслеживаемых сервисов
            if (trackingComponentList.size() == 0) {
                query.getResultList().forEach(item -> {
                    trackingComponentList.add(new TrackingComponent(session, (TrackApps) item));
                });
            }
            //запускаем контроллер .
            // По таймеру обновляет состояние отслеживаемых компонентов , обновляет выводиму инфу через PUSH.
            try {
                Thread.sleep(2000);
                Timer timer = new Timer(true);
                HeartBeatTimer heartBeatTimer = new HeartBeatTimer(session,trackingComponentList);
                if (!heartBeatTimer.hasStarted()) {
                    timer.scheduleAtFixedRate(heartBeatTimer,0,1000*120);
                }
            } catch (InterruptedException e) {
                logger.error(ExceptionUtils.getMessage(e));
            }
        } catch (HibernateException e) {
            logger.error(ExceptionUtils.getMessage(e));
        }




        //Выводим сводную информацию и обновляем сводную информацию
        try {
            Thread.sleep(2000);
            summaryView = new SummaryView(trackingComponentList);
            summaryView.setHeight("65%");
            summaryView.setWidth("100%");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Табы для просмотра последних записей лог файлов.

        logsTab = new LogsTabView(trackingComponentList);
        logsTab.setHeight("35%");

        refreshBtn.setIcon(VaadinIcon.REFRESH.create());
        refreshBtn.addClickListener(buttonClickEvent -> {
            remove(logsTab);
            logsTab = new LogsTabView(trackingComponentList);
            summaryView.getGrid().setItems(summaryView.getTrackingComponentList());
            summaryView.setHeight("65%");
            summaryView.setWidth("100%");
            logsTab.setHeight("35%");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            add(logsTab);
        });


        add(refreshBtn,summaryView,logsTab);
        setMargin(false);
        setSpacing(false);
        setSizeFull();


    }
}
