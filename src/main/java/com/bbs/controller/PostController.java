package com.bbs.controller;

import com.bbs.bean.Board;
import com.bbs.bean.Post;
import com.bbs.bean.Reply;
import com.bbs.service.BoardService;
import com.bbs.service.PostService;
import com.bbs.service.ReplyService;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Controller;
import com.easyFramework.annotation.RequestMapping;
import com.easyFramework.annotation.RequestParam;
import com.easyFramework.bean.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * PostController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
@RequestMapping(value = "/post")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    BoardService boardService;
    @Autowired
    ReplyService replyService;


    public PostController() {
    }

    /**
     * 添加帖子
     * @return 返回页面
     */
    @RequestMapping(value = "/addPost",method = "post")
    public ModelAndView addPost(@RequestParam("postBoardId")int postBoardId,@RequestParam("postUserName")String postUserName,
                                @RequestParam("postTitle") String postTitle,@RequestParam("postContent") String postContent,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (postTitle != null&&postContent != null) {
            Post newPost = new Post();
//            newPost.setPostId(postBoardId);
            newPost.setPostBoardId(postBoardId);
            newPost.setPostUserName(postUserName);
            newPost.setPostTitle(postTitle);
            newPost.setPostContent(postContent);
            Timestamp createLoginTime = new Timestamp(System.currentTimeMillis());
            newPost.setPostCreateTime(createLoginTime);
            newPost.setPostUpdateTime(createLoginTime);

            postService.addPostByPost(newPost);
            boardService.updatePostNum(postBoardId);

            Board board = boardService.listAllPostOfBoard(postBoardId);
            modelAndView.setPath("post/postMain.jsp");
            modelAndView.addModel("board", board);
            request.getSession().setAttribute("boardId", postBoardId);
            return modelAndView;
        }
        modelAndView.setPath("error.jsp");
        return modelAndView;
    }

    /**
     * 查看帖子
     *
     * @param postId  帖子 id
     * @return 返回页面
     */
    @RequestMapping(value = "/postContent")
    public ModelAndView intoPost(@RequestParam("postId") int postId,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.listPostContent(postId);
        List<Reply> replies = replyService.listReplyByPostId(postId);

        if (post == null) {
            modelAndView.setPath("error.jsp");
            return modelAndView;
        }
        // 帖子有回复则添加回复信息
        if (replies != null) {
            modelAndView.addModel("replies", replies);
        }
        modelAndView.addModel("post", post);
        request.getSession().setAttribute("postId", postId);
        modelAndView.setPath("post/postContent.jsp");
        return modelAndView;
    }
}
