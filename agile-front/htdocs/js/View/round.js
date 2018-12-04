class Round{
    constructor(){
        this.paths = [];
        this.colors = ["green", "yellow", "purple", "blue", "lime", "aqua", "fuschia", "red", "olive", "teal", "maroon", "#E74C3C", "#9B59B6", "#2980B9", "#3498DB", "#1ABC9C", "#27AE60", "#2ECC71", "#F1C4OF", "#F39C12"];
        this.firstPath = -1;
        this.stop=null;
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
            console.log("coucou");
            console.log(data);
            
            var totalTime = new Date().getTime()-ajaxTime;
            for(var i=0; i<data.length; i++){
                let round = data[i].listPath;
                let color1 = object.colors[i];
                var temp = {id:i, display:true, color:color1, data:[], arrival:data[i].arrival};
                $("#pathMenu").append(object.createPathHtml(color1,100, i));
                for(var j in round){
                   let path = round[j].path;
                   let roudPart = [];
                   for(var k in path){
                       var el = path[k];
                       roudPart.push({start:el.start.id, end:el.end.id});
                   }
                   temp.data.push(roudPart);
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
            if(this.paths[i].display && (this.firstPath===-1 || (i!=this.firstPath))){
                let totalPath = this.paths[i].data;
                this.drawSegment(totalPath, coord, ctx, this.paths[i].color, 1);
            }
        }
        
        let path = this.paths[this.firstPath];
        if(this.firstPath!=-1 && path.display){
            for(var j in path.data){
                let totalPath = path.data;
                this.drawSegment(totalPath, coord, ctx, path.color, 2);
            }
        }
    }
    //[10,5]
    drawSegment(totalPath, coord, ctx, color, thickness){
        for(var j in totalPath){
            let path = totalPath[j];
            ctx.beginPath();
            ctx.setLineDash([]);
            ctx.strokeStyle = color;
            ctx.lineWidth = Ctrl.View.Canvas.ratio*thickness*(Ctrl.View.zoomLevel +1);
            for(var j in path){
                let start = coord[path[j].start];
                let end = coord[path[j].end];
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
            }
            ctx.stroke();
        }
    }

    switchPathDisplay(id1, state){
        console.log(id1);
        for(var i in this.paths){
            console.log(this.paths[i].id);
            if(this.paths[i].id === id1){
                console.log("coucou");
                this.paths[i].display=state;
                return true;
            }
        }
        return false;
    }

    createPathHtml(color, totalTime, id){
        var temp =  "<div class='pathLine'>";
        temp += "<div id='colorSample' style='background-color:"+color+";'></div>";
        temp += "<p id='roundDes'>Durée : 20<br>Temps : 45</p>";
        temp += "<div class='delLineButtons'>";
        temp += "<button class='btn btn-warning viewButton' onclick='Ctrl.pathToForeground(this,"+id+");'><i class='fas fa-arrow-up'></i></button>";
        temp += "<button class='btn btn-warning viewButton' onclick='Ctrl.disableRound(this, "+id+")'><i class='fas fa-eye'></i></button>"
        temp += "</div></div>";
        return temp;
    }

    pathToForeground(id){
        this.firstPath = id;
    }

    setStop(node){
        this.stop = node;
    }
}