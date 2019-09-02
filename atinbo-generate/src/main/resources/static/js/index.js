$(function () {

    $('#table').DataTable({
        language : {
            "lengthMenu" : '每页显示<select>' + '<option value="10">10</option>'
                + '<option value="20">20</option>'
                + '<option value="30">30</option>'
                + '<option value="40">40</option>'
                + '<option value="50">50</option>' + '</select>条',
            "paginate" : {
                "first" : "首页",
                "last" : "尾页",
                "previous" : "上一页",
                "next" : "下一页"
            },
            "processing" : "加载中...",  //DataTables载入数据时，是否显示‘进度’提示
            "emptyTable" : "暂无数据",
            "info" : "共 _PAGES_ 页  _TOTAL_ 条数据  ",
            "infoEmpty" : "暂无数据",
            "emptyTable" : "暂无要处理的数据...",  //表格中无数据
            "search": "搜索:",
            "infoFiltered" : " —— 从  _MAX_ 条数据中筛选",
            "zeroRecords":    "没有找到记录"

        },
        ajax : "gen/list",
        columns: [
            {
                "data": "className",
                "name" : "className",
                "sDefaultContent":"",  //默认空字符串
                "sClass": "text-center"
            },
            {
                "orderable" : false,
                "data": "classComment",
                "name" : "classComment",
                'sClass': "text-center"
            },
            {
                "orderable" : false,
                "data": "author",
                'sClass': "text-center"
            }
        ]
    });
    /**
     * 生成代码
     */
    $('.codeGenerate').click(function () {
        var tableName = $(this).data("name");
        $.ajax({
            url: "gen",
            type: "POST",
            data: {"tableName" : tableName},
            success: function (result) {
                if(result.code == 200){
                    layer.alert("代码生成成功");
                }else {
                    layer.alert(result.msg);
                }
            },
            error: function (e) {
                console.error(e);
                layer.alert("代码生成异常");
            }
        });
    });

});