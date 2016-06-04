package com.tigersndragons.docbookmarks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="DOCBOOKMARKS",name="ORPHAN_BOOKMARK")
public class OrphanBookmark extends V3Object {

	private static final long serialVersionUID = 1L;
	private DocBookmark bookmark;
	private Integer isOrphan ;
	private SecurityUser user;
	
	@Column(name="IS_ORPHAN")
	public Integer getIsOrphan() {
		return isOrphan;
	}
	public void setIsOrphan(Integer isOrphan) {
		this.isOrphan = isOrphan;
	}
	@JoinColumn(name="MARK_ID")
	@OneToOne//(fetch=FetchType.LAZY)
	public DocBookmark getBookmark() {
		return bookmark;
	}
	public void setBookmark(DocBookmark bookmark) {
		this.bookmark = bookmark;
	}
	@JoinColumn(name="USER_ID")
	@ManyToOne//(fetch=FetchType.LAZY)
	public SecurityUser getUser() {
		return user;
	}
	public void setUser(SecurityUser user) {
		this.user = user;
	}
	
	
}
