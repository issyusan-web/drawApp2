package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import service.UserLoginService;

//ログインリクエストを処理するクラス
public class LoginAction implements Action{
	//ユーザーの入力した情報をチェックして、登録されてるユーザーか確認する。
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// ユーザーが入力した情報を取得 
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		//ID、パスワードの入力チェック
		if (loginId == null || loginId.isEmpty() ||
				password == null || password.isEmpty()) {
			request.setAttribute("loginError", "ログインIDとパスワードを入力してください。");
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			//rd.forward(request, response);
			return "/login";
		}

		// UserLoginService の loginCheck メソッドにパラメータを渡す 
		UserLoginService loginService = new UserLoginService();
		User user = loginService.loginCheck(loginId, password);

		// ログイン成功した場合は
		if (user != null) {
			//セッションスコープに user を保存。
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			//response.sendRedirect(request.getContextPath() + "/memberSelectAllController");
			return "/admin/admin";

			// ログイン ID やパスワードが間違っていた場合は
		} else {
			//リクエストスコープにエラーメッセージを格納。
			request.setAttribute("loginError", "ログインID またはパスワードが間違っています。");
			//loginページに戻して、再入力を促す。
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			//rd.forward(request, response);
			return "/login";
		}
	}
}
