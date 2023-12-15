//package com.ssafy.enjoytrip.controller;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.ssafy.enjoytrip.member.model.MemberDto;
//import com.ssafy.enjoytrip.member.model.service.MemberService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Controller
//@Slf4j
//public class MainController {
//	private final MemberService service;
//
//	public MainController(MemberService service) {
//		this.service = service;
//	}
//
//	@GetMapping("/")
//	public String index() {
//		log.debug("GET /");
//
//		return "index";
//	}
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		String cmd=req.getParameter("cmd");
//
//		switch(cmd) {
//		case "sign_form":
//			resp.sendRedirect(req.getContextPath()+"/member/signup.jsp");
//			break;
//		case "sign":
//			String userId=req.getParameter("userId");
//			String userName=req.getParameter("userName");
//			String userPass=req.getParameter("userPass");
//			String userPass2=req.getParameter("userPass2");
//			System.out.println(userId+" / "+userName+" / "+userPass+" / "+userPass2);
//
////			MemberDto memberdto= new MemberDto(userId,userName,userPass,"");
//
//			try {
//				service.registerMember(memberdto);
//				resp.sendRedirect(req.getContextPath()+"/index.jsp");
//				break;
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				resp.sendRedirect(req.getContextPath()+"/error/error.jsp");
//			}
//			break;
//		case "list":
//			try {
//				List<MemberDto> list=service.list();
//				req.setAttribute("list", list);
//
//				RequestDispatcher disp = req.getRequestDispatcher("/member/manage.jsp");
//				disp.forward(req, resp);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				resp.sendRedirect(req.getContextPath() + "/error/error.jsp");
//			}
//			break;
//		case "detail":
//			try {
//				String id = req.getParameter("userId");
//
//				MemberDto user = service.select(id);
//
//				if(user == null) throw new IllegalArgumentException();
//
//				// 2-1. JSP로 보낼 데이터 담기
//				req.setAttribute("userDetail", user);
//
//				// 3. response
//				RequestDispatcher disp = req.getRequestDispatcher("/member/mypage.jsp");
//				disp.forward(req, resp);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				resp.sendRedirect(req.getContextPath() + "/error/error.jsp");
//			}
//			break;
//
//		case "login_form":
//			resp.sendRedirect(req.getContextPath() + "/member/login.jsp");
//			break;
//
//		case "login":
//			login(req, resp);
//			break;
//
//		case "logout":
//			logout(req, resp);
//			break;
//		case "remove":
//			// 1. Get Parameter
//			String id = req.getParameter("userId");
//			System.out.println("회원삭제 호출 id: "+id);
//			// 2. 비지니스 로직 처리
//			try {
//				service.deleteMember(id);
//				logout(req, resp);
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				resp.sendRedirect(req.getContextPath() + "/error/error.jsp");
//			}
//			break;
//		case "modify":
//			// 1. Get Parameter
//			userId=req.getParameter("userId");
//			userName=req.getParameter("userName");
//			userPass=req.getParameter("userPass");
//			System.out.println(userId+" / "+userName+" / "+userPass);
//
//			MemberDto memberdto2=new MemberDto(userId,userName,userPass,"");
//			System.out.println("회원수정 호출 dto: "+memberdto2);
//
//			// 2. 비지니스 로직 처리
//			try {
//				service.modifyMember(memberdto2);
//				resp.sendRedirect(req.getContextPath()+"/index.jsp");
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//				resp.sendRedirect(req.getContextPath() + "/error/error.jsp");
//			}
//			break;
//		}
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		doGet(req,resp);
//	}
//
//	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String id = request.getParameter("user-id");
//		String password = request.getParameter("user-password");
//		String remember = request.getParameter("remember-id");
//
//		try {
//			if(id == null || password == null)
//				throw new IllegalArgumentException();
//
//			MemberDto member = service.login(id, password);
//			String redirect = "/main?cmd=login_form";
//
//			if(member != null) {
//				HttpSession session = request.getSession();
//				session.setAttribute("user", member);
//
//				Cookie cookie = new Cookie("savedId", member.getId());
//				cookie.setPath(request.getContextPath());
//				cookie.setMaxAge(remember == null ? 0 : 60 * 60 * 24 * 90);
//				response.addCookie(cookie);
//
//				redirect = "/index.jsp";
//			}
//
//			response.sendRedirect(request.getContextPath() + redirect);
//		}
//		catch(SQLException | IllegalArgumentException e) {
//			e.printStackTrace();
//			response.sendRedirect(request.getContextPath() + "/error/error.jsp");
//		}
//	}
//
//	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		request.getSession().invalidate();
//		response.sendRedirect(request.getContextPath() + "/index.jsp");
//	}
//
//}
