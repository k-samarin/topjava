const mealAjaxUrl = "ajax/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function filterMeal(startDate, endDate, startTime, endTime) {
    let url = ctx.ajaxUrl + "filter" +
        "?startDate=" + encodeURIComponent(startDate) +
        "&endDate=" + encodeURIComponent(endDate) +
        "&startTime=" + encodeURIComponent(startTime) +
        "&endTime=" + encodeURIComponent(endTime);
    $.get(url, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    const startDateInput = $('#startDate');
    const endDateInput = $('#endDate');
    const startTimeInput = $('#startTime');
    const endTimeInput = $('#endTime');

    startDateInput.val('');
    endDateInput.val('');
    startTimeInput.val('');
    endTimeInput.val('');

    filterMeal('', '', '', '');
}