package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//ログアウトリクエストを処理するクラス
public class LogoutAction implements Action {

	//ログアウトのリンクが押された際に呼ばれるメソッド。
	// セッションを破棄し、アプリ説明画面を表示する。
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションオブジェクト取得（無ければnull）
		HttpSession session = request.getSession(false);
		// セッションが存在すれば破棄し、ログイン画面にリダイレクトする。
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/draw/about");
		return null;
	}

}
