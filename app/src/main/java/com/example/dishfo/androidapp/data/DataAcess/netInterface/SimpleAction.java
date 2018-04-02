package com.example.dishfo.androidapp.data.DataAcess.netInterface;

import com.google.gson.JsonElement;

@FunctionalInterface
public interface SimpleAction {
	
	public Object behave(JsonElement dst, Object[] args);
	
}
