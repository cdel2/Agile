class Controller{
    constructor(){
        this.selectedMap = $("#mapSelector").val().toLowerCase();
        this.selectedDel = "grand-12";
        this.View;
        this.state = new InitState();
        
        //Interaction
        this.lastX; this.lastY;
        this.dragged = false;
        this.clicked = false;
    }

    loadDeliveries(){
        this.View.loadDeliveries(this.selectedDel);
    }

    loadRound(){
        let value = $("#numInput").val();
        if(value === ""){
            value = 3;
        }
        this.View.loadRound(value);
    }

    changeMap(element){
        this.selectedMap = element.value.toLowerCase();
    }

    changeDel(element){
        this.selectedDel = this.selectedMap+"-"+element.value;
    }

    loadMap(){
        this.View = new Viewer();
        this.View.setupCanvas();
        this.View.loadMap(this.selectedMap);
        switch(this.selectedMap){
            case "petit":
                $("#delSelector").html("<option>6</option><option>3</option>");
                this.selectedDel = this.selectedMap + "-6";
                break;
            case "moyen":
                $("#delSelector").html("<option>12</option><option>9</option>");
                this.selectedDel = this.selectedMap + "-12";
                break;
            case "grand":
                $("#delSelector").html("<option>20</option><option>15</option><option>12</option>");
                this.selectedDel = this.selectedMap + "-20";
                break;
        }
    }

    addPoint(){
        if(this.state.constructor.name === "AddPointState"){
            this.state = new DelState();
            this.View.update();
        }else if(this.state.constructor.name === "DelState"){
            this.state= new AddPointState();
            this.View.update();
        }
    }

    rmvPoint(){
        if(this.state.constructor.name === "RmvPointState"){
            this.state = new DelState();
            this.View.update();
        }else if(this.state.constructor.name === "DelState"){
            this.state= new RmvPointState();
            this.View.update();
        }
    }


}