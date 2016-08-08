package kr.ac.sungkyul.mysite.web.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.sungkyul.mysite.dao.UserDao;
import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증여부 확인
		HttpSession session = request.getSession();
		if( session == null ) {
			WebUtil.redirect( "/mysite/main", request, response);
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			WebUtil.redirect( "/mysite/main", request, response);
			return;
		}		
		
		Long no = authUser.getNo();
		String name = request.getParameter( "name" );
		String password = request.getParameter( "password" );
		String gender = request.getParameter( "gender" );
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setName( name );
		vo.setPassword( password );
		vo.setGender(gender);
		
		UserDao dao = new UserDao();
		dao.update( vo );
		
		WebUtil.redirect("/mysite/user?a=modifyform&res=success", request, response);
	}
}
