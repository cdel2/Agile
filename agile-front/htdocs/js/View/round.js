class Round{
    constructor(){
        this.paths = [];
        this.colors = ["green", "yellow", "purple", "blue", "lime", "aqua", "fuschia", "red", "olive", "teal", "maroon", "#E74C3C", "#9B59B6", "#2980B9", "#3498DB", "#1ABC9C", "#27AE60", "#2ECC71", "#F1C4OF", "#F39C12"];

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
                let color1 = object.colors[i];
                var temp = {id:i, display:true, color:color1, data:[]};
                $("#pathMenu").append(object.createPathHtml(color1,100, i));
                for(var j in round){
                   let path = round[j].path;
                   for(var k in path){
                       var el = path[k];
                       temp.data.push({start:el.start.id, end:el.end.id});
                   }
                }
                object.paths.push(temp);
            }
            $("#execTime").text("  "+totalTime/1000+"s");
            Ctrl.View.update();
            Ctrl.state = new CalcState();
        }).fail(function(textStatus){
            alertBox("Something wrong happened !");
            console.log("Round file not loaded !");
            console.log(textStatus);
            Ctrl.state = new DelState();
        }).always(function(){
            $("#loaderEl").hide();
        });
    }

    display(ctx, coord){
        ctx.lineWidth = Ctrl.View.Canvas.ratio*1*(Ctrl.View.zoomLevel +1);
        
        ctx.globalAlpha = 1;
        for(var i in this.paths){
            if(this.paths[i].display){
                let path = this.paths[i].data;
                ctx.beginPath();
                ctx.strokeStyle = this.paths[i].color;
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

    switchPathDisplay(id, state){
        for(var i in this.paths){
            if(this.paths[i].id === id){
                this.paths[i].display=state;
            }
        }
    }

    createPathHtml(color, totalTime, id){
        var temp =  "<div class='pathLine'>";
        temp += "<div id='colorSample' style='background-color:"+color+";'></div>";
        temp += totalTime;
        temp += "<button class='btn btn-warning viewButton' onclick='Ctrl.disableRound(this, "+id+")'><i class='fas fa-eye'></i></button>"
        temp += "</div>";
        return temp;
    }
}