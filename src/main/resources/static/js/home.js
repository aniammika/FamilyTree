document.addEventListener("DOMContentLoaded", function() {

    var deathDetails = document.getElementById("deathDetails");
    var addhome = document.getElementById("add-home-field");
    deathDetails.style.display = "none";
    addhome.style.display = "none";

    document.getElementById("isDeadBtn").addEventListener("change", function () {


        if (this.checked == false) {
            deathDetails.style.display = "none";
        }
        else {
            deathDetails.style.display = "block";
        }
    })

    document.getElementById("add-home-manually").addEventListener("change", function () {


        if (this.checked == false) {
            addhome.style.display = "none";
        }
        else {
            addhome.style.display = "block";
        }
    })

    var blockRelatedPerson = document.getElementById("relation-view-person")
    var person = blockRelatedPerson.innerText;

    if (person == ""){
        blockRelatedPerson.removeAttribute("disabled", true);
    }
    else {
        blockRelatedPerson.setAttribute("disabled", true);
    }

    var blockRelationType = document.getElementById("relation-view-type");
    var relationType = blockRelationType.innerText;

    if (relationType == ""){
        blockRelationType.removeAttribute("disabled", true);

    }
    else {
        blockRelationType.setAttribute("disabled", true);
    }
});