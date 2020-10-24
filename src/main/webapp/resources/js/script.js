const INTERVAL = 9000;

function getFormatString(numb) {
    return numb < 10 ? '0' + String(numb) : String(numb);
}

function setTime(hours, minutes, seconds){
    const hoursInDegrees = (((hours%12)*3600+minutes*60+seconds)/(12*3600))*360;
    const minutesInDegrees = ((minutes*60+seconds)/3600)*360;
    const secondsInDegrees = seconds*6;
    $("#seconds").attr({transform: `rotate(${secondsInDegrees} 150 150)`});
    $("#minutes").attr({transform: `rotate(${minutesInDegrees} 150 150)`});
    $("#hours").attr({transform: `rotate(${hoursInDegrees} 150 150)`});
}

function updateClock() {
    const currentDate = new Date();

    const year = currentDate.getFullYear();
    const month = getFormatString(currentDate.getMonth()+1);
    const day = getFormatString(currentDate.getDate());

    const hours = currentDate.getHours();
    const minutes = currentDate.getMinutes();
    const seconds = currentDate.getSeconds();
    const date = `${day}.${month}.${year}`;

    $(".date").html(date);
    setTime(hours, minutes, seconds);
}

updateClock();

setInterval(updateClock, INTERVAL);