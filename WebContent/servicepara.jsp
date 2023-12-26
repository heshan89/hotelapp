
<!-- Design & Frontend Develop By Heshan Pramith -->
<!doctype html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
    <title>Service Quality Parameters</title>
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
    <header>
      <div class="container-fluid">
        <div class="row">
          <div class="col-3 col-xs-4 col-sm-3 col-md-2 col-lg-1 text-center">
            <div class="logo">
              <a data-bs-toggle="offcanvas" href="#offcanvasExample" role="button" aria-controls="offcanvasExample"><img src="images/logoin.png" class="img-fluid"></a>
            </div>
          </div>
          <div class="col-8 col-xs-7 col-sm-8 col-md-9 col-lg-10 p-l-0">
            <h1>Welcome - <span>Admin</span></h1>
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
        <h4>Service</h4>
        <ul>
          <li><a href="RatingServlet">Service Quality Parameters</a></li>
        </ul>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <h2 class="main-title"><a href="adminhome.jsp" class="back"><i class="fa-solid fa-chevron-left"></i></a> Service Quality Parameters</h2>
        </div>
      </div>
      <form id="user-rating-form" action="RatingServlet" method="post">
        <div class="row">
          <div class="col-6 col-sm-6 col-md-4 col-lg-3 col-xl-2">
            <label class="form-label" for="">Hotel</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for=""><i class="fa-solid fa-building-circle-check"></i></label>
              <select class="form-select form-control form-control-sm" id="ratingHotel" name="ratingHotel">
                <c:forEach var="hotel" items="${ratingHotels}">
                  <option value="${hotel.id}">${hotel.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-3 col-xl-3 col-xxl-2">
            <label class="form-label" for="">Feedback Category</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for="inputGroupSelect01"><i class="fa-solid fa-message"></i></label>
              <select class="form-select form-control form-control-sm" id="inputGroupSelect01" name="feedbackCategory">
                <c:forEach var="feedbackCategory" items="${feedbackCategories}">
                  <option value="${feedbackCategory.id}">${feedbackCategory.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-3 col-xl-3 col-xxl-2">
            <label class="form-label" for="">Category</label>
            <div class="input-group input-group-sm">
              <label class="input-group-text" for=""><i class="fa-solid fa-message"></i></label>
              <select class="form-select form-control form-control-sm" id="category", name="ratingCategory">
                <c:forEach var="category" items="${categories}">
                  <option value="${category.id}">${category.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-6 col-sm-6 col-md-4 col-lg-3 col-xl-2">
            <label class="form-label" for="updatedDate">Source</label>
            <div class="input-group input-group-sm">
              <input type="text" class="form-control form-control-sm" id="" name="ratingSource" placeholder="">
            </div>
          </div>
          <div class="col-12 col-sm-12 col-md-4 col-lg-3 col-xl-2">
            <label class="form-label w-100" for="updatedDate">Rating</label>
            <span class="user-rating mb-3">
              <input type="radio" name="ratingValue" value="5"><span class="star"></span>
              <input type="radio" name="ratingValue" value="4"><span class="star"></span>
              <input type="radio" name="ratingValue" value="3"><span class="star"></span>
              <input type="radio" name="ratingValue" value="2"><span class="star"></span>
              <input type="radio" name="ratingValue" value="1"><span class="star"></span>
            </span>
            <span id="selected-rating" class="selected-rating" >0</span>
          </div>
          <div class="col-12 col-sm-12 col-md-8 col-lg-8 col-xl-6">
            <label class="form-label" for="updatedDate">Description</label>
            <div class="input-group input-group-sm">
              <textarea class="form-control form-control-sm" id="" name="ratingDesc" placeholder="" rows="5"></textarea>
            </div>
          </div>          
          <div class="col-12 col-sm-12 col-md-4 col-lg-3 col-xl-2">
            <label class="form-label" for="">&nbsp;</label>
            <div class="input-group input-group-sm">
              <button class="add btn btn-sm" title="Add" name="add" type="submit"><i class="fa-solid fa-circle-plus"></i> Add To List</button>
            </div>
          </div>
        </div>
      </form>

      <c:if test="${rateAddSuccess == 'true'}">
          <div class="alert alert-success alert-dismissible fade show" role="alert">
            Rate added successfully
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>

      <c:if test="${rateAddError == 'true'}">
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Could not add Rate
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>

      <form>
        <div class="row mt-4">
          <div class="col-12">
            <div class="table-responsive">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Hotel</th>
                    <th>Description</th>
                    <th>Source</th>
                    <th>Feedback Category</th>
                    <th>Category</th>
                    <th>Rating</th>
                  </tr>
                </thead>
                <tbody>

                <c:forEach var="rat" items="${ratings}">

                    <tr>

                            <td>
                                  ${rat.createdDate}
                            </td>

                            <td>
                                  ${rat.ratingHotelName}
                            </td>

                            <td>
                                  ${rat.ratingDesc}
                            </td>

                            <td>
                                  ${rat.ratingSource}
                            </td>

                            <td>
                                  ${rat.ratingFeedbackCategoryName}
                            </td>

                            <td>
                                  ${rat.ratingCategoryName}
                            </td>

                            <td>
                                  ${rat.ratingValue}
                            </td>

                    </tr>

                </c:forEach>

                </tbody>
              </table>
            </div>
          </div>
        </div>
      </form>
    </div>
    <script type="text/javascript">
      $('#user-rating-form').on('change','[name="rating"]',function(){
        $('#selected-rating').text($('[name="rating"]:checked').val());
      });
    </script>

  </body>
</html>