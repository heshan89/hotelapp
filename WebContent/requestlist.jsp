<!-- Design & Frontend Develop By Heshan Pramith -->
<!doctype html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
    <meta property="og:type" content="website" />
    <meta property="og:image" content="images/og.png" />
    <meta property="og:url" content="http://www.centurionfacilities.com" />
    <title>CF - Admin</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97c7a8a58f.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="js/custom.js"></script>
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
		LocalDate sessionOrderDate = (LocalDate) Optional.ofNullable(session.getAttribute("filterOrderDate")).orElse(LocalDate.now());
		if(filterOrderDate!=sessionOrderDate){
			filterOrderDate=sessionOrderDate;
		}
  		Integer filterFloor = (Integer) Optional.ofNullable(request.getAttribute("filterFloor")).orElse(0);
  		Map<Integer, List<PlacedOrderItemDTO>> orderHistorySesObj = (Map<Integer, List<PlacedOrderItemDTO>>) Optional
  				.ofNullable(session.getAttribute("hystoryData")).orElse(new HashMap<>());
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
        <!-- <h4>Maintenance</h4>
        <ul>
          <li><a href="faultlist.html">Fault List</a></li>
          <li><a href="afaulthistory.html">Fault History</a></li>
        </ul> -->
        <h4>User Management</h4>
        <ul>
          <li><a href="AddUserServlet">Add/Edit User(s)</a></li>
        </ul>
      </div>
    </div>
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="adminhome.jsp" class="back"><i class="fa-solid fa-chevron-left"></i></a> Request List</h2>
          <!-- <div class="home-lang-inner">
            <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
            <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a>
          </div> -->
        </div>
      </div>
      <form id="" action="RequestListServlet" method="get" class="fillterarea">
        <div class="row">
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">Date</label>
            <div class="input-group input-group-sm">
              <input type="date" class="form-control form-control-sm" id="date" name="filterDate" value=<%=filterOrderDate%> placeholder="DD/MM/YYYY" onchange="updateDate()">
              <span class="input-group-text date" id="basic-addon1"><i class="fa-solid fa-calendar-days"></i></span>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Floor</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="filterFloor" onchange="updateFloor()">
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
          <!-- <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Room</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01">
                <option selected>All</option>
                <option value="1">Room 100</option>
                <option value="2">Room 101</option>
                <option value="3">Room 102</option>
                <option value="4">Room 103</option>
                <option value="5">Room 104</option>
                <option value="6">Room 105</option>
                <option value="7">Room 106</option>
                <option value="8">Room 107</option>
                <option value="9">Room 108</option>
              </select>
            </div>
          </div> -->
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Filter" type="submit"><i class="fa-solid fa-magnifying-glass"></i> Filter</button>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="adminadd btn btn-sm" title="Add Item" type="button"><i class="fa-solid fa-circle-plus" onclick="updateAddFormData()"></i> Add Item(s)</button>
            </div>
          </div>
        </div>
        <c:if test="${existUpdateDate == 'true'}">
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                Update date available. please reset or submit data before filter
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
          </c:if>
      </form>
      <!-- Admin item add -->
      <!-- Admin item add -->
      <!-- Admin item add -->
      <!-- Admin item add -->
      <form id="adminoderadd" class="adminadhide" action="AdminAddListServlet" method="get">
        <div class="row">
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">Date</label>
            <div class="input-group input-group-sm">
              <input type="date" class="form-control form-control-sm" id="addListFilterDate" name="addListFilterDate" placeholder="DD/MM/YYYY" value=<%=filterOrderDate%>>
              <span class="input-group-text date" id="basic-addon1"><i class="fa-solid fa-calendar-days"></i></span>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Floor</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for=""><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="addListFilterFloor" name="addListFilterFloor" onChange="enableDisableAddItemBtn()">
                <option selected>All</option>
                <option value=4  <%if(filterFloor==4){%> selected <% } %> >4</option>
                <option value=5  <%if(filterFloor==5){%> selected <% } %> >5</option>
                <option value=6  <%if(filterFloor==6){%> selected <% } %> >6</option>
                <option value=7  <%if(filterFloor==7){%> selected <% } %> >7</option>
                <option value=8  <%if(filterFloor==8){%> selected <% } %> >8</option>
                <option value=9  <%if(filterFloor==9){%> selected <% } %> >9</option>
                <option value=10  <%if(filterFloor==10){%> selected <% } %> >10</option>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Item</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelectItem"><i class="fa-solid fa-list-check"></i></label>
              <select class="form-select form-control form-control-sm" id="addListFilterItem" name="addListFilterItem"  onChange="enableDisableAddItemBtn()">
                <option value="Select">Select Item</option>
                <option value="Pillow Case">Pillow Case</option>
                <option value="S-Sheet">S-Sheet</option>
                <option value="D-Sheet">D-Sheet</option>
                <option value="Cleaner">Cleaner</option>
                <option value="Yukata">Yukata</option>
                <option value="Slipper">Slippers</option>
                <option value="Bath Robe">Bath Robe</option>
                <option value="Bath Towel">Bath Towel</option>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Amount</label>
            <div class="input-group input-group-sm action">
              <input type="button" value="" class="button-minus rem" data-field="addListFilterQuantity"  onclick="enableDisableAddItemBtnWithSubtractQuantityBtn()">
              <input type="number" step="1" max="" value="0" name="addListFilterQuantity" class="quantity-field form-control form-control-sm text-center" id="addListFilterQuantity"  onChange="enableDisableAddItemBtn()"> 
              <input type="button" value="" class="button-plus addn" data-field="addListFilterQuantity" onclick="enableDisableAddItemBtnWithAddQuantityBtn()">
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Add" name="addToList" id="addToList" type="submit" disabled="disabled"><i class="fa-solid fa-circle-plus"></i> Add To List</button>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="filterview btn btn-sm" title="Add" name="filter" id="" type="button"><i class="fa-solid fa-magnifying-glass"></i> Filter View</button>
            </div>
          </div>
        </div>
        <c:if test="${invalidItemName == 'true'}">
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                Please selct the item
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
        </c:if>
        <c:if test="${invalidQuantity == 'true'}">
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                Please add quantity
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
          </c:if>
      </form>
      <!-- Admin item add -->
      <!-- Admin item add -->
      <!-- Admin item add -->
      <!-- Admin item add -->
      <form id="reset-form" action="RequestListServlet" method="post">
      <input type="hidden" id="filterDate1" name="filterDateRest">
      <input type="hidden" id="filterFloor1" name="filterFloorRest">        
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
						String name = "floor-"+key+"-"+itemDTO.getId();
  					%>
                      <div class="col-6 col-sm-6 col-md-4 col-lg-2">
                        <label class="form-label" for=""><%=itemDTO.getItemName()%></label>
                        <!-- <input type="number" value=<%=itemDTO.getAmount()%> name="quantity" class="form-control form-control-sm text-center" readonly disabled> -->
                      	<div class="input-group input-group-sm action">
                            <%-- <input type="button" value="" class="button-minus rem" data-field=<%=name%>> --%>
                            <input type="number" step="1" max="" value=<%=itemDTO.getAmount()%> name=<%=name%> class="quantity-field form-control form-control-sm text-center">    
                            <%-- <input type="button" value="" class="button-plus addn" data-field=<%=name%>> --%>
                        </div>
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
        <div class="row">
          <div class="col-6">
            <button class="rst update btn btn-sm" name="reset" title="Add" type="submit"><i class="fa-solid fa-trash-can"></i> Reset</button>
          </div>
          <div class="col-6">
            <button class="add update btn btn-sm" name="update" title="Add" type="submit"><i class="fa-solid fa-cloud-arrow-up"></i> Update</button>
          </div>
        </div>
        
        <%
        	Map<String, Integer> itemAmountMap = (HashMap<String, Integer>) Optional.ofNullable(request.getAttribute("summaryItemAmount")).orElse(new HashMap<>());
            if(!itemAmountMap.isEmpty()){            		
        %>
        <div class="final">
          <div class="row">
            <div class="col-12">
              <h6>Total Order(s)</h6>
            </div>
            <div class="col-12">
              <div class="table-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Item</th>
                      <th>Total Quantity</th>
                    </tr>
                  </thead>
                  <tbody>
									<%
                        				for (Map.Entry<String, Integer> pair : itemAmountMap.entrySet()) {
									%>
									<tr>
										<td><%=pair.getKey()%></td>
										<td><%=pair.getValue()%></td>
									</tr>
									<%
										};
									%>
				</tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        
        <div class="row">
          <div class="col-12">
            <button class="add update btn btn-sm" title="Add" name="sendEmail" type="submit"><i class="fa-solid fa-paper-plane"></i> Submit/E-Mail</button>
          </div>
        </div>
        <% } %>
        <c:if test="${sendEmailSuccess == 'true'}">
              <div class="alert alert-success alert-dismissible fade show" role="alert">
                Send the Email
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
          </c:if>
      </form>
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
    
    function updateDate(){
        let updateValue = document.getElementById("date").value;
        console.log(updateValue);
        document.getElementById("filterDate1").value = updateValue;
        document.getElementById("addListFilterDate").value = updateValue;
        console.log(document.getElementById("addListFilterDate").value);
    }
    
    function updateFloor(){
        let updateValue = document.getElementById("inputGroupSelect01").value;
        document.getElementById("filterFloor1").value = updateValue;
    }
    function enableDisableAddItemBtn(){
    	let floor = document.getElementById("addListFilterFloor").value;
    	let item = document.getElementById("addListFilterItem").value;
    	let quantity = document.getElementById("addListFilterQuantity").value;
    	console.log(floor);
    	console.log(item);
    	console.log(quantity);
		var btnSubmit = document.getElementById("addToList");
		
        if(floor=="All" || item=="Select" || quantity==0 ) {
        	console.log("disebld");
        	btnSubmit.disabled = true;
        } else{
        	console.log("enabled");
        	btnSubmit.disabled = false;
        }
        
    }
    
    function enableDisableAddItemBtnWithAddQuantityBtn(){
    	let floor = document.getElementById("addListFilterFloor").value;
    	let item = document.getElementById("addListFilterItem").value;
    	let quantity = document.getElementById("addListFilterQuantity").value
		var btnSubmit = document.getElementById("addToList");
		
        if((quantity+1)<=0 || floor=="All" || item=="Select") {
        	btnSubmit.disabled = true;
        } else{
        	btnSubmit.disabled = false;
        }
        
    }
    
    function enableDisableAddItemBtnWithSubtractQuantityBtn(){
    	let floor = document.getElementById("addListFilterFloor").value;
    	let item = document.getElementById("addListFilterItem").value;
    	let quantity = document.getElementById("addListFilterQuantity").value
		var btnSubmit = document.getElementById("addToList");
		
        if((quantity-1)<=0 || floor=="All" || item=="Select") {
        	btnSubmit.disabled = true;
        } else{
        	btnSubmit.disabled = false;
        }
        
    }
    
  </script>
  <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
  </body>
</html>