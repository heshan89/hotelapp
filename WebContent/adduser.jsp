<!-- Design & Frontend Develop By Heshan Pramith -->

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hotel.dto.UsersDto"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Hotel Portal - Add User</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="js/custom.js"></script>
    <link rel="icon" type="image/x-icon" href="images/favicon.ico">
    <link href="stylesheets/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="stylesheets/screen.css" rel="stylesheet">
  </head>
  <body class="inner adduser">

  <%
        //remove cash page
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

  		//allow access only if session exists
  		UsersDto userDto = null;
  		String user = null;
  		if (session.getAttribute("user") == null) {
  			response.sendRedirect("index.html");
  		} else {
  			userDto = (UsersDto) session.getAttribute("user");
  			user = userDto.getUserName();
  			//allow only admin
            if (!userDto.getRoleCode().equals("ADMIN")) {
                response.sendRedirect("index.html");
            }
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

  		List<UsersDto> allUsers = (List<UsersDto>)Optional.ofNullable(session.getAttribute("allUsers")).orElse(new ArrayList<>());

  %>

    <header>
      <div class="container-fluid">
        <div class="row">
          <div class="col-3 col-xs-4 col-sm-3 col-md-2 col-lg-1 text-center">
            <div class="logo">
              <a data-bs-toggle="offcanvas" href="#offcanvasExample" role="button" aria-controls="offcanvasExample"><img src="images/logoin.png" class="img-fluid"></a>
            </div>
          </div>
          <div class="col-8 col-xs-7 col-sm-8 col-md-9 col-lg-10 p-l-0">
            <h1>Welcome - <span><%=user%></span></h1>
            <p>Last login 10:20 am 08/10/2022</p>
          </div>
          <div class="col-1 col-xs-1 col-sm-1 col-md-1 col-lg-1 text-right">
            <a href="LogoutServelet" class="logout"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
          </div>
        </div>
      </div>
    </header>

    <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
      <div class="offcanvas-header">
        <div class="container">
          <div class="row">
            <div class="col-9">
              <img src="images/logo.png" class="img-fluid">
            </div>
            <div class="col-3">
              <button type="button" class="close" data-bs-dismiss="offcanvas" aria-label="Close"><i class="fa-solid fa-xmark"></i></button>
            </div>
          </div>
        </div>
      </div>
      <div class="offcanvas-body">
        <h4>Place Order(s)</h4>
        <ul>
          <li><a href="requestlist.html">Order List</a></li>
          <li><a href="ahistory.html">Order History</a></li>
        </ul>
        <h4>Maintenance</h4>
        <ul>
          <li><a href="faultlist.html">Fault List</a></li>
          <li><a href="afaulthistory.html">Fault History</a></li>
        </ul>
        <h4>User Management</h4>
        <ul>
          <li><a href="adduser.html">Add/Edit User(s)</a></li>
        </ul>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="adminhome.html" class="back"><i class="fa-solid fa-chevron-left"></i></a> Add/Edit User(s)</h2>
          <div class="home-lang-inner">
            <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
            <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a>
          </div>
        </div>
      </div>
      <form id="addUser" action="AddUserServlet" method="post">
        <div class="row">
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">User ID</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-address-card"></i></label>
              <input type="number" id="userId" name="userId" class="form-control form-control-sm">
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">User Name</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-italic"></i></label>
              <input type="text" id="userName" name="userName" class="form-control form-control-sm">
            </div>
          </div>
          <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">User Type</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-user"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" id="userType" name="userType">
                <option value="CHECKER"selected>Checker</option>
                <option value="ADMIN">Admin</option>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">Password</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-key"></i></label>
              <input type="Password" id="password" name="password" class="form-control form-control-sm">
            </div>
          </div>
          <div class="col-12 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Add" type="submit"><i class="fa-solid fa-circle-plus"></i> Add User</button>
            </div>
          </div>
        </div>
      </form>

        <div class="row">
          <div class="col-12">
            <div class="adduser-wrp">
              <div class="table-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th width="20%;">User ID</th>
                      <th width="20%;">User Type</th>
                      <th width="20%;">User Name</th>
                      <th width="20%;">Password</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>

                  <c:forEach var="user" items="${allUsers}">

                    <tr>
                      <td><input type="text" name="" class="form-control form-control-sm" value="${user.userId}" disabled readonly></td>
                      <td>
                        <select class="form-select form-control form-control-sm" id="inputGroupSelect01" value="${user.roleCode}" disabled>
                          <c:if test="${user.roleCode == 'ADMIN'}">
                            <option value="ADMIN">Admin</option>
                          </c:if>
                          <c:if test="${user.roleCode == 'CHECKER'}">
                            <option value="CHECKER">Checker</option>
                          </c:if>
                        </select>
                      </td>
                      <td><input type="text" name="" class="form-control form-control-sm" value="${user.userName}" disabled readonly></td>
                      <td><input type="Password" name="" class="form-control form-control-sm" value="45/*dd8dbj" disabled readonly></td>
                      <td>
                        <button type="button" class="del"><i class="fa-solid fa-trash-can"></i></button>
                        <button type="button" class="edt"><i class="fa-solid fa-pen-to-square"></i></button>
                        <c:if test="${user.isAskForPwReset}">
                        <div class="spinner-grow spinner-grow-sm text-danger" role="status">
                          <span class="visually-hidden">Loading...</span>
                        </div>
                        </c:if>
                      </td>
                    </tr>

                  </c:forEach>



                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
    </div>

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