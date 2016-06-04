package com.tigersndragons.docbookmarks.formactions;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.model.Tag;

public class DocShellFlowActions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String selectedMark ;
	private String docShellId;
	private String employee;
	private Boolean isDone;
	private Map<String, String> notSelectedBookmarks = new LinkedHashMap<String, String>();
	private List<MappedDocBookmark> mappedDocBookmarks ;
	private List<Tag> tags;

	public String getSelectedMark() {
		return selectedMark;
	}

	public void setSelectedMark(String selectedMark) {
		this.selectedMark = selectedMark;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public String getDocShellId() {
		return docShellId;
	}

	public void setDocShellId(String docShellId) {
		this.docShellId = docShellId;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Map<String, String> getNotSelectedBookmarks() {
		return notSelectedBookmarks;
	}

	public void setNotSelectedBookmarks(Map<String, String> notSelectedBookmarks) {
		this.notSelectedBookmarks = notSelectedBookmarks;
	}

	public List<MappedDocBookmark> getMappedDocBookmarks() {
		return mappedDocBookmarks;
	}

	public void setMappedDocBookmarks(List<MappedDocBookmark> mappedDocBookmarks) {
		this.mappedDocBookmarks = mappedDocBookmarks;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
