package com.lohika.morning.ml.api;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class Launcher{
    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser(){
        System.setProperty("java.awt.headless","false");
        Desktop desktop = Desktop.getDesktop();
        try{
            desktop.browse(new URI("http://localhost:9090/home"));
        }catch(Exception e){}
    }
}
