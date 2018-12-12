class Round{
    constructor(){
        this.paths = new Object();
        this.userPaths;
        this.colors = ["green", "red", "purple", "blue", "lime", "aqua", "olive", "teal", "maroon", "#E74C3C", "#9B59B6", "#2980B9", "#3498DB", "#1ABC9C", "#27AE60", "#2ECC71", "#F1C4OF", "#F39C12"];
        this.firstPath = -1;
        this.stop=null;
    }

    load(action, num1, num2){
        let apiUrl = "http://localhost:8080/";
        switch(action){
            case "init":
                apiUrl+="calculation/start/"+num1;
                break;
            case "add":
                apiUrl+="delivery/add/"+num1+"/"+num2;
                break;
            case "remove1":
                apiUrl+="delivery/rmv/"+num1+"/true";
                break;
            case "remove2":
                apiUrl+="delivery/rmv/"+num1+"/false";
                break;
            case "undo":
                apiUrl+="undo";
                break;
            case "redo":
                apiUrl+="redo";
                break;
        }

        
        delete this.userPaths;
        this.userPaths;
        delete Ctrl.View.Deliveries.userDelNodes;
        this.userPaths = new Object();
        Ctrl.View.Deliveries.userDelNodes = new Object();
        let object = this;

        $("#pathMenu").html("");
        $("#loaderEl").show();
        let ajaxTime= new Date().getTime();
        $.ajax({
            url: apiUrl,
            type:"GET"
        }).done(function( data ) {
            console.log(data);
            var totalTime = new Date().getTime()-ajaxTime;
            alertBox("  "+totalTime/1000+"s");

            var endTimes = [];
            var cmpt = 0;
            for(var i in data){
                let color1 = object.colors[cmpt];
                for(var z = 0; z<data[i].listRound.length; z++){
                    let round = data[i].listRound[z].listPath;
                    let deliveryTemp = [];
                    let temp = {display:true, color:color1, data:[], departureTime:{hours:8, minutes:0, seconds:0}, arrivalTime:data[i].listRound[0].endTime};

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

                    if(z === 0){
                        $("#pathMenu").append(object.createPathHtml(color1, data[i].listRound[0].startTime, data[i].listRound[0].endTime, cmpt));
                        endTimes.push(data[i].listRound[0].endTime);
                        
                        object.paths[cmpt] = temp;
                        Ctrl.View.Deliveries.delNodes[cmpt] = deliveryTemp;
                    }else{
                        object.userPaths[cmpt] = temp;
                        Ctrl.View.Deliveries.userDelNodes[cmpt] = deliveryTemp;
                    }
                }
                cmpt++;
            }

            delete Ctrl.View.Deliveries.delNodes[-1];
            initSlider(endTimes);
            Ctrl.View.update();
            $("#loadRounds").html("Calculer itinéraires").addClass("btn-warning").removeClass("btn-danger");
            Ctrl.state = new CalcState();
        }).fail(function(textStatus){
            let status = textStatus.status;
            if(status === 422){
                alertBox("Erreur critique, resynchronisation des serveurs...");
                Ctrl.reset();
            }else if(status === 400){
                alertBox("Un problème est survenu, vous tentez peut-être de livrer dans une impasse ?");
            }else{
                alertBox("Erreur : Le serveur n'est pas joignable !");
                Ctrl.state = new DelState();
            }
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
            if(this.userPaths != undefined && this.userPaths[this.firstPath] != undefined){
                let totalPath2 = this.userPaths[this.firstPath].data;
                this.drawSegment(totalPath2, coord, ctx, path.color, 2, time);
            }
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