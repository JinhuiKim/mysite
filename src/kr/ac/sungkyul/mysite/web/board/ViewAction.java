package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ViewAction implements Action {

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
		
		// 조회수 증가
		dao.updateViewCount( no );
		
		request.setAttribute( "vo", vo );
		WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
	}

}
