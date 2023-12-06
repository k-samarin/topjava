const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});

function toggleUser(checkbox, id) {
    const row = $(checkbox).closest('tr');
    row.attr("data-user-enabled", checkbox.checked);

    if (checkbox.checked) {
        $.ajax({
            type: 'POST',
            url: ctx.ajaxUrl + id + "/enable"
        });
    } else {
        $.ajax({
            type: 'POST',
            url: ctx.ajaxUrl + id + "/disable"
        });
    }
}