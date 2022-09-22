<!-- Design & Frontend Dev By Heshan Pramith / BE & DEV Rangana Madumal/Harsha Athapaththu  -->

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hotel.dto.UsersDto"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>CF - Reset Login</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script src="js/custom.js"></script>
    <link rel="icon" type="image/x-icon" href="images/favicon.ico">
    <link href="stylesheets/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="stylesheets/screen.css" rel="stylesheet">
  </head>
  <body id="login">

   <%
        //remove cash page
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

  		//allow access only if session exists
  		UsersDto userDto = null;
  		String user = null;
  		if (session.getAttribute("user") == null) {
  			response.sendRedirect("index.jsp");
  		} else {
  			userDto = (UsersDto) session.getAttribute("user");
  			user = userDto.getUserName();
        }
  		String userName = null;
  		String sessionID = null;
  		Cookie[] cookies = request.getCookies();
  		if (cookies != null) {
  			for (Cookie cookie : cookies) {
  				if (cookie.getName().equals("user"))
  					userName = cookie.getValue();
  				if (cookie.getName().equals("JSESSIONID"))
  					sessionID = cookie.getValue();
  			}
  		} else {
  			sessionID = session.getId();
  		}

  %>

    <div class="container-fluid">
      <div class="row justify-content-center">
        <div class="col-xs-12 col-sm-8 col-md-4 col-lg-3 col-xl-3 col-xxl-3 col-xxxl-2 p-0">
          <div class="login-wrapper">
            <div class="logo">
              <a href="index.jsp"><img src="images/logo2.png" class="img-fluid"></a>
            </div>
            <div class="home-lang">
              <!-- <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
              <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a> -->
            </div>
            <h1>Reset Login</h1>
            <form class="row g-3 needs-validation" novalidate action="LoginServlet" method="post">
              <div class="col-12">
                <!-- <label for="password" class="form-label">Current Password</label> -->
                <div class="input-group2">
                  <!-- <span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-key"></i></span> -->
                  <input placeholder="Current Password" type="password" class="form-control" name="oldPassword" id="password" required>
                </div>
              </div>
              <div class="col-12">
                <!-- <label for="password" class="form-label">New Password</label> -->
                <div class="input-group2">
                  <!-- <span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-key"></i></span> -->
                  <input placeholder="New Password" type="password" class="form-control" name="newPassword" id="password" required>
                </div>
              </div>
              <div class="col-12">
                <!-- <label for="password" class="form-label">Confirm Password</label> -->
                <div class="input-group2">
                  <!-- <span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-key"></i></span> -->
                  <input placeholder="Confirm Password" type="password" class="form-control" name="confPassword" id="password" required>
                </div>
                <!-- Show hide this warning block -->
                <!-- Show hide this warning block -->
                <c:if test="${passwordMismatchError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">New password and confirm password does not match</div>
                    </div>
                </c:if>

                <c:if test="${oldPasswordWrongError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Invalid old password</div>
                    </div>
                </c:if>

                <c:if test="${passwordResetError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Could not reset the password</div>
                    </div>
                </c:if>

                <c:if test="${nullResetPwError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Current Password and/or New Password and/or Confirm Password can not be empty</div>
                    </div>
                </c:if>
                <!-- Show hide this warning block -->
                <!-- Show hide this warning block -->
              </div>
              <div class="col-12">
                <!-- <button class="btn btn-primary btn-sm" type="submit">Log in</button> -->
                <input id="signbutton" type="submit" value="Reset Login" name="resetLogin" class="btn btn-primary btn-sm signbutton"/>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="wave2"></div>
    <script type="text/javascript">
      function googleTranslateElementInit() {
        new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.FloatPosition.TOP_LEFT}, 'google_translate_element');
      }

      function triggerHtmlEvent(element, eventName) {
        var event;
        if (document.createEvent) {
        event = document.createEvent('HTMLEvents');
        event.initEvent(eventName, true, true);
        element.dispatchEvent(event);
        } else {
        event = document.createEventObject();
        event.eventType = eventName;
        element.fireEvent('on' + event.eventType, event);
        }
      }

      jQuery('.lang-select').click(function() {
        var theLang = jQuery(this).attr('data-lang');
        jQuery('.goog-te-combo').val(theLang);

        //alert(jQuery(this).attr('href'));
        window.location = jQuery(this).attr('href');
        location.reload();

      });
    </script>
    <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
  </body>
</html>