package com.example.dishfo.androidapp.netInterface;

import com.google.gson.JsonElement;

@FunctionalInterface
public interface SimpleAction {
	
	public Object behave(JsonElement dst, Object[] args);
	
}
