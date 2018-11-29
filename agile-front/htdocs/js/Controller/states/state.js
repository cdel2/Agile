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
    
        if(Ctrl.clicked){
            Ctrl.dragged=true;
        }else{
            Ctrl.dragged=false;
        }
    
        
        if (Ctrl.dragged){
            let newX = ratio*evt.offsetX;
            let newY = ratio*evt.offsetY;
            View.deltaX += newX-Ctrl.lastX;
            View.deltaY += newY-Ctrl.lastY;
            /*if(View.deltaX>0){
                View.deltaX=0;
            }
            if(View.deltaY<0){
                View.deltaY=0;
            }*/
            View.update();
            Ctrl.lastX = newX;
            Ctrl.lastY = newY;
        }
    }
    
    MouseUp(evt){
        Ctrl.clicked=false;
        if(Ctrl.dragged){
            Ctrl.dragged = false;
        }
    }
}