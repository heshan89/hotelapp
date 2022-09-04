<!-- Design & Frontend Dev By Heshan Pramith / BE & DEV Rangana Madumal/Harsha Athapaththu  -->

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hotel.dto.UsersDto"%>
<%@page import="java.util.Optional"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0', shrink-to-fit=no">
    <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height, target-densitydpi=device-dpi" />
    <title>TimtoFix</title>
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

  %>

    <div class="container-fluid login-cont">
      <div class="row justify-content-center">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xl-3 col-xxl-3 col-xxxl-2 p-0">
          <div class="login-wrapper">
            <div class="logo">
              <img src="images/logo2.png" class="img-fluid">
            </div>
            <div class="home-lang">
              <!-- <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
              <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a> -->
            </div>
            <h1>Sign in</h1>
            <form class="row g-3 needs-validation" novalidate action="LoginServlet" method="post">
              <div class="col-12">
                <!-- <label for="uname" class="form-label">Username</label> -->
                <div class="input-group">
                  <span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-user"></i></span>
                  <input type="text" placeholder="Username" class="form-control" name="uname" id="uname" value="" required>
                </div>
              </div>
              <div class="col-12">
                <!-- <label for="password" class="form-label">Password</label> -->
                <div class="input-group">
                  <span class="input-group-text" id="basic-addon1"><i class="fa-solid fa-key"></i></span>
                  <input type="password" placeholder="Password" class="form-control" name="password" id="password" value="" required>
                </div>
                <button type="submit" name="forgetPw" value="forgetPw" class="forgotup">Forgot your Username/Password</button>
                <!-- Show hide this warning block -->
                <!-- Show hide this warning block -->

                <c:if test="${invalidUserPwError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Invalid username password</div>
                    </div>
                </c:if>

                <c:if test="${nullUserPwError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Username and/or password can not be empty</div>
                    </div>
                </c:if>

                <c:if test="${nullUserError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Username can not be empty for Forgot password reset</div>
                    </div>
                </c:if>

                <c:if test="${pwAskForResetSuccess == 'true'}">
                    <div class="er-wrp sucsess">
                        <div class="succe-msg">Password reset notification send to the admin</div>
                    </div>
                </c:if>

                <c:if test="${userNameError == 'true'}">
                    <div class="er-wrp warning">
                        <div class="err-msg">Incorrect user name</div>
                    </div>
                </c:if>

                <c:if test="${logoutSuccess == 'true'}">
                    <div class="er-wrp sucsess">
                        <div class="succe-msg">Thank you! You are successfully logged out</div>
                    </div>
                </c:if>
                <!-- Show hide this warning block -->
                <!-- Show hide this warning block -->
              </div>
              <div class="col-12">
                <!-- <button class="btn btn-primary btn-sm" type="submit">Log in</button> -->
                <input id="signbutton" type="submit" name="login" value="Log in" class="btn btn-primary btn-sm signbutton" onclick="window.location.href = 'checkerhome.html'" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="animation-wrapper d-block d-sm-block d-md-none">
      <div class="particle particle-1"></div>
    </div>
    <footer>Copyright &copy; eightynine creations (Pvt) Ltd, All Rights Reserved.</footer>
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