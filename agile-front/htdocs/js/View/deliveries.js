class Deliveries{
    constructor(){
        this.warehouseDisp = {radius: 15, color: "red"};
        this.nodeDisp = {radius: 10, color: "blue"};
        this.userNodeDisp = {radius: 10, color: "green"};
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
        $.ajax({
            url: "http://localhost:8080/deliveries/dl-"+delFile,
            type:"GET"
        }).done(function( del ) {
            object.warehouse = del.warehouse;
            for(var el in del.listDelivery){
               object.delNodes.push(del.listDelivery[el]);
            }
            Ctrl.View.update();
        }).fail(function(){
            console.log("Delivery file not loaded !");
            alertBox("Something wrong happened !");
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
            console.log(node);

            ctx.globalAlpha = 0.8;
            ctx.drawImage(this.img, View.norm(node.longitude, true)-47/2,View.norm(node.latitude, false)-75);
            showMessage(true, "Duration : "+node.duration+"<br />Latitude : "+node.latitude+"<br />Longitude : "+node.longitude);
            ctx.beginPath();         

        }
    }

    drawCircle(X, Y, R, color, ctx){
        ctx.beginPath();
        ctx.arc(X, Y, R, 0, 2 * Math.PI, false);
        ctx.fillStyle = color;
        ctx.globalAlpha = 0.7;
        ctx.fill();
        ctx.stroke();
    }

    addUserNode(node){
        let good = true;
        for(var i=0; i<this.delNodes.length; i++){
            let node1 = this.delNodes[i];
            if(this.comparePos(node, node1)){
                good=false;
            }
        }
        for(var i=0; i<this.userDelNodes.length; i++){
            let node1 = this.userDelNodes[i];         
            if(this.comparePos(node, node1)){
                good=false;
            }   
        }
        if(good){
            this.userDelNodes.push(node);
        }else{
            alertBox("Point already on map !");
        }
    }

    nodeInfos(node){
        if(this.comparePos(this.warehouse, node)){
            if(this.nodeInfo != null && this.comparePos(this.nodeInfo, this.warehouse)){
                this.nodeInfo=null;
                showMessage(false);
                return;
            }else{
                this.nodeInfo=this.warehouse;
                return;
            }
        }
        for(var i=0; i<this.delNodes.length; i++){
            let node1 = this.delNodes[i];
            if(this.comparePos(node, node1)){
                if(this.nodeInfo != null && this.comparePos(this.nodeInfo, node1)){
                    this.nodeInfo=null;
                    showMessage(false);
                }else{
                    this.nodeInfo=node1;
                }
            }
        }
    }

    comparePos(node, node1){
        if(node.longitude === node1.longitude && node.latitude === node1.latitude){
            return true;
        }
        return false;
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