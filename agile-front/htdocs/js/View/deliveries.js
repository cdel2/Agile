class Deliveries{
    constructor(){
        this.warehouseDisp = {radius: Ctrl.View.Canvas.ratio*8, color: "red"};
        this.nodeDisp = {radius: Ctrl.View.Canvas.ratio*4, color: "blue"};
        this.userNodeDisp = {radius: Ctrl.View.Canvas.ratio*4, color: "green"};
        this.warehouse = null;
        this.delNodes = [];
        this.userDelNodes = [];
        this.nodeInfo = null;

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
            for(var el in del){
               object.delNodes.push(del[el]);
            }
            
            $.ajax({
                url: "http://localhost:8080/warehouse",
                type:"GET"
            }).done(function( del ) {
                object.warehouse = del;
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

    display(ctx, View){
        let node = this.warehouse;
        this.drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), this.warehouseDisp.radius, this.warehouseDisp.color, ctx);
        for(var i = 0; i < this.delNodes.length; i++){
            let node = this.delNodes[i];
            this.drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), this.nodeDisp.radius, this.nodeDisp.color, ctx);
        }
        for(var i = 0; i < this.userDelNodes.length; i++){
            let node = this.userDelNodes[i];
            this.drawCircle(View.norm(node.longitude, true), View.norm(node.latitude, false), this.userNodeDisp.radius, this.userNodeDisp.color, ctx);
        }
        if(this.nodeInfo!=null){
            let node = this.nodeInfo;
            let ratio = Ctrl.View.Canvas.ratio*0.7;
            let imgH = ratio*this.img.height;
            let imgW = ratio*this.img.width;
            ctx.globalAlpha = 0.8;
            ctx.drawImage(this.img, View.norm(node.longitude, true)-imgW/2,View.norm(node.latitude, false)-imgH, imgW, imgH);
            showMessage(true, "Durée : "+node.duration+"<br />Latitude : "+node.latitude+"<br />Longitude : "+node.longitude);
            ctx.beginPath();         

        }
    }

    drawCircle(X, Y, R, color, ctx){
        ctx.beginPath();
        ctx.arc(X, Y, R*(Ctrl.View.zoomLevel/2 +1), 0, 2 * Math.PI, false);
        ctx.fillStyle = color;
        ctx.strokeStyle = "black";
        ctx.lineWidth = 3;
        ctx.globalAlpha = 0.7;
        ctx.fill();
        //ctx.stroke();
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

    findBestNode(X,Y){
        let bestNode;
        var bestDistance = Number.MAX_VALUE;
        let tab = this.userDelNodes.concat(this.delNodes);
        tab.push(this.warehouse);
        console.log(tab);
        for (var prop in tab) {
            let node = tab[prop];
            let temp = distance(X,Y, Ctrl.View.norm(node.longitude, true), Ctrl.View.norm(node.latitude, false));
            if(temp<bestDistance){
                bestDistance = temp;
                bestNode = node;
            }
        }
        console.log(bestDistance);
        if(bestDistance>25){
            return null;
        }else{
            return bestNode;
        }
    }

    nodeInfos(node){
        this.nodeInfo = node;
        return;
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