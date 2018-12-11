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
    return hours + minutes;
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

function timeToString(time){
    return pad(time.hours,2)+":"+pad(time.minutes,2);
}

function initSlider(ticks1){
    if($("#timelineIn").length != 0){
        $("#sliderInit").slider('destroy');
    }

    let ticks = "[80,";
    for(var j in ticks1){
        ticks += timeToSlider(ticks1[j]) + ",";
    }
    ticks += "180]";

    $("#sliderInit").attr('data-slider-ticks', ticks);
    $("#sliderInit").attr('data-slider-ticks-snap-bounds', 1);
    $("#sliderInit").slider({
        formatter: function(value) {
            var time = timeFormat(value);
            return pad(time[0],2)+":"+pad(time[1],2);
        }
    }).on('slide', function(val){
        var hour = Math.floor(val.value/10);
        var rawMinutes = val.value-hour*10;
        var minutes = (rawMinutes/10)*60;
        Ctrl.changeTime({hours:hour, minutes:minutes, seconds:0});
    });
}

function secondsToMS(seconds1){
    let minutes = Math.floor(seconds1/60);
    let seconds = seconds1 - minutes*60;
    return minutes+"\'"+seconds+"\'\'";
}