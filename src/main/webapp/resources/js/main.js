function checkResult(x, y, r) {
    isInCircle = (x, y, r) => x >= 0 && y <= 0 && r * r / 4 >= x * x + y * y;
    isInTriangle = (x, y, r) => x <= 0 && y <= 0 && y >= -x / 2 - r / 2;
    isInRectangle = (x, y, r) => x >= 0 && y >= 0 && x <= r && y <= r / 2;
    return isInCircle(x, y, r) || isInTriangle(x, y, r) || isInRectangle(x, y, r);
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

function checkYInput() {
    const yValue = $(".y").val().replace(",", ".");
    $(".y").val(yValue);
    const result = /^(-?\d+)(\.\d+)?$/.test(yValue);
    if (result) {
        $(".invalid-y").addClass("d-none");
    } else {
        $(".invalid-y").removeClass("d-none");
    }
    return result;
}

clearForm();
drawPointsFromTable();

$(".data-form").submit(function (e) {
    if (!checkOneRequiredX() || !checkYInput() || !checkOneRequiredR()) {
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