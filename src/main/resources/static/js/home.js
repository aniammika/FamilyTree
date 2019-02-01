document.addEventListener("DOMContentLoaded", function() {

    var deathDetails = document.getElementById("dead-person-details");
    deathDetails.style.display = "none";

    document.getElementById("isDeadBtn").addEventListener("change", function () {


        if (this.checked == false) {
            deathDetails.style.display = "none";
        }
        else {
            deathDetails.style.display = "block";
        }
    })

    $('.dropdown-toggle').dropdown()
});