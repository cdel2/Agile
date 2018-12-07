function drawCircle(X, Y, R, color, ctx){
    R = Ctrl.View.Canvas.ratio*R;
    ctx.beginPath();
    ctx.arc(X, Y, R*(Ctrl.View.zoomLevel/2 +1), 0, 2 * Math.PI, false);
    ctx.fillStyle = color;
    ctx.strokeStyle = "black";
    ctx.lineWidth = 3;
    ctx.globalAlpha = 0.7;
    ctx.fill();
}