package com.tigersndragons.docbookmarks.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema="DOCBOOKMARKS",name="DOC_BOOKMARK")
@AttributeOverride(name="id", column=@Column(name="MARK_ID"))
public class DocBookmark  extends V3Object{
	
	private static final long serialVersionUID = 1L;

	private String markName;
	private String internalName;
	private String markSql;
	private String docTypeName;
	private Integer docSubtypeId;
	
	private Set<MappedDocBookmark> mappedDocBookmarks = new HashSet<MappedDocBookmark>(0);
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.docBookmark")
	public Set<MappedDocBookmark> getMappedDocBookmarks() {
		return mappedDocBookmarks;
	}
	public void setMappedDocBookmarks(Set<MappedDocBookmark> mappedDocBookmarks) {
		this.mappedDocBookmarks = mappedDocBookmarks;
	}

	@Column(name="INTERNAL_NAME")
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	@Column(name="MARK_SQL")
	public String getMarkSql() {
		return markSql;
	}
	public void setMarkSql(String markSql) {
		this.markSql = markSql;
	}
	@Column(name="DOC_TYPE_NAME")
	public String getDocTypeName() {
		return docTypeName;
	}
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}
	@Column(name="MARK_NAME")
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName;
	}
	@Column(name="DOC_SUBTYPE_ID")
	public Integer getDocSubtypeId() {
		return docSubtypeId;
	}
	public void setDocSubtypeId(Integer docSubtypeId) {
		this.docSubtypeId = docSubtypeId;
	}
	
	@Override
	public String toString() {
		return this.getId() + this.getMarkName();
	}

}
