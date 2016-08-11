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
		
		// 1. 글번호 파라미터 가져오기(비어 있거나 숫자가 아닌 번호인 경우 오류)
		if( sno == null || sno.matches("-?\\d+(\\.\\d+)?") == false ){
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		Long no = Long.parseLong(sno);

		// 2. 페이지 파라미터 가져오기
		int page = 1;
		String sPage = request.getParameter( "p" );
		if( sPage != null && sPage.matches("-?\\d+(\\.\\d+)?") == true ){
			page = Integer.parseInt( sPage );
		}

		// 3. DAO를 통해 글 정보 가져오기
		BoardDao dao = new BoardDao(); 
		BoardVo vo = dao.get( no );
		
		// 4. 존재하지 않는 게시물인 경우
		if( vo == null ) {
			WebUtil.redirect("/mysite/board", request, response);
			return;
		}
		
		// 5. 조회수 증가
		dao.updateViewCount( no );
		
		// 6. view로 포워딩
		request.setAttribute( "vo", vo );
		request.setAttribute( "page", page );
		WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
	}

}
