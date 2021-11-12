var player1 = null;
var player2 = null;
let blueTurn = true;
let p1location = null;
let p2location = null;
window.onload = init;

function roleDice(){
    let index = Math.floor(Math.random()*6 )+1;
        switch (index) {
            case 1:
                document.getElementById('dice').src = "../img/dice1.png";
                break;
            case 2:
                document.getElementById('dice').src = "../img/dice2.png";
                break;
            case 3:
                document.getElementById('dice').src = "../img/dice3.png";
                break;
            case 4:
                document.getElementById('dice').src = "../img/dice4.png";
                break;
            case 5:
                document.getElementById('dice').src = "../img/dice5.png";
                break;
            case 6:
                document.getElementById('dice').src = "../img/dice6.png";
                break;
            default:
                break;
        }
        return index;
}

function init(){
    player1 = document.getElementById("player1");
    player1.style.left = -80 + "px";
    player1.style.bottom = 0 + "px";
    p1location = document.getElementById('player1Location');
    p2location = document.getElementById('player2Location');
    p1location.style.backgroundColor = "white";
    p1location.style.outlineStyle = "solid";
    player2 = document.getElementById("player2");
    player2.style.left = -80 + "px";
    player2.style.bottom = 0 + "px";
    let audio = document.getElementById('audio');
    audio.load();
}

// Returns a Promise that resolves after "ms" Milliseconds
const timer = ms => new Promise(res => setTimeout(res, ms))

function playerMove(){
    if(blueTurn){
        blueMove();
        blueTurn = false;
    }
    else{
        redMove();
        blueTurn = true;
    }
}

async function blueMove(){
    let diceNum = roleDice();
    document.getElementById("dice").style.pointerEvents = "none";
    for(let i=1;i<=diceNum;i++){
        player1.style.left = parseInt(player1.style.left) + 80 + "px";
        if(player1.style.left == 720 +"px" && player1.style.bottom == 720 +"px"){
            player1.style.left = 720 +"px"; 
            player1.style.bottom = 720 +"px";
            p1location.innerHTML = 'Player 1: 100';
            audio.src = "../audio/win.mp3"
            audio.play();
            await timer(200);
            let confirmAction = confirm("Player 1 Win while Player 2 still in "+playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom))+",Restart?");
            if(confirmAction){
                document.getElementById('dice').src = "../img/dice0.png";
                player1.style.left = -80 + "px";
                player1.style.bottom = 0 + "px";
                player2.style.left = -80 + "px";
                player2.style.bottom = 0 + "px";
                p1location.innerHTML = 'Player 1: 0';
                p2location.innerHTML = 'Player 2: 0';
                break;
            }
            else{
                window.open("../","_self")
            }
        }
        if(player1.style.left == 800 + "px"){
            player1.style.left = 0 + "px";
            player1.style.bottom = parseInt(player1.style.bottom) + 80 + "px";
        }
        p1location.innerHTML = 'Player 1: ' + playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom));
        await timer(200);
    }
    laddersCases();
    snakesCases();
    p1location.innerHTML = 'Player 1: ' + playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom));
    p1location.style.backgroundColor = "transparent";
    p1location.style.outlineStyle = "none";
    p2location.style.backgroundColor = "white";
    p2location.style.outlineStyle = "solid";
    document.getElementById("dice").style.pointerEvents = "auto";
}

