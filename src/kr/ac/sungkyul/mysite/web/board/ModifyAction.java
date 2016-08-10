package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 타이틀
		String title = request.getParameter( "title" );
		// 내용
		String content = request.getParameter( "content" );
		
		// 글번호 
		Long no = Long.parseLong( request.getParameter( "no" ) );
		
		// 인증 여부
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}		

		// 수정
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUserNo( authUser.getNo() );
		
		new BoardDao().update( vo );
		
		WebUtil.redirect("/mysite/board?a=view&no=", request, response);
	}

}
