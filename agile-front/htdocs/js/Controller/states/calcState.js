class CalcState extends State{
    constructor(){
        super();

        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#rmvDel", "#mapSelector", "#delSelector", "#addDel", "#addDel"]);
        disableButtons([""]);
        console.log("Etat delState"); 
        $("#snoInfoBox").hide();
        $("#pathMenu").show();
        $("#timeline").show();
        
        $("#addDel").html("<i class='fas fa-plus'></i>").addClass("btn-warning").removeClass("btn-success");
        $("#rmvDel").html("<i class='fas fa-minus'></i>").addClass("btn-warning").removeClass("btn-success");
    }
    
    handleScroll(evt){
        super.scroll(event);
    };
    
    
    handleMouseDown(evt){
        super.MouseDown(evt);
    }
    
    handleMouseMove(evt){
        super.MouseMove(evt);
    }
    
    handleMouseUp(evt){
        let View = Ctrl.View;
        if(!Ctrl.dragged && evt.srcElement.tagName==="CANVAS"){
            console.log("ok");
            let ratio = View.Canvas.ratio;
            let node = View.Deliveries.findBestNode(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            console.log(node);
            View.Deliveries.nodeInfos(node);
            View.Round.setStop(node);
            View.update();
        }
        
        super.MouseUp(evt);
    }
}