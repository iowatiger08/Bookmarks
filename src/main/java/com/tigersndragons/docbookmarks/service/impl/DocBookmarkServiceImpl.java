package com.tigersndragons.docbookmarks.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.service.DocBookmarkService;
import com.tigersndragons.docbookmarks.dao.DocBookmarksDAO;
import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Required;

public class DocBookmarkServiceImpl implements DocBookmarkService {

	private DocBookmarksDAO dbDAO;
	public List<DocBookmark> getListOfDocBookmarks()  {
		return dbDAO.retrieveEntities(DocBookmark.class);
	}

	public DocBookmark getDocBookmarkById(Long id) throws EntityNotFoundException  {
		if (id ==null){
			throw new IllegalArgumentException("id is null");
		}
		List<DocBookmark> obj = dbDAO.findByKeyValue(DocBookmark.class, "id", id);
		 if (obj ==null || obj.isEmpty() || obj.size()>1){
			 throw new EntityNotFoundException("id");
		 }else{
			 return obj.get(0);
		 }
	}

	public DocBookmark getDocBookmarkByName(String name)  throws EntityNotFoundException {
		if (name ==null){
			throw new IllegalArgumentException("name is null");
		}
		List<DocBookmark> alist =dbDAO.findByKeyValue(DocBookmark.class, "internalName", name);
		 if (alist ==null || alist.isEmpty() ){
			 throw new  EntityNotFoundException(name);
		 }else{
			 return alist.get(0);
		 }
	}
	
	public Map<String, String> filterOutMappedMarks(
			Set<MappedDocBookmark> mappedDocBookmarks){
		List<DocBookmark> list = getListOfDocBookmarks();
		
		for (MappedDocBookmark mapped : mappedDocBookmarks){
			if (list.contains(mapped.getDocBookmark())){
				list.remove(mapped.getDocBookmark());
			}
		}
		Map<String, String> mappedlist = new LinkedHashMap<String, String>();
		for (DocBookmark db : list){
			mappedlist.put(db.getId()+"", db.getInternalName());
		}
		return mappedlist;
	}
	
	public List<DocBookmark> listOfNonMappedMarks(
			Set<MappedDocBookmark> mappedDocBookmarks){
		List<DocBookmark> list = getListOfDocBookmarks();
		
		for (MappedDocBookmark mapped : mappedDocBookmarks){
			if (list.contains(mapped.getDocBookmark())){
				list.remove(mapped.getDocBookmark());
			}
		}
	
		return list;
	}
	
	public List<MappedDocBookmark> getListOfMappedBookmarks() {		
		return dbDAO.retrieveEntities(MappedDocBookmark.class);
	}

	public List<DocBookmark> getListOfUnMappedBookmarks() {
		List<DocBookmark> list = getListOfDocBookmarks();
		List<Object> mappedDocBookmarks = listOfGroupByMappedBookmarks();
		//listOfGroupByMappedBookmarks();
		for (Object mapped : mappedDocBookmarks){
			if (mapped instanceof DocBookmark 
					&& list.contains((DocBookmark)mapped)){
				list.remove(mapped);
			}
		}
		return list;
	}

	public List<Object> listOfGroupByMappedBookmarks() {
		List<Object> list = dbDAO.queryWithGroupBy(MappedDocBookmark.class, "pk.docBookmark");
		
		return list;
	}
	
	@Required
	public void setDbDAO(DocBookmarksDAO dbDAO) {
		this.dbDAO = dbDAO;
	}
}
