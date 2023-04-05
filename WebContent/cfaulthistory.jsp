<!-- Design & Frontend Develop By Heshan Pramith -->
<!doctype html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Optional"%>
<%@page import="com.hotel.dto.FaultTypeDto"%>
<%@page import="com.hotel.dto.FaultDto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
    <title>CF - Fault History</title>
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
      Integer filterFloor = (Integer) Optional.ofNullable(request.getAttribute("filterFloor")).orElse(0);
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
            <h1>Welcome - <span>Checker 01</span></h1>
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
        <h4>Maintenance</h4>
        <ul>
          <li><a href="cfaultreport.jsp">Fault Report</a></li>
          <li><a href="cfaultsall.html">Marked Fault(s)</a></li>
          <li><a href="cfaulthistory.jsp">Fault History</a></li>
        </ul>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="checkerhome.jsp" class="back"><i class="fa-solid fa-chevron-left"></i></a> Fault History</h2>
          <!-- <div class="home-lang-inner">
            <a href="#googtrans(en|en)" class="lang-en lang-select" data-lang="en"><img src="images/english.png"></a>
            <a href="#googtrans(en|ja)" class="lang-es lang-select" data-lang="ja"><img src="images/japan.png"></a>
          </div> -->
        </div>
      </div>
      <form id="checkerFaultHistory" action="CheckerFaultHistoryServlet" method="post">
        <div class="row">
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">Date</label>
            <div class="input-group input-group-sm">
              <input type="date" class="form-control form-control-sm" id="date" name="filterDate" placeholder="DD/MM/YYYY">
              <span class="input-group-text date" id="basic-addon1"><i class="fa-solid fa-calendar-days"></i></span>
            </div>
          </div>
          <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">Floor</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="filterFloor">
                <option value=0 >ALL</option>
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
          <div class="col-6 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">Room</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-house-chimney"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="faultRoom">
                <option value="ALL">ALL</option>
                <option value="100R">100R</option>
                <option value="101R">101R</option>
                <option value="102R">102R</option>
                <option value="103R">103R</option>
                <option value="104R">104R</option>
                <option value="105R">105R</option>
                <option value="106R">106R</option>
                <option value="107R">107R</option>
                <option value="108R">108R</option>
                <option value="109R">109R</option>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="">Fault Type</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-list-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="faultType">
                <c:forEach var="faultType" items="${allFaultTypes}">
                  <option value=0>ALL</option>
                  <option value="${faultType.id}">${faultType.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-2">
            <label class="form-label" for="updatedDate">Status</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-list-check"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="faultStatus">
                <c:forEach var="faultStatus" items="${allFaultStatus}">
                  <option value=0>ALL</option>
                  <option value="${faultStatus.id}">${faultStatus.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-12 col-sm-3 col-md-3 col-lg-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Add" name="filter" type="submit"><i class="fa-solid fa-magnifying-glass"></i> Filter</button>
            </div>
          </div>
        </div>
      </form>
      <div class="row">
        <div class="col-12">
          <div class="looper">
            <div class="table-responsive mb-3">
              <table class="table table-striped" style="table-layout: fixed">
                <thead>
                  <tr>
                    <th width="150px">Date</th>
                    <th width="150px">Floor</th>
                    <th width="150px;">Room</th>
                    <th width="150px;">Fault Type</th>
                    <th width="100px;">Status</th>
                    <th width="100px;">Image</th>
                    <th width="100px;">Comment</th>
                  </tr>
                </thead>
                <tbody>

                <c:forEach var="fault" items="${faultsList}">
                    <tr>
                        <td>
                              ${fault.createdDate}
                        </td>

                        <td>
                              ${fault.floor}
                        </td>

                        <td>
                              ${fault.room}
                        </td>

                        <td>
                              ${fault.faultTypeName}
                        </td>

                        <td>
                              ${fault.faultStatusName}
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

    <!-- Popups -->
    <div class="modal fade" id="exampleModalToggle" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel">View attachment(s)</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img src="images/faults/electrical-fault.jpg" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel">View attachment(s)</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img src="images/faults/floor-damage.jpg" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="exampleModalToggle3" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel">View attachment(s)</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img src="images/faults/water-leak.jpg" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="exampleModalToggle4" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel">View attachment(s)</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img src="images/faults/windows-cracked.jpg" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="exampleModalToggle5" aria-hidden="true" aria-labelledby="exampleModalToggleLabel" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalToggleLabel">View attachment(s)</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <img src="images/faults/paint-fault.jpg" class="img-fluid">
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