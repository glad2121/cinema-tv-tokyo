package com.twitter.cinema_tv_tokyo.search.impl;

import static org.junit.Assert.*;

import java.net.Authenticator;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.util.ProxyAuthenticator;
import com.twitter.cinema_tv_tokyo.search.ProgramSearchService;

/**
 * {@link ProgramSearchServiceImpl} の単体テスト。
 * 
 * @author ITO Yoshiichi
 */
public class ProgramSearchServiceImplTest {

    ProgramSearchServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new ProgramSearchServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testParse() throws Exception {
        URL url = getClass().getResource("example.html");
        Page<Program> page = service.parse(url);
        assertEquals(17, page.getTotalCount());
        assertEquals(10, page.getOffset());
        assertEquals(7, page.getCount());
        
        List<Program> list = page.getContent();
        assertEquals(7, list.size());
    }

    public static void main(String[] args) {
        ProxyAuthenticator authenticator = ProxyAuthenticator.INSTANCE;
        if (authenticator.getProxyUser() != null) {
            Authenticator.setDefault(authenticator);
        }
        ProgramSearchService service = new ProgramSearchServiceImpl();
        Page<Program> page = service.findProgram(null);
        for (Program program : page.getContent()) {
            System.out.println(program);
        }
    }

}
