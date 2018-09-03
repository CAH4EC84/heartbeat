package golikov.alexander.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import golikov.alexander.backend.TrackingComponent;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogsTabView extends VerticalLayout {
    LinkedList<TrackingComponent> trackingComponentList;
    LinkedHashMap<Tab, Component> tabsToPages = new LinkedHashMap<>();


    public LogsTabView(LinkedList<TrackingComponent> trackingComponentList) {
        this.trackingComponentList = trackingComponentList;
        generateTabs();
    }



    public void generateTabs() {
        Tabs tabs = new Tabs();
        Div pages = new Div();
        //Заполняем карту табов \ Логов
        tabsToPages.clear();
        for (TrackingComponent item : trackingComponentList) {
            if (item.getApp().getCheker().equals("FileIsRefreshing")) {
                createTabElement(item);
            }
        }

        //Выводим карту
        tabsToPages.forEach((key,value) -> {
            tabs.add(key);
            pages.add(value);
        });

        Set<Component> pageShown = Stream.of(tabsToPages.entrySet().iterator().next().getValue()).collect(Collectors.toSet());


        //Вешаем обработчик для отображения инфы связанной с заголовком вкладки.
        tabs.addSelectedChangeListener(selectedChangeEvent -> {
            pageShown.forEach(item-> item.setVisible(false));
            pageShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pageShown.add(selectedPage);
        });


        tabs.setFlexGrowForEnclosedTabs(1);
        pages.setSizeFull();
        add(tabs,pages);
    }


    private void createTabElement(TrackingComponent item) {
        Div divContent = new Div();
        Tab tab = new Tab();
        TextArea textArea = new TextArea();
        StringBuilder logMessage = new StringBuilder();

        try {
            ReversedLinesFileReader fileRaader = new ReversedLinesFileReader(new File(item.getApp().getPath()),4,item.getApp().getCharSet());
            String line;
            int i = 0;
            while ((line = fileRaader.readLine()) != null &&  i < 50) {
                logMessage.append(line + "\n");
                i++;
            }
            fileRaader.close();
        } catch (IOException e) {
            ExceptionUtils.getMessage(e);
            logMessage.append("Error! Check service debug.log");
        }


        textArea.setSizeFull();
        textArea.setValue(logMessage.toString());
        divContent.add(textArea);
        if (tabsToPages.size()>0) divContent.setVisible(false);
        divContent.setSizeFull();
        tab.setLabel(item.getApp().getName());

        tabsToPages.put(tab,divContent);
    }

}
