package kr.ac.sungkyul.mysite.web.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.vo.UserVo;
import kr.ac.sungkyul.web.Action;
import net.sf.json.JSONObject;

public class CheckEmailAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter( "email" );
		
		
		response.setContentType( "application/json; charset=utf-8" ); 
		PrintWriter out = response.getWriter();
		
		//out.println( "{ \"result\" : \"success\", \"exist\":true }" );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", true );
		
		//연습
		UserVo vo = new UserVo();
		vo.setEmail( "kickscar@gmail.com" );
		vo.setNo( 1000L );
		map.put( "data2", vo );
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		out.println( jsonObject.toString() );
	}
}
