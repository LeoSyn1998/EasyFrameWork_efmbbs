package com.bbs.Dao;

import com.bbs.bean.Post;

import java.util.List;

public interface PostDao {
    /**
     * 添加文章
     *
     * @param post 需要添加的文章
     */
    void addPost(Post post);

    /**
     * 通过文章 id 查找文章
     *
     * @param postId 文章 id
     * @return 查找到的文章
     */
    Post findPostByPostId(int postId);

    /**
     * 获取所有文章
     *
     * @return 文章列表
     */
    List<Post> listAllPostInfo();

    /**
     * 通过文章 id 删除文章
     *
     * @param postId 文章 id
     */
    void deletePostById(int postId);

    /**
     * 更新文章
     *
     * @param post 需要更新的文章
     */
    void updatePostByPost(Post post);
}
