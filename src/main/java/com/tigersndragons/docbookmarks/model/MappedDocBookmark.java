package com.tigersndragons.docbookmarks.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity
@Table(schema="DOCBOOKMARKS",name="MAPPED_SHELL_MARK")
@AssociationOverrides({
	@AssociationOverride(name="pk.docShell", joinColumns=@JoinColumn(name="DOC_SHELL_ID")),
	@AssociationOverride(name="pk.docBookmark", joinColumns=@JoinColumn(name="MARK_ID"))
})
public class MappedDocBookmark implements Serializable{
	//extends V3Object {

	private static final long serialVersionUID = 1L;
	private MappedDocBookmarkId pk = new MappedDocBookmarkId();
	
	@EmbeddedId
	public MappedDocBookmarkId getPk(){
		return pk;
	}

	private DateTime createDate;
	private SecurityUser user;

	@Transient
	public DocShell getDocShell() {
		return getPk().getDocShell();
	}

	public void setDocShell(DocShell docShellId) {
		getPk().setDocShell( docShellId );
	}
	@Transient
	public DocBookmark getDocBookmark() {
		return getPk().getDocBookmark();
	}

	public void setDocBookmark(DocBookmark markId) {
		getPk().setDocBookmark(markId);
	}
	public void setPk( MappedDocBookmarkId pk){
		this.pk = pk;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj== null )
			return false;
		if (!(obj instanceof MappedDocBookmark) )
			return false;
		MappedDocBookmark other=  (MappedDocBookmark)obj ;
		if (pk == null){
			if (other.pk != null)
				return false;
		}else if (!pk.equals(other.pk))
			return false;
		//if () other
		return true;
	}
	
	@Override 
	public int hashCode(){
		return (getPk() !=null ? getPk().hashCode():0);
	}

	@Column(name="CREATED_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime dateTime) {
		this.createDate = dateTime;
	}
	@JoinColumn(name="USER_NAME")
	@ManyToOne//(fetch=FetchType.LAZY)
	public SecurityUser getUser() {
		return user;
	}

	public void setUser(SecurityUser user) {
		this.user = user;
	}
	

}
