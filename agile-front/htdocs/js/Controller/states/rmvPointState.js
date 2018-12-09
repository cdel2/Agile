class RmvPointState extends State{
    constructor(){
        super();
        $("#rmvDel").html("<i class='fas fa-ban'></i>").addClass("btn-danger").removeClass("btn-warning");
        disableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#addDel", "#mapSelector", "#delSelector"]);
        console.log("Etat rmvPointState"); 
        $("#pathMenu").hide();
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
        let a = false;
        if(!Ctrl.dragged && evt.srcElement.tagName==="CANVAS"){
            console.log("CANVAS");
            let ratio = View.Canvas.ratio;
            var nodeId = View.Map.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Deliveries.rmvUserDelivery(parseInt(nodeId));
            View.update();
            a = true;
        }
        super.MouseUp(evt);
        if(a) Ctrl.state = new CalcState();
    }
}