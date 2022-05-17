package com.gbrsni.votoelettronico.models;

import com.gbrsni.votoelettronico.controller.Listener;

public interface Observer {

	public void addListener(Listener listener) ;
	public void removeListener(Listener listener);	
	public void notifyListeners();
}
