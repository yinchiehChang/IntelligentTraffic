/**
 * Created by Administrator on 2016/8/4.
 */

var workTitle, Publisher, currentID, workTime, flag = true;
function Workload() {
    $(function () {
        $('#table').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,

            dataType: "json",
            pagination: true, //分页
            pageSize: 10,
            pageNumber: 1,
            search: false, //显示搜索框
            contentType: "application/x-www-form-urlencoded",
            queryParams: null,
            columns: [
                {
                    checkbox:"true",
                    field: 'ID',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: "编号",
                    field: 'class',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '日志信息',
                    field: 'sex',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作人',
                    field: 'type',
                    align: 'center'
                },
                {
                    title: '操作时间',
                    field: 'name',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '操作类型',
                    field: 'name',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '数据源',
                    field: 'work',
                    align: 'center'
                },
                {
                    title: '操作内容',
                    field: 'id',
                    align: 'center',
                    formatter: function (value, row) {
                        var e = '<button button="#" mce_href="#" onclick="delWork(\'' + row.WORKRECORDID + '\')">审批</button> '

                        return e;
                    }
                }
            ]
        });
    });
    getWorkTableData();
}
function getWorkTableData() {
    if (flag) {
        workTitle = "";
        Publisher = "";
        workTime = "";
        flag = false;
    } else {
        workTitle = $("#tit").val();
        Publisher = $("#person").val();
        workTime = $("#demo").val();
    }
    $.ajax({
        type: "GET",
        url: "../WorkRecord/SearchWork?dtStart=" +workTitle + "&dtEnd=" + Publisher + "&dtEnd=" + workTime,
        dataType: "json",
        success: function (result) {
            if (result.data) {
                var NoticeTableData = result.data;
                $('#table').bootstrapTable("load", NoticeTableData);
            }
        }
    })
}
function addWork() {
    openlayer()
    currentID = "";
}
function editWork(id) {
    openlayer()
    currentID = id;
}
function outWork(id) {
    alert(id)
    var NoticeId = id;
    $.ajax({
        url: '../WorkRecord/DeleteWork?workId=' + NoticeId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data.data) {
                alert("删除成功！")
                // getNoticeTableData();
            } else {
                alert("删除失败")
            }
        },
        error: function (err) {
        }
    });
}
function getCurrentID() {
    return currentID;
}
function openlayer() {
    layer.open({
        type: 2,
        title: '通知信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['98%', '98%'],
        shadeClose: true,
        closeBtn: 2,
        content:" work_tail"

    });
    
}





