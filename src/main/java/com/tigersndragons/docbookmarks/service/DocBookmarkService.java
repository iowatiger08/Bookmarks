package com.tigersndragons.docbookmarks.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface DocBookmarkService {
	
	public List<DocBookmark> getListOfDocBookmarks();
	
	public DocBookmark getDocBookmarkById(Long id) throws EntityNotFoundException;
	
	public DocBookmark getDocBookmarkByName(String name) throws EntityNotFoundException;
	
	public List<DocBookmark> getListOfUnMappedBookmarks();

	public Map<String, String> filterOutMappedMarks(
			Set<MappedDocBookmark> mappedDocBookmarks);
	
	public List<DocBookmark> listOfNonMappedMarks(
			Set<MappedDocBookmark> mappedDocBookmarks);
	
	public List<Object> listOfGroupByMappedBookmarks( );
}
