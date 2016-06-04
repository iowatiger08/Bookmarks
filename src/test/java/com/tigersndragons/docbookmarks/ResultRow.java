package com.tigersndragons.docbookmarks;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResultRow {
	private Map<String, Object> valueMap = new LinkedHashMap<String, Object>();
	
	public ResultRow(Map<String, Object> valueMap) {
		this.valueMap.putAll(valueMap);
	}
	public ResultRow() {
	}
	
	public ResultRow addValue(String columnName, Object value) {
		valueMap.put(columnName, value);
		return this;
	}
	public boolean hasValue(String columnName){
		return valueMap.containsKey(columnName);
	}
	
	@Override
	public String toString() {
		return valueMap.toString() + "\n";
	}
	
	public List<String> getColumnNames(){
		return new LinkedList<String>(valueMap.keySet());
	}
	public Object getValue(String columnName){
		return valueMap.get(columnName);
	}
}