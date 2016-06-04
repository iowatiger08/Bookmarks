package com.tigersndragons.docbookmarks.service;

import java.util.List;

import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.DocShell;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface DocShellService {
	public List<DocShell> getListOfDocShells();
	
	public DocShell getDocShellById(Long id) throws EntityNotFoundException;
	
	public DocShell getDocShellByName(String name) throws EntityNotFoundException;
	
	public Boolean docShellHasDocuments(Long Id) throws EntityNotFoundException;
	
	public List<MappedDocBookmark> getListOfMappedBookmarks();
	
	//public List<MappedDocBookmark> getListOfBookmarksForDocShell(DocShell ds);
	
	public MappedDocBookmark addMappedBookmark(DocShell docShell, DocBookmark markId, SecurityUser user);
	
	public List<DocShell> getListOfDocShellwoBookmarks();
	
	public MappedDocBookmark removeMappedMarkFromShell(MappedDocBookmark mark)  throws EntityNotFoundException;
	
	public List<DocShell> getListOfDoneDocShells();
	public List<DocShell> getListOfNotDoneDocShells();


	public List<DocShell> getListOfPartialDoneDocShells();
	
	public DocShell setDocShellIsDone(Long id) throws EntityNotFoundException;

	public DocShell updateDocShell(DocShell ds) throws EntityNotFoundException;

}
