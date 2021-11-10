var player1 = null;
var player2 = null;
let blueTurn = true;
window.onload = init;

function roleDice(){
    let index = Math.floor(Math.random()*6 )+1;
        switch (index) {
            case 1:
                document.getElementById('dice').src = "img/dice1.png";
                break;
            case 2:
                document.getElementById('dice').src = "img/dice2.png";
                break;
            case 3:
                document.getElementById('dice').src = "img/dice3.png";
                break;
            case 4:
                document.getElementById('dice').src = "img/dice4.png";
                break;
            case 5:
                document.getElementById('dice').src = "img/dice5.png";
                break;
            case 6:
                document.getElementById('dice').src = "img/dice6.png";
                break;
            default:
                break;
        }
        return index;
}

function init(){
    player1 = document.getElementById("player1");
    player1.style.left = 0 + "px";
    player1.style.bottom = 0 + "px";
    player2 = document.getElementById("player2");
    player2.style.left = 0 + "px";
    player2.style.bottom = 0 + "px";
}

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

// Returns a Promise that resolves after "ms" Milliseconds
const timer = ms => new Promise(res => setTimeout(res, ms))

async function blueMove(){
    let diceNum = roleDice();
    document.getElementById("dice").style.pointerEvents = "none";
    for(let i=1;i<=diceNum;i++){
        player1.style.left = parseInt(player1.style.left) + 80 + "px";
        if(player1.style.left == 720 +"px" && player1.style.bottom == 720 +"px"){
            player1.style.left = 720 +"px"; 
            player1.style.bottom = 720 +"px";
            document.getElementById('player1Location').innerHTML = 'Player 1: 100';
            await timer(200);
            let confirmAction = confirm("Player 1 Win while Player 2 still in "+playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom))+",Restart?");
            if(confirmAction){
                player1.style.left = 0 + "px";
                player1.style.bottom = 0 + "px";
                player2.style.left = 0 + "px";
                player2.style.bottom = 0 + "px";
                document.getElementById('player1Location').innerHTML = 'Player 1: 1';
                document.getElementById('player2Location').innerHTML = 'Player 2: 1';
                break;
            }
            else{
                window.close();
            }
        }
        if(player1.style.left == 800 + "px"){
            player1.style.left = 0 + "px";
            player1.style.bottom = parseInt(player1.style.bottom) + 80 + "px";
        }
        document.getElementById('player1Location').innerHTML = 'Player 1: ' + playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom));
        await timer(200);
    }
    laddersCases();
    snakesCases();
    document.getElementById('player1Location').innerHTML = 'Player 1: ' + playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom));
    document.getElementById('playerTurn').innerHTML = 'Turn: Player 2';
    document.getElementById('playerTurn').style.color = 'crimson';
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
            document.getElementById('player2Location').innerHTML = 'Player 2: 100';
            await timer(200);
            let confirmAction = confirm("Player 2 Win while Player 1 still in "+playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom))+",Restart?");
            if(confirmAction){
                player1.style.left = 0 + "px";
                player1.style.bottom = 0 + "px";
                player2.style.left = 0 + "px";
                player2.style.bottom = 0 + "px";
                document.getElementById('player1Location').innerHTML = 'Player 1: 1';
                document.getElementById('player2Location').innerHTML = 'Player 2: 1';
                break;
            }
            else{
                window.close();
            }
        }
        if(player2.style.left == 800 + "px"){
            player2.style.left = 0 + "px";
            player2.style.bottom = parseInt(player2.style.bottom) + 80 + "px";
        }
        document.getElementById('player2Location').innerHTML = 'Player 2: ' + playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom));
        await timer(200);
    }
    laddersCases();
    snakesCases();
    document.getElementById('player2Location').innerHTML = 'Player 2: ' + playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom));
    document.getElementById('playerTurn').innerHTML = 'Turn: Player 1';
    document.getElementById('playerTurn').style.color = 'skyblue';
    document.getElementById("dice").style.pointerEvents = "auto";
}

function playerLocation(x, y) {
    return(x/80 + y/8 + 1);
}

function laddersCases(){
    //Ladder 1
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 3) {
        player1.style.left = 0 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 3) {
        player2.style.left = 0 + "px";
        player2.style.bottom = 400 + "px";
    }
    //Ladder 2
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 6) {
        player1.style.left = 480 + "px";
        player1.style.bottom = 160 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 6) {
        player2.style.left = 480 + "px";
        player2.style.bottom = 160 + "px";
    }
    //Ladder 3
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 20) {
        player1.style.left = 720 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 20) {
        player2.style.left = 720 + "px";
        player2.style.bottom = 480 + "px";
    }
    //Ladder 4
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 36) {
        player1.style.left = 320 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 36) {
        player2.style.left = 320 + "px";
        player2.style.bottom = 400 + "px";
    }
    //Ladder 5
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 63) {
        player1.style.left = 320 + "px";
        player1.style.bottom = 720 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 63) {
        player2.style.left = 320 + "px";
        player2.style.bottom = 720 + "px";
    }
    //Ladder 6
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 68) {
        player1.style.left = 560 + "px";
        player1.style.bottom = 720 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 68) {
        player2.style.left = 560 + "px";
        player2.style.bottom = 720 + "px";
    }
}

function snakesCases(){
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 34) {
        player1.style.left = 0 + "px";
        player1.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 34) {
        player2.style.left = 0 + "px";
        player2.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 25) {
        player1.style.left = 320 + "px";
        player1.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 25) {
        player2.style.left = 320 + "px";
        player2.style.bottom = 0 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 47) {
        player1.style.left = 640 + "px";
        player1.style.bottom = 80 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 47) {
        player2.style.left = 640 + "px";
        player2.style.bottom = 80 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 65) {
        player1.style.left = 80 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 65) {
        player2.style.left = 80 + "px";
        player2.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 87) {
        player1.style.left = 480 + "px";
        player1.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 87) {
        player2.style.left = 480 + "px";
        player2.style.bottom = 400 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 91) {
        player1.style.left = 0 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 91) {
        player2.style.left = 0 + "px";
        player2.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player1.style.left),parseInt(player1.style.bottom)) == 99) {
        player1.style.left = 640 + "px";
        player1.style.bottom = 480 + "px";
    }
    if(playerLocation(parseInt(player2.style.left),parseInt(player2.style.bottom)) == 99) {
        player2.style.left = 640 + "px";
        player2.style.bottom = 480 + "px";
    }
}