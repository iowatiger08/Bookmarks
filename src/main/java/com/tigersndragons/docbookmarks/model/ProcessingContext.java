package com.tigersndragons.docbookmarks.model;


public interface ProcessingContext {

	public void pushNode(Object left, Object right, String fieldName);

	public void popNode(Object choosenObject);


	public void markObjectReplaced(Object old, Object newObj);

	public boolean hasObjectBeenReplaced(Object obj);

	public Object getReplacementObject(Object obj);
	
	public void dumpReplacementMap();

	public String getNodeStackLog();

	public int getNextUniqueKey();
	
	
}
