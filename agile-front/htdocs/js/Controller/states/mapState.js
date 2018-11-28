class MapState{
    constructor(){
        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#mapSelector", "#delSelector", "#addDel"]);
        disableButtons(["#addDel", "#rmvDel"]);
        $("#snoInfoBox").hide();
        console.log("Etat mapState"); 
    }
    
    handleScroll(evt){
        console.log("d");
        let delta = evt.wheelDelta ? evt.wheelDelta/40 : evt.detail ? -evt.detail : 0;
        let rate = delta/7;
        Ctrl.View.zoom(rate);
        
        return evt.preventDefault() && false;
    };
    
    
    handleMouseDown(evt){
        console.log("e");
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
        
    
        if(Ctrl.addingPoint || Ctrl.removePoint){
            let nodeId = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Map.highlightNode(nodeId, View.Canvas.ctx);
        }
    }
    
    handleMouseUp(evt){
        //console.log(this.tagName);
        let View = Ctrl.View;
        View.clicked=false;
        if(View.dragged){
            View.dragged = false;
        }else{
            if(Ctrl.addingPoint && this.tagName==="CANVAS"){
                let ratio = View.Canvas.ratio;
                var node = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
                View.Deliveries.addUserNode(View.Map.coord[node]);
                View.update();
            } 
            if(Ctrl.removePoint && this.tagName==="CANVAS"){
                let ratio = View.Canvas.ratio;
                var node = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
                View.Deliveries.removeNode(View.Map.coord[node]);
                View.update();
            } 
        }
    }
}