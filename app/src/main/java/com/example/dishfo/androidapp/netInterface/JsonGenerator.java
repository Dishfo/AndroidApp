package com.example.dishfo.androidapp.netInterface;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class JsonGenerator {
	
	public final static int EMPTY=-2;
	public final static int DEAD=-1;
	public final static int LIFE_NODE=1;
	public final static int LIFE_ARRAY=2;
	
	
	private JsonObject root;
	private ArrayDeque<JsonElement> elements;
	private JsonElement currentNode;
	
	private int status;
	
	public JsonGenerator() {
		elements=new ArrayDeque<>();
		status=EMPTY;
	}
	
	public JsonGenerator openNode() {
		if(status==DEAD) {
			
		}else {
			currentNode=new JsonObject();
			elements.push(currentNode);
			if(status==EMPTY) {
				root=(JsonObject) currentNode;
			}
			status=LIFE_NODE;
		}
		return this;
	}
	
	public JsonGenerator openArray() {
		if(status==EMPTY||status==DEAD)
			throw new IllegalArgumentException("can't open array ");
		currentNode=new JsonArray();
		elements.push(currentNode);
		status=LIFE_ARRAY;
		return this;
	}
	
	public JsonGenerator closeNode(String currentTag){
		if(currentNode==null)
			return this;
		elements.pop();
		JsonElement parent=null;
		try {
			parent=elements.getFirst();
		}catch (NoSuchElementException e) {
			parent=null;
		}
		
		if(parent==null) {
			status=DEAD;
			currentNode=null;
		}else {
			if(parent instanceof JsonObject) {
				((JsonObject)parent).add(currentTag, currentNode);
			}else if(parent instanceof JsonArray) {
				((JsonArray)parent).add(currentNode);
			}
			currentNode=parent;
		}
		return this;
	}
	
	public JsonGenerator compete(SimpleAction action,Object ... args) {
		if(currentNode == null) {
			throw new IllegalArgumentException(" current node is null");
		}else if(currentNode instanceof JsonObject) {
			return competeNode(action, args);
		}else if(currentNode instanceof JsonArray) {
			return competeArray(action, args);
		}
		return this;
		
	}
	
	private JsonGenerator competeNode(SimpleAction action,Object ... args) {
		action.behave(currentNode,args);
		return this;
	}
	
	private JsonGenerator competeArray(SimpleAction action,Object ... args) {
		action.behave(currentNode,args);
		return this;
	}
	
	
	public String toJson() {
		Gson gson=new Gson();
		return gson.toJson(root);
	}
	
	
}
