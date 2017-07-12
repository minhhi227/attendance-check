<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache"> 
    <meta http-equiv="Cache-Control" content="no-cache"> 
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    
    <title>Student Manager | Home</title>
    
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">
    
    <!--[if lt IE 9]>
		<script src="static/js/html5shiv.min.js"></script>
		<script src="static/js/respond.min.js"></script>
	<![endif]-->
</head>
<body>

	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/" class="navbar-brand">Students:${studentid}</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="students">All Courses</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${student.firstName} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

    </c:if>

	</div>
	<c:choose>
		<c:when test="${mode == 'MODE_COURSE'}">
			<div class="container text-center" id="tasksDiv">
				<h3>My Courses</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left">
						<thead>
							<tr>
								<th>Course Number</th>
								<th>Name</th>
								<th>Start Date</th>
								<th>Period</th>
								<th>Faculty</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="enrolled" items="${enrolled}">
								<tr>
									<td>${enrolled.offering.course.number}</td>
									<td>${enrolled.offering.course.name}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${enrolled.offering.startDate}"/></td>
									<td>${enrolled.offering.period}</td>
									<td>${enrolled.offering.faculty.firstName}</td>
									<c:choose>
									    <c:when test="${enrolled.offering.startDate <= today}">
									        <td><a href="all-attendance?id=${enrolled.offering.id}"><span class="glyphicon glyphicon-arrow-right"></span></a></td> 
									    </c:when>    
									    <c:otherwise>
									        <td><span class="glyphicon glyphicon-ban-circle"></span></td>
									    </c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		
		<c:when test="${mode == 'MODE_ATTENDANCE'}">
			<div class="container text-center" id="tasksDiv">
				<h3>My Attendance</h3>
				<h1>${msg}</h1>
				<hr>
				<div class="table-responsive">
					<table class="text-left">
					  <tr>
					    <td>Percentage of attendance: ${meditaionPercentage} %</td>
					  </tr>
					  <tr>
					    <td>Number of sessions attended: ${meditationCount}</td>
					  </tr>
					  <tr>
					    <td>Total number of sessions in this block: ${numberOfRequiredSessions}</td>
					  </tr>
					  <tr>
					    <td>Total extra grade: ${extraGrade}</td>
					  </tr>
					</table>
		
					<table class="table table-striped table-bordered text-left">
						<thead>
							<tr>
								<th>Date</th>
								<th>Attendance</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="map" items="${map}">
								<tr>
									<td>${map.key}</td>
									
									<c:choose>
									    <c:when test="${map.value == true}">
									        <td><span class="glyphicon glyphicon-ok"></span></td> 
									    </c:when>    
									    <c:otherwise>
									         <td><span class="glyphicon glyphicon-minus"></span></td> 
									    </c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
			</div>
		</c:when>
	</c:choose>

	<script src="static/js/jquery-1.11.1.min.js"></script>    
    <script src="static/js/bootstrap.min.js"></script>
</body>
</html>