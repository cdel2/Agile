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
            console.log(data);
            
            var totalTime = new Date().getTime()-ajaxTime;
            for(var i in data){
                let round = data[i].listPath;
                var temp = [];
                for(var j in round){
                   let path = round[j].path;
                   for(var k in path){
                       var el = path[k];
                       temp.push({start:el.start.id, end:el.end.id});
                   }
                }
                object.paths.push(temp);
            }
            $("#execTime").text("  "+totalTime/1000+"s");
            Ctrl.View.update();
        }).fail(function(textStatus){
            alertBox("Something wrong happened !");
            console.log("Round file not loaded !");
            console.log(textStatus);
        }).always(function(){
            $("#loaderEl").hide();
        });
    }

    display(ctx, coord){
        ctx.lineWidth = 5;
        for(var i in this.paths){
            ctx.beginPath();
            ctx.strokeStyle = this.colors[i];
            let path = this.paths[i];
            for(var j in path){
                let start = coord[path[j].start];
                let end = coord[path[j].end];
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
            }
            ctx.stroke();
        }
        
    }
}