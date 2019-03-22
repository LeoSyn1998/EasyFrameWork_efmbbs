package com.bbs.controller;



import com.bbs.bean.Board;
import com.bbs.bean.User;
import com.bbs.service.BoardService;
import com.bbs.service.PostService;
import com.bbs.service.UserService;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Controller;
import com.easyFramework.annotation.RequestMapping;
import com.easyFramework.annotation.RequestParam;
import com.easyFramework.bean.ModelAndView;
import java.util.List;

/**
 * MainController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
public class MainController {
    @Autowired
    BoardService boardService;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    public MainController() {
    }

    /**
     * 主页
     *OK
     * @return 主页
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView =new ModelAndView("index.jsp");
        return modelAndView;
    }

    /**
     * 显示内容主页
     *OK
     * @param
     * @return 返回页面
     */
    @RequestMapping("/main")
    public ModelAndView mainPage() {
        ModelAndView modelAndView =new ModelAndView();
        List<Board> boards = boardService.listAllBoard();
        modelAndView.addModel("board",boards);
        modelAndView.setPath("mainPage.jsp");
        return modelAndView;
    }

    /**
     * 显示用户登录页面
     *OK
     * @return 返回页面
     */
    @RequestMapping("/userLogin")
    public ModelAndView userLogin() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("user/userLogin.jsp");
        return modelAndView;
    }

    /**
     * 显示注册页面
     *OK
     * @return 返回页面
     */
    @RequestMapping("/userRegister")
    public ModelAndView userRegister() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("user/userRegister.jsp");
        return modelAndView;
    }

    @RequestMapping("/addPost")
    public ModelAndView addPost() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("post/addPost.jsp");
        return modelAndView;
    }

    /**
     * 显示添加回复的页面
     * @return 返回页面
     */
    @RequestMapping("/addReply")
    public ModelAndView addReply() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("reply/reply.jsp");
        return modelAndView;
    }

    /**
     * 显示添加板块的页面
     *
     * @return 返回页面
     */
    @RequestMapping("/addBoard")
    public ModelAndView addBoardPage() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("admin/addBoard.jsp");
        return modelAndView;
    }

    /**
     * 显示板块
     *OK
     * @param boardId 板块 id
     * @return 返回页面
     */
    @RequestMapping("/updateBoardPage")
    public ModelAndView updateBoardPage(@RequestParam("boardId") int boardId) {
        ModelAndView modelAndView =new ModelAndView();
        Board board = boardService.intoBoardByBoardId(boardId);
        if (board != null) {
            modelAndView.addModel("boardName", board.getBoardName());
            modelAndView.addModel("boardDesc", board.getBoardDesc());
            modelAndView.addModel("boardId", board.getBoardId());
            modelAndView.addModel("boardPostNum", board.getBoardPostNum());
            modelAndView.setPath("admin/updateBoard.jsp");
        }
        return modelAndView;
    }

    @RequestMapping("/updateUserPage")
    public ModelAndView updateUserPage(@RequestParam("username") String username) {
        ModelAndView modelAndView =new ModelAndView();
        User user = userService.getUserByUserName(username);
        if (user != null) {
            modelAndView.addModel("user",user);
            modelAndView.setPath("user/userUpdateInfo.jsp");
            return modelAndView;
        }
        modelAndView.setPath("admin/error.jsp");
        return modelAndView;
    }


    /**
     * 错误页面
     *
     * @return 返回错误页面
     */
    @RequestMapping("/error")
    public ModelAndView error() {
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setPath("error.jsp");
        return modelAndView;
    }
}
