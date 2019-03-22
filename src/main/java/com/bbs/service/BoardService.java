package com.bbs.service;


import com.bbs.Dao.BoardDao;
import com.bbs.bean.Board;
import com.bbs.util.MybatisUtil;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * BoardService
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Service
public class BoardService{
//    private final BoardDAO boardDao;
//
//    @Autowired
//    public BoardService(BoardDAO boardDao) {
//        this.boardDao = boardDao;
//    }
//    @Autowired
//    BoardDao boardDao;


    public BoardService(){

    }

    public void addBoardByBoard(Board board) {
//        if (board != null) {
//            boardDao.addBoard(board);
//        }
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        boardDao.addBoard(board);

        session.commit();

        if(session!=null)
            session.close();
    }


//    public void deleteBoardByBoardName(String boardName) { }


    public List<Board> listAllBoard() {
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        List<Board> boards=boardDao.listAllBoard();

        if(session!=null)
            session.close();
        return boards;
    }

    public void updateBoardInfo(Board board) {
        SqlSession session =MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        boardDao.updateBoardByBoard(board);

        session.commit();
        if(session!=null)
            session.close();
    }

    public void deleteBoard(int boardId) {
        SqlSession session =MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        boardDao.deleteBoardById(boardId);

        session.commit();
        if(session!=null)
            session.close();
    }


    public Board listAllPostOfBoard(int boardId) {
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        Board board=boardDao.listAllPostsOfBoard(boardId);

        if(session!=null)
            session.close();
        return board;
    }


    public Board intoBoardByBoardId(int boardId) {
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        Board board=boardDao.findBoardByBoardId(boardId);

        if(session!=null)
            session.close();
        return board;
    }

    public void updatePostNum(int boardId) {
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        Board board = boardDao.findBoardByBoardId(boardId);
        board.setBoardPostNum(board.getBoardPostNum() + 1);
        boardDao.updateBoardByBoard(board);

        session.commit();
        if(session!=null)
            session.close();
    }

    public Board intoBoardByBoardName(String boardName) {
        SqlSession session = MybatisUtil.openSession();
        BoardDao boardDao = session.getMapper(BoardDao.class);

        Board board =boardDao.findBoardByBoardName(boardName);

        if(session!=null)
            session.close();
        return board;
    }




}
