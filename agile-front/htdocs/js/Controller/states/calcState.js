class CalcState extends State{
    constructor(){
        super();

        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#rmvDel", "#mapSelector", "#delSelector", "#addDel", "#addDel"]);
        disableButtons([""]);
        console.log("Etat CalcState"); 
        $("#snoInfoBox").hide();
        $("#pathMenu").show();
        $("#timeline").show();
        $("#delCollapse").collapse('hide');
        
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
            //View.update();
        }
        super.MouseUp(evt);
    }

    handleKeyPress(evt){
        var evtobj = window.event? event : evt
        ///console.log(evtobj);
        if(evtobj.ctrlKey){
            switch(evt.key){
                case 'z':
                    Ctrl.undo();
                    break;
                case 'y':
                    Ctrl.redo();
                    break;
                case "ArrowRight":
                    Ctrl.changeTime(7, false);
                    break;
                case "ArrowLeft":
                    Ctrl.changeTime(-7, false);
                    break;
            }
        }else{
            switch(evt.key){
                case "ArrowRight":
                    Ctrl.changeTime(2, false);
                    break;
                case "ArrowLeft":
                    Ctrl.changeTime(-2, false);
                    break;
            }
        }
    }
}