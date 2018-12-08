function getRetinaRatio() {
    var devicePixelRatio = window.devicePixelRatio || 1
    var c = document.createElement('canvas').getContext('2d')
    var backingStoreRatio = [
        c.webkitBackingStorePixelRatio,
        c.mozBackingStorePixelRatio,
        c.msBackingStorePixelRatio,
        c.oBackingStorePixelRatio,
        c.backingStorePixelRatio,
        1
    ].reduce(function(a, b) { return a || b })

    return devicePixelRatio / backingStoreRatio
}

function drawCircle(X, Y, R, color, ctx){
    R = Ctrl.View.Canvas.ratio*R;
    ctx.beginPath();
    ctx.arc(X, Y, R*(Ctrl.View.zoomLevel/2 +1), 0, 2 * Math.PI, false);
    ctx.fillStyle = color;
    ctx.lineWidth = 3;
    ctx.globalAlpha = 0.7;
    ctx.fill();
}