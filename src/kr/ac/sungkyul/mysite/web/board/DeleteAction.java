package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1.인증 여부
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
		
		// 2.파라미터  no(글번호) 가져오기 (비어 있거나 숫자가 아닌 번호인 경우 에러)
		String sno = request.getParameter( "no" );
		if( sno == null || sno.matches("-?\\d+(\\.\\d+)?") == false ){
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		Long no = Long.parseLong(sno);
		
		// 3.파라미터 p(페이지) 가져오기
		int page = 1;
		String sPage = request.getParameter( "p" );
		if( sPage != null && sPage.matches("-?\\d+(\\.\\d+)?") == true ){
			page = Integer.parseInt( sPage );
		}
		
		// 4.삭제
		BoardDao dao = new BoardDao();
		dao.delete( authUser.getNo(), no );
		
		// 5.리다이렉트
		WebUtil.redirect( "/mysite/board?a=list&p=" + page, request, response );
	}

}
