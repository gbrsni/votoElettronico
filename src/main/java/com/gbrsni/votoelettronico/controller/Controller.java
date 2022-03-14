package com.gbrsni.votoelettronico.controller;

import com.gbrsni.votoelettronico.Home;
import java.io.IOException;

public abstract class Controller {
	
	public abstract void onNavigateFrom(Controller sender, Object parameter);
	
    public void navigate(String view, Object parameter) {
        try {
            Home.navigate(this, view, parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigate(String view) {
        navigate(view, null);
    }
}