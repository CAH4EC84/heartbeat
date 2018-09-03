package golikov.alexander.backend.utils;

import com.vaadin.flow.component.UI;
import golikov.alexander.frontend.SummaryView;

import java.time.LocalDateTime;

public class SummaryViewRefresher extends Thread {
    private UI ui;
    private SummaryView summaryView;

    public SummaryViewRefresher(UI ui, SummaryView summaryView) {
        this.ui = ui;
        this.summaryView = summaryView;
    }



    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(1000*300);
                System.out.println(Thread.currentThread().getName()+"SummaryViewRefresher:" + LocalDateTime.now());
                synchronized (ui) {
                    ui.access(() -> {
                        summaryView.getButton().setText(LocalDateTime.now().toString());
                        summaryView.getGrid().setItems(summaryView.getTrackingComponentList());
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}

