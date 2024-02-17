<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.Map"%>
<%@page import="com.hotel.dto.UsersDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <title>CF - Reports</title>
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
          <li><a href="order.jsp">Place Order</a></li>
          <li><a href="chistory.jsp">Order History</a></li>
          <li><a href="attendance.jsp">Attendance</a></li>
        </ul>
        <!-- <h4>Maintenance</h4>
        <ul>
          <li><a href="cfaultreport.jsp">Fault Report</a></li>
          <li><a href="cfaultsall.html">Marked Fault(s)</a></li>
          <li><a href="cfaulthistory.html">Fault History</a></li>
        </ul> -->
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="adminhome.jsp" class="back"><i class="fa-solid fa-chevron-left"></i></a> Reports</h2>
        </div>
      </div>
      <form id="generateReport" action="ReportServlet" method="post">

          <form id="reportSelection" action="ReportServlet" method='post'>

                <div class="row">
                  <div class="col-12 col-sm-6 col-md-4 col-lg-2">
                    <label class="form-label" for="">Report Type</label>
                    <div class="input-group input-group-sm">
                      <label class="input-group-text" for="inputGroupSelectFloor"><i class="fa-solid fa-clipboard-user"></i></label>
                      <select name="rptSelection" class="form-select form-control form-control-sm" onchange="this.form.submit()">
                        <option value="0" ${selected0 == true ? 'selected' : ''}>Select</option>
                        <option value="1" ${selected1 == true ? 'selected' : ''}>Employee Wise</option>
                        <option value="2" ${selected2 == true ? 'selected' : ''}>Detail Report</option>
                        <option value="3" ${selected3 == true ? 'selected' : ''}>Hotel Wise</option>
                        <option value="4" ${selected4 == true ? 'selected' : ''}>Ongoing daily report</option>
                      </select>
                    </div>
                  </div>

          </form>

          <c:if test="${fieldEnable_employeeNameOrId == 'true'}">
              <div class="col-6 col-sm-6 col-md-4 col-lg-2" >
                <label class="form-label" for="">Employee Name or ID</label>
                <div class="input-group input-group-sm">
                  <label class="input-group-text" for="inputGroupSelectFloor"><i class="fa-solid fa-user"></i></label>
                  <input type="text" name="userNameOrId" class="form-control form-control-sm">
                </div>
              </div>
          </c:if>

          <c:if test="${fieldEnable_hotel == 'true'}">
              <div class="col-6 col-sm-6 col-md-4 col-lg-2">
                <label class="form-label" for="">Hotel</label>
                <div class="input-group input-group-sm">
                  <label class="input-group-text" for=""><i class="fa-solid fa-building"></i></label>
                  <select class="form-select form-control form-control-sm">
                        <c:forEach var="hotels" items="${allActiveHotels}">
                          <option value="${hotels.id}">${hotels.name}</option>
                        </c:forEach>
                  </select>
                </div>
              </div>
          </c:if>

          <c:if test="${fieldEnable_from == 'true'}">
              <div class="col-6 col-sm-6 col-md-4 col-lg-2">
                <label class="form-label" for="">From</label>
                <div class="input-group input-group-sm">
                  <label class="input-group-text" for=""><i class="fa-solid fa-clock"></i></label>
                  <input type="date" name="from" class="form-control form-control-sm">
                </div>
              </div>
          </c:if>

          <c:if test="${fieldEnable_to == 'true'}">
              <div class="col-6 col-sm-6 col-md-4 col-lg-2">
                <label class="form-label" for="">To</label>
                <div class="input-group input-group-sm">
                  <label class="input-group-text" for=""><i class="fa-solid fa-clock"></i></label>
                  <input type="date" name="to" class="form-control form-control-sm">
                </div>
              </div>
          </c:if>

          <c:if test="${buttonEnable_generateReport == 'true'}">
              <div class="col-12 col-sm-6 col-md-4 col-lg-2">
                <label class="form-label" for="">&nbsp;</label>
                <div class="input-group input-group-sm">
                  <button class="add btn btn-sm" id="genReportBtn" name="genReport" type="submit"><i class="fa-solid fa-clipboard-user"></i> Generate Report</button>
                </div>
              </div>
          </c:if>

        </div>
      </form>

      <c:if test="${noRecordFound_employeeWiseReport == 'true'}">
          <div class="alert alert-success alert-dismissible fade show" role="alert">
            No recode found
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>

      <c:if test="${reportEnable_employeeWiseReport == 'true'}">
          <div class="row">
            <div class="col-12">
              <h6 class="mt-3 ttl">Employee Wise</h6>
            </div>
            <div class="col-12">
              <table class="table table-bordered mt-3 bord">
                <thead>
                  <tr>
                    <th>Staff ID</th>
                    <th>Staff Name</th>
                    <th>Hotel Name</th>
                    <th>Time Duration</th>
                    <th>Wages per Hour</th>
                    <th>Total</th>
                  </tr>
                </thead>
                <tbody id="attendanceTableBody">

            <c:forEach var="empWiseSummary" items="${empWiseSummaryReportList}" varStatus="status">
                <c:forEach var="timeDto" items="${empWiseSummary.empWiseSummaryReportTimes}" varStatus="timeStatus">
                    <tr>
                        <td>
                            <c:if test="${timeStatus.index == 0}">
                                ${empWiseSummary.userId}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${timeStatus.index == 0}">
                                ${empWiseSummary.userName}
                            </c:if>
                        </td>
                        <td>${timeDto.hotelName}</td>
                        <td>${timeDto.timeDuration}</td>
                        <td>${timeDto.wagePerHour}</td>
                        <td>${timeDto.total}</td>
                    </tr>
                </c:forEach>
            </c:forEach>

                </tbody>
              </table>
            </div>
          </div>
      </c:if>

      <c:if test="${reportEnable_hotelWiseReport == 'true'}">
          <div class="row">
            <div class="col-12">
              <h6 class="mt-3 ttl">Company Wise</h6>
            </div>
            <div class="col-12">
              <table class="table table-bordered mt-3 bord">
                <thead>
                  <tr>
                    <th>Hotel Name</th>
                    <th>Staff ID</th>
                    <th>Name</th>
                    <th class="text-end">Hours</th>
                    <th>Hour wage</th>
                    <th>Total </th>
                  </tr>
                </thead>
                <tbody id="attendanceTableBody">
                  <tr>
                    <td rowspan="4">Grand Nikko</td>
                    <td>100</td>
                    <td>kuf</td>
                    <td class="text-end">20</td>
                    <td>1000</td>
                    <td>20000</td>
                  </tr>
                  <tr>
                    <td>101</td>
                    <td>fgy</td>
                    <td class="text-end">40</td>
                    <td>1100</td>
                    <td>44000</td>
                  </tr>
                  <tr>
                    <td>102</td>
                    <td>jhuj</td>
                    <td class="text-end">30</td>
                    <td>1050</td>
                    <td>31500</td>
                  </tr>
                  <tr>
                    <td>103</td>
                    <td>hgyh</td>
                    <td class="text-end">10</td>
                    <td>1100</td>
                    <td>11000</td>
                  </tr>
                </tbody>
                <tfoot>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="text-end">100</td>
                    <td></td>
                    <td>100</td>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
      </c:if>

    </div>

    <script>
      // JavaScript to handle Check In/Out and timer
      let isCheckedIn = false;
      let timerInterval;
    
      document.getElementById("checkInOutButton").addEventListener("click", function () {
        const checkInOutButton = document.getElementById("checkInOutButton");

        if (isCheckedIn) {
          // Perform Check Out
          clearInterval(timerInterval);
          checkInOutButton.innerHTML = '<i class="fa-solid fa-building-circle-check"></i> Check In';
          isCheckedIn = false;
          resetTimer();
          checkInOutButton.classList.remove("rst");
          // Add logic to save Check Out data if needed
        } else {
          // Perform Check In
          isCheckedIn = true;
          checkInOutButton.innerHTML = '<i class="fa-solid fa-building-circle-exclamation"></i> Check Out';
          startTimer();
          checkInOutButton.classList.add("rst");
          // Add logic to save Check In data if needed
        }
      });
    
      function startTimer() {
        let seconds = 0;
        timerInterval = setInterval(function () {
          seconds++;
          const hours = Math.floor(seconds / 3600);
          const minutes = Math.floor((seconds % 3600) / 60);
          const remainingSeconds = seconds % 60;
          const formatTimeHours = formatTime(hours);
          const formatTimeMinutes formatTime(minutes);
          const formatTimeRemainingSeconds = formatTime(remainingSeconds);
          document.getElementById("timerDisplay").innerText = `${formatTimeHours}:${formatTimeMinutes}:${formatTimeRemainingSeconds}`;
        }, 1000);
    
        // Display the timer element
        document.getElementById("timer").style.display = "block";
      }
    
      function resetTimer() {
        clearInterval(timerInterval);
        document.getElementById("timerDisplay").innerText = "00:00:00";
      }
    
      function formatTime(time) {
        return time < 10 ? `0${time}` : time;
      }
    </script>

  </body>
</html>