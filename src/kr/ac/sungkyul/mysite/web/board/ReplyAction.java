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

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증 유무 체크
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect( "/mysite/main", request, response);
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			WebUtil.redirect( "/mysite/main", request, response);
			return;
		}
		
		String sno = request.getParameter( "no" );
		
		// 글번호 파라미터 자겨오기(비어 있거나 숫자가 아닌 번호인 경우 오류)
		if( sno == null || sno.matches("-?\\d+(\\.\\d+)?") == false ){
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		
		Long no = Long.parseLong(sno);
		BoardDao dao = new BoardDao(); 
		BoardVo vo = dao.get( no );
		
		request.setAttribute( "vo", vo );
		WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
	}
}
