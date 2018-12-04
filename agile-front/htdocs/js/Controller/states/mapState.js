class MapState extends State{
    constructor(){
        super();
        enableButtons(["#loadDel", "#loadMap", "#mapSelector", "#delSelector", "#addDel"]);
        disableButtons(["#addDel", "#rmvDel", "#loadRounds", "#undo", "#redo"]);
        $("#snoInfoBox").hide();
        $("#pathMenu").hide();
        $("#timeline").hide();
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