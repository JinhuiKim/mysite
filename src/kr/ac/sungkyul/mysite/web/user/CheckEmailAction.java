package kr.ac.sungkyul.mysite.web.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.UserDao;
import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import net.sf.json.JSONObject;

public class CheckEmailAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter( "email" );
		UserVo vo = new UserDao().get( email );
		
		response.setContentType( "application/json; charset=utf-8" ); 
		PrintWriter out = response.getWriter();
		
		//out.println( "{ \"result\" : \"success\", \"exist\":true }" );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo != null  ); //true -> exist
		                                //false -> not exist 
		
		// JSON 문자열로 변환
		JSONObject jsonObject = JSONObject.fromObject( map );
		out.println( jsonObject.toString() );
	}
}
