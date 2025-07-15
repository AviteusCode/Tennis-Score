package org.tennis.service;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.List;

@Plugin(
  name = "MapAppender", 
  category = Core.CATEGORY_NAME,
  elementType = Appender.ELEMENT_TYPE)
public class MapAppender extends AbstractAppender {

    private static List<String> eventMap = new ArrayList<>();

    protected MapAppender(String name, Filter filter) {
        super(name, filter, null);
    }


    public static void clear(){
        eventMap = new ArrayList<>();
    }

    @PluginFactory
    public static MapAppender createAppender(
      @PluginAttribute("name") String name,
      @PluginElement("Filter") Filter filter) {
        return new MapAppender(name, filter);
    }

    public static List<String> getEventList() {
        return eventMap;
    }

    @Override
    public void append(LogEvent event) {
        eventMap.add(event.getMessage().getFormattedMessage());
    }
}