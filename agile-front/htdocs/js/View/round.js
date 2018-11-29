class Round{
    constructor(){
        this.paths = [];
        this.colors = ["green", "yellow", "purple", "blue", "lime", "aqua", "fuschia"]
    }

    load(num1){
        let object = this;
        let num = num1;
        $("#loaderEl").show();
        var ajaxTime= new Date().getTime();
        $.ajax({
            url: "http://localhost:8080/calc/"+num,
            type:"GET"
        }).done(function( data ) {
            var totalTime = new Date().getTime()-ajaxTime;
            for(var i in data){
                //console.log(data[i]);
                let round = data[i];
                var temp = [];
                for(var j in round){
                   // console.log(round[j]);
                    temp.push(round[j]);
                }
                object.paths.push(temp);
            }
            $("#execTime").text("  "+totalTime/1000+"s");
            Ctrl.View.update();
        }).fail(function(textStatus, errorThrown){
            alertBox("Something wrong happened !");
            console.log("Round file not loaded !");
            console.log(textStatus);
        }).always(function(){
            $("#loaderEl").hide();
        });
    }

    display(ctx){
        for(var i in this.paths){
            ctx.beginPath();
            let path = this.paths[i];
            for(var j=0; j<path.length-1; j++){
                let start = path[j];
                let end = path[j+1];
                
                //console.log(i);
                //console.log(this.colorGen(i));
                ctx.strokeStyle = this.colors[i];
                ctx.lineWidth = 5;
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
            }
            ctx.stroke();
        }
        
    }
}