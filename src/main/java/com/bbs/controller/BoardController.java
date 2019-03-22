package com.bbs.controller;

import com.bbs.bean.Board;
import com.bbs.service.BoardService;
import com.easyFramework.annotation.*;
import com.easyFramework.bean.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * BoardController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
@RequestMapping(value = "/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    /**
     * 显示指定板块的文章
     *
     * @param boardId 板块 id
     * @return 返回页面
     */
    @RequestMapping("/listPosts")
    public ModelAndView listPosts(@RequestParam("boardId") int boardId, HttpServletRequest request) {
        ModelAndView modelandview = new ModelAndView();
        Board board = boardService.listAllPostOfBoard(boardId);
        modelandview.setPath("post/postMain.jsp");
        modelandview.addModel("board", board);
        request.getSession().setAttribute("boardId", boardId);
        return modelandview;
    }
}
