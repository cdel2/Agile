class CalculatingState extends State{
    constructor(){
        super();
        $("#loadRounds").html("Cancel").addClass("btn-danger").removeClass("btn-warning");
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
        super.MouseUp(evt);
    }

    handleKeyPress(evt){
        
    }
}