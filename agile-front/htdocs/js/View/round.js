class Round{
    constructor(){
        this.paths = new Object();
        this.colors = ["green", "red", "purple", "blue", "lime", "aqua", "fuschia", "yellow", "olive", "teal", "maroon", "#E74C3C", "#9B59B6", "#2980B9", "#3498DB", "#1ABC9C", "#27AE60", "#2ECC71", "#F1C4OF", "#F39C12"];
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
            $("#execTime").text("  "+totalTime/1000+"s");

            var endTimes = [];
            var cmpt = 0;
            for(var i in data){
                let round = data[i].listRound[0].listPath;
                let color1 = object.colors[cmpt];
                let deliveryTemp = [];
                let temp = {display:true, color:color1, data:[], departureTime:{hours:8, minutes:0, seconds:0}, arrivalTime:data[i].listRound[0].endTime};
                $("#pathMenu").append(object.createPathHtml(color1, data[i].listRound[0].startTime, data[i].listRound[0].endTime, cmpt));
                endTimes.push(data[i].listRound[0].endTime);
                for(var j in round){
                   let path = round[j].path;
                   let roudPart = [];
                   for(var k in path){
                       var el = path[k];
                       roudPart.push({start:el.start.id, end:el.end.id});
                   }
                   let arrival = round[j].arrival;
                   temp.data.push({roundSeg : roudPart, arrival:{id:arrival.id, timeArrival:arrival.timeArrival}}); //REVOIR
                   deliveryTemp.push({id:arrival.id, timeArrival:arrival.timeArrival, duration:arrival.duration, color: color1});
                }
                object.paths[cmpt] = temp;
                Ctrl.View.Deliveries.delNodes[cmpt] = deliveryTemp;
                cmpt++;
            }

            delete Ctrl.View.Deliveries.delNodes[-1];
            initSlider(endTimes);
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

    display(ctx, coord, time){
        ctx.globalAlpha = 1;
        for(var i in this.paths){
            if(this.paths[i].display && (this.firstPath===-1 || (i!=this.firstPath))){
                let totalPath = this.paths[i].data;
                this.drawSegment(totalPath, coord, ctx, this.paths[i].color, 1.5, time);
            }
        }
        
        let path = this.paths[this.firstPath];
        if(this.firstPath!=-1 && path.display){
            let totalPath = path.data;
            this.drawSegment(totalPath, coord, ctx, path.color, 2, time);
        }
    }
    //[10,5]
    drawSegment(totalPath, coord, ctx, color, thickness, time){
        let present = true; //true if we are before time
        for(var j in totalPath){
            let path = totalPath[j];
            if(compareTime(path.arrival.timeArrival, time) >= 0) present = false;
            if(present){
                ctx.globalAlpha = 1; 
                ctx.setLineDash([]);
            }else{ 
                ctx.globalAlpha = 0.4;
                ctx.setLineDash([10,5]);
            }
            ctx.strokeStyle = color;
            ctx.lineWidth = Ctrl.View.Canvas.ratio*thickness*(Ctrl.View.zoomLevel +1);
            ctx.beginPath();
            for(var j in path.roundSeg){
                let start = coord[path.roundSeg[j].start];
                let end = coord[path.roundSeg[j].end];
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
            }
            ctx.stroke();
        }
    }

    switchPathDisplay(id, state){
        this.paths[id].display = state;
        return false;
    }

    createPathHtml(color, startTime, endTime, id){
        console.log(startTime);
        console.log(endTime);
        var temp =  "<div class='pathLine'>";
        temp += "<div id='colorSample' style='background-color:"+color+";'></div>";
        temp += "<p id='roundDes'>Depart : "+timeToString(startTime)+"<br>Arrivée : "+timeToString(endTime)+"</p>";
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