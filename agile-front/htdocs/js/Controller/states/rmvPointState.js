class RmvPointState{
    constructor(){
        $("#rmvDel").html("<i class='fas fa-check'></i>").addClass("btn-success").removeClass("btn-warning");
        disableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#addDel", "#mapSelector", "#delSelector"]);
        console.log("Etat addPointState"); 
    }
    
    handleScroll(evt){
        let delta = evt.wheelDelta ? evt.wheelDelta/40 : evt.detail ? -evt.detail : 0;
        let rate = delta/7;
        Ctrl.View.zoom(rate);
        
        return evt.preventDefault() && false;
    };
    
    
    handleMouseDown(evt){
        let View = Ctrl.View;
        
        let ratio = View.Canvas.ratio;
    
        document.body.style.mozUserSelect = document.body.style.webkitUserSelect = document.body.style.userSelect = 'none';
        View.lastX = ratio*evt.offsetX;
        View.lastY = ratio*evt.offsetY;
        Ctrl.View.clicked = true;
    }
    
    handleMouseMove(evt){
        let View = Ctrl.View;
        let ratio = View.Canvas.ratio;
    
        if(View.clicked){
            View.dragged=true;
        }else{
            View.dragged=false;
        }
    
        
        if (View.dragged){
            let newX = ratio*evt.offsetX;
            let newY = ratio*evt.offsetY;
            View.deltaX += newX-View.lastX;
            View.deltaY += newY-View.lastY;
            View.update();
            View.lastX = newX;
            View.lastY = newY;
        }
        
    
        let nodeId = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
        View.Map.highlightNode(nodeId, View.Canvas.ctx);
    }
    
    handleMouseUp(evt){
        let View = Ctrl.View;
        View.clicked=false;
        if(View.dragged){
            console.log("DRAGGED");
            View.dragged = false;
        }else if(evt.srcElement.tagName==="CANVAS"){
            console.log("CANVAS");
            let ratio = View.Canvas.ratio;
            var node = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Deliveries.addUserNode(View.Map.coord[node]);
            View.update();
        }
    }
}