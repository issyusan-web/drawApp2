package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.LoginAction;
import action.LogoutAction;

@WebServlet("/draw/*")
public class FrontController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//*の値を取得
		String pathInfo = request.getPathInfo();
		//何も無ければ404を表示
		if (pathInfo == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//ログインチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			if (!"/about".equals(pathInfo) && !"/login".equals(pathInfo)) {
				response.sendRedirect(request.getContextPath() + "/draw/about");
				return;
			}
		}

		//遷移先のパスが入る変数
		String nextPath = "";

		//pathInfoの中身を判定後、遷移先のパスを格納
		switch (pathInfo) {

		//about.jspへ遷移
		case "/about":
			nextPath = "/about";
			break;
		//myPage.jspへ遷移
		case "/myPage":
			nextPath = "/myPage";
			break;
		//login.jspへ遷移
		case "/login":
			nextPath = "/login";
			break;

		//どれにも当てはまらなければ404を表示
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//nextPathを使ってJSPに遷移する
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp" + nextPath + ".jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//*の値を取得
		String pathInfo = request.getPathInfo();
		//何も無ければ404を表示
		if (pathInfo == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//ログインチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			if (!"/login".equals(pathInfo)) {
				response.sendRedirect(request.getContextPath() + "/draw/login");
				return;
			}
		}

		//遷移先のパスが入る変数
		String nextPath = "";

		//リクエスト処理クラスのインスタンスを格納する変数
		Action action = null;

		//pathInfoの中身を判定後、遷移先のパスを格納
		switch (pathInfo) {
		//ログイン処理
		case "/login":
			action = new LoginAction();
			break;
		//ログアウト処理
		case "/logout":
			action = new LogoutAction();
			break;

		//どれにも当てはまらなければ404を表示
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//処理メソッドを実行し、遷移先のパスとなる文字列を取得
		nextPath = action.execute(request, response);
		//もしnextPathの中身がnullなら、処理を終了
		if (nextPath == null) {
			return;
		}
		//nextPathを使ってJSPに遷移する
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp" + nextPath + ".jsp");
		rd.forward(request, response);

	}

}
