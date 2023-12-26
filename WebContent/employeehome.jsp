<!-- Design & Frontend Develop By Heshan Pramith -->

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.hotel.dto.OrderItemDTO"%>
<%@page import="java.util.Optional"%>
<%@page import="com.hotel.dto.OrderDTO"%>
<%@page import="com.hotel.dto.UsersDto"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0', shrink-to-fit=no">
    <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height, target-densitydpi=device-dpi" />
    <meta property="og:title" content="Centurion Facilities" />
    <meta property="og:description" content="Welcome to Centurion Facilities portal" />
    <meta property="og:type" content="website" />
    <meta property="og:image" content="images/og.png" />
    <meta property="og:url" content="http://www.centurionfacilities.com" />
    <title>CF - Employee</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script src="js/custom.js"></script>
    <link rel="apple-touch-icon" sizes="128x128" href="images/apple-touch-icon.png">
    <link rel="icon" sizes="192x192" href="images/android-chrome-192x192.png">
    <link rel="icon" type="image/x-icon" href="images/favicon.ico">
    <link href="stylesheets/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="stylesheets/screen.css" rel="stylesheet">
  </head>
  <body class="inner dashboard">
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
		} else
			userDto = (UsersDto) session.getAttribute("user");
		user = userDto.getUserName();
		//allow only admin
		if (!userDto.getRoleCode().equals("EMPLOYEE")) {
			response.sendRedirect("index.jsp");
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
          <li><a href="AttendanceServlet">Attendance</a></li>
        </ul>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title">Employee Dashboard</h2>
          <!-- <div class="home-lang-inner">
            <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
            <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a>
          </div> -->
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <div class="row">
            <div class="col-12">
              <h2 class="subt">Attendance</h2>
            </div>
          </div>
          <ul class="row">
						<li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
							<a href="AttendanceServlet" class="liner3">
								<i class="fa-solid fa-clipboard-user"></i> <span>Attendance</span>
							</a>
						</li>
		  		</ul>
        </div>
      </div>
    </div>

  <script type="text/javascript">
			function googleTranslateElementInit() {
				new google.translate.TranslateElement(
						{
							pageLanguage : 'en',
							layout : google.translate.TranslateElement.FloatPosition.TOP_LEFT
						}, 'google_translate_element');
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