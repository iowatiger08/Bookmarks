package com.tigersndragons.docbookmarks.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class MappedDocBookmarkId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private DocShell docShell;	

	private DocBookmark docBookmark;	
	
	@ManyToOne
	public DocShell getDocShell() {
		return docShell;
	}

	public void setDocShell(DocShell docShell) {
		this.docShell = docShell;
	}
	@ManyToOne
	public DocBookmark getDocBookmark() {
		return docBookmark;
	}

	public void setDocBookmark(DocBookmark docBookmark) {
		this.docBookmark = docBookmark;
	}

	@Override
	public int hashCode() {
		int hashCode;
		hashCode = (docShell.getId() !=null ? docShell.getId().hashCode():0);
		hashCode = 31 * hashCode + (docBookmark.getId() != null ? docBookmark.getId().hashCode():0);
		return hashCode;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj== null 
				|| !(obj instanceof MappedDocBookmarkId) )
			return false;
		
		MappedDocBookmarkId other=  (MappedDocBookmarkId)obj ;
		if (docShell !=null ? !docShell.getId().equals(other.docShell.getId()): other.docShell.getId()!=null ) return false;
		if (docBookmark !=null ? !docBookmark.getId().equals(other.docBookmark.getId()) : other.docBookmark.getId() !=null) return false;
		return true;
	}	
	
}
