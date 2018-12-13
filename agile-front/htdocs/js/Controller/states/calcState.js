class CalcState extends State{
    constructor(){
        super();

        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#rmvDel", "#mapSelector", "#delSelector", "#addDel", "#addDel"]);
        disableButtons([""]);
        console.log("Etat CalcState"); 
        $("#snoInfoBox").hide();
        $("#pathMenu").show();
        $("#timeline").show();
        
        $("#addDel").html("<i class='fas fa-plus'></i>").addClass("btn-warning").removeClass("btn-danger");
        $("#rmvDel").html("<i class='fas fa-minus'></i>").addClass("btn-warning").removeClass("btn-danger");
        
    }
    
    handleScroll(evt){
        super.scroll(evt);
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
            let ratio = View.Canvas.ratio;
            let deliveryId = View.Deliveries.findBestDelivery(ratio*(evt.offsetX-View.Canvas.html.offsetTop), ratio*(evt.offsetY-View.Canvas.html.offsetLeft));
            View.Deliveries.selectDelivery(deliveryId);
            View.update();
        }
        super.MouseUp(evt);
    }
}