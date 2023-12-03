const userAjaxUrl = "meals/";

const ctx = {
    ajaxUrl: userAjaxUrl
};

$(function () {
    makeEditable(
    $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data) {
                        return new Date(data).toLocaleString();
                    }
                },
                {
                    "data": "description",
                    "render": function (data) {
                        return new String(data);
                    }
                },
                {
                    "data": "calories",
                    "render": function (data) {
                        return new Number(data);
                    }
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