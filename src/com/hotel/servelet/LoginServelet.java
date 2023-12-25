package com.hotel.servelet;

import com.hotel.dao.UserDAO;
import com.hotel.dto.UsersDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class LoginServelet
 */
@WebServlet("/LoginServlet")
public class LoginServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ADMIN = "ADMIN";
    private static final String CHECKER = "CHECKER";
    private static final String EMPLOYEE = "EMPLOYEE";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServelet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();

        request.setAttribute("invalidUserPwError", "false");
        request.setAttribute("nullUserPwError", "false");
        request.setAttribute("passwordMismatchError", "false");
        request.setAttribute("oldPasswordWrongError", "false");
        request.setAttribute("passwordResetError", "false");
        request.setAttribute("nullResetPwError", "false");
        request.setAttribute("nullUserError", "false");
        request.setAttribute("pwAskForResetSuccess", "false");
        request.setAttribute("userNameError", "false");

        if (request.getParameter("login") != null) {
            // get request parameters for userID and password
            String user = request.getParameter("uname");
            String pwd = request.getParameter("password");

            if ((null != user && !"".equals(user)) && (null != pwd && !"".equals(pwd))) {
                UsersDto usersDto = userDAO.userLogin(user, pwd);
                if (null != usersDto && user.equals(usersDto.getUserName())) {
                    initiateLogin(response, usersDto, session);
                } else {
                    request.setAttribute("invalidUserPwError", "true");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("nullUserPwError", "true");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }

        if (request.getParameter("resetLogin") != null) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confPassword = request.getParameter("confPassword");

            if ((null != oldPassword && !"".equals(oldPassword)) &&
                    (null != newPassword && !"".equals(newPassword)) &&
                    (null != confPassword && !"".equals(confPassword))) {
                if (!newPassword.equals(confPassword)) {
                    request.setAttribute("passwordMismatchError", "true");
                    request.getRequestDispatcher("resetlogin.jsp").forward(request, response);
                } else {
                    UsersDto usersDto = (UsersDto) session.getAttribute("user");
                    String currentUserName = usersDto.getUserName();
                    UsersDto loginUser = userDAO.userLogin(currentUserName, oldPassword);

                    if (null != loginUser && currentUserName.equals(loginUser.getUserName())) {
                        int i = userDAO.passwordReset(currentUserName, newPassword);

                        if (i > 0) {
                            UsersDto latestUserDto = userDAO.userLogin(currentUserName, newPassword);
                            initiateLogin(response, latestUserDto, session);
                        } else {
                            request.setAttribute("passwordResetError", "true");
                            request.getRequestDispatcher("resetlogin.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("oldPasswordWrongError", "true");
                        request.getRequestDispatcher("resetlogin.jsp").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("nullResetPwError", "true");
                request.getRequestDispatcher("resetlogin.jsp").forward(request, response);
            }
        }

        if (request.getParameter("forgetPw") != null) {
            String user = request.getParameter("uname");

            int userForUserName = userDAO.getUserForUserName(user);
            if (userForUserName > 0) {
                if (null != user) {
                    int i = userDAO.askForPwReset(user);
                    if (i > 0) {
                        request.setAttribute("pwAskForResetSuccess", "true");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("nullUserError", "true");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("userNameError", "true");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
    }

    private void initiateLogin(HttpServletResponse response, UsersDto usersDto, HttpSession session) throws IOException {
        session.setAttribute("user", usersDto);
        //setting session to expiry in 30 mins
        session.setMaxInactiveInterval(30 * 60);
        Cookie userName = new Cookie("user", usersDto.getUserName());
        response.addCookie(userName);
        //Get the encoded URL string
        if (usersDto.isPasswordChange()) {
            String encodedURL = response.encodeRedirectURL("resetlogin.jsp");
            response.sendRedirect(encodedURL);
        } else if (CHECKER.equals(usersDto.getRoleCode())) {
            String encodedURL = response.encodeRedirectURL("checkerhome.jsp");
            response.sendRedirect(encodedURL);
        } else if (EMPLOYEE.equals(usersDto.getRoleCode())) {
            String encodedURL = response.encodeRedirectURL("employeehome.jsp");
            response.sendRedirect(encodedURL);
        } else if (ADMIN.equals(usersDto.getRoleCode())) {
            String encodedURL = response.encodeRedirectURL("adminhome.jsp");
            response.sendRedirect(encodedURL);
        }
    }

}
