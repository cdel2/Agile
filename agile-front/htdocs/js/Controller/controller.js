class Controller{
    constructor(){
        this.selectedMap = "moyen";
        this.selectedDel = "grand-12";
        this.View;
        this.state = new InitState();
    }

    loadDeliveries(){
        this.View.loadDeliveries(this.selectedDel);
        this.state = new DelState();
    }

    loadRound(){
        this.View.loadRound();
    }

    changeMap(element){
        switch(element.value){
            case "Big":
                console.log("ok1");
                this.selectedMap = "grand";
                break;
            case "Average":
                console.log("ok2");
                this.selectedMap = "moyen";
                break;
            case "Small":
                console.log("ok3");
                this.selectedMap = "petit";
                break;
            default:
                console.log("ok4");
                this.selectedMap = "grand";
                break;
        }
    }

    changeDel(element){
        this.selectedDel = this.selectedMap+"-"+element.value;
    }

    loadMap(){
        this.View = new Viewer();
        this.View.setupCanvas();
        this.View.loadMap(this.selectedMap);
        this.state = new MapState();
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
