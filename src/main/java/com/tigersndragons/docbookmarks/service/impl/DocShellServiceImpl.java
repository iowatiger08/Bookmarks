package com.tigersndragons.docbookmarks.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.DocShell;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import com.tigersndragons.docbookmarks.dao.DocBookmarksDAO;
import com.tigersndragons.docbookmarks.service.DocShellService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Required;

public class DocShellServiceImpl implements DocShellService {

	
	private DocBookmarksDAO dbDAO;
	public List<DocShell> getListOfDocShells() {
		return dbDAO.retrieveEntities(DocShell.class);
	}

	public DocShell getDocShellById(Long id) throws EntityNotFoundException {
		if (id ==null){
			throw new IllegalArgumentException("id is null");
		}
		List<DocShell> obj = dbDAO.findByKeyValue(DocShell.class, "id", id);
		 if (obj ==null || obj.isEmpty() ){
			 throw new  EntityNotFoundException("id");
		 }else if (obj.size()>1){
			 throw new EntityNotFoundException("Non-Unique id");
		 }else{	 
			 return obj.get(0);
		 }
	}

	public DocShell getDocShellByName(String name) throws EntityNotFoundException {
		if (name ==null){
			throw new IllegalArgumentException("name is null");
		} 
		List<DocShell> alist =dbDAO.findByKeyValue(DocShell.class, "docShellDisplayName", name);
		 if (alist ==null || alist.isEmpty() ){
			 throw new  EntityNotFoundException(name);
		 }else{
			 return alist.get(0);
		 }
	}

	public Boolean docShellHasDocuments(Long Id) throws EntityNotFoundException {
		if (Id ==null){
			throw new IllegalArgumentException("id is null");
		}
		DocShell ds = getDocShellById(Id);		
		return ds !=null 
				&& ds.getHasDocument()!=null 
				&& ds.getHasDocument()==1;
	}
	
	public List<MappedDocBookmark> getListOfMappedBookmarks() {
		return dbDAO.retrieveEntities(MappedDocBookmark.class);
	}	

	public MappedDocBookmark addMappedBookmark(DocShell docShell,
											   DocBookmark markId, SecurityUser user) {
		MappedDocBookmark mdb = new MappedDocBookmark();
		mdb.setDocBookmark(markId);
		mdb.setDocShell(docShell);
		mdb.setUser(user);
		mdb.setCreateDate(new DateTime());

		if (!docShell.getMappedDocBookmarks().contains(mdb)
				&& !markId.getMappedDocBookmarks().contains(mdb)){
			dbDAO.save(mdb);
		}
		if (docShell.getIsDone().equals(new Integer(1))){
			docShell.setIsDone(new Integer(0));
			dbDAO.save(docShell);
		}
		return mdb;
	}

	public List<DocShell> getListOfDocShellwoBookmarks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Required
	public void setDbDAO(DocBookmarksDAO dbDAO) {
		this.dbDAO = dbDAO;
	}

	public MappedDocBookmark removeMappedMarkFromShell(MappedDocBookmark value)  throws EntityNotFoundException{
		if (value !=null){
			dbDAO.remove(value);
		}else{
			 throw new EntityNotFoundException("MappedDocBookmark");
		}
		return value;
	}

	public List<DocShell> getListOfDoneDocShells() {
		List<DocShell> alist =dbDAO.findByKeyValue(DocShell.class, "isDone", new Integer(1));
		if (!alist.isEmpty() 
				&&(alist.get(0)).getId()<=2){
			return alist.subList(1, alist.size());//.remove(alist.get(0));//test element
		}
		return alist;
	}

	public List<DocShell> getListOfNotDoneDocShells() {
		List<DocShell> alist =dbDAO.findByKeyValue(DocShell.class, "isDone", new Integer(0));
		if (!alist.isEmpty() 
				&&(alist.get(0)).getId()<=2){
			return alist.subList(1, alist.size());// test element
		}
		return alist;
	}

	public DocShell setDocShellIsDone(Long id) throws EntityNotFoundException {
		DocShell selected = getDocShellById(id);	
		selected.setDoneDate(new DateTime());
		selected.setIsDone(new Integer(1));
		dbDAO.save(selected);
		return selected;
	}

	public DocShell updateDocShell(DocShell ds) throws EntityNotFoundException {
		dbDAO.save(ds);
		return ds;
	}

	public List<DocShell> getListOfPartialDoneDocShells() {
		List<DocShell> alist = getListOfNotDoneDocShells();
		List<DocShell> partials= new ArrayList<DocShell>();
		for (DocShell ds : alist){
			if (ds.getMappedDocBookmarks()!=null  
					&& !ds.getMappedDocBookmarks().isEmpty()){
				partials.add(ds);
			}
		}		
		return partials;
	}

}
