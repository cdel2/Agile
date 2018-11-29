class MapState extends State{
    constructor(){
        super();
        enableButtons(["#undo", "#redo", "#loadDel", "#loadMap", "#mapSelector", "#delSelector", "#addDel"]);
        disableButtons(["#addDel", "#rmvDel", "#loadRounds"]);
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