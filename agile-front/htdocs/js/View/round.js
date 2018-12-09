class Round{
    constructor(){
        this.paths = new Object();
        this.userPaths;
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
                   let path = round[j].listSegment;
                   let roundPart = [];
                   for(var k in path){
                       var el = path[k];
                       roundPart.push({start:el.start.id, end:el.end.id, passageTime:el.passageTime});
                   }
                   let arrival = round[j].arrival;
                   temp.data.push(roundPart); //REVOIR
                   deliveryTemp.push({id:arrival.id, timeArrival:arrival.timeArrival, duration:arrival.duration, color: color1, idPath: cmpt});
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

    addDelivery(nodeId){
        delete this.userPaths;
        this.userPaths = new Object();
        let object = this;

        $("#loaderEl").show();
        $.ajax({
            url: "http://localhost:8080/add/delivery/"+nodeId,
            type:"GET"
        }).done(function( data ) {
            console.log(data);
            var cmpt = 0;
            for(var i in data){
                for(var j=1; j<data[i].listRound.length; j++){
                    let round = data[i].listRound[j].listPath;
                    console.log(round);
                    let color1 = object.colors[cmpt];
                    let deliveryTemp = [];
                    let temp = {display:true, color:color1, data:[], departureTime:{hours:8, minutes:0, seconds:0}, arrivalTime:data[i].listRound[0].endTime};
                    //$("#pathMenu").append(object.createPathHtml(color1, data[i].listRound[0].startTime, data[i].listRound[0].endTime, cmpt));
                    for(var j in round){
                        let path = round[j].listSegment;
                        let roundPart = [];
                        for(var k in path){
                            var el = path[k];
                            roundPart.push({start:el.start.id, end:el.end.id, passageTime:el.passageTime});
                        }
                        let arrival = round[j].arrival;
                        temp.data.push(roundPart); //REVOIR
                        deliveryTemp.push({id:arrival.id, timeArrival:arrival.timeArrival, duration:arrival.duration, color: color1, idPath: cmpt});
                    }
                    object.userPaths[cmpt] = temp;
                    Ctrl.View.Deliveries.userDelNodes[cmpt] = deliveryTemp;
                    
                }
                cmpt++;
            }
            Ctrl.View.update();
        }).fail(function(){
            console.log("Issue !");
            alertBox("Something wrong happened !");
            Ctrl.View.update();
            Ctrl.state = new MapState();
        }).always(function(){    
            $("#loaderEl").hide();
        });        
    }

    rmvDelivery(nodeId){
        
    }

    display(ctx, coord, time){
        ctx.globalAlpha = 1;
        for(var i in this.paths){
            if(this.paths[i].display && (this.firstPath===-1 || (i!=this.firstPath))){
                let totalPath = this.paths[i].data;
                this.drawSegment(totalPath, coord, ctx, this.paths[i].color, 1.5, time);
            }
        }

        for(var i in this.userPaths){
            if(this.userPaths[i].display && (this.firstPath===-1 || (i!=this.firstPath))){
                let totalPath = this.userPaths[i].data;
                this.drawSegment(totalPath, coord, ctx, this.userPaths[i].color, 1.5, time);
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
            ctx.strokeStyle = color;
            ctx.lineWidth = Ctrl.View.Canvas.ratio*thickness*(Ctrl.View.zoomLevel +1);
            for(var j in path){
                if(compareTime(path[j].passageTime, time) >= 0) present = false;
                if(present){
                    ctx.globalAlpha = 1; 
                    ctx.setLineDash([]);
                }else{ 
                    ctx.globalAlpha = 0.4;
                    ctx.setLineDash([10,5]);
                }
                ctx.beginPath();
                let start = coord[path[j].start];
                let end = coord[path[j].end];
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
                ctx.stroke();
            }
        }
    }

    switchPathDisplay(id, state){
        this.paths[id].display = state;
        if(this.userPaths[id] != undefined){
            this.userPaths[id].display = state;
        }
        return false;
    }

    createPathHtml(color, startTime, endTime, id){
        var temp =  "<div class='pathLine' id='pl"+id+"'>";
        temp += "<div id='colorSample' style='background-color:"+color+";'></div>";
        temp += "<p id='roundDes'>Depart : "+timeToString(startTime)+"<br>Arrivée : "+timeToString(endTime)+"</p>";
        temp += "<div class='delLineButtons'>";
        temp += "<button class='btn btn-warning viewButton' data-toggle='collapse' href='#cl"+id+"'><i class='fas fa-info-circle'></i></button>";
        temp += "<button class='btn btn-warning viewButton' onclick='Ctrl.pathToForeground("+id+");'><i class='fas fa-arrow-up'></i></button>";
        temp += "<button class='btn btn-warning viewButton' onclick='Ctrl.disableRound(this,"+id+")'><i class='fas fa-eye'></i></button>"
        temp += "</div></div>";
        temp += "<div class='collapse' id='cl"+id+"'><div class='card card-body' id='cl"+id+"t'></div></div>"
        return temp;
    }

    pathToForeground(id){
        this.firstPath = id;
    }
}