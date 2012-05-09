package com.chriscarr.bang;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AjaxServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
                               throws ServletException, IOException {
	  response.setContentType("text/xml"); 
      response.setHeader("Cache-Control", "no-cache");
      
      String messageType = request.getParameter("messageType");
      if(messageType != null && !messageType.equals("")){    	
    	JSPUserInterface userInterface = (JSPUserInterface)WebInit.userInterface;
    	if(userInterface != null){
    		GameState gameState = userInterface.getGameState();
    		response.getWriter().write("<gamestate>");
	    		response.getWriter().write("<players>");
	    		for(GameStatePlayer player : gameState.getPlayers()){
	    			writePlayer(player, response);
	    		}
	    		response.getWriter().write("</players>");
	    		if(gameState.isGameOver()){
	    			response.getWriter().write("<gameover/>");
	    		}
	    		response.getWriter().write("<currentname>");
	    		response.getWriter().write(gameState.getCurrentName());
	    		response.getWriter().write("</currentname>");
	    		response.getWriter().write("<decksize>");
	    		response.getWriter().write(Integer.toString(gameState.getDeckSize()));
	    		response.getWriter().write("</decksize>");
	    		GameStateCard topCard = gameState.discardTopCard();
	    		if(topCard != null){
	    			response.getWriter().write("<discardtopcard>");
	    			writeCard(topCard, response);
	    			response.getWriter().write("</discardtopcard>");
	    		}
    		response.getWriter().write("</gamestate>");
    	} else {
    		response.getWriter().write("<gamestate/>");
    	}
      }
  }
  
  private void writePlayer(GameStatePlayer player, HttpServletResponse response) throws IOException{
	  	response.getWriter().write("<player>");
	  	response.getWriter().write("<name>");
		response.getWriter().write(player.name);
		response.getWriter().write("</name>");
		response.getWriter().write("<specialability>");
		response.getWriter().write(player.specialAbility);
		response.getWriter().write("</specialability>");
		response.getWriter().write("<health>");
		response.getWriter().write(Integer.toString(player.health));
		response.getWriter().write("</health>");
		response.getWriter().write("<maxhealth>");
		response.getWriter().write(Integer.toString(player.maxHealth));
		response.getWriter().write("</maxhealth>");
		response.getWriter().write("<handsize>");
		response.getWriter().write(Integer.toString(player.handSize));
		response.getWriter().write("</handsize>");
		if(player.isSheriff){
			response.getWriter().write("<issheriff/>");
		}
		if(player.gun != null){
			response.getWriter().write("<gun>");
			writeCard(player.gun, response);
			response.getWriter().write("</gun>");
		}
		List<GameStateCard> inPlay = player.inPlay;
		if(inPlay != null && !inPlay.isEmpty()){
			response.getWriter().write("<inplay>");
			for(GameStateCard inPlayCard : inPlay){
				response.getWriter().write("<inplaycard>");
				writeCard(inPlayCard, response);
				response.getWriter().write("</inplaycard>");
			}			
			response.getWriter().write("</inplay>");
		}
		response.getWriter().write("</player>");
  }
  
  private void writeCard(GameStateCard card, HttpServletResponse response) throws IOException{	  	
		response.getWriter().write("<name>");
		response.getWriter().write(card.name);
		response.getWriter().write("</name>");
		response.getWriter().write("<suit>");
		response.getWriter().write(card.suit);
		response.getWriter().write("</suit>");
		response.getWriter().write("<value>");
		response.getWriter().write(card.value);
		response.getWriter().write("</value>");
		response.getWriter().write("<type>");
		response.getWriter().write(card.type);
		response.getWriter().write("</type>");
  }
}