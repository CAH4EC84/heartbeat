package golikov.alexander.backend;

import golikov.alexander.frontend.SummaryView;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

public class HeartBeatTimer extends TimerTask {
    public  static  Boolean hasStarted = false;
    Session session;
    List<TrackingComponent> trackingComponentList;

    SummaryView summaryView;

    public Boolean hasStarted() {
        return hasStarted;
    }

    public HeartBeatTimer(Session session, List<TrackingComponent> trackingComponentList) {
        this.session = session;
        this.trackingComponentList = trackingComponentList;
    }

    @Override
    public void run() {
        this.hasStarted = true;
        System.out.println("HeartBeatTimer:" + LocalDateTime.now());
        trackingComponentList.forEach(item->item.check());

    }
}
