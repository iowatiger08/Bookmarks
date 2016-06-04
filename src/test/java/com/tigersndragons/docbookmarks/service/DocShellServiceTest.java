package com.tigersndragons.docbookmarks.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.MappedDocBookmark;
import com.tigersndragons.docbookmarks.BaseTestCase;
import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import com.tigersndragons.docbookmarks.model.DocShell;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DocShellServiceTest extends BaseTestCase {
/*	
  		private static final String SELECT_DOC_SHELL_1 = 
			"SELECT * FROM DOC_SHELL order by DOC_SHELL_ID asc";

		private static final String SELECT_DOC_SHELL_2 = 
			"SELECT * FROM DOC_SHELL WHERE DOC_SHELL_ID = 606";
*/
		@Autowired
		DocShellService docShellService;
		@Autowired
		DocBookmarkService docBookmarkService;
		@Test
		public void loadDocShellRecords(){
			List<DocShell> alist = docShellService.getListOfDocShells();
			assertNotNull  (alist);
			assert (!alist.isEmpty() && alist.size()>0);
			//printResults(alist);
		}
		
		@Test
		public void loadDocShellRecord356(){
			Long id = 356L;
			DocShell db = null;
			try {
				db = docShellService.getDocShellById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull(db);
			}
			assertNotNull  (db);
			assert ( db.getDocShellName().trim().equalsIgnoreCase("EFT Authorization") );
		}
		
		@Test
		public void testAddandRemoveMappedBookmark(){
			Long id = 356L;
			DocShell db = null;
			try {
				db = docShellService.getDocShellById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull(db);
			}
			id = 58L;
			DocBookmark mark = null;
			try {
				mark = docBookmarkService.getDocBookmarkById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull (mark);
			}
			SecurityUser user = new SecurityUser();
//			user.setId(1L);
			user.setUserName("tdillon");//(1L);
			MappedDocBookmark mdb = docShellService.addMappedBookmark(db, mark, user);
			assertNotNull(mdb);
			
			assert (db.getMappedDocBookmarks().contains(mdb)
					&& mark.getMappedDocBookmarks().contains(mdb));	
			
			//remove it now  - restore to pre-testing condition
			try {
				mdb = docShellService.removeMappedMarkFromShell(mdb);
			} catch (EntityNotFoundException e) {
				assert(false);
			}
			assert (!db.getMappedDocBookmarks().contains(mdb)
					&& !mark.getMappedDocBookmarks().contains(mdb));
			
		}
		
		@Test
		public void testGetMappedBookmark(){
			Long id = 1L;
			DocShell ds = null;
			try {
				ds = docShellService.getDocShellById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull(ds);
			}
			
			id = 1L;
			DocBookmark mark = null;
			try {
				mark = docBookmarkService.getDocBookmarkById(id);
			} catch (EntityNotFoundException e) {
				assertNotNull (mark);
			}
			MappedDocBookmark testmdb = new MappedDocBookmark();
			testmdb.setDocBookmark(mark);
			testmdb.setDocShell(ds);
			assert (ds.getMappedDocBookmarks().size()>0
					&& mark.getMappedDocBookmarks().size() >0
					&& ds.getMappedDocBookmarks().contains(testmdb)
					&& mark.getMappedDocBookmarks().contains(testmdb)
					);
			
			List<MappedDocBookmark > aList= docShellService.getListOfMappedBookmarks();
			assert aList.contains(testmdb);
			
		}
}
