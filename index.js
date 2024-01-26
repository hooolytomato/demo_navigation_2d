var grid = new Array();
var cols = 5;
var rows = 5;

function setup(){
    createCanvas(400,400);

    for (var i=0; i<cols; i++){
        grid[i] = new Array(rows);
    }

    for (var i=0; i<cols; i++){
        for (var j=0; j<rows; j++){ 
            grid[i] = new Array(rows)
        }
    }

}

function draw(){

}