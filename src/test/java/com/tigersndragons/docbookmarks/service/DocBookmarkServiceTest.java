package com.tigersndragons.docbookmarks.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.BaseTestCase;
import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DocBookmarkServiceTest extends BaseTestCase {
/*
		private static final String SELECT_DOC_BOOKMARK_1 = 
			"SELECT * FROM DOC_BOOKMARK order by MARK_ID asc";

		private static final String SELECT_DOC_BOOKMARK_2 = 
			"SELECT * FROM DOC_BOOKMARK WHERE MARK_ID = 606";
*/
		@Autowired
		DocBookmarkService docBookmarkService;
		
		@Test
		public void loadDocBookmarRecord58(){
			Long id = 58L;
			DocBookmark db = null;
			try {
				db = docBookmarkService.getDocBookmarkById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull (db);
			}
			assertNotNull  (db);
			assert ( db.getMarkName().trim().equalsIgnoreCase("Dependent_Info") );
		}
		
		@Test
		public void loadDocBookmarRecords(){
			List<DocBookmark> alist = docBookmarkService.getListOfDocBookmarks();
			assertNotNull  (alist);
			assert (!alist.isEmpty() && alist.size()>0);
		}
		
		@Test
		public void loadOrphanedDocBookmarRecords(){
			List<DocBookmark> alist = docBookmarkService.getListOfUnMappedBookmarks();
			assertNotNull  (alist);
			assert (!alist.isEmpty() && alist.size()>0);
			DocBookmark db = null;
			try {
				db = docBookmarkService.getDocBookmarkById(1L);
			} catch (EntityNotFoundException e) {
				assert (false);
			}
			assert (!alist.contains(db) );
		}
		
		@Test
		public void loadGroupedMappedDocBookmarRecords(){
			List<Object> alist = docBookmarkService.listOfGroupByMappedBookmarks();
			assertNotNull  (alist);
			assert (!alist.isEmpty() && alist.size()>0);
			DocBookmark db = null;
			try {
				db = docBookmarkService.getDocBookmarkById(1L);
			} catch (EntityNotFoundException e) {
				assert (false);
			}
			assert (alist.get(0) instanceof DocBookmark 
					&& alist.get(0).equals(db) );
		}
}
