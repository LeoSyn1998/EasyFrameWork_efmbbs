package com.bbs.controller;


import com.bbs.bean.Post;
import com.bbs.bean.Reply;
import com.bbs.service.PostService;
import com.bbs.service.ReplyService;
import com.bbs.service.UserService;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Controller;
import com.easyFramework.annotation.RequestMapping;
import com.easyFramework.annotation.RequestParam;
import com.easyFramework.bean.ModelAndView;

import java.util.List;

/**
 * ReplyController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
@RequestMapping(value = "/reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    /**
     * 添加回复
     * @return 重定向页面
     */
        @RequestMapping(value = "/addReply", method = "post")
        public ModelAndView addReply(@RequestParam("replyPostId")int replyPostId,
                                     @RequestParam("replyUserName")String replyUserName,
                                     @RequestParam("replyContent") String replyContent) {
        ModelAndView modelAndView = new ModelAndView();
        if (replyPostId > 0 && replyUserName != null) {
            Reply reply = new Reply();
            Post post = postService.listPostContent(replyPostId);
            reply.setReplyContent(replyContent);
            reply.setReplyPostId(replyPostId);
            reply.setReplyUserName(replyUserName);
            replyService.addReply(reply);

            List<Reply> replies = replyService.listReplyByPostId(replyPostId);

            if (post == null) {
                modelAndView.setPath("error.jsp");
                return modelAndView;
            }
            // 帖子有回复则添加回复信息
            if (replies != null) {
                modelAndView.addModel("replies", replies);
            }
            modelAndView.addModel("post", post);
            modelAndView.setPath("post/postContent.jsp");
            return modelAndView;
        }
        modelAndView.setPath("redirect:error.jsp");
        return modelAndView;
    }
}
