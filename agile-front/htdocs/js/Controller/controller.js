class Controller{
    constructor(){
        this.selectedMap = $("#mapSelector").val().toLowerCase();
        this.selectedDel = "grand-12";
        this.View;
        this.state = new InitState();

        //undo-redo
        this.userActions = [];
        this.formerUserActions = [];

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
        $("#loadRounds").html("Cancel").addClass("btn-danger").removeClass("btn-warning");
        if(value === ""){
            value = 3;
        }
        this.View.loadRound(value);
        return false;
    }

    changeMap(element){
        this.selectedMap = element.value.toLowerCase();
    }

    changeDel(element){
        this.selectedDel = this.selectedMap+"-"+element.value;
    }

    loadMap(){
        if(this.View != undefined){
            delete this.View;
        }
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
        console.log()
        if(this.state.constructor.name === "AddPointState"){
            this.state = new CalcState();
            this.View.update();
        }else if(this.state.constructor.name === "CalcState"){
            this.state= new AddPointState();
            this.View.update();
        }
    }

    rmvPoint(){
        if(this.state.constructor.name === "RmvPointState"){
            this.state = new CalcState();
            this.View.update();
        }else if(this.state.constructor.name === "CalcState"){
            this.state= new RmvPointState();
            this.View.update();
        }
    }

    disableRound(el, id){
        var jel = $(el);
        if(jel.hasClass('hiddenPath')){
            jel.removeClass("hiddenPath");
            $(el).html("<i class='fas fa-eye'></i>");
            this.View.Round.switchPathDisplay(id, true);
            this.View.update();
        }else{
            jel.addClass("hiddenPath");
            $(el).html("<i class='fas fa-eye-slash eye'></i>");
            this.View.Round.switchPathDisplay(id, false);
            this.View.update();
        }
    }

    pathToForeground(num){
        let el = $("#pl"+num);
        if(el.hasClass('activeLine')){
            el.removeClass("activeLine");
            this.View.Round.pathToForeground(num);
            this.View.update();
        }else{
            let list = $(".activeLine");
            console.log(list);
            for(var i=0; i<list.length; i++){
                console.log($(list[i]).parent());
                $(list[i]).removeClass("activeLine");
            }
            el.addClass("activeLine");
            this.View.Round.pathToForeground(num);
            this.View.update();
        }
    }

    changeTime(time){        
        $("#timeDisp").text(pad(time.hours,2)+":"+pad(time.minutes,2));
        this.View.time = time;
        this.View.update();
    }

    reset(){
        delete this.View.Map;
        this.View.update();
        this.state = new InitState();
    }

    undo(){
        var lastAction = this.userActions.pop();
        if(lastAction === undefined){
            alertBox("Nothing to undo");
        }else{  
            this.formerUserActions.push(lastAction);
            console.log(lastAction);
            switch(lastAction.action){
                case "add":
                    console.log("unadd");
                    this.View.Deliveries.updateDelivery(lastAction.id, false);
                    break;
                case "remove":
                    console.log("unrmv");
                    this.View.Deliveries.updateDelivery(lastAction.id, true);
                    break;
            }
        }
    }

    redo(){
        var lastUndoAction = this.formerUserActions.pop();
        if(lastUndoAction === undefined){
            alertBox("Nothing to redo");
        }else{  
            this.userActions.push(lastUndoAction);
            console.log(lastUndoAction);
            switch(lastUndoAction.action){
                case "add":
                    console.log("readd");
                    this.View.Round.updateDelivery(lastUndoAction.id, true);
                    break;
                case "remove":
                    console.log("rermv");
                    this.View.Round.updateDelivery(lastUndoAction.id, false);
                    break;
            }
        }
    }
}
