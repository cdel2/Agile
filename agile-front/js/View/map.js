class Map{
    constructor() {
        this.coord = new Object();
        this.graph = null;
        this.latRange = 0;
        this.longRange = 0;
    }
    
    load(mapFile1){
        let object = this;
        let mapFile = mapFile1;
        $.ajax({
            url: "http://localhost:8080/map/"+mapFile1,
            type:"GET"
        }).done(function( map ) {
            let latRange = [Number.MAX_VALUE, Number.MIN_VALUE];
            let longRange = [Number.MAX_VALUE, Number.MIN_VALUE];

            let graph = map.graph
            object.graph = graph;
            for (var seg in graph) {
                object.coord[seg]=graph[seg][0].start;
            };

            for (var seg in graph) {
                if(object.coord[graph[seg][0].end.id] === undefined){
                    object.coord[graph[seg][0].end.id] = graph[seg][0].end; 
                }
            };

            for (var node in object.coord) {
                let lat = object.coord[node].latitude;
                let long = object.coord[node].longitude;
                if(latRange[0]>lat) latRange[0]=lat;
                if(latRange[1]<lat) latRange[1]=lat;
                if(longRange[0]>long) longRange[0]=long;
                if(longRange[1]<long) longRange[1] =long;
            }

            object.latRange = latRange;
            object.longRange = longRange;
            console.log(this.coord);
            Ctrl.View.update();
        }).fail(function(){
            console.log("Map file not loaded !");
            alertBox("Something wrong happened !");
        });  
    }

    display(ctx){
        ctx.beginPath();
        for(var segListId in this.graph){
            let segList = this.graph[segListId];
            for(var seg in segList){
                let start = segList[seg].start;
                let end = segList[seg].end;


                ctx.strokeStyle = "dark";
                ctx.lineWidth = 3;
                ctx.moveTo(Ctrl.View.norm(start.longitude, true),Ctrl.View.norm(start.latitude, false));
                ctx.lineTo(Ctrl.View.norm(end.longitude, true),Ctrl.View.norm(end.latitude, false));
            }
        }
        ctx.stroke();
    }

    highlightNode(node, ctx){
        Ctrl.View.update();
        this.drawCircle(Ctrl.View.norm(node.longitude, true), Ctrl.View.norm(node.latitude, false), 15, "yellow", ctx);
    }

    findBestNode(X,Y){
        let bestNode;
        let bestDistance = Number.MAX_VALUE;
        for (var prop in Ctrl.View.Map.coord) {
            let node = this.coord[prop];
            let temp = this.distance(X,Y, Ctrl.View.norm(node.longitude, true), Ctrl.View.norm(node.latitude, false));
            if(temp<bestDistance){
                bestDistance = temp;
                bestNode = this.coord[prop];
            }
        }
        return bestNode;
    }

    distance(Xa, Ya, Xb, Yb){
        let tempLat = Yb-Ya;
        let tempLong = Xb-Xa;
        let temp = tempLat*tempLat + tempLong*tempLong;
        return Math.sqrt(temp);
    }

    drawCircle(X, Y, R, color, ctx){
        ctx.beginPath();
        ctx.arc(X, Y, R, 0, 2 * Math.PI, false);
        ctx.fillStyle = color;
        ctx.globalAlpha = 0.7;
        ctx.fill();
        ctx.stroke();
    }
}