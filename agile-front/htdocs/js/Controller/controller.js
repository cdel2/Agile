class Controller{
    constructor(){
        this.selectedMap = $("#mapSelector").val().toLowerCase();
        this.selectedDel = "grand-12";
        this.View;
        this.state = new InitState();
        
        this.time = null;

        //Interaction
        this.lastX; this.lastY;
        this.dragged = false;
        this.clicked = false;
    }

    loadDeliveries(){
        this.View.loadDeliveries(this.selectedDel);
    }

    loadRound(){
        $("#pathMenu").html("");
        let value = $("#numInput").val();
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
            this.state = new DelState();
            this.View.update();
        }else if(this.state.constructor.name === "DelState"){
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

    pathToForeground(el, id){
        var jel = $(el);
        if(jel.hasClass('activeLine')){
            jel.parent().parent().removeClass("activeLine");
            this.View.Round.pathToForeground(id);
            this.View.update();
        }else{
            let list = $(".activeLine");
            console.log(list);
            for(var i=0; i<list.length; i++){
                console.log($(list[i]).parent());
                $(list[i]).removeClass("activeLine");
            }
            jel.parent().parent().addClass("activeLine");
            this.View.Round.pathToForeground(id);
            this.View.update();
        }
    }

    changeTime(time){
        var hour = Math.floor(time.value/10);
        var rawMinutes = time.value-hour*10;
        var minutes = (rawMinutes/10)*60;
        $("#timeDisp").text(pad(hour,2)+":"+pad(minutes,2));
    }
}
