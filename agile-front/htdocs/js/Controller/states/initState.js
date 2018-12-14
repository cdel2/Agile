/**
 * Primary state, nothing loaded
 * @extends State
 */
class InitState{
    constructor(){
        disableButtons(["#undo", "#redo", "#loadDel", "#loadRounds", "#rmvDel", "#delSelector"]); 
        console.log("Etat init"); 
        $("#pathMenu").hide();
        $("#timeline").hide();
        $("#delCollapse").collapse('hide');
    }
    
    handleScroll(evt){
        console.log("kl");
    };
    
    handleMouseDown(evt){
    }
    
    handleMouseMove(evt){
    }
    
    handleMouseUp(evt){
    }

    handleWindowResize(evt){

    }
}