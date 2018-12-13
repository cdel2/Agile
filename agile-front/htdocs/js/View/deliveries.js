/**
 * Coordinates the delivery data
 * It load, diaplay and toggle the interface
 */
class Deliveries{
    constructor(){
        this.userNodeDisp = {radius: 4, color: "green"};
        this.warehouse = null;
        this.delNodes = new Object();
        this.userDelNodes = new Object();
        this.selectedDel = null;

        this.imgPin = new Image();
        this.imgPin.src = 'img/pin.png';
        this.imgHome = new Image();
        this.imgHome.src = 'img/home.png';
    }

    /**
     * @desc load the deliveries from the backend
     * @param string $msg - file to load
     * @return nothing
    */
    load(delFile1){
        let object = this;
        let delFile = delFile1;
        $("#loaderEl").show();
        $.ajax({
            url: "http://localhost:8080/deliveries/dl-"+delFile,
            type:"GET"
        }).done(function(del) {
            console.log(del);
            var tmp = [];
            for(var el in del){
               tmp.push({id:del[el].id, duration:del[el].duration, color:"blue"});
            }
            object.delNodes[-1] = tmp;
            
            $.ajax({
                url: "http://localhost:8080/warehouse",
                type:"GET"
            }).done(function( del ) {
                object.warehouse = {id:del.id, duration:null, color:"red"};
                Ctrl.state = new DelState();
                Ctrl.View.update();
            }).fail(function(){
                alertBox("Warehouse data not loaded !");
                Ctrl.View.update();
                Ctrl.state = new MapState();
            }).always(function(){    
                $("#loaderEl").hide();
            });
        }).fail(function(textStatus){
            let status = textStatus.status;
            if(status === 422){
                alertBox("Erreur critique, resynchronisation des serveurs...");
                Ctrl.reset();
            }else{
                alertBox("Erreur : Le serveur n'est pas joignable !");
                Ctrl.View.update();
                Ctrl.state = new MapState();
            }
        }).always(function(){    
            $("#loaderEl").hide();
        });  
    }

