$(function () {

    $('#table').DataTable({
        language: {
            "bProcessing": true, //DataTables载入数据时，是否显示‘进度’提示
            "bPaginate": true, //是否显示（应用）分页器
            "bInfo": true, //是否显示页脚信息，DataTables插件左下角显示记录数
            "lengthMenu": '每页显示<select>' + '<option value="10">10</option>'
                + '<option value="20">20</option>'
                + '<option value="30">30</option>'
                + '<option value="40">40</option>'
                + '<option value="50">50</option>' + '</select>条',
            "paginate": {
                "first": "首页",
                "last": "尾页",
                "previous": "上一页",
                "next": "下一页"
            },
            "processing": "加载中...",  //DataTables载入数据时，是否显示‘进度’提示
            "emptyTable": "暂无数据",
            "info": "共 _PAGES_ 页  _TOTAL_ 条数据  ",
            "infoEmpty": "暂无数据",
            "emptyTable": "暂无要处理的数据...",  //表格中无数据
            "search": "搜索:",
            "infoFiltered": " —— 从  _MAX_ 条数据中筛选",
            "zeroRecords": "没有找到记录"

        },
        ajax: {
            url: "gen/list",
            dataSrc: ""
        },
        columns: [
            {
                "data": "tableName",
                "name": "tableName",
                "sDefaultContent": "",  //默认空字符串
                "sClass": "text-center"
            },
            {
                "data": "className",
                "name": "className",
                "sDefaultContent": "",  //默认空字符串
                "sClass": "text-center"
            },
            {
                "orderable": false,
                "data": "classComment",
                "name": "classComment",
                'sClass': "text-center"
            },
            {
                "orderable": false,
                "data": "author",
                'sClass': "text-center"
            },
            {
                "orderable": false,
                'sClass': "text-center",
                render: function (data, type, row, meta) {
                    return "<a class=\"codeGenerate\" data-name=\"" + row.tableName + "\" href=\"javascript:;\">生成</a>";
                }
            }
        ],
        initComplete: function () {
            //表格加载完毕，手动添加按钮到表格上
            $("#table_length").append("<a href='javascript:void(0);' " +
                "class='btn btn-default btn-sm codeGenerate' style='margin-left: 5px;'>全部生成</a>");
        }

    });
    /**
     * 生成代码
     */
    $(".table-content").on("click", ".codeGenerate", function () {
        var tableName = $(this).data("name");
        $.ajax({
            url: "gen",
            type: "POST",
            data: {"tableName": tableName},
            success: function (result) {
                if (result.code == 0) {
                    layer.alert("代码生成成功");
                } else {
                    layer.alert(result.message);
                }
            },
            error: function (e) {
                console.error(e);
                layer.alert("代码生成异常");
            }
        });
    });

});