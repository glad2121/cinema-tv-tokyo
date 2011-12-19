package com.twitter.cinema_tv_tokyo.search;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.model.ProgramCriteria;

/**
 * 番組検索サービス。
 * 
 * @author ITO Yoshiichi
 */
public interface ProgramSearchService {

    Page<Program> findProgram(ProgramCriteria criteria);

}
