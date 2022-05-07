package com.gbrsni.votoelettronico.models;

public interface Observer {

	public void addListener(TimerListener listener) ;
	public void removeListener(TimerListener listener);	
	public void notifyListeners();
}
