class Round{
    constructor(){
        this.paths = [];
    }

    load(Coord1){
        let object = this;
        let Coord = Coord1;
        $.ajax({
            url: "deliveries/round.xml",
            type:"GET"
        }).done(function( xmlDoc ) {
            var del = xmlDoc.getElementsByTagName("round")[0].getElementsByTagName("livreur");
            for(var j = 0; j < del.length; j++){
                let round = del[j].childNodes;
                let path = [];
                for(var i = 0; i < round.length; i++){
                    let el = round[i];
                    //console.log(el);
                    if(el.tagName === "entrepot" || el.tagName === "livraison"){
                    path.push(Coord[el.getAttribute("adresse")]);
                    }
                }
                object.paths.push(path);
            }
            Ctrl.View.update();
        }).fail(function(textStatus, errorThrown){
            console.log("Round file not loaded !");
            console.log(textStatus);
        });
    }

    display(ctx, view){
        
        for(var i = 0; i < this.paths.length; i++){
            ctx.beginPath();
            let path = this.paths[i];
            let color = this.colorGen();
            for(var j = 0; j<path.length-1; j++){
                //console.log(path);
                let start = path[j];
                let end = path[j+1];
                ctx.strokeStyle = color;
                ctx.lineWidth = 5;
                ctx.moveTo(view.norm(start.long, true),view.norm(start.lat, false));
                ctx.lineTo(view.norm(end.long, true),view.norm(end.lat, false));
            }
            ctx.stroke();
        }
        
    }

    colorGen(){
        var temp = Math.floor(3*Math.random());
        switch(temp){
            case 0:
                return "green";
            case 1:
                return "yellow";
            case 2:
                return "purple";
            default:
                return "blue";
        }
    }
}