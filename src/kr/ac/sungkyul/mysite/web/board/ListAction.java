package kr.ac.sungkyul.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.sungkyul.mysite.dao.BoardDao;
import kr.ac.sungkyul.mysite.vo.BoardVo;
import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.WebUtil;

public class ListAction implements Action {
	private final int LIST_PAGESIZE = 10; // 리스팅되는 게시물의 수
	private final int LIST_BLOCKSIZE = 5; // 페이지 리스트에서 표시되는 페이지 수

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 페이지 파라미터 가져오기
		int page = 1;
		String sPage = request.getParameter( "p" );
		if( sPage != null && sPage.matches("-?\\d+(\\.\\d+)?") == true ){
			page = Integer.parseInt( sPage );
		}		
		
		// 2. Dao 생성
		BoardDao dao = new BoardDao();
		
		// 3. 페이징을 위한 기본 데이터 세팅
		int totalCount = dao.getTotalCount();
		int pageCount = (int)Math.ceil((double) totalCount / LIST_PAGESIZE);
		int blockCount = (int) Math.ceil((double) pageCount / LIST_BLOCKSIZE);
		int currentBlock = (int) Math.ceil((double) page / LIST_BLOCKSIZE);

		// 4. page값 검증 
		if (page < 1) {
			page = 1;
			currentBlock = 1;
		} else if (page > pageCount) {
			page = pageCount;
			currentBlock = (int)Math.ceil((double) page / LIST_BLOCKSIZE);
		}

		// 5. 페이지를 그리기 위한 값  계산
		int startPage = (currentBlock - 1) * LIST_BLOCKSIZE + 1;
		int endPage = (startPage - 1) + LIST_BLOCKSIZE;
		int prevPage = (currentBlock > 1) ? (currentBlock - 1) * LIST_BLOCKSIZE : 0;
		int nextPage = (currentBlock < blockCount) ? currentBlock * LIST_BLOCKSIZE + 1 : 0;

		//4. 리스트 가져오기
		List<BoardVo> list = dao.getList( page, LIST_PAGESIZE );

		request.setAttribute( "sizeList",  LIST_PAGESIZE );
		request.setAttribute( "totalCount",  totalCount );
		request.setAttribute( "firstPage", startPage );
		request.setAttribute( "lastPage", endPage );
		request.setAttribute( "prevPage", prevPage );
		request.setAttribute( "nextPage", nextPage );
		request.setAttribute( "currentPage", page );
		request.setAttribute( "pageCount", pageCount );
		request.setAttribute( "list", list);
		
		WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
	}

}
