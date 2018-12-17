/**
 * Original class state implementing common usages of the application
 */
class State{
    constructor(){
    }
    
    scroll(evt){
        let delta = evt.wheelDelta ? evt.wheelDelta/40 : evt.detail ? -evt.detail : 0;
        let rate = delta/7;
        Ctrl.View.zoom(rate);
        
        return evt.preventDefault() && false;
    };
    
    
    MouseDown(evt){
        let View = Ctrl.View;
        
        let ratio = View.Canvas.ratio;

        document.body.style.mozUserSelect = document.body.style.webkitUserSelect = document.body.style.userSelect = 'none';
        Ctrl.lastX = ratio*evt.offsetX;
        Ctrl.lastY = ratio*evt.offsetY;
        Ctrl.clicked = true;
    }
    
    MouseMove(evt){
        let View = Ctrl.View;
        let ratio = View.Canvas.ratio;
        let newX = ratio*evt.offsetX;
        let newY = ratio*evt.offsetY;

        if(Ctrl.dragged || (Ctrl.clicked && newX-Ctrl.lastX!=0 && newY-Ctrl.lastY!=0)){
            Ctrl.dragged=true;
        }else{
            Ctrl.dragged=false;
        }
        
        if (Ctrl.dragged){
            View.deltaX += newX-Ctrl.lastX;
            View.deltaY += newY-Ctrl.lastY;
            View.update();
            Ctrl.lastX = newX;
            Ctrl.lastY = newY;
        }
    }
    
    MouseUp(evt){
        Ctrl.clicked=false;
        Ctrl.dragged = false;
    }
}