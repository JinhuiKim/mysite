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

public class WriteAction implements Action {

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
		
		String title = request.getParameter( "title" );
		String content = request.getParameter( "content" );
		
		Long groupNo = null;
		String sGourpNo = request.getParameter( "group_no" );
		if( sGourpNo != null && sGourpNo.matches("-?\\d+(\\.\\d+)?") ){
			groupNo = Long.parseLong( sGourpNo );
		}
		
		Long orderNo = null;
		String sOrderNo = request.getParameter( "order_no" );
		if( sOrderNo != null && sOrderNo.matches("-?\\d+(\\.\\d+)?") ){
			orderNo = Long.parseLong( sOrderNo ) + 1;
		}


		Integer depth = null;
		String sDepth = request.getParameter( "depth" );
		if( sDepth != null && sDepth.matches("-?\\d+(\\.\\d+)?") ){
			depth = Integer.parseInt( sDepth ) + 1;
		}
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		vo.setUserNo(authUser.getNo());
	
		BoardDao dao = new BoardDao();
		if( groupNo != null ){ // 답글인 경우
			dao.updateOrderNo(groupNo, orderNo );
		}
		dao.insert( vo );
		
		WebUtil.redirect("/mysite/board", request, response);
	}
}
