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

function fromSvgToRX(x) {
    return getRValue() * (x - CANVAS_WIDTH / 2) / CANVAS_R_VALUE;
}

function fromSvgToRY(y) {
    return getRValue() * (CANVAS_HEIGHT / 2 - y) / CANVAS_R_VALUE;
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
        // first empty row - bug in h:dataTable
        if(isNaN(x) || isNaN(y)){
            return;
        }

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
    let rValue = parseFloat($('.r-checkbox-selected + div > label').text());
    if (isNaN(rValue)) {
        rValue = parseFloat($("tbody tr").last().find(">:nth-child(3)").text());
        if (isNaN(rValue)) {
            rValue = DEFAULT_R_VALUE;
        }
    }
    return rValue;
}

function clickPlotHandler(e) {
    const offset = $(this).offset();
    const x = e.pageX - offset.left;
    const y = e.pageY - offset.top;
    if (checkOneRequiredR()) {
        const xValue = fromSvgToRX(x);
        const yValue = fromSvgToRY(y);
        const rValue = getRValue();

        console.log(xValue);
        console.log(yValue);
        console.log(rValue);

        $(".x-hidden").val(xValue);
        $(".y-hidden").val(yValue);
        $(".r-hidden").val(rValue);
        $(".hidden-submit-btn").click();
    }
}

function uncheckCheckboxes() {
    $("input:checked").prop("checked", false);
}

function clearForm() {
    uncheckCheckboxes();
    $(".r-checkbox-selected").toggleClass("r-checkbox-selected").toggleClass("r-checkbox");
    $(".y").val("");
}

function checkOneRequiredX() {
    const result = $(".x-checkbox:checked").length !== 0;
    if (result) {
        $(".invalid-x").addClass("d-none");
    } else {
        $(".invalid-x").removeClass("d-none");
    }
    return result;
}

function checkOneRequiredR() {
    const result = $(".r-checkbox-selected").length !== 0;
    if (result) {
        $(".invalid-r").addClass("d-none");
    } else {
        $(".invalid-r").removeClass("d-none");
    }
    return result;
}

clearForm();
drawPointsFromTable();

$(".data-form").submit(function (e) {
    if (!checkOneRequiredX() || !checkOneRequiredR()) {
        e.preventDefault();
    }
});

$(".clear-table-btn").on('click', deleteAllPointsFromPlot);

$(".clear-form-btn").on('click', clearForm);

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