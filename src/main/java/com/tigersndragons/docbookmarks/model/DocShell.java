package com.tigersndragons.docbookmarks.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(schema="DOCBOOKMARKS",name="DOC_SHELL")
@AttributeOverride(name="id", column=@Column(name="DOC_SHELL_ID"))
public class DocShell  extends V3Object{

	private static final long serialVersionUID = 1L;

	private String docShellName;
	private String docShellDisplayName;
	private String shellType;
	private String datawindowObjectName;

	private String initialStatus;
	private String currentVersion;

	private String published;
	private DateTime pubDate;	
	private Integer hasDocument;
	private DateTime insertedDate;
	private String insertedBy;
	private String updatedBy;
	private DateTime updatedDate;
	
	private DateTime doneDate;
	private SecurityUser user;
	private Integer isDone;
	
	private Set<MappedDocBookmark> mappedDocBookmarks = new HashSet<MappedDocBookmark>(0);
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.docShell")
	public Set<MappedDocBookmark> getMappedDocBookmarks() {
		return mappedDocBookmarks;
	}
	public void setMappedDocBookmarks(Set<MappedDocBookmark> mappedDocBookmarks) {
		this.mappedDocBookmarks = mappedDocBookmarks;
	}
	@Column(name="DOC_SHELL_NAME")
	public String getDocShellName() {
		return docShellName;
	}
	public void setDocShellName(String docShellName) {
		this.docShellName = docShellName;
	}
	@Column(name="DOC_SHELL_DISPLAY_NAME")
	public String getDocShellDisplayName() {
		return docShellDisplayName;
	}
	public void setDocShellDisplayName(String docShellDisplayName) {
		this.docShellDisplayName = docShellDisplayName;
	}
	@Column(name="SHELL_TYPE")
	public String getShellType() {
		return shellType;
	}
	public void setShellType(String shellType) {
		this.shellType = shellType;
	}
	@Column(name="DATAWINDOW_OBJECT_NAME")
	public String getDatawindowObjectName() {
		return datawindowObjectName;
	}
	public void setDatawindowObjectName(String datawindowObjectName) {
		this.datawindowObjectName = datawindowObjectName;
	}
	@Column(name="INITIAL_STATUS")
	public String getInitialStatus() {
		return initialStatus;
	}
	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}
	@Column(name="CURRENT_VERSION")	
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	@Column(name="PUBLISHED")	
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	@Column(name="PUBLISHED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getPubDate() {
		return pubDate;
	}
	public void setPubDate(DateTime pubDate) {
		this.pubDate = pubDate;
	}
	@Column(name="HAS_DOCUMENTS")
	public Integer getHasDocument() {
		return hasDocument;
	}
	public void setHasDocument(Integer hasDocument) {
		this.hasDocument = hasDocument;
	}
	@Column(name="INSERTED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getInsertedDate() {
		return insertedDate;
	}
	public void setInsertedDate(DateTime insertedDate) {
		this.insertedDate = insertedDate;
	}
	@Column(name="INSERTED_BY")
	public String getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(String insertedBy) {
		this.insertedBy = insertedBy;
	}
	@Column(name="UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name="UPDATED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	@Column(name="DONE_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDoneDate() {
		return doneDate;
	}

	@Column(name="IS_DONE")
	public Integer getIsDone() {
		return isDone;
	}

	public void setIsDone(Integer isDone) {
		this.isDone = isDone;
	}

	public void setDoneDate(DateTime doneDate) {
		this.doneDate = doneDate;
	}
	@JoinColumn(name="USER_NAME")
	@ManyToOne(fetch=FetchType.LAZY)
	public SecurityUser getUser() {
		return user;
	}

	public void setUser(SecurityUser user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return this.getId() + " " + this.getDocShellDisplayName() + " " + this.getShellType();
	}
}
