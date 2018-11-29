class Viewer{
    constructor(){
        this.Map = null;
        this.Deliveries = null;
        this.Round = null;
        this.Canvas = {
            ratio:null,
            html:null,
            ctx:null,
            width:0,
            height:0,
            range:null
        };
        this.zoomLevel = 1;

        this.deltaX =0;
        this.deltaY=0;
        this.lastX; this.lastY;
        this.dragged = false;
        this.clicked = false;
    }

    loadMap(mapFile){
        this.panSetup();
        this.zoomSetup();
        this.Map = new Map();
        this.Map.load(mapFile);
    }

    loadDeliveries(delFile){
        this.Deliveries = new Deliveries();
        this.Deliveries.load(delFile);
    }

    loadRound(){
        this.Round = new Round();
        this.Round.load(this.Map.coord);
    }

    update(){
        this.Canvas.ctx.clearRect(0, 0, this.Canvas.width, this.Canvas.height);
        this.Map.display(this.Canvas.ctx);
        if(this.Deliveries != null){
            this.Deliveries.display(this.Canvas.ctx, this);
        }
        if(this.Round != null){
            this.Round.display(this.Canvas.ctx, this);
        }
    }

    setupCanvas(){
        let width = $("#mapCol").width();
        let height = $("#mapCol").height();
    
        let ratio = this.getRetinaRatio()
        let scaledWidth = width * ratio
        let scaledHeight = height * ratio
        this.Canvas.ratio = ratio;

        d3.select('#map')
            .attr('width', scaledWidth)
            .attr('id', "map")
            .attr('height', scaledHeight)
            .style('width', width + 'px')
            .style('height', height + 'px')
    
        var canvas = $("#map").get(0);
        this.Canvas.ctx = canvas.getContext("2d");
        this.Canvas.range = [0,canvas.height];
        this.Canvas.width = canvas.width;
        this.Canvas.height = canvas.height;
        this.Canvas.html = canvas;   
    }

    reset(){
        this.zoomLevel = 1;
        this.deltaX = 0;
        this.deltaY = 0;
        this.update();
    }

    getRetinaRatio() {
        var devicePixelRatio = window.devicePixelRatio || 1
        var c = document.createElement('canvas').getContext('2d')
        var backingStoreRatio = [
            c.webkitBackingStorePixelRatio,
            c.mozBackingStorePixelRatio,
            c.msBackingStorePixelRatio,
            c.oBackingStorePixelRatio,
            c.backingStorePixelRatio,
            1
        ].reduce(function(a, b) { return a || b })
    
        return devicePixelRatio / backingStoreRatio
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
        if(temp>0.8 && temp<3){
            this.zoomLevel = temp;
            this.deltaY += (this.Canvas.height/2)*rate;
            this.deltaX -= (this.Canvas.width/2)*rate; 
            this.update();
        }
    }

    panSetup(){
        this.Canvas.html.addEventListener('mousedown', function(evt){
            Ctrl.state.handleMouseDown(evt);
        },false);
        this.Canvas.html.addEventListener('mousemove', function(evt){
            Ctrl.state.handleMouseMove(evt);
        },false);
        //window.addEventListener('mouseup', Ctrl.state.handleMouseUp,false);
        this.Canvas.html.addEventListener('mouseup', function(evt){
            Ctrl.state.handleMouseUp(evt);
        },false);
    }

    zoomSetup(){
        this.Canvas.html.addEventListener('DOMMouseScroll',function(evt){
            Ctrl.state.handleScroll(evt);
        },false);
        this.Canvas.html.addEventListener('mousewheel',function(evt){
            Ctrl.state.handleScroll(evt);
        },false);
    }
}