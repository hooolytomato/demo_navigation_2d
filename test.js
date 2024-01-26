var canvas = document.getElementById("map2dCanvas");
var ctx = canvas.getContext("2d");

var grid = new Array(cols);
var cols = 5;
var rows = 5;
var start;
var end;
var openList = [];
var closeList = [];
var nodeW = canvas.width/cols;
var nodeH = canvas.height/rows;

const floor5_img = new Image();
floor5_img.src = "5floor_done.png";



const floor6_img = new Image();
floor6_img.src = "6floor.png";

// remove the element from the openlist
function removeFromArray(arr, elt){
  for (var i=arr.length-1; i>=0; i--){
    if(arr[i] == elt){
      arr.splice(i,1);
    }
  }
}

function heuristic(a,b){
  var d = Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - b.y), 2));
  return d;
}

function Node(i,j){
  this.f = 0;
  this.g = 0;
  this.h = 0;
  this.x = i;
  this.y = j;
  this.neighbors = [];

  // to show the node
  this.show = function(color) {
    ctx.fillRect(this.x*nodeW, this.y*nodeH,nodeW,nodeH);
    ctx.fillStyle = color;
    ctx.strokeRect(this.x*nodeW, this.y*nodeH,nodeW,nodeH);
  }

  // search the negihbors
  this.addNeighbors = function(grid){
    if (i<cols-1){
      this.neighbors.push(grid[this.x+1][this.y]) //left neigbors      
    }
    if(i>0){
      this.neighbors.push(grid[this.x-1][this.y]) //right neigbors
    }
    if (j<rows-1){
      this.neighbors.push(grid[this.x][this.y+1]) //up neigbors
    }
    if(j>0){
      this.neighbors.push(grid[this.x][this.y-1]) //down neigbors
    }
  }
}

function setup(){
  for (var i=0; i<cols; i++){
    grid[i] = new Array(rows);
  }
  
  for (var i=0; i<cols; i++){
    for (var j=0; j<rows; j++){
      grid[i][j] = new Node(i,j);
    }
  }

  for (var i=0; i<cols; i++){
    for (var j=0; j<rows; j++){
      grid[i][j].addNeighbors(grid);
    }
  }

  // start = grid[41][5];
  // end = grid[55][59];
  start = grid[0][0];
  end = grid[cols-1][rows-1];

  openList.push(start);

  floor5_img.onload = () =>{

    ctx.drawImage(floor5_img, 10, 0, canvas.width, canvas.height);
    draw();

  }
}

function draw(){

  if (openList.length > 0){
    var lowest = 0;
    for (var i=1; i<openList.length; i++){
      if(openList[i].f<openList[lowest].f){
        lowest = i;
      }
    }

    var current = openList[lowest];
    if (openList[lowest] == end){
      console.log("Done");
    }

    removeFromArray(openList, current);
    closeList.push(current)

    
          }
        }else {
          neighbor.g = tempG;
          openList.push(neighbor);                       
        }

        neighbor.h = heuristic(neighbor,end);
        neighbor.f = neighbor.g + neighbor.h;
      }

      
    }
    
  }else{
    // stop warning
  }

  ctx.fillStyle = "rgba(255,255,255,0.1)";
  ctx.fillRect(0, 0, canvas.width, canvas.height);

  for (var i=0; i<cols; i++){ // may improve
    for (var j=0; j<rows; j++){
      grid[i][j].show("rgba(255,255,255,0.01)");
    }
  }
  
 

  for (var i=0; i<openList.length; i++){
    openList[i].show("rgb(255,255,0)");
  }

  for (var i=0; i<closeList.length; i++){
    closeList[i].show("rgb(0,0,255)");
  }

  // grid[41][5].show("green");
  // grid[55][59].show("red")
  
  // // end point
  // grid[55][0].show("black")
  // grid[55][5].show("black")
  // grid[55][51].show("black")
}

setup();