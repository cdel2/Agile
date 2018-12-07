var Ctrl;

$( document ).ready(function() {
    console.log( "ready!" );
    init();
    Ctrl = new Controller();
    Ctrl.loadMap();
});

function init(){
    window.onresize = function(event) {
        Ctrl.View.setupCanvas();
        Ctrl.View.update();
    };

    $("#sliderInit").slider({
        tooltip: 'always',
        formatter: function(value) {
            var time = timeFormat(value);
            //console.log(time);
            return pad(time[0],2)+":"+pad(time[1],2);
        }
    }).on('slide', function(val){
        var hour = Math.floor(val.value/10);
        var rawMinutes = val.value-hour*10;
        var minutes = (rawMinutes/10)*60;
        Ctrl.changeTime({hours:hour, minutes:minutes, seconds:0});
    });
    
    /*window.addEventListener('mouseup', function(evt){
        Ctrl.state.handleMouseUp(evt);
    },false);*/

    let map = $("#map").get(0);
    map.addEventListener('mousedown', function(evt){
        Ctrl.state.handleMouseDown(evt);
    },false);
    map.addEventListener('mousemove', function(evt){
        Ctrl.state.handleMouseMove(evt);
    },false);
    
    map.addEventListener('mouseup', function(evt){
        Ctrl.state.handleMouseUp(evt);
    },false);


    map.addEventListener('DOMMouseScroll',function(evt){
        Ctrl.state.handleScroll(evt);
    },false);
    map.addEventListener('mousewheel',function(evt){
        Ctrl.state.handleScroll(evt);
    },false);
}


