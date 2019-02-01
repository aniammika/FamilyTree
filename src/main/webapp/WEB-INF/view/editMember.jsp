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


<c:if test="${editStatus == 'editSuccess'}">
    <div class="alert alert-success" role="alert">
        Zapisano zmiany
    </div>
</c:if>

<div class="row justify-content-md-center">
    <div class="row align-items-left" id="all-members">
        <div class="col"/>
        <div class="col">
            <form:form method="POST" modelAttribute="familyMemberDetails">

                <label for="familyName">nazwisko rodowe:</label>
                <form:input class="form-control" path="nameDetails.familyName" itemValue="familyName"
                            id="familyName"
                            itemLabel="familyName"/>
                <form:errors path="nameDetails.familyName"/>

                <label for="actualHome">aktualny dom:</label>
                <form:select class="form-control" itemValue="id" itemLabel="name"
                             path="actualHome" items="${homesList}" id="actualHome"/>


                <br>

                <label for="biographicalNote">nota biograficzna:</label>
                <small id="biographicalNote" class="form-text text-muted">Szkoła, praca, hobby? Napisz wszystko
                    co
                    wiesz. Albo co ważne :)
                </small>
                <form:textarea path="biographicalNote" class="form-control" id="biographicalNote"/>

                <div class="form-row">
                    <div class="col">
                        <label for="birthDate">data urodzenia:</label>
                        <form:input type="date" class="form-control" path="birthDetails.birthDate"
                                    id="birthDate"/>
                    </div>
                    <div class="col">
                        <label for="birthPlace">miejsce urodzenia:</label>
                        <form:input class="form-control" path="birthDetails.birthPlace" id="birthPlace"/><br>
                    </div>
                </div>

                <br><b>najbliższe pokrewieństwa</b><br>
                <div class="form-group">
                    <table class="table table-striped">
                        <c:forEach items="${relations}" var="relation">
                            <tr>
                                <td>${relation.relatedPerson.nameDetails.firstName}</td>
                                <td>${relation.relatedPerson.nameDetails.lastName}</td>
                                <td>${relation.kindOfRelation}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>



                <label for="addPicture">Dodaj zdjęcia</label>
                <input type="file" class="form-control-file" id="addPicture">

                <div class="form-check form-check-inline">
                    <br> osoba nie żyje:
                    <form:checkbox name="checkIfDead" class="form-check-input" path="dead" value="false"
                                   id="isDeadBtn"/>
                </div>
                <div id="dead-person-details">

                    <div class="col">
                        <label for="birthDate">data śmierci:</label>
                        <form:input type="date" class="form-control" path="deathDetails.deathDate"
                                    id="deathDate"/>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="deathPlace">miejsce śmierci:</label>
                            <form:input class="form-control" path="deathDetails.deathPlace" id="deathPlace"/>
                        </div>
                        <div class="col">
                            <label for="deathReason">powód śmierci:</label>
                            <form:input class="form-control" path="deathDetails.deathReason" id="deathReason"/>
                        </div>
                    </div>
                </div>

                <br>
                <input type="submit" class="btn btn-info" value="zapisz zmiany" id="sbmtBtn"/>


            </form:form>
        </div>

        <div class="col"/>
    </div>
</div>
</body>
</html>
