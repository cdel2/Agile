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
        ticks: [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
        ticks_labels: ['8h', '9h', '10h', '11h', '12h', "13h", '14h', '15h', '16h', '17h', '18h'],
        ticks_snap_bounds: 30
    }).on('slide', function(val){
        Ctrl.changeTime(val);
    })
    .data('slider');
    
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


