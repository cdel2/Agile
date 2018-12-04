function alertBox(message){
    $("#snoAlertBox").text(message);
    $("#snoAlertBox").fadeIn();
    closeSnoAlertBox();
}

function closeSnoAlertBox(){
    window.setTimeout(function () {
      $("#snoAlertBox").fadeOut(300)
    }, 3000);
} 

function disableButtons(list){
    for(var i=0; i<list.length; i++){
        let el = list[i];
        $(el).attr("disabled", true);
    }
}

function enableButtons(list){
    for(var i=0; i<list.length; i++){
        let el = list[i];
        $(el).removeAttr("disabled");
    }
}

function showMessage(bool, text){
    if(bool){
        $("#snoInfoBox").html(text).show();
    }else{
        $("#snoInfoBox").hide().html("");
    }
}

function distance(Xa, Ya, Xb, Yb){
    let tempLat = Yb-Ya;
    let tempLong = Xb-Xa;
    let temp = tempLat*tempLat + tempLong*tempLong;
    return Math.sqrt(temp);
}

function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

//Format value between 80 to t 180 in [8,11 to 18h format
function timeFormat(time){
    var hour = Math.floor(time/10);
    var rawMinutes = time-hour*10;
    var minutes = (rawMinutes/10)*60;
    return [hour, minutes];
}