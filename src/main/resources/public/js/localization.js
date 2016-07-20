/**
 * Created by Edward on 19-Jul-16.
 */

    var key = "AIzaSyAhp_oWV92dRF8zwbb9dVyem31D9bWMV-I"
    var baseurl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
    var x = "placeholder"

    window.onload=loaded();
    function loaded() {
        var path = window.location.pathname;
        if (path == "/game/pale" || path == "/game/loto" || path == "/user/transferfunds") {
            x = document.getElementById("country");
            getLocation();
            console.log("ready");

        }
    }

        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                x.innerHTML = "Geolocation is not supported by this browser.";
            }
        }

        function showPosition(position) {
            $.ajax({
                url: baseurl + position.coords.latitude + "," + position.coords.longitude + "&result_type=country&key=" + key,
                success: getCountryCode
            });
        }

        function getCountryCode(countryData) {
            var countrycode = countryData.results[0].address_components[0].short_name
            x.value = countrycode

        }

