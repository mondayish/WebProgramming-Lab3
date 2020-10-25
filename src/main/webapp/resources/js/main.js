const CANVAS_WIDTH = 300;
const CANVAS_HEIGHT = 300;
const CANVAS_R_VALUE = 120;
const DEFAULT_R_VALUE = 3;

function fromTableToSvgX(x) {
    return x / getRValue() * CANVAS_R_VALUE + CANVAS_WIDTH / 2;
}

function fromTableToSvgY(y) {
    return CANVAS_HEIGHT / 2 - y / getRValue() * CANVAS_R_VALUE;
}

function fromSvgToRX(x, r) {
    return r * (x - CANVAS_WIDTH / 2) / CANVAS_R_VALUE;
}

function fromSvgToRY(y, r) {
    return r * (CANVAS_HEIGHT / 2 - y) / CANVAS_R_VALUE;
}

function checkResult(x, y, r) {
    isInCircle = (x, y, r) => x >= 0 && y <= 0 && r * r / 4 >= x * x + y * y;
    isInTriangle = (x, y, r) => x <= 0 && y <= 0 && y >= -x / 2 - r / 2;
    isInRectangle = (x, y, r) => x >= 0 && y >= 0 && x <= r && y <= r / 2;
    return isInCircle(x, y, r) || isInTriangle(x, y, r) || isInRectangle(x, y, r);
}

function drawPointsFromTable() {
    $("tbody tr").each(function () {
        const point = $(this);
        const x = parseFloat(point.find(">:first-child").text());
        const y = parseFloat(point.find(">:nth-child(2)").text());
        const color = checkResult(x, y, getRValue()) ? 'green' : 'red';

        const plot = $(".graphics svg");

        const existingContent = plot.html();
        const contentToInsert = `<circle class="point" r="4" cx="${fromTableToSvgX(x)}" cy="${fromTableToSvgY(y)}" fill="${color}"></circle>`;
        plot.html(existingContent + contentToInsert);
    });
}

function deleteAllPointsFromPlot() {
    $(".point").remove();
}

function getRValue() {
    const rText = $('.r-checkbox-selected + div > label').text();
    let rValue = parseFloat(rText);
    if (rText === '') {
        rValue = DEFAULT_R_VALUE;
    }
    return rValue;
}

function clickPlotHandler(e) {
    const offset = $(this).offset();
    const x = e.pageX - offset.left;
    const y = e.pageY - offset.top;
    const rValue = getRValue();

    if (rValue !== null) {
        const xValue = fromSvgToRX(x, rValue);
        const yValue = fromSvgToRY(y, rValue);
        // todo message about required R
        // todo something to send fields
    }
}

function checkEmptyRow() {
    const firstRow = $("tbody tr").first();
    if ($("tbody tr").first().html() === "<td></td><td></td><td></td><td></td><td></td>") {
        firstRow.remove();
    }
}

checkEmptyRow();
drawPointsFromTable();

$(".graphics svg").click(clickPlotHandler);

$(".r-checkbox").click(function () {
    $(".r-checkbox-selected")
        .prop("checked", false)
        .add(this)
        .toggleClass("r-checkbox-selected")
        .toggleClass("r-checkbox");
    deleteAllPointsFromPlot();
    drawPointsFromTable();
});