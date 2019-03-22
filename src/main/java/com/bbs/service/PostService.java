package com.bbs.service;

import com.bbs.Dao.BoardDao;
import com.bbs.Dao.PostDao;
import com.bbs.bean.Board;
import com.bbs.bean.Post;
import com.bbs.util.MybatisUtil;
import com.easyFramework.annotation.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * PostService
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Service
public class PostService{

    public PostService(){

    }


    public void addPostByPost(Post post) {
        SqlSession session = MybatisUtil.openSession();
        PostDao postDao = session.getMapper(PostDao.class);

        postDao.addPost(post);
        session.commit();

        if (session != null)
            session.close();
    }


    public Post listPostContent(int postId) {
        SqlSession session = MybatisUtil.openSession();
        PostDao postDao = session.getMapper(PostDao.class);

        Post post=postDao.findPostByPostId(postId);

        if (session != null)
            session.close();

        return post;
    }


    public List<Post> listAllPost() {
        SqlSession session = MybatisUtil.openSession();
        PostDao postDao = session.getMapper(PostDao.class);

        List<Post> posts=postDao.listAllPostInfo();

        if (session != null)
            session.close();
        return posts;
    }

    public void deletePost(int postId) {
        SqlSession session = MybatisUtil.openSession();
        PostDao postDao = session.getMapper(PostDao.class);
        BoardDao boardDao =session.getMapper(BoardDao.class);


        int boardId = postDao.findPostByPostId(postId).getPostBoardId();
        Board board = boardDao.findBoardByBoardId(boardId);
        board.setBoardPostNum(board.getBoardPostNum() - 1);
        boardDao.updateBoardByBoard(board);

        postDao.deletePostById(postId);

        session.commit();
        if (session != null)
            session.close();
    }
}