async function redMove(){
    let diceNum = roleDice();
    document.getElementById("dice").style.pointerEvents = "none";
    for(let i=1;i<=diceNum;i++){
        player2.style.left = parseInt(player2.style.left) + 80 + "px";
        if(player2.style.left == 720 +"px" && player2.style.bottom == 720 +"px"){
            player2.style.left = 720 +"px"; 
            player2.style.bottom = 720 +"px";
            p2location.innerHTML = 'Player 2: 100';
            audio.src = "../audio/win.mp3"
            audio.play();
            await timer(200);
            let confirmAction = confirm("Player 2 Win while Player 1 still in "+playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom))+",Restart?");
            if(confirmAction){
                document.getElementById('dice').src = "../img/dice0.png";
                player1.style.left = -80 + "px";
                player1.style.bottom = 0 + "px";
                player2.style.left = -80 + "px";
                player2.style.bottom = 0 + "px";
                p1location.innerHTML = 'Player 1: 0';
                p2location.innerHTML = 'Player 2: 0';
                break;
            }
            else{
                window.open("../","_self")
            }
        }
        if(player2.style.left == 800 + "px"){
            player2.style.left = 0 + "px";
            player2.style.bottom = parseInt(player2.style.bottom) + 80 + "px";
        }
        p2location.innerHTML = 'Player 2: ' + playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom));
        await timer(200);
    }
    laddersCases();
    snakesCases();
    p2location.innerHTML = 'Player 2: ' + playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom));
    p2location.style.backgroundColor = "transparent";
    p2location.style.outlineStyle = "none";
    p1location.style.backgroundColor = "white";
    p1location.style.outlineStyle = "solid";
    document.getElementById("dice").style.pointerEvents = "auto";
}

function playerLocation(x, y) {
    return(x/80 + y/8 + 1);
}

function laddersCases(){
    audio.src = "../audio/ladder.mp3"
    //Ladder 1
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 3) {    
        audio.play();
        player1.style.left = 0 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 3) {
        audio.play();
        player2.style.left = 0 + "px";
        player2.style.bottom = 400 + "px";
    }
    //Ladder 2
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 6) {
        audio.play();
        player1.style.left = 480 + "px";
        player1.style.bottom = 160 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 6) {
        audio.play();
        player2.style.left = 480 + "px";
        player2.style.bottom = 160 + "px";
    }
    //Ladder 3
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 20) {
        audio.play();
        player1.style.left = 720 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 20) {
        audio.play();
        player2.style.left = 720 + "px";
        player2.style.bottom = 480 + "px";
    }
    //Ladder 4
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 36) {
        audio.play();
        player1.style.left = 320 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 36) {
        audio.play();
        player2.style.left = 320 + "px";
        player2.style.bottom = 400 + "px";
    }
    //Ladder 5
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 63) {
        audio.play();
        player1.style.left = 320 + "px";
        player1.style.bottom = 720 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 63) {
        audio.play();
        player2.style.left = 320 + "px";
        player2.style.bottom = 720 + "px";
    }
    //Ladder 6
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 68) {
        audio.play();
        player1.style.left = 560 + "px";
        player1.style.bottom = 720 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 68) {
        audio.play();
        player2.style.left = 560 + "px";
        player2.style.bottom = 720 + "px";
    }
}

function snakesCases(){
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 34) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player1.style.left = 0 + "px";
        player1.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 34) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player2.style.left = 0 + "px";
        player2.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 25) {
        audio.src = "../audio/snake3.mp3"
        audio.play();
        player1.style.left = 320 + "px";
        player1.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 25) {
        audio.src = "../audio/snake3.mp3"
        audio.play();
        player2.style.left = 320 + "px";
        player2.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 47) {
        audio.src = "../audio/snake1.mp3"
        audio.play();
        player1.style.left = 640 + "px";
        player1.style.bottom = 80 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 47) {
        audio.src = "../audio/snake1.mp3"
        audio.play();
        player2.style.left = 640 + "px";
        player2.style.bottom = 80 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 65) {
        audio.src = "../audio/snake3.mp3"
        audio.play();
        player1.style.left = 80 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 65) {
        audio.src = "../audio/snake3.mp3"
        audio.play();
        player2.style.left = 80 + "px";
        player2.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 87) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player1.style.left = 480 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 87) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player2.style.left = 480 + "px";
        player2.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 91) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player1.style.left = 0 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 91) {
        audio.src = "../audio/snake2.mp3"
        audio.play();
        player2.style.left = 0 + "px";
        player2.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 99) {
        audio.src = "../audio/snake1.mp3"
        audio.play();
        player1.style.left = 640 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 99) {
        audio.src = "../audio/snake1.mp3"
        audio.play();
        player2.style.left = 640 + "px";
        player2.style.bottom = 480 + "px";
    }
}