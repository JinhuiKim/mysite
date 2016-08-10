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

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sno = request.getParameter( "no" );
		
		// 비어 있거나 숫자가 아닌 번호인 경우
		if( sno == null || sno.matches("-?\\d+(\\.\\d+)?") == false ){
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		
		Long no = Long.parseLong(sno);
		BoardDao dao = new BoardDao(); 
		
		BoardVo vo = dao.get( no );
		
		// 존재하지 않는 게시물
		if( vo == null ) {
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		
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
		
		// 글주인과 로그인 사용자가 틀림
		if( vo.getUserNo() != authUser.getNo() ) {
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		
		request.setAttribute( "vo", vo );
		WebUtil.forward("/WEB-INF/views/board/modify.jsp", request, response);
	}

}
