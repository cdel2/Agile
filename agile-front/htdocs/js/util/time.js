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

function timeToSlider(time){
    let minutes = time.minutes/60*10;
    let hours = time.hours*10;
    console.log(minutes+hours);
}

function compareTime(time1, time2){
    if(time1.hours>time2.hours){
        return 1;
    }else if(time1.hours === time2.hours){
        if(time1.minutes > time2.minutes){
            return 1;
        }else if(time1.minutes === time2.minutes){
            return 0;
        }else{
            return -1;
        }
    }else{
        return -1;
    }
}