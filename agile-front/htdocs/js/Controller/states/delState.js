class DelState{
    constructor(){
        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#rmvDel", "#mapSelector", "#delSelector", "#addDel", "#addDel"]);
        disableButtons([""]);
        console.log("Etat delState"); 
        $("#snoInfoBox").hide();
        
        $("#addDel").html("<i class='fas fa-plus'></i>").addClass("btn-warning").removeClass("btn-success");
        $("#rmvDel").html("<i class='fas fa-minus'></i>").addClass("btn-warning").removeClass("btn-success");
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

    }
    
    handleMouseUp(evt){
        let View = Ctrl.View;
        View.clicked=false;
        if(View.dragged){
            View.dragged = false;
        }else if(evt.srcElement.tagName==="CANVAS"){
            let ratio = View.Canvas.ratio;
            var node = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Deliveries.nodeInfos(node);
            View.update();
        }
    }
}