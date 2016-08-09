package kr.ac.sungkyul.mysite.web.board;

import kr.ac.sungkyul.web.Action;
import kr.ac.sungkyul.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if( "writeform".equals( actionName ) ){
			action = new WriteFormAction();
		} else if( "write".equals( actionName ) ){
			action = new WriteAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
