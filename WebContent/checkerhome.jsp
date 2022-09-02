<!-- Design & Frontend Develop By Heshan Pramith -->

<%@page import="com.hotel.dto.UsersDto"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TimtoFix</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script src="js/custom.js"></script>
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
			response.sendRedirect("index.html");
		} else
			userDto = (UsersDto) session.getAttribute("user");
			user = userDto.getUserName();
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
          <li><a href="order.jsp">Place Order</a></li>
          <li><a href="chistory.jsp">Order History</a></li>
        </ul>
        <!-- <h4>Maintenance</h4>
        <ul>
          <li><a href="cfaultreport.html">Fault Report</a></li>
          <li><a href="cfaultsall.html">Marked Fault(s)</a></li>
          <li><a href="cfaulthistory.html">Fault History</a></li>
        </ul> -->
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title">Checker Dashboard</h2>
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
              <h2 class="subt">Place Order(s)</h2>
            </div>
          </div>
          <ul class="row">
						<li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
							<a href="order.jsp" class="liner1">
								<i class="fa-solid fa-cart-plus"></i> <span>Place Order</span>
							</a>
						</li>
						<li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
							<a href="chistory.jsp" class="liner2">
								<i class="fa-solid fa-clock-rotate-left"></i> <span>Order History</span>
							</a>
						</li>
		  		</ul>
		  		<!-- <div class="row">
            <div class="col-12">
              <h2 class="subt">Maintenance</h2>
            </div>
          </div>
          <ul class="row">
            <li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
              <a href="cfaultreport.html" class="liner3">
                <i class="fa-solid fa-file-circle-exclamation"></i>
                <span>Fault Report</span>
              </a>
            </li>
            <li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
              <a href="cfaultsall.html" class="liner5">
                <i class="fa-solid fa-table-list"></i>
                <span>Marked Fault(s)</span>
              </a>
            </li>
            <li class="col-6 col-sm-4 col-md-3 col-lg-2 col-xl-2 col-xxl-2 col-xxxl-1 text-center">
              <a href="cfaulthistory.html" class="liner4">
                <i class="fa-solid fa-file-lines"></i>
                <span>Fault History</span>
              </a>
            </li>
          </ul> -->
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