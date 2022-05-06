package com.gbrsni.votoelettronico.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gbrsni.votoelettronico.controller.TimerListener;

import javafx.util.Pair;

public class Timer{
	
	private boolean stopWasRequested = false;
	private final ExecutorService service = Executors.newCachedThreadPool();
	private final List<TimerListener> listeners = new ArrayList<>();
	private int minutes;
	private int seconds;
	
	public Timer(int time) {
		
		service.submit(new Runnable() {
			@Override
			public void run() {
				while(!stopWasRequested) {
				int timet= time * 60; //tempo in secondi
				long delay = timet * 1000; 

				do
				{
					minutes = timet / 60;
					seconds = timet % 60;
					notifyListeners();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timet = timet - 1;
					delay = delay - 1000;
				}
				while (delay != 0);
				
				System.out.println("Time's Up!");
			}
			}
		});

	}
	
	public void addListener(TimerListener listener) {
		listeners.add(listener);
	}
	public void removeListener(TimerListener listener) {
		listeners.remove(listener);
	}
	
	public void notifyListeners() {
		for (TimerListener listener : listeners) {
			listener.onReandingChange();
		}
	}
	
	public Pair<Integer,Integer> getTimer() {
		return new Pair(minutes,seconds);
	}
	
	public void shutdown() {
		stopWasRequested = true;
		service.shutdown();
	}
}