package com.bbs.service;

import com.bbs.Dao.PostDao;
import com.bbs.Dao.ReplyDao;
import com.bbs.bean.Post;
import com.bbs.bean.Reply;
import com.bbs.util.MybatisUtil;
import com.easyFramework.annotation.Service;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.List;

/**
 * ReplyService
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Service
public class ReplyService {
//    private  ReplyDao replyDao;
//    private  PostDao postDao;
//
//    public ReplyService(ReplyDao replyDao, PostDao postDao) {
//        this.replyDao = replyDao;
//        this.postDao = postDao;
//    }

    public ReplyService(){

    }

    public void addReply(Reply reply) {
        // 更新post信息
//        Reply dbReply = reply;
//        int postId = reply.getReplyPostId();
//        Post post = postDao.findPostByPostId(postId);
//        post.setPostReplyCount(post.getPostReplyCount() + 1);
//        postDao.updatePostByPost(post);
//
//        // 添加回复
//        dbReply.setReplyCreateTime(new Timestamp(System.currentTimeMillis()));
//        replyDao.addReply(dbReply);

        SqlSession session = MybatisUtil.openSession();
        ReplyDao replyDao = session.getMapper(ReplyDao.class);
        PostDao postDao = session.getMapper(PostDao.class);

        // 更新post信息
        Reply dbReply = reply;
        int postId = reply.getReplyPostId();
        Post post = postDao.findPostByPostId(postId);
        post.setPostReplyCount(post.getPostReplyCount() + 1);
        postDao.updatePostByPost(post);

        // 添加回复
        dbReply.setReplyCreateTime(new Timestamp(System.currentTimeMillis()));
        replyDao.addReply(dbReply);

        session.commit();
        if (session != null)
            session.close();

    }


    public List<Reply> listReplyByPostId(int replyPostId) {
//        return replyDao.listReplyByPostId(replyPostId);
        SqlSession session = MybatisUtil.openSession();
        ReplyDao replyDao = session.getMapper(ReplyDao.class);

        List<Reply> rep= replyDao.listReplyByPostId(replyPostId);

        if (session != null)
            session.close();

        return rep;
    }


    public void deleteReply(int replyId) {
//        // 更新post信息
//        Reply reply = replyDao.findReplyByReplyId(replyId);
//        Post post = postDao.findPostByPostId(reply.getReplyPostId());
//        post.setPostReplyCount(post.getPostReplyCount() - 1);
//        postDao.updatePostByPost(post);
//
//        // 删除回复
//        replyDao.deleteReplyById(replyId);
        SqlSession session = MybatisUtil.openSession();
        ReplyDao replyDao = session.getMapper(ReplyDao.class);
        PostDao postDao = session.getMapper(PostDao.class);

        Reply reply = replyDao.findReplyByReplyId(replyId);
        Post post = postDao.findPostByPostId(reply.getReplyPostId());
        post.setPostReplyCount(post.getPostReplyCount() - 1);
        postDao.updatePostByPost(post);

        replyDao.deleteReplyById(replyId);

        session.commit();
        if (session != null)
            session.close();

    }
}
