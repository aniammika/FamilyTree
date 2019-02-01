<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Facebook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css"/>

</head>
<body>
<div class="container-fluid">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Familytree</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Strona główna <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>

        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/familyMember/add">Dodaj członka rodziny<span class="sr-only">(current)</span></a>
            </li>
        </ul>

        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <div class="dropdown">
                    <a class="btn btn-secondary dropdown-toggle" href="/familyMember/showAll" role="button"
                       id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Nasza rodzina
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="/familyMember/showAll">Lista</a>
                        <a class="dropdown-item" href="#">Drzewo</a>
                        <a class="dropdown-item" href="#">Mapa</a>
                    </div>
                </div>
            </li>
        </ul>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/home/add">Dodaj dom<span class="sr-only">(current)</span></a>
            </li>
        </ul>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/home/showAll">Nasze domy<span class="sr-only">(current)</span></a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
        &nbsp&nbsp
        <a href="/logout" class="btn btn-primary active" role="button" aria-pressed="true">Wyloguj</a>
</div>
</nav>


<br>
<div class="row justify-content-md-center">
    <p class="font-weight-normal h3">${memberFirstName}&nbsp;${memberLastName}</p>
</div>
<br>

<div class="row justify-content-md-center">
    <div class="row align-items-left" id="all-members">
        <div class="col"/>
        <div class="col">
            <table class="table">
                <tr>
                    <td>nazwisko rodowe:</td>
                    <td>${familyMemberDetails.nameDetails.familyName}</td>
                </tr>
                <tr>
                    <td>aktualny dom:</td>
                    <td>${familyMemberDetails.actualHome.name}</td>
                </tr>
                <tr>
                    <td>nota biograficzna</td>
                </tr>
                <tr>
                    <td>${familyMemberDetails.biographicalNote}</td>
                </tr>
                <tr>
                    <td>data urodzenia:</td>
                    <td>${familyMemberDetails.birthDetails.birthDate}</td>
                </tr>
                <tr>
                    <td>miejsce urodzenia:</td>
                    <td>${familyMemberDetails.birthDetails.birthPlace}</td>
                </tr>
            </table>
            <br>
            <table class="table">
                <tr>
                    <th>krewni</th>
                </tr>
                <c:forEach items="${relations}" var="relation">
                    <tr>
                        <td>${relation.relatedPerson.nameDetails.firstName}</td>
                        <td>${relation.relatedPerson.nameDetails.lastName}</td>
                        <td>${relation.kindOfRelation}</td>
                    </tr>

                </c:forEach>
            </table>


            <c:if test="${isPersonDead == 'true'}">
                <table class="table">
                    <tr>
                        <td>data śmierci</td>
                        <td>${familyMemberDetails.deathDetails.deathDate}</td>
                    </tr>
                    <tr>
                        <td>miejsce śmierci</td>
                        <td>${familyMemberDetails.deathDetails.deathPlace}</td>
                    </tr>
                </table>

            </c:if>


            <table class="table">
                <tr>
                    <td>${familyMemberDetails.images}</td>
                </tr>

            </table>




            <a href="${pageContext.request.contextPath}/familyMember/editMember/${familyMemberDetails.id}"
               class="btn btn-info" role="button" aria-pressed="true">Edytuj</a>
            <a href="${pageContext.request.contextPath}/delete/${familyMemberDetails.id}" class="btn btn-info"
               role="button" aria-pressed="true">Usuń</a>

        </div>

        <div class="col"/>
    </div>
</div>
</body>
</html>
