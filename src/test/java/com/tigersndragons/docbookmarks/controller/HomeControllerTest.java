package com.tigersndragons.docbookmarks.controller;

import com.tigersndragons.docbookmarks.BaseTestCase;
import com.tigersndragons.docbookmarks.exception.EntityNotFoundException;
import com.tigersndragons.docbookmarks.model.DocBookmark;
import com.tigersndragons.docbookmarks.model.DocShell;
import com.tigersndragons.docbookmarks.service.DocBookmarkService;
import com.tigersndragons.docbookmarks.service.DocShellService;
import com.tigersndragons.docbookmarks.service.LoginService;
import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by user on 6/6/16.
 */
public class HomeControllerTest extends BaseTestCase {

    private LoginService loginService;
    private DocShellService docShellService;
    private DocBookmarkService docBookmarkService;

    private DocShell testDocShell(){
        DocShell docShell = new DocShell();
        docShell.setId(0L);
        docShell.setDocShellName("TESTER");
        return docShell;
    }

    private DocBookmark testDocBookmark(){
        DocBookmark docBookmark = new DocBookmark();
        docBookmark.setId(0L);
        docBookmark.setMarkName("TestMark");
        return docBookmark;
    }
    @Before
    private void setUp() throws EntityNotFoundException {
        loginService = mock (LoginService.class);
        docShellService = mock(DocShellService.class);
        docBookmarkService = mock (DocBookmarkService.class);

        when (docShellService.getDocShellById(0L)).thenReturn (testDocShell());
        when (docBookmarkService.getDocBookmarkById(0L)).thenReturn(testDocBookmark());
    }
}
