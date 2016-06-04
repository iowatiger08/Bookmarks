package com.tigersndragons.docbookmarks;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;

public class ResultRowPrinter {
	private List<ResultRow> rows = new LinkedList<ResultRow>();
	private Map<String, Integer> columnWidths = new LinkedHashMap<String, Integer>();
	
	private String[] propertyNameOrder = null;
	
	public ResultRowPrinter(List<ResultRow> rows) {
		for(ResultRow row:rows){
			addRow(row);
		}
	}
	public ResultRowPrinter(String[] propertyNameOrder) {
		this.propertyNameOrder = propertyNameOrder;
	}
	public ResultRowPrinter() {
	}
	public void addRow(ResultRow row){
		rows.add(row);
		updateColumnWidths(row);
	}
	
	
	public void printAsTable(){
		if(rows == null || rows.isEmpty()){
			System.out.println("No Data!");
			return;
		}
		System.out.println(getHeaderRow());
		System.out.println(getHeaderDivider());
		for(ResultRow row:rows){
			System.out.println(getDataRow(row));
		}
	}
	private String getDataRow(ResultRow row){
		StringBuilder builder = new StringBuilder();
		for(String column:getPropertyNameOrder()){
			String value = convertToString(row.getValue(column));
			builder.append(StringUtils.rightPad(value, columnWidths.get(column)) + " ");
		}
		return builder.toString();
	}
	private String convertToString(Object obj){
		if(obj instanceof Date){
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) obj);
			return DatatypeConverter.printDateTime(cal);
		}
		return String.valueOf(obj);
	}
	public String getHeaderRow(){
		StringBuilder builder = new StringBuilder();
		for(String column:getPropertyNameOrder()){
			builder.append(StringUtils.rightPad(column, columnWidths.get(column)) + " ");
		}
		return builder.toString();
	}
	public String getHeaderDivider(){
		StringBuilder builder = new StringBuilder();
		for(String column:getPropertyNameOrder()){
			builder.append(StringUtils.leftPad("", columnWidths.get(column), '-') + " ");
		}
		return builder.toString();
	}
	public String[] getPropertyNameOrder() {
		if(propertyNameOrder != null){
			return propertyNameOrder;
		}else if (!rows.isEmpty()){
			return rows.get(0).getColumnNames().toArray(new String[]{});
		}
		throw new IllegalStateException();
	}
	public void setPropertyNameOrder(String[] propertyNameOrder) {
		this.propertyNameOrder = propertyNameOrder;
	}
	
	private void updateColumnWidths(ResultRow row){
		for(String name:row.getColumnNames()){
			updateColumnWidth(name, row.getValue(name));
		}
	}
	private void updateColumnWidth(String column, Object value){
		if(!columnWidths.containsKey(column)){
			columnWidths.put(column, column.length());
		}
		int length  = convertToString(value).length();
		columnWidths.put(column, 
			Math.max(columnWidths.get(column), 
			length));
	}
}