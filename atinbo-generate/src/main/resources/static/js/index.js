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
            url: "gen/index",
            dataSrc: function (data) {
                var prop = data["properties"];
                $.each(prop, function(key,value){
                    $("#"+key).val(value);
                });
                return data["classInfos"];
            }
        },
        columns: [
            {
                "orderable": false,
                'sClass': "text-center",
                render: function (data, type, row, meta) {
                    return "<input type=\"checkbox\" class=\"gen-table\" value=\"" + row.tableName + "\">";
                }
            },
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
                'sClass': "text-center",
                render: function (data, type, row, meta) {
                    return "<a class=\"codeGenerate\" data-name=\"" + row.tableName + "\" href=\"javascript:;\">生成</a>";
                }
            }
        ],
        initComplete: function () {
            //表格加载完毕，手动添加按钮到表格上
            $("#table_length").append("<a href='javascript:void(0);' " +
                "class='btn btn-default btn-sm checked-generate' style='margin-left: 5px;'>批量生成</a>");
        }

    });
    /**
     * 生成代码
     */
    $(".table-content").on("click", ".codeGenerate", function () {
        genTable($(this).data("name"));
    });

    /**
     * 批量生成代码
     */
    $(".table-content").on("click", ".checked-generate", function () {
        var checked = $(".gen-table:checked");
        if (checked.length != 0) {
            var tables = [];
            checked.each(function (i, e) {
                tables[i] = $(e).val();
            });
            genTable(tables.join(","));
        }
    });

    /**
     * 全选
     */
    $("#checkAll").click(function () {
        $(".gen-table").prop("checked", $(this).prop("checked"));
    });

    function genTable(tableName) {
        var formData = {};
        var a = $("#settingForm").serializeArray();
        $.each(a, function () {
            formData[this.name] = this.value || '';
        });
        formData["tableName"] = tableName;
        $.ajax({
            url: "gen",
            type: "POST",
            data: formData,
            success: function (result) {
                layer.alert(result.message);
            },
            error: function (e) {
                console.error(e);
                layer.alert("代码生成异常");
            }
        });
    }
});