class AddPointState extends State{
    constructor(){
        super();
        $("#addDel").html("<i class='fas fa-check'></i>").addClass("btn-success").removeClass("btn-warning");
        disableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#rmvDel", "#mapSelector", "#delSelector"]);
        console.log("Etat addPointState"); 
    }
    
    handleScroll(evt){
        super.scroll(evt);
    };
    
    
    handleMouseDown(evt){
        super.MouseDown(evt);
    }
    
    handleMouseMove(evt){
        super.MouseMove(evt);       
    
        let View = Ctrl.View;
        let ratio = View.Canvas.ratio;
        let nodeId = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
        View.Map.highlightNode(nodeId, View.Canvas.ctx);
    }
    
    handleMouseUp(evt){
        let View = Ctrl.View;
        if(!Ctrl.dragged && evt.srcElement.tagName==="CANVAS"){
            console.log("CANVAS");
            let ratio = View.Canvas.ratio;
            var node = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Deliveries.addUserNode(node);
            View.update();
        }
        super.MouseUp(evt);
    }
}