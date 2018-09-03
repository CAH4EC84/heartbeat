package golikov.alexander.frontend;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import golikov.alexander.backend.TrackingComponent;

import java.time.LocalDateTime;
import java.util.List;

public class SummaryView extends VerticalLayout {
     List<TrackingComponent> trackingComponentList;
     Grid<TrackingComponent> grid = new Grid<>();
     Button button = new Button();


    public SummaryView(List<TrackingComponent> trackingComponentList) {
        this.trackingComponentList = trackingComponentList;

        grid.setItems(trackingComponentList);
        grid.addColumn(trackingComponent -> trackingComponent.getApp().getName())
                .setHeader("App")
                .setResizable(false)
                .setWidth("15%")
                .setSortable(true);
        grid.addColumn(trackingComponent -> trackingComponent.getApp().getCheker())
                .setHeader("Tracking")
                .setResizable(false)
                .setSortable(true)
                .setWidth("15%");
        grid.addColumn(trackingComponent -> trackingComponent.getApp().getPath())
                .setHeader("Path")
                .setResizable(true)
                .setSortable(true)
                .setWidth("40%");
        grid.addColumn(trackingComponent -> trackingComponent.getApp().getLastSuccessCheck().toString().substring(0,19))
                .setHeader("LastSuccessCheck")
                .setResizable(false)
                .setWidth("15%");
        grid.addColumn(trackingComponent -> trackingComponent.getApp().getErrorLevel())
                .setHeader("ErrorLevel")
                .setResizable(false)
                .setWidth("5%")
                .setSortable(true);



        button.setIcon(VaadinIcon.REFRESH.create());
        button.addClickListener(buttonClickEvent -> {
            grid.setItems(getTrackingComponentList());
        });
//        add(button);



        add(grid);
    }

    public Grid<TrackingComponent> getGrid() {
        return grid;
    }

    public Button getButton() {
        return button;
    }

    public List<TrackingComponent> getTrackingComponentList() {
        return trackingComponentList;
    }
}
