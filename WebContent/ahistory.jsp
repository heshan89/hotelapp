<!-- Design & Frontend Develop By Heshan Pramith -->
<!doctype html>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Optional"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.hotel.dto.UsersDto"%>
<%@page import="com.hotel.dto.PlacedOrderItemDTO"%>
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
    <title>CF - Admin</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="js/custom.js"></script>
    <link rel="apple-touch-icon" sizes="128x128" href="images/apple-touch-icon.png">
    <link rel="icon" sizes="192x192" href="images/android-chrome-192x192.png">
    <link rel="icon" type="image/x-icon" href="images/favicon.ico">
    <link href="stylesheets/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="stylesheets/screen.css" rel="stylesheet">
  </head>
  <body class="inner">
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
		if (!userDto.getRoleCode().equals("ADMIN")) {
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
		
		LocalDate filterOrderDate = (LocalDate) Optional.ofNullable(request.getAttribute("filterOrderDate")).orElse(LocalDate.now());
  		Integer filterFloor = (Integer) Optional.ofNullable(request.getAttribute("filterFloor")).orElse(0);
  		Map<Integer, List<PlacedOrderItemDTO>> orderHistorySesObj = (Map<Integer, List<PlacedOrderItemDTO>>) Optional
  				.ofNullable(request.getAttribute("hystoryData")).orElse(new HashMap<>());
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
          <li><a href="AdminFaultServlet">Fault List</a></li>
          <li><a href="afaulthistory.html">Fault History</a></li>
        </ul>
        <h4>User Management</h4>
        <ul>
          <li><a href="AddUserServlet">Add/Edit User(s)</a></li>
        </ul>
      </div>
    </div>
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="adminhome.jsp" class="back"><i class="fa-solid fa-chevron-left"></i></a> Order History</h2>
          <!-- <div class="home-lang-inner">
            <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
            <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a>
          </div> -->
        </div>
      </div>
      <form id="" action="AdminHistoryServelet" method="get">
        <div class="row">
          <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="updatedDate">Date</label>
            <div class="input-group input-group-sm">
              <input type="date" class="form-control form-control-sm" id="date" name="filterDate" value=<%=filterOrderDate %> placeholder="DD/MM/YYYY">
              <!-- <span class="input-group-text date" id="basic-addon1"><i class="fa-solid fa-calendar-days"></i></span> -->
            </div>
          </div>
          <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">Floor</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="filterFloor">
                <option selected>All</option>
                <option value=4  <%if(filterFloor==4){%> selected <% } %> >4</option>
                <option value=5  <%if(filterFloor==5){%> selected <% } %> >5</option>
                <option value=6  <%if(filterFloor==6){%> selected <% } %> >6</option>
                <option value=7  <%if(filterFloor==7){%> selected <% } %> >7</option>
                <option value=8  <%if(filterFloor==8){%> selected <% } %> >8</option>
                <option value=9  <%if(filterFloor==9){%> selected <% } %> >9</option>
                <option value=10  <%if(filterFloor==10){%> selected <% } %> >10</option>
                <option value=11  <%if(filterFloor==11){%> selected <% } %> >11</option>
                <option value=12  <%if(filterFloor==12){%> selected <% } %> >12</option>
                <option value=13 <%if(filterFloor==13){%> selected <% } %> >13</option>
              </select>
            </div>
          </div>
          <!-- <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">Room</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-house-chimney"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01">
                <option selected>100R</option>
                <option value="1">101R</option>
                <option value="2">102R</option>
                <option value="3">103R</option>
                <option value="4">104R</option>
                <option value="5">105R</option>
                <option value="6">106R</option>
                <option value="7">107R</option>
                <option value="8">108R</option>
                <option value="9">109R</option>
              </select>
            </div>
          </div> -->
          <div class="col-12 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Add" type="submit"><i class="fa-solid fa-magnifying-glass"></i> Filter</button>
            </div>
          </div>
        </div>
      </form>
      <div class="row">
        <div class="col-12">
          <div class="looper">
            <div class="accordion" id="itemlist">
               <% 
					List<Integer> keyList = (List<Integer>)new ArrayList(orderHistorySesObj.keySet()).stream().sorted().collect(Collectors.toList());
   					for (Integer key : keyList){
					String heading = "heading"+key;
					String collapse = "collapse"+key;
					String accName = "Floor "+key;
  				%>
              <div class="accordion-item floor">
                <h2 class="accordion-header" id=<%=heading%>>
                  <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target=#<%=collapse%> aria-expanded="true" aria-controls=<%=collapse%>>
						<i class="fa-solid fa-building-circle-check"></i> <span><%=accName%></span>
				  </button>
                </h2>
                <div id=<%=collapse%> class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
					 <div class="accordion-body">
					 <div class="row">
                    <%
						List<PlacedOrderItemDTO> valueList = orderHistorySesObj.get(key);
						for (PlacedOrderItemDTO itemDTO : valueList){
						String name = "floor-"+key+itemDTO.getItemName();
  					%>
                      <div class="col-6 col-sm-6 col-md-4 col-lg-2">
                        <label class="form-label" for=""><%=itemDTO.getItemName()%></label>
                        <input type="number" value=<%=itemDTO.getAmount()%> name="quantity" class="form-control form-control-sm text-center" readonly disabled>
                      </div>
                    <% } %>  
                    </div>
                  </div>
                </div>
              </div>
             <% } %> 
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