package com.tigersndragons.docbookmarks.controller;

import com.tigersndragons.docbookmarks.BaseTestCase;
import com.tigersndragons.docbookmarks.service.LoginService;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * Created by user on 6/6/16.
 */
public class AcctControllerTest extends BaseTestCase {
    private LoginService loginService;

    @Before
    private void setUp(){
        loginService = mock (LoginService.class);

    }
}
