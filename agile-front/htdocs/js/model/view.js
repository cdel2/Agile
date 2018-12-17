class Viewer{
    constructor(){
        this.Map;
        this.Deliveries;
        this.Round;
        this.Canvas = {
            ratio:null,
            html:null,
            ctx:null,
            width:0,
            height:0,
            range:null
        };
        this.time = {hours:19, minutes:59, seconds:59};

        this.zoomLevel = 1;
        this.deltaX =0;
        this.deltaY=0;
    }

    /**
     * @desc Create a new map and load the data
     * @param string $mapFile - file to load
    */
    loadMap(mapFile){
        delete this.Deliveries;
        delete this.Round;

        this.Map = new Map();
        this.Map.load(mapFile);
    }

    /**
     * @desc Create a new Delivery object and load the data
     * @param string $mapFile - file to load
    */
    loadDeliveries(delFile){
        delete this.Round;

        this.Deliveries = new Deliveries();
        this.Deliveries.load(delFile);
    }

    /**
     * @desc Create and new Round object and load the data
     * @param string $mapFile - file to load
    */
    loadRound(num){
        this.Round = new Round();
        this.Round.load("init",num);
    }

    update(){
        this.Canvas.ctx.clearRect(0, 0, this.Canvas.width, this.Canvas.height);
        if(this.Map != null){
            this.Map.display(this.Canvas.ctx);
            if(this.Round != null){
                this.Round.display(this.Canvas.ctx, this.Map.coord, this.time);
            }
    
            if(this.Deliveries != null){
                this.Deliveries.display(this.Canvas.ctx, this, this.Map.coord, this.time);
            }
        }
    }

    setupCanvas(){
        let width = $("#mapCol").width();
        let height = $("#mapCol").height();
    
        let ratio = getRetinaRatio();
        let scaledWidth = width * ratio;
        let scaledHeight = height * ratio;
        this.Canvas.ratio = ratio;

        d3.select('#map')
            .attr('width', scaledWidth)
            .attr('id', "map")
            .attr('height', scaledHeight)
            .style('width', width + 'px')
            .style('height', height + 'px');
    
        var canvas = $("#map").get(0);
        this.Canvas.ctx = canvas.getContext("2d");
        this.Canvas.range = [0,canvas.height];
        this.Canvas.width = canvas.width;
        this.Canvas.height = canvas.height;
        this.Canvas.html = canvas;   
    }

    /**
     * @desc reset the zoom and pan back to their original values
    */
    reset(){
        this.zoomLevel = 1;
        this.deltaX = 0;
        this.deltaY = 0;
        this.update();
    }

    norm(value, state){
        if(state){
            var temp = (value-this.Map.longRange[0])/(this.Map.longRange[1]-this.Map.longRange[0]);
        }else{
            //vertical
            var temp = (value-this.Map.latRange[0])/(this.Map.latRange[1]-this.Map.latRange[0]);
        }
        var canvas = $("#map").get(0);
        
        temp = this.Canvas.range[0]+(this.Canvas.range[1]-this.Canvas.range[0])*temp;
        temp = this.zoomLevel*temp;
        if(state){
            //horizontal
            return temp+this.deltaX;
        }else{
            //vertical
            return canvas.height-temp+this.deltaY;
        }
    }

    zoom(rate){
        var temp = this.zoomLevel + rate;
        if(temp>=0.9 && temp<3){
            this.zoomLevel = temp;
            this.deltaY += (this.Canvas.height/2)*rate;
            this.deltaX -= (this.Canvas.width/2)*rate; 
            this.update();
        }
        
    }
}