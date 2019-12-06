package com.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Ajax
 */
@WebServlet("/ModuleController2")
public class ModuleController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String fn = request.getParameter("fn");
		System.out.println(fn);
		String[] commands = new String[] { 
				"cd ..//..//", 
				"cd  %systemdrive%//users//%username%//desktop//processbuilder", 
				"python findByName.py " + "  "+ fn 
		};
		try {

			// 리스트 형태로 실행파일과 파일경로, 파라미터값을 넘겨준다

			ProcessBuilder pb = new ProcessBuilder("cmd");

			pb.redirectErrorStream(true);

			Process p = pb.start();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

			for (String cmd : commands) {
				writer.write(cmd + "\n");
				writer.flush();
			}

			writer.write("exit" + "\n");
			writer.flush();

			BufferedReader std = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String outputLine = "";
			String outputMessage = "";
			while ((outputLine = std.readLine()) != null) {
				outputMessage += outputLine + "\r\n";
				System.out.println(outputLine);
			}
			p.waitFor();
			
			System.out.println(outputMessage);
			String dateState = outputMessage.substring(outputMessage.lastIndexOf("r1")+3,outputMessage.lastIndexOf("r2"));
			String dateProba = outputMessage.substring(outputMessage.lastIndexOf("r2")+3,outputMessage.lastIndexOf("r3"));
			String airline = outputMessage.substring(outputMessage.lastIndexOf("r4")+3,outputMessage.lastIndexOf("r5"));
			String airport = outputMessage.substring(outputMessage.lastIndexOf("r5")+3,outputMessage.lastIndexOf("r6"));
			String goal = outputMessage.substring(outputMessage.lastIndexOf("r6")+3,outputMessage.lastIndexOf("r7"));
			String date = outputMessage.substring(outputMessage.lastIndexOf("r7")+3,outputMessage.lastIndexOf("r8"));
System.out.println(airline);

			//System.out.println(start+startProba);
			//System.out.println(end+endProba);
			session.setAttribute("dateState", dateState);
			session.setAttribute("dateProba", dateProba);
			session.setAttribute("airline", airline);
			session.setAttribute("airport", airport);
			session.setAttribute("goal", goal);
			session.setAttribute("date", date);
			session.setAttribute("fn", fn);
			
			System.out.println(session.getAttribute("dateState"));
			System.out.println(session.getAttribute("dateProba"));
			System.out.println(session.getAttribute("airline"));
			System.out.println(session.getAttribute("airport"));
			System.out.println(session.getAttribute("goal"));
			System.out.println(session.getAttribute("date"));
			System.out.println(session.getAttribute("fn"));
			response.sendRedirect("afterFlightNameSearch.jsp");


		} catch (Exception e) {
			System.out.println(e);
		}
	}

}