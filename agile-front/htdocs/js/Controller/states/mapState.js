class MapState extends State{
    constructor(){
        super();
        enableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#loadMap", "#mapSelector", "#delSelector", "#addDel"]);
        disableButtons(["#addDel", "#rmvDel"]);
        $("#snoInfoBox").hide();
        console.log("Etat mapState"); 
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
        super.MouseUp(evt);
    }
}