package com.tigersndragons.docbookmarks.formactions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.DocShell;
import org.springframework.security.core.userdetails.UserDetails;

public class HomeFlowActions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<DocBookmark> docBookmarks = new ArrayList<DocBookmark>();
	//private List<DocShell> notDoneDocShells = new ArrayList<DocShell>();
	private List <DocShell> doneDocShells = new ArrayList<DocShell>();
	private Map<String,String> notDoneDocShells = new LinkedHashMap<String,String>();
	
	private String selectedDocShell ;
	private UserDetails userDetails = null;

	public List<DocBookmark> getDocBookmarks() {
		return docBookmarks;
	}

	public void setDocBookmarks(List<DocBookmark> docBookmarks) {
		this.docBookmarks = docBookmarks;
	}

	public Map<String, String> getNotDoneDocShells() {
		return notDoneDocShells;
	}

	public void setNotDoneDocShells(Map<String, String> notDoneDocShells) {
		this.notDoneDocShells = notDoneDocShells;
	}
	
	public void setNotDoneDocShells(List<DocShell> notDoneDocShells) {
		for (DocShell ds: notDoneDocShells){
			this.notDoneDocShells.put(ds.getId()+"", ds.toString());
		}
	}

	public List <DocShell> getDoneDocShells() {
		return doneDocShells;
	}

	public void setDoneDocShells(List <DocShell> doneDocShells) {
		this.doneDocShells = doneDocShells;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getSelectedDocShell() {
		return selectedDocShell;
	}

	public void setSelectedDocShell(String selectedDocShell) {
		this.selectedDocShell = selectedDocShell;
	}
}
