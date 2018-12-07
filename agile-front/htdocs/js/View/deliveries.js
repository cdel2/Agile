class Deliveries{
    constructor(){
        this.userNodeDisp = {radius: 4, color: "green"};
        this.warehouse = null;
        this.delNodes = new Object();
        this.userDelNodes = [];
        this.selectedDel = null;

        this.img = new Image();
        this.img.src = 'img/pin.png';
    }

    load(delFile1){
        let object = this;
        let delFile = delFile1;
        $("#loaderEl").show();
        $.ajax({
            url: "http://localhost:8080/deliveries/dl-"+delFile,
            type:"GET"
        }).done(function( del ) {
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
        }).fail(function(){
            console.log("Delivery file not loaded !");
            alertBox("Something wrong happened !");
            Ctrl.View.update();
            Ctrl.state = new MapState();
        }).always(function(){    
            $("#loaderEl").hide();
        });        
    }

    display(ctx, View, coord){
        for(var del in this.delNodes){
            let pathNodes = this.delNodes[del];
            for(var i = 0; i < pathNodes.length; i++){
                let node = coord[pathNodes[i].id];
                drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), 4, pathNodes[i].color, ctx);
            }
        }
        for(var i = 0; i < this.userDelNodes.length; i++){
            let node = this.userDelNodes[i];
            drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), this.userNodeDisp.radius, pathNodes[i].color, ctx);
        }

        //affichage warehouse
        let node = coord[this.warehouse.id];
        drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), 8, this.warehouse.color, ctx);        

        //afficha pin
        if(this.selectedDel!=null){
            let node = coord[this.selectedDel.id];
            let ratio = Ctrl.View.Canvas.ratio*0.7;
            let imgH = ratio*this.img.height;
            let imgW = ratio*this.img.width;
            ctx.globalAlpha = 0.8;
            ctx.drawImage(this.img, View.norm(node.longitude, true)-imgW/2,View.norm(node.latitude, false)-imgH, imgW, imgH);
            //showMessage(true, "Durée : "+node.duration+"<br />Latitude : "+node.latitude+"<br />Longitude : "+node.longitude);
            ctx.beginPath();         

        }
    }

    addUserNode(node){
        /*let good = true;
        for(var i=0; i<this.delNodes.length; i++){
            let node1 = this.delNodes[i];
            good=false;
        }
        for(var i=0; i<this.userDelNodes.length; i++){
            let node1 = this.userDelNodes[i];         
            if(this.comparePos(node, node1)){
                good=false;
            }   
        }*/
        if(true){
            this.userDelNodes.push(node);
        }else{
            alertBox("Point already on map !");
        }
    }

    findBestDelivery(X,Y){
        let bestDel = null;
        var bestDistance = Number.MAX_VALUE;
        for (var i in this.delNodes) {
            let path = this.delNodes[i];
            for(var j in path){
                let del = path[j];
                let node = Ctrl.View.Map.coord[path[j].id];
                let temp = distance(X,Y, Ctrl.View.norm(node.longitude, true), Ctrl.View.norm(node.latitude, false));
                if(temp<bestDistance){
                    bestDistance = temp;
                    bestDel = del;
                }
            }
        }
        if(bestDistance>25){
            return null;
        }else{
            return bestDel;
        }
    }

    selectDelivery(node){
        if(node != null){
            let time = node.timeArrival;
            let sliderTime = timeToSlider(time);
            $("#sliderInit").slider('setValue', sliderTime);
            Ctrl.changeTime(time);
        }else{

        }
        if(node === null){
            $(".collapse").collapse("hide");
            this.selectedDel = null;
            return;
        }
        if(this.selectedDel != null && (node.idPath === this.selectedDel.idPath)){
            $("#cl"+node.idPath+"t").html(this.collapseFiller(node));
        }else{
            Ctrl.pathToForeground(node.idPath);
            $(".collapse").collapse("hide");
            $("#cl"+node.idPath+"t").html(this.collapseFiller(node));
            $("#cl"+node.idPath).collapse('show');
        }
        this.selectedDel = node;
    }

    collapseFiller(node){
        let past = true;
        let tmp = "";
        let pathDel = this.delNodes[node.idPath];
        for(var j in pathDel){
                let del = pathDel[j];
                if(node === del){
                    tmp+="<b>"+j+" - Temps livraison : "+ del.duration + "s, Livraison en cours ("+timeToString(del.timeArrival)+")...<br/></b>";
                    past=false;
                }else if(past){
                    tmp+="<i>"+j+" - Temps livraison : "+ del.duration + "s, Livré à "+timeToString(del.timeArrival)+"<br/></i>";
                }else{
                    tmp+=j+" - Temps livraison : "+ del.duration + "s, Sera livré à "+timeToString(del.timeArrival)+"<br/>";
                }
        }
        return tmp;
    }

    removeNode(node){
        for(var i=0; i<this.delNodes.length; i++){
            let node1 = this.delNodes[i];
            if(node.lat === node1.lat && node.long === node1.long){
                this.delNodes.splice(i,1);
                return;
            }
        }
        for(var i=0; i<this.userDelNodes.length; i++){
            let node1 = this.userDelNodes[i];         
            if(node.lat === node1.lat && node.long === node1.long){
                this.userDelNodes.splice(i,1);
                return;
            }   
        }
        alertBox("No point found !");
    }
};