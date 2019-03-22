package com.bbs.controller;



import com.bbs.bean.Board;
import com.bbs.bean.Post;
import com.bbs.bean.Reply;
import com.bbs.bean.User;
import com.bbs.service.BoardService;
import com.bbs.service.PostService;
import com.bbs.service.ReplyService;
import com.bbs.service.UserService;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Controller;
import com.easyFramework.annotation.RequestMapping;
import com.easyFramework.annotation.RequestParam;
import com.easyFramework.bean.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * AdminController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    ReplyService replyService;


    /**
     * 论坛管理中心
     *OK
     * @return 页面
     */
    @RequestMapping("/manageCenter")
    public ModelAndView manageCenter() {
        ModelAndView modelAndView = new ModelAndView();
//        if (username.equals("admin")) {
            modelAndView.setPath("admin/manageCenter.jsp");
            return modelAndView;
//        }
//        modelAndView.setPath("redirect:/error.jsp");
//        return modelAndView;
    }

    /**
     * 论坛版块管理中心
     *OK
     * @return 页面
     */
    @RequestMapping("/manageBoard")
    public ModelAndView manageBoard(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
//        if (username.equals("admin")) {
            List<Board> boards = boardService.listAllBoard();
            modelAndView.addModel("boards", boards);
            request.setAttribute("boards", boards);
            modelAndView.setPath("admin/manageBoard.jsp");
//        }
//        modelAndView.setPath("redirect:/error.jsp");
        return modelAndView;
    }

    /**
     * 添加论坛板块
     * @return 页面
     * OK
     */
    @RequestMapping(value ="/addBoard",method = "post")
    public ModelAndView addBoard(@RequestParam("boardName")String boardName,@RequestParam("boardDesc")String boardDesc) {
        ModelAndView modelAndView = new ModelAndView();
        if (boardName != null&&boardDesc != null) {
            Board board = new Board();
            board.setBoardName(boardName);
            board.setBoardDesc(boardDesc);
            boardService.addBoardByBoard(board);
            List<Board> boards = boardService.listAllBoard();
            modelAndView.addModel("boards", boards);
            modelAndView.setPath("admin/manageBoard.jsp");
            return modelAndView;
        }
        modelAndView.setPath("admin/error.jsp");
    return modelAndView;
    }

    /**
     * 修改板块信息
     * OK
     * @return 页面
     */
    @RequestMapping(value = "/updateBoard",method="post")
    public ModelAndView updateBoard(@RequestParam("boardId") int boardId,
                                    @RequestParam("boardPostNum") int boardPostNum,
                                    @RequestParam("boardName")String boardName,
                                    @RequestParam("boardDesc")String boardDesc) {
        ModelAndView modelAndView = new ModelAndView();
        Board board = boardService.intoBoardByBoardId(boardId);
        if (board != null) {
            board.setBoardPostNum(boardPostNum);
            board.setBoardName(boardName);
            board.setBoardDesc(boardDesc);
            boardService.updateBoardInfo(board);
            // 成功更新数据后跳转页面
            List<Board> boards = boardService.listAllBoard();
            modelAndView.addModel("boards", boards);
            modelAndView.setPath("admin/manageBoard.jsp");
            return modelAndView;
        }
        modelAndView.setPath("error.jsp");
        // 更新数据失败跳转到错误页面
        return modelAndView;
    }

    /**
     * 管理用户信息
     *OK
     * @return 页面
     */
    @RequestMapping("/manageUser")
    public ModelAndView manageUser() {
        ModelAndView modelAndView =new ModelAndView();
        List<User> users = userService.getAllUser();
        if (users != null) {
            modelAndView.addModel("users", users);
            modelAndView.setPath("/admin/allUserInfo.jsp");
            return modelAndView;
        }

        modelAndView.setPath("redirect:/error.jsp");
        return modelAndView;
    }

    /**
     * 管理发表的主题
     * @return 页面
     * OK
     */
    @RequestMapping("/managePost")
    public ModelAndView managePost() {
        ModelAndView modelAndView =new ModelAndView();
        List<Post> posts = postService.listAllPost();
        if (posts != null) {
            modelAndView.addModel("posts", posts);
            modelAndView.setPath("/admin/allPostInfo.jsp");
            return modelAndView;
        }
        modelAndView.setPath("redirect:/error.jsp");
        return modelAndView;
    }

    /**
     * 删除已经发表的文章
     *OK
     * @param postId 文章 id
     * @param postBoardId 板块 id
     * @return 页面
     */
    @RequestMapping(value = "/deletePost")
    public ModelAndView deletePost(@RequestParam("postId") int postId, @RequestParam("postBoardId") int postBoardId) {
        ModelAndView modelAndView = new ModelAndView();
        postService.deletePost(postId);
        Board board = boardService.listAllPostOfBoard(postBoardId);
        modelAndView.setPath("post/postMain.jsp");
        modelAndView.addModel("board", board);
        return modelAndView;
    }

    /**
     * 删除回复
     *OK
     * @param replyId 回复 id
     * @param replyPostId 回复的文章 id
     * @return 页面
     */
    @RequestMapping(value = "/deleteReply")
    public ModelAndView deleteReply(@RequestParam("replyId") int replyId,@RequestParam("replyPostId") int replyPostId) {
        ModelAndView modelAndView = new ModelAndView();
        replyService.deleteReply(replyId);
        Post post = postService.listPostContent(replyPostId);
        List<Reply> replies = replyService.listReplyByPostId(replyPostId);
        if (post == null) {
            modelAndView.setPath("error.jsp");
            return modelAndView;
        }
        if (replies != null) {
            modelAndView.addModel("replies", replies);
        }
        modelAndView.addModel("post", post);
        modelAndView.setPath("post/postContent.jsp");
        return modelAndView;
    }

    /**
     * 删除板块
     *OK
     * @param boardId 板块 id
     * @return 页面
     */
    @RequestMapping(value = "/deleteBoard")
    public ModelAndView deleteBoard(@RequestParam("boardId") int boardId) {
        ModelAndView modelAndView = new ModelAndView();
        boardService.deleteBoard(boardId);
//        if (username.equals("admin")) {
        List<Board> boards = boardService.listAllBoard();
        modelAndView.addModel("boards", boards);
        modelAndView.setPath("admin/manageBoard.jsp");
//        }
        return modelAndView;
    }
}
