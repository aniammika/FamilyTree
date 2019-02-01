<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Family Tree</title>
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
                    <a class="btn btn-secondary dropdown-toggle" href="/familyMember/showAll" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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

    <div class="row">
        <div class="col"/>
        <div class="col-6 align-self-center">
            <p class="h2">dodaj dom</p>
        </div>

        <div class="col"/>
    </div>
    <div class="row">
        <div class="col"></div>
        <div class="col align-self-center">
                <div class="home-form">

                    <form:form method="POST" action="/home/add" modelAttribute="home">
                    <div class="form-row">
                        <div class="col">
                        <label for="name">nazwa:</label>
                        <form:input class="form-control" path="name" itemValue="name"
                                    id="name" placeholder="dom musi mieć nazwę"
                                    itemLabel="name"/>
                        <form:errors path="name"/>
                        </div>

                        <label for="address">adres:</label>
                        <form:input class="form-control" path="address" itemValue="address"
                                    id="address" placeholder="ulica, nr domu" itemLabel="address"/>
                        <form:errors path="address"/>

                    </div>

                    <div class="form-row">
                        <div class="col">
                            <label for="town">miasto:</label>
                            <form:input class="form-control" path="town" itemValue="town"
                                        id="town" placeholder="miasto" itemLabel="town"/>
                            <form:errors path="town"/>
                            <br>
                            <label for="country">kraj:</label>
                            <form:input class="form-control" path="country" itemValue="country"
                                        id="country" placeholder="country" itemLabel="country"/>
                            <form:errors path="country"/>
                        </div>
                        <div id="addPictureField">
                            <label for="addPicture">dodaj zdjęcia</label>
                            <input type="file" class="form-control-file" id="addPicture">
                        </div>

                    </div>
                </div>

                <div class="col"/>
                <input type="submit" class="btn btn-dark" value="zapisz" id="sbmtBtn"/>
                <div class="col"/>
            </div>
        </div>
        </form:form>
        <div class="" id="backToMainPage">
            <a href="/">Wróc do strony głównej</a>
        </div>
</body>
</html>