    /**
     * @desc display the deliveries (nodes + userNodes + wharehouse + pin + update the deliveries description)
     * @param context $ctx - context of the canvas to write in
     * @param context $time - current time
     * @return nothing
    */
    display(ctx, time){
        let View = Ctrl.View;
        let coord = Ctrl.View.Map.coord;

        for(var del in this.delNodes){
            let pathNodes = this.delNodes[del];
            for(var i = 0; i < pathNodes.length-1; i++){
                let node = coord[pathNodes[i].id];
                drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), 4, pathNodes[i].color, ctx);
            }
        }

        for(var del in this.userDelNodes){
            let pathNodes = this.userDelNodes[del];
            for(var i = 0; i < pathNodes.length-1; i++){
                let node = coord[pathNodes[i].id];
                drawSquare(View.norm(node.longitude, true), View.norm(node.latitude, false), 8, pathNodes[i].color, ctx);
            }
        }

        //affichage warehouse
        let node = coord[this.warehouse.id];
        ctx.globalAlpha = 1;
        let ratio = Ctrl.View.Canvas.ratio*0.8;
        //drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), 8, "red", ctx);      
        let imgH = ratio*this.imgHome.height;
        let imgW = ratio*this.imgHome.width;  
        ctx.drawImage(this.imgHome, View.norm(node.longitude, true)-imgW/2,View.norm(node.latitude, false)-imgH/2, imgW, imgH);
        ctx.beginPath(); 

        //afficha pin
        if(this.selectedDel!=null){
            let node = coord[this.selectedDel.id];
            let ratio = Ctrl.View.Canvas.ratio*0.7;
            let imgH = ratio*this.imgPin.height;
            let imgW = ratio*this.imgPin.width;
            ctx.globalAlpha = 0.8;
            ctx.drawImage(this.imgPin, View.norm(node.longitude, true)-imgW/2,View.norm(node.latitude, false)-imgH, imgW, imgH);
            ctx.beginPath();         

        }

        if(this.delNodes[-1] === undefined){
            this.updatePathsInfo(time);
        }
    }

    /**
     * @desc updates the path delivery infos in the right panel
     * @param time $time - current time
     * @return nothing
    */
    updatePathsInfo(time){
        for(var j in this.delNodes){
            $("#cl"+j+"t").html(this.collapseFiller(this.delNodes[j], time));
        }
        for(var j in this.userDelNodes){
            $("#cl"+j+"t").append(this.collapseFiller(this.userDelNodes[j], time));
        }
    }

    /**
     * @desc Create a description of the path deliveries corresponding to the id according to the time given
     * @param int $id - id of the path deliveries to describe
     * @param time $time - current time
     * @return the description of the path in html
    */
    collapseFiller(pathDel, time){
        let past = true;
        let tmp = "";
        for(var j = 0; j<pathDel.length; j++){
                let del = pathDel[j];
                tmp+="<span class='selectable' onclick='Ctrl.View.Deliveries.selectDelivery("+del.id+")'>";
                if(this.selectedDel!=null && this.selectedDel === del){
                    tmp+="<b>"+j+" - Livraison à : "+timeToString(del.timeArrival)+ ", durée : "+ secondsToMS(del.duration) + " (sélectionnée)</b>";
                }else{
                    if(del.id === this.warehouse.id){
                        tmp+="<i class='fas fa-home'></i> - Entrepot, Arrivée à "+timeToString(del.timeArrival)+"<br/>";
                        break;
                    }
                    if(past && compareTime(del.timeArrival,time)>=0){
                        past=false;
                    }
                    if(past){
                        tmp+="<i>"+j+" - Livraison à : "+timeToString(del.timeArrival)+ ", durée : "+ secondsToMS(del.duration) + "</i>";
                    }else{
                        tmp+=j+" - Livraison à : "+timeToString(del.timeArrival)+ ", durée : "+ secondsToMS(del.duration);
                    }
                }
                tmp+="</span>"
        }
        return tmp;
    }

    /**
     * @desc find the closest delivery node to the coordinates in param
     * @param int $X - X coordinate
     * @param int $Y - Y coordinate
     * @return returns null if no close del node was found, return the node otherwise
    */
    findBestDelivery(X,Y){
        let bestDelId = null;
        var bestDistance = Number.MAX_VALUE;
        for (var i in this.delNodes) {
            let path = this.delNodes[i];
            if(this.userDelNodes[i] != undefined){
                path = path.concat(this.userDelNodes[i]);
            }
            for(var j in path){
                let node = Ctrl.View.Map.coord[path[j].id];
                let temp = distance(X,Y, Ctrl.View.norm(node.longitude, true), Ctrl.View.norm(node.latitude, false));
                if(node.id != this.warehouse.id && temp<bestDistance){
                    bestDistance = temp;
                    bestDelId = node.id;
                }
            }
        }
        console.log(bestDelId);
        if(bestDistance>25){
            return null;
        }else{
            return bestDelId;
        }
    }

    /**
     * @desc toggles the right infos in the right section according to the node in param
     * @param context $nodeId - nodeId ?delivery to wich we need to display the infos
     * @return nothing
    */
    selectDelivery(nodeId){
        //lets check if it is really a delivery
        var node = undefined;
        for(var k in this.delNodes){
            let delPath = this.delNodes[k];
            for(var i = 0; i<delPath.length-1; i++){
                if(delPath[i].id === nodeId) node = delPath[i];
            }
        }
        for(var k in this.userDelNodes){
            let delPath = this.userDelNodes[k];
            for(var i = 0; i<delPath.length-1; i++){
                if(delPath[i].id === nodeId) node = delPath[i];
            }
        }
        if(node === undefined) return;

        //lets toggle the menus
        if(node != null){
            let time = node.timeArrival;
            let sliderTime = timeToSlider(time);
            $("#sliderInit").slider('setValue', sliderTime);
            Ctrl.changeTime(time);
        }

        if(node === null){
            $(".collapse").collapse("hide");
            this.selectedDel = null;
            return;
        }

        if(this.selectedDel != null && (node.idPath != this.selectedDel.idPath)){
            Ctrl.pathToForeground(node.idPath);
            //$(".collapse").collapse("hide");
            $("#cl"+node.idPath).collapse('show');
        }else{
            $("#cl"+node.idPath).collapse('show');
        }

        this.selectedDel = node;
        Ctrl.View.update();
    }

    /**
     * @desc add a delivery
     * @param nodeId $node - node' id to remove
     * @return true if the node was added succesfully, false otherwise
    */
   addUserDelivery(nodeId, duration){
        let good = true;
        for(var i in this.delNodes){
            let path = this.delNodes[i];
            if(this.userDelNodes[i] != undefined){
                path = path.concat(this.userDelNodes[i]);
            }
            for(var j in path){
                if(nodeId === path[j].id) good=false;
            }
        }

        if(good){
            Ctrl.View.Round.load("add",nodeId, duration);
            return true;
        }else{
            alertBox("Point already on map !");
            return false;
        }
    }

    /**
     * @desc remove a delivery 
     * @param nodeId $node - node' id to remove
     * @return true if the node was removed succesfully, null otherwise
    */
    rmvUserDelivery(nodeId){
        let good = false;
        for(var i in this.delNodes){
            let path = this.delNodes[i];
            if(this.userDelNodes[i] != undefined){
                path = path.concat(this.userDelNodes[i]);
            }
            for(var j in path){
                if(nodeId === path[j].id){
                    good=true;
                } 
            }
        }

        if(good){
            $('#changePath').attr("onclick", "Ctrl.View.Round.load('remove1',"+nodeId+");");
            $('#keepPath').attr("onclick", "Ctrl.View.Round.load('remove2',"+nodeId+");");
            $('#modalRemove').modal('show');
            return true;
        }else{
            alertBox("No point found !");
            return false;
        }
    }
};