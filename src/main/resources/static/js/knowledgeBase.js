var base, securities, articles, points, items, indicators, functionPoints, checkItems;
var baseId, kbType;
var nodes;

function onLoad(bid, KBType) {
    baseId = bid;
    kbType = KBType;

    switch(KBType) {
        case 0:
        $('#query').html('<div class="tabe_bot">' +
                             '<div class="l_left"><label>关键字：</label><input type="text" id="keyword"></div>' +
                             '<div class="l_left"><label>测评对象：</label><input type="text" id="object"></div>' +
                             '<div class="l_left"><label>安全等级：</label><input type="text" id="securityLevel"></div>' +
                             '<button class="tabe_btn " onClick="query()">查询</button>' +
                             '<div class="clear"></div>' +
                         '</div>');
        $('#add').html('<a class="add" data-toggle="modal" data-target="#ItemAddModal">添加</a>');
        $("#KB-content").html('<div id="main-content">' +
                               '<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">' +
                                     '<ul class="layui-tab-title" id="tab-title">' +
                                     '</ul>' +
                                     '<div class="layui-tab-content" id="tab-content">' +
                                     '</div>' +
                               '</div>' +
                            '</div>');
        $.ajax({
            url: "/KBtreePage",
            type: "GET",//请求方式为get
            dataType: "json", //返回数据格式为json
            data: 'bid=' + bid,
            success: function(data) {//请求成功完成后要执行的方法
                base = data.base[0];
                securities = data.securities;
                articles = data.articles;
                points = data.points;
                items = data.items;
                $('#left_title').html(base.base_name);
                createNodes();
                layui.use('element', function () {
                    var element = layui.element;
                    layui.element.render()
                });
            },
            eroor: function() {
                console.log('error!');
            }
        });
        break;

        case 1:
        $('#query').html('<div class="tabe_bot">' +
                                     '<div class="l_left"><label>文件名关键字：</label><input type="text" id="keyword"></div>' +
                                     '<button class="tabe_btn " onClick="queryFile()">查询</button>' +
                                     '<div class="clear"></div>' +
                                 '</div>');
        $("#KB-content").html('<div id="main-content">' + '</div>');
        $.ajax({
            url: "/KBfilePage",
            type: "GET",//请求方式为get
            dataType: "json", //返回数据格式为json
            data: 'bid=' + bid,
            success: function(data) {//请求成功完成后要执行的方法
                base = data.base[0];
                $('#left_title').html(base.base_name);
                genFileList(data.attachements);
            },
            eroor: function() {
                console.log('error!');
            }
        });
        break;

        case 2:
        $("#KB-content").html('<div id="main-content">' +
                                    '<div class="layui-collapse" style="margin-left:10px" id="0-0Content"> </div>' +
                              '</div>');
        $.ajax({
            url: "/KBtreePage2",
            type: "GET",//请求方式为get
            dataType: "json", //返回数据格式为json
            data: 'bid=' + bid,
            success: function(data) {//请求成功完成后要执行的方法
                base = data.base[0];
                indicators = data.info.indicators;
                functionPoints = data.info.functionPoints;
                checkItems = data.info.checkItems;
                //console.log(data);
                var root = data.root;
                $('#left_title').html(base.base_name);
                dfsBaseTree(root, 0, -1);
                layui.use('element', function () {
                    var element = layui.element;
                    layui.element.render()
                });
            },
            eroor: function() {
                console.log('error!');
            }
        });
        break;

        case 3:
                $("#KB-content").html('<div id="main-content">' + '</div>');
                $.ajax({
                    url: "/KBbugLibList",
                    type: "GET",//请求方式为get
                    dataType: "json", //返回数据格式为json
                    data: 'bid=' + bid,
                    success: function(data) {//请求成功完成后要执行的方法
                        base = data.base[0];
                        bugItems = data.bugItems;
                        console.log(bugItems);
                        $('#left_title').html(base.base_name);
                        $("#addBtn").html('<a class="add" href="toBugDetail?bid=-1&baseId='+ base.base_id +'">添加</a>');
                        genBugLibList(bugItems);
                        layui.use('element', function () {
                            var element = layui.element;
                            layui.element.render()
                        });
                    },
                    eroor: function() {
                        console.log('error!');
                    }
                });
                break;
    }

}

function createNodes(){
    var nodesId = 0;
    for(var s = 0; s < securities.length; s++) {
        //Security
        var aBros = [];
        for(var a = 0; a < articles.length; a++) {
            //暂存articles兄弟节点
            if(articles[a].security_id === securities[s].security_id) {
                aBros.push(articles[a]);
            }
        }
        insertTab(securities[s]);
        for(var ab = 0; ab < aBros.length; ab++) {
            //Article
            var pBros = [];
            for(var p = 0; p < points.length; p++) {
                if(points[p].article_id === aBros[ab].article_id) {
                    //暂存points兄弟节点
                    pBros.push(points[p]);
                }
            }
            insertArticle(aBros[ab], securities[s].security_id);
            for(var pb = 0; pb < pBros.length; pb++) {
                //Point
                var iBros = [];
                for(var i = 0; i < items.length; i++) {
                    if(items[i].point_id === pBros[pb].point_id) {
                        //暂存item兄弟节点
                        iBros.push(items[i]);
                    }
                }
                insertPoint(pBros[pb], aBros[ab].article_id);
                insertTable(iBros, pBros[pb].point_id);
            }
        }
    }
}

function insertTab(node) {
    var tabTitle = $('#tab-title'),
    tabLi = $('#tab-title li'),
    tabContent = $('#tab-content');

    if(tabLi.length === 0) {
        tabTitle.append('<li class="layui-this">' + node.security_level + '</li>');
        tabContent.append('<div class="layui-tab-item layui-show" id="sId' + node.security_id + '"></div>');
    } else {
        tabTitle.append('<li>' + node.security_level + '</li>');
        tabContent.append('<div class="layui-tab-item" id="sId' + node.security_id + '"></div>');
    }


}

function insertArticle(node, parentId) {
    var parentElem = $('#sId' + parentId.toString());
    //console.log("Colla " + parentElem);

    if(!$.trim(parentElem.html())) {//判断当前节点的父节点下是否有元素
        parentElem.append('<div class="layui-collapse" style="margin-left:10px" id="sId' + parentId + 'Content"> </div>');
    }
    $('#sId' + parentId.toString() + 'Content').append('<div class="layui-colla-item">\n' +
                                                         '<h2 class="layui-colla-title", style="background-color:#dddddd">' + node.article_name + '</h2>\n' +
                                                         '<div class="layui-colla-content" id="aId' + node.article_id + '"></div>\n' +
                                                     '</div>\n');
}

function insertPoint(node, parentId) {
    var parentElem = $('#aId' + parentId.toString());
    //console.log("Colla " + parentElem);

    if(!$.trim(parentElem.html())) {//判断当前节点的父节点下是否有元素
        parentElem.append('<div class="layui-collapse" style="margin-left:10px" id="aId' + parentId + 'Content"> </div>');
    }
    $('#aId' + parentId.toString() + 'Content').append('<div class="layui-colla-item">\n' +
                                                         '<h2 class="layui-colla-title", style="background-color:#dddddd">' + node.point_name + '</h2>\n' +
                                                         '<div class="layui-colla-content" id="pId' + node.point_id + '"></div>\n' +
                                                     '</div>\n');
}

function insertTable(nodes, parentId) {
    var parentElem = $('#pId' + parentId.toString());
    //console.log("Table " + parentElem);
    parentElem.append('<table class="layui-table">\n' +
                         '<colgroup> <col> <col width="200"> <col width="200"> </colgroup>\n' +
                         '<thead> <tr> <th>检测项</th> <th>详情</th> <th>操作</th> </tr> </thead>\n' +
                         '<tbody id="pId' + parentId + 'Content"> </tbody>' +
                     '</table>');
    for(var i = 0; i < nodes.length; i++){
        $('#pId' + parentId.toString() + 'Content').append(
                              '<tr>\n' +
                                  '<td>' + nodes[i].nr + '</td>\n' +
                                  '<td style="text-align:center"><button type="button" class="layui-btn layui-btn-small layui-btn-normal" onclick="detail('+ nodes[i].item_id +')">详情</button></td>\n' +
                                  '<td style="text-align:center">\n' +
                                    '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateItem('+ nodes[i].item_id +')">修改</button>\n' +
                                    '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteItem('+ nodes[i].item_id +')">删除</button>\n' +
                                  '</td>\n' +
                              '</tr>\n');
    }
}

function dfsBaseTree(node, depth, superId) {
    if(node === undefined) {
        return;
    }
    if(depth != 0) {
        insertNode(node, depth, superId);
        if(node.subNode.length === 0) {
            insertLastLevel(node, depth, node.info_id);
        }
    }

    node.subNode.forEach(function (subNode) {
        dfsBaseTree(subNode, depth+1, node.node_id);
    });
}

function insertNode(node, depth, parentId) {
    //console.log('depth:' + (depth) + ' name:' + node.node_name + ' id:' + node.node_id + ' parent:' + (depth-1).toString() + '-' + parentId.toString() + 'Content');
    var parentElem;
    if(depth === 1) {
        parentElem = $('#KB-content');
    } else {
        parentElem = $('#' + (depth-1).toString() + '-' + parentId.toString());
    }

    if(!$.trim(parentElem.html())) {//判断当前节点的父节点下是否有元素
        parentElem.append('<div class="layui-collapse" style="margin-left:10px" id="' + (depth-1).toString() + '-' + parentId.toString() + 'Content"> </div>');
    }
    $('#' + (depth-1).toString() + '-' + parentId.toString() + 'Content').append('<div class="layui-colla-item">\n' +
                                                         '<h2 class="layui-colla-title" style="background-color:#dddddd">' + node.node_name + '</h2>\n' +
                                                         '<div class="layui-colla-content" id="' + depth.toString() + '-' + node.node_id + '"></div>\n' +
                                                     '</div>\n');
}

function insertLastLevel(node, depth, id) {
    var parentElem = $('#' + depth.toString() + '-' + node.node_id.toString());
    switch (node.info_table) {
        case "INDICATOR":
            $('#query').html('<div class="tabe_bot">' +
                                 '<div class="l_left"><label>一级指标：</label><input type="text" id="Lv1"></div>' +
                                 '<div class="l_left"><label>二级指标：</label><input type="text" id="Lv2"></div>' +
                                 '<div class="l_left"><label>三级指标：</label><input type="text" id="Lv3"></div>' +
                                 '<button class="tabe_btn " onClick="query(3)">查询</button>' +
                                 '<div class="clear"></div>' +
                             '</div>');
            $('#add').html('<a class="add" data-toggle="modal" data-target="#IndicatorAddModal">添加</a>');

            indicator = indicators.find(function(cur) { return cur.indicatorId === id; });
            parentElem.append('<table class="layui-table">\n' +
                                 '<colgroup> <col> <col width="200"> <col> <col> </colgroup>\n' +
                                 '<thead> <tr> <th>描述</th> <th>计算式分子</th> <th>计算式分母</th> <th>测量输入</th> <th>操作</th></tr> </thead>\n' +
                                 '<tbody">'+
                                    '<tr>\n' +
                                       '<td>' + indicator.indicatorDescription + '</td>\n' +
                                       '<td>' + indicator.molecular + '</td>\n' +
                                       '<td>' + indicator.denominator + '</td>\n' +
                                       '<td>' + indicator.input + '</td>\n' +
                                       '<td style="text-align:center">\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateIndicator('+ indicator.indicatorId +')">修改</button>\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteIndicator('+ indicator.indicatorId +')">删除</button>\n' +
                                       '</td>\n' +
                                   '</tr>\n' +
                                 '</tbody>' +
                             '</table>');
            break;

        case "FUNCTION_POINT":
            $('#query').html('<div class="tabe_bot">' +
                             '<div class="l_left"><label>一级功能点：</label><input type="text" id="Lv1"></div>' +
                             '<div class="l_left"><label>二级功能点：</label><input type="text" id="Lv2"></div>' +
                             '<div class="l_left"><label>三级功能点描述：</label><input type="text" id="Lv3"></div>' +
                             '<button class="tabe_btn " onClick="query(3)">查询</button>' +
                             '<div class="clear"></div>' +
                         '</div>');
            $('#add').html('<a class="add" data-toggle="modal" data-target="#FunctionPointAddModal">添加</a>');

            fPoint = functionPoints.find(function(cur) { return cur.fid === id; });
            parentElem.append('<table class="layui-table">\n' +
                                 '<colgroup> <col> <col> <col></colgroup>\n' +
                                 '<thead> <tr> <th>序列号</th> <th>功能测试说明</th> <th>操作</th> </tr> </thead>\n' +
                                 '<tbody">'+
                                    '<tr>\n' +
                                       '<td>' + fPoint.serial + '</td>\n' +
                                       '<td><pre>' + fPoint.description + '</pre></td>\n' +
                                       '<td style="text-align:center">\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateFunctionPoint('+ fPoint.fid +')">修改</button>\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteFunctionPoint('+ fPoint.fid +')">删除</button>\n' +
                                       '</td>\n' +
                                   '</tr>\n' +
                                 '</tbody>' +
                             '</table>');
            break;

        case "CHECK_ITEM":
            $('#query').html('<div class="tabe_bot">' +
                             '<div class="l_left"><label>检查范围：</label><input type="text" id="Lv1"></div>' +
                             '<div class="l_left"><label>检查项：</label><input type="text" id="Lv2"></div>' +
                             '<button class="tabe_btn " onClick="query(2)">查询</button>' +
                             '<div class="clear"></div>' +
                         '</div>');
            $('#add').html('<a class="add" data-toggle="modal" data-target="#CheckItemAddModal">添加</a>');

            cItem = checkItems.find(function(cur) { return cur.cid === id; });
            parentElem.append('<table class="layui-table">\n' +
                                 '<colgroup> <col> <col> <col></colgroup>\n' +
                                 '<thead> <tr> <th>检查项编号</th> <th>权重</th> <th>检查意见</th> <th>操作</th> </tr> </thead>\n' +
                                 '<tbody">'+
                                    '<tr>\n' +
                                       '<td>' + cItem.serial + '</td>\n' +
                                       '<td>' + cItem.weight + '</td>\n' +
                                       '<td><pre>' + cItem.advice + '</pre></td>\n' +
                                       '<td style="text-align:center">\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateCheckItem('+ cItem.cid +')">修改</button>\n' +
                                          '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteCheckItem('+ cItem.cid +')">删除</button>\n' +
                                       '</td>\n' +
                                   '</tr>\n' +
                                 '</tbody>' +
                             '</table>');
            break;
    }

}

function detail(id) {
    layui.use('layer', function(){
        var layer = layui.layer;

        item = items.find(function(currentItem) { return currentItem.item_id === id});

        layer.open({
            type: 1,
            title: '详细信息',
            area: '900px',
            content: '<table class="layui-table">' +
  '<colgroup>' +
    '<col width="100">' +
    '<col width="100">' +
    '<col width="500">' +
  '</colgroup>' +
  '<thead>' +
    '<tr>' +
      '<th>测评对象</th>' +
      '<th>风险等级</th>' +
      '<th>实施步骤</th>' +
    '</tr>' +
  '</thead>' +
  '<tbody>' +
    '<tr>' +
      '<td>' + item.dx + '</td>' +
      '<td>' + item.fxdj + '</td>' +
      '<td><pre>' + item.ssbz +'</pre></td>' +
    '</tr>' +
  '</tbody>' +
'</table>' //这里content是一个普通的String
        });
     });
}

function updateItem(id) {
    var item = items.find(function(currentItem) { return currentItem.item_id === id});
    var itemUpdateModal = $('#ItemUpdateModal');
    itemUpdateModal.modal('show');

    itemUpdateModal.on('shown.bs.modal', function () {
        console.log(id);
        $('#update-footer').html(
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
        '<button type="button" class="btn btn-primary" onclick="updateConfirm(' + item.item_id + ')">确认修改</button>');
        $('#item_u_nr').val(item.nr);
        $('#item_u_dx').val(item.dx);
        $('#item_u_ssbz').val(item.ssbz);
        $('#item_u_fxdj').val(item.fxdj);
        $('#item_u_sxh').val(item.sxh);

        itemUpdateModal.off('shown.bs.modal');
    });
}

function updateConfirm(id) {
    var item = {
        "item_id": id,
        "nr": $('#item_u_nr').val(),
        "dx": $('#item_u_dx').val(),
        "ssbz": $('#item_u_ssbz').val(),
        "fxdj": $('#item_u_fxdj').val(),
        "sxh": $('#item_u_sxh').val()
    }
    console.log(item);

    $.ajax({
        url: "/KBItemUpdate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(item),
        success: function(res) {//请求成功完成后要执行的方法
            console.log(res.result);
            $('#ItemUpdateModal').modal('hide');
            window.location.reload();
        },
        eroor: function() {
            console.log('error!');
        }
    });
}

function deleteItem(id) {
    itemDeleteModal = $('#deleteConfirmModal');
    itemDeleteModal.modal('show');

    itemDeleteModal.on('shown.bs.modal', function (){
        $('#delete-body').html('确认删除该项？');
        $('#delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteConfirm(' + id + ')">确认</button>');
        itemDeleteModal.off('shown.bs.modal')
    });
}

function deleteConfirm(id) {
    $.ajax({
        url: "/KBItemDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"item_id": id} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteConfirmModal').modal('hide');
            console.log('rest:' + res.itemsInSamePoint);
            if(res.itemsInSamePoint === 0) {
                deletePoint(res.point_id);
            } else {
                window.location.reload();
            }
        },
        error: function() {
            console.log('error!');
        }
    });
}

function deletePoint(id) {
    pointDeleteModal = $('#deletePointModal');
    pointDeleteModal.modal('show');

    pointDeleteModal.on('shown.bs.modal', function (){
        $('#deletePoint-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deletePointConfirm(' + id + ')">确认</button>');
        pointDeleteModal.off('shown.bs.modal');
    });
}

function deletePointConfirm(id) {
    $.ajax({
        url: "/KBPointDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"point_id": id} ),
        success: function(res) {
            $('#deletePointModal').modal('hide');
            console.log('rest:' + res.pointsInSameArticle);
            if(res.pointsInSameArticle === 0) {
                deleteArticle(res.article_id);
            } else {
                window.location.reload();
            }
        },
        error: function() {
           console.log('error!');
       }
    });
}

function deleteArticle(id) {
    articleDeleteModal = $('#deleteArticleModal');
    articleDeleteModal.modal('show');

    articleDeleteModal.on('shown.bs.modal', function (){
        $('#deleteArticle-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteArticleConfirm(' + id + ')">确认</button>');
        articleDeleteModal.off('shown.bs.modal');
    });
}

function deleteArticleConfirm(id) {
    $.ajax({
        url: "/KBArticleDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"article_id": id} ),
        success: function(res) {
            $('#deleteArticleModal').modal('hide');
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function query(MaxLevel) {
console.log('query:' + 'KBType: ' + kbType);
    switch(kbType) {
        case 0:
            var keyword = $('#keyword').val(),
                object = $('#object').val(),
                securityLevel = $('#securityLevel').val();

            if(keyword == '' && object == '' && securityLevel == '') {
                alert('请输入搜索条件！');
                return;
            }

            $('#left_title').html('搜索结果');

            $.ajax({
               url: "/KBquery",
               type: "GET",//请求方式为get
               data: 'kw=' + keyword + '&obj=' + object + '&sl=' + securityLevel,
               dataType: "json", //返回数据格式为json
               success: function(data) {//请求成功完成后要执行的方法
                    //console.log(data);
                    items = data;
                    showQueryResult();
               },
               eroor: function() {//错误处理
                   console.log('error!');
               }
            });
            break;

        case 1:

            break;

        case 2:
            var lv1 = $('#Lv1').val(),
                lv2 = $('#Lv2').val(),
                lv3 = $('#Lv3').val();

            if(lv1 == '' && lv2 == '' && lv3 == '') {
                alert('请输入搜索条件！');
                return;
            }

            $('#left_title').html('搜索结果');

            $.ajax({
               url: "/KBquery2",
               type: "GET",//请求方式为get
               data: 'lv1=' + lv1 + '&lv2=' + lv2 + '&lv3=' + lv3 + '&MaxLevel=' + MaxLevel,
               dataType: "json", //返回数据格式为json
               success: function(data) {//请求成功完成后要执行的方法
                    console.log(data);
                    showQueryResult2(data, MaxLevel);
               },
               eroor: function() {//错误处理
                   console.log('error!');
               }
            });
        break;
    }

}

function showQueryResult() {
    var mainContent = $('#main-content');
    mainContent.html('');
    var thead1 = ' <div class="table-responsive">'+
                      '<table class="table table-striped table-sm">' +
                          '<thead>' +
                          '<tr>' +
                              '<th>所属知识库</th>' +
                              '<th>安全等级</th>' +
                              '<th>所属检测类</th>' +
                              '<th>所属检测点</th>' +
                              '<th>检测项内容</th>' +
                              '<th style="text-align:center">详情</th>' +
                              '<th style="text-align:center">操作</th>'
                          '</tr>' +
                          '</thead>' +
                          '<tbody>';
    var tbody = '';
    for(var i = 0; i < items.length; i++){
        var item = items[i];
        tbody += '<tr>' +
                     '<td>'+item.base_name+'</td>' +
                     '<td>'+item.aqjb+'</td>' +
                     '<td>'+item.article_name+'</td>' +
                     '<td>'+item.point_name+'</td>' +
                     '<td>'+item.nr+'</td>' +
                     '<td style="text-align:center"><button type="button" class="layui-btn layui-btn-small layui-btn-normal" onclick="detail('+ item.item_id +')">详情</button></td>' +
                     '<td style="text-align:center">\n' +
                       '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateItem('+ item.item_id +')">修改</button>\n' +
                       '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteItem('+ item.item_id +')">删除</button>\n' +
                     '</td>\n' +
                 '</tr>';
    }

    var thead2 = '</tbody>'+
          '</table>'+
      '</div>';

    mainContent.append( thead1 + tbody +thead2);
}

function showQueryResult2(data, MaxLevel) {
    var mainContent = $('#main-content');
    mainContent.html('');
    var baseList = data.baseList;
    var lv1Nodes = data.lv1Nodes;
    var lv2Nodes = data.lv2Nodes;
    var lv3Nodes = data.lv3Nodes;

    var thead1 = ' <div class="table-responsive">'+
                      '<table class="table table-striped table-sm">' +
                          '<thead>' +
                          '<tr>' +
                              '<th>所属知识库</th>' +
                              '<th>一级</th>' +
                              '<th>二级</th>' +
                              (MaxLevel === 3 ?  '<th>三级</th>' : '') +
                              '<th>'+infoName(data)+'</th>' +
                              '<th style="text-align:center">操作</th>'
                          '</tr>' +
                          '</thead>' +
                          '<tbody>';
    var tbody = '';
    for(var i = 0; i < baseList.length; i++){
        content = infoContent(data);
        tbody += '<tr>' +
                     '<td>'+baseList[i].base_name+'</td>' +
                     '<td>'+lv1Nodes[i].node_name+'</td>' +
                     '<td>'+lv2Nodes[i].node_name+'</td>' +
                     (MaxLevel === 3 ? '<td>'+lv3Nodes[i].node_name+'</td>' : '') +
                     '<td>'+content[i].display+'</td>' +
                     '<td style="text-align:center">\n' +
                       '<button type="button" class="layui-btn layui-btn-small layui-btn-primary" onclick="updateIndicator('+ content[i].id +')">修改</button>\n' +
                       '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteIndicator('+ content[i].id +')">删除</button>\n' +
                     '</td>\n' +
                 '</tr>';
    }

    var thead2 = '</tbody>'+
          '</table>'+
      '</div>';

    mainContent.append( thead1 + tbody +thead2);
}

function infoName(data) {
    if(data.info.indicators.length != 0) {return "计算方法";}
    if(data.info.functionPoints.length != 0) {return "功能点描述";}
    if(data.info.checkItems.length != 0) {return "检查意见";}
}

function infoContent(data) {
    var content = [];
    if(data.info.indicators.length != 0) {
        data.info.indicators.forEach(function (indicator) {
            content.push({"id": indicator.indicatorId, "display": indicator.molecular + " / " + indicator.denominator});
        });
    }
    if(data.info.functionPoints.length != 0) {
        data.info.functionPoints.forEach(function (fPoint) {
            content.push({"id": fPoint.fid, "display": fPoint.description});
        });
    }
    if(data.info.checkItems.length != 0) {
        data.info.checkItems.forEach(function (cItem) {
            content.push({"id": cItem.cid, "display": cItem.advice});
        });
    }

    return content;
}

function addBase(baseClass) {
    var baseName = $('#base_name').val();
    var baseType = $('#base_type').children('option:selected').val();

    $.ajax({
        url: "/KBAddBase",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"base_name": baseName, "zt": 0, "base_class": baseClass, "base_type": baseType} ),
        success: function(res) {
            //清空文本框
            $("#base_name").val("");

            if(res.result === 0) {
                //关闭对话框
                $("#BaseAddModal").modal("hide");
                //刷新页面
                window.location.reload();
            } else {
                alert('该知识库已存在！');
            }
        },
        error: function() {
           console.log('error!');
       }
    });
}

function deleteBase(id) {
//    $('#BaseDeleteModal').modal('hide');
    baseDeleteConfirmModal = $('#BaseDeleteConfirmModal');
    baseDeleteConfirmModal.modal('show');

    baseDeleteConfirmModal.on('shown.bs.modal', function (){
        $('#deleteBase-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteBaseConfirm(' + id + ')">确认</button>');
        baseDeleteConfirmModal.off('shown.bs.modal');
    });
}

function deleteBaseConfirm(id) {
    $.ajax({
        url: "/KBDeleteBase",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"base_id": id} ),
        success: function(res) {
            $('#BaseDeleteConfirmModal').modal('hide');
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function addItem() {
    var item = {
        "security_level": $('#security_level').children('option:selected').val(),
        "article_name": $('#article_name').val(),
        "point_name": $('#point_name').val(),
        "base_name": base.base_name,
        "nr": $('#item_nr').val(),
        "dx": $('#item_dx').val(),
        "ssbz": $('#item_ssbz').val(),
        "fxdj": $('#item_fxdj').val(),
        "sxh": $('#item_sxh').val()
    }

    $.ajax({
        url: "/KBAddItem",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(item),
        success: function(res) {
            //关闭对话框
            $("#ItemAddModal").modal("hide");
            //刷新页面
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function genFileList(attachements) {
     var mainContent = $('#main-content');
        mainContent.html('');
        var thead1 = ' <div class="table-responsive">'+
                          '<table class="table table-striped table-sm">' +
                              '<thead>' +
                              '<tr>' +
                                  '<th>文件名</th>' +
                                  '<th>文件类型</th>' +
                                  '<th style="text-align:center">操作</th>'
                              '</tr>' +
                              '</thead>' +
                              '<tbody>';
        var tbody = '';
        for(var i = 0; i < attachements.length; i++){
            var attachement = attachements[i];
            var path = attachement.uri.substring(attachement.uri.search("KBFile"));
            var baseFileName = attachement.uri.substring(attachement.uri.lastIndexOf("\\")+1);

            tbody += '<tr>' +
                         '<td><a href="' + path + '" target="_blank">'+attachement.filename+'</a></td>' +
                         '<td>'+attachement.type+'</td>' +
                         '<td style="text-align:center">\n' +
                           '<a type="button" class="layui-btn layui-btn-small layui-btn-primary" href="KBDownloadFile?bid=' + base.base_id + '&fileName=' + baseFileName + '">下载</a>\n' +
                           '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteFile(\'' + attachement.uid + '\')">删除</button>\n' +
                         '</td>\n' +
                     '</tr>';
        }

        var thead2 = '</tbody>'+
              '</table>'+
          '</div>';

        mainContent.append( thead1 + tbody +thead2);
}

function deleteFile(uid) {
    fileDeleteConfirmModal = $('#FileDeleteModal');
    fileDeleteConfirmModal.modal('show');

    fileDeleteConfirmModal.on('shown.bs.modal', function (){
        $('#deleteFile-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteFileConfirm(\'' + uid + '\')">确认</button>');
        fileDeleteConfirmModal.off('shown.bs.modal');
    });
}

function deleteFileConfirm(uid) {
    $.ajax({
            url: "/KBDeleteFile",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify( {"uid": uid} ),
            success: function(res) {
                //关闭对话框
                $("#FileDeleteModal").modal("hide");
                //刷新页面
                window.location.reload();
            },
            error: function() {
               console.log('error!');
           }
        });
}

function updateIndicator(id) {
    var indicator = indicators.find(function(cur) { return cur.indicatorId === id});
    var indicatorUpdateModal = $('#IndicatorUpdateModal');
    indicatorUpdateModal.modal('show');

    indicatorUpdateModal.on('shown.bs.modal', function () {
        $('#indicator-update-footer').html(
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
        '<button type="button" class="btn btn-primary" onclick="updateIndicatorConfirm(' + indicator.indicatorId + ')">确认修改</button>');
        $('#indicator_u_molecular').val(indicator.molecular);
        $('#indicator_u_denominator').val(indicator.denominator);
        $('#indicator_u_description').val(indicator.indicatorDescription);
        $('#indicator_u_input').val(indicator.input);

        indicatorUpdateModal.off('shown.bs.modal');
    });
}

function updateIndicatorConfirm(id) {
    var indicator = {
        "indicatorId": id,
        "molecular": $('#indicator_u_molecular').val(),
        "denominator": $('#indicator_u_denominator').val(),
        "indicatorDescription": $('#indicator_u_description').val(),
        "input": $('#indicator_u_input').val(),
    }

    $.ajax({
        url: "/KBIndicatorUpdate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(indicator),
        success: function(res) {//请求成功完成后要执行的方法
            console.log(res.result);
            $('#IndicatorUpdateModal').modal('hide');
            window.location.reload();
        },
        eroor: function() {
            console.log('error!');
        }
    });
}

function updateFunctionPoint(id) {
    var fPoint = functionPoints.find(function(cur) { return cur.fid === id});
    var fPointUpdateModal = $('#FunctionPointUpdateModal');
    fPointUpdateModal.modal('show');

    fPointUpdateModal.on('shown.bs.modal', function () {
        $('#fPoint-update-footer').html(
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
        '<button type="button" class="btn btn-primary" onclick="updateFunctionPointConfirm(' + fPoint.fid + ')">确认修改</button>');
        $('#functionPoint_u_serial').val(fPoint.serial);
        $('#functionPoint_u_description').val(fPoint.description);

        fPointUpdateModal.off('shown.bs.modal');
    });
}

function updateFunctionPointConfirm(id) {
    var fPoint = {
        "fid": id,
        "serial": $('#functionPoint_u_serial').val(),
        "description": $('#functionPoint_u_description').val(),
    }

    $.ajax({
        url: "/KBFunctionPointUpdate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(fPoint),
        success: function(res) {//请求成功完成后要执行的方法
            console.log(res.result);
            $('#FunctionPointUpdateModal').modal('hide');
            window.location.reload();
        },
        eroor: function() {
            console.log('error!');
        }
    });
}

function updateCheckItem(id) {
    var cItem = checkItems.find(function(cur) { return cur.cid === id});
    var cItemUpdateModal = $('#CheckItemUpdateModal');
    cItemUpdateModal.modal('show');

    cItemUpdateModal.on('shown.bs.modal', function () {
        $('#cItem-update-footer').html(
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
        '<button type="button" class="btn btn-primary" onclick="updateCheckItemConfirm(' + cItem.cid + ')">确认修改</button>');
        $('#checkItem_u_serial').val(cItem.serial);
        $('#checkItem_u_advice').val(cItem.advice);
        $('#checkItem_u_weight').val(cItem.weight);

        cItemUpdateModal.off('shown.bs.modal');
    });
}

function updateCheckItemConfirm(id) {
    var cItem = {
        "cid": id,
        "serial": $('#checkItem_u_serial').val(),
        "advice": $('#checkItem_u_advice').val(),
        "weight": $('#checkItem_u_weight').val(),
    }

    $.ajax({
        url: "/KBCheckItemUpdate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(cItem),
        success: function(res) {//请求成功完成后要执行的方法
            console.log(res.result);
            $('#CheckItemUpdateModal').modal('hide');
            window.location.reload();
        },
        eroor: function() {
            console.log('error!');
        }
    });
}

function deleteIndicator(id) {
    indicatorDeleteModal = $('#deleteConfirmModal');
    indicatorDeleteModal.modal('show');

    indicatorDeleteModal.on('shown.bs.modal', function (){
        $('#delete-body').html('确认删除该项？');
        $('#delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteIndicatorConfirm(' + id + ')">确认</button>');
        indicatorDeleteModal.off('shown.bs.modal');
    });
}

function deleteIndicatorConfirm(id) {
    $.ajax({
        url: "/KBIndicatorDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"indicatorId": id} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteConfirmModal').modal('hide');
            console.log('rest:' + res.node3Size);
            if(res.node3Size === 0) {
                deleteNode(2, res.superId);
            } else {
                window.location.reload();
            }
        },
        error: function() {
            console.log('error!');
        }
    });
}

function deleteFunctionPoint(id) {
    fPointDeleteModal = $('#deleteConfirmModal');
    fPointDeleteModal.modal('show');

    fPointDeleteModal.on('shown.bs.modal', function (){
        $('#delete-body').html('确认删除该项？');
        $('#delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteFunctionPointConfirm(' + id + ')">确认</button>');
        fPointDeleteModal.off('shown.bs.modal');
    });
}

function deleteFunctionPointConfirm(id) {
    $.ajax({
        url: "/KBFunctionPointDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"fid": id} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteConfirmModal').modal('hide');
            console.log('rest:' + res.node3Size);
            if(res.node3Size === 0) {
                deleteNode(2, res.superId);
            } else {
                window.location.reload();
            }
        },
        error: function() {
            console.log('error!');
        }
    });
}

function deleteCheckItem(id) {
    cItemDeleteModal = $('#deleteConfirmModal');
    cItemDeleteModal.modal('show');

    cItemDeleteModal.on('shown.bs.modal', function (){
        $('#delete-body').html('确认删除该项？');
        $('#delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteCheckItemConfirm(' + id + ')">确认</button>');
        cItemDeleteModal.off('shown.bs.modal');
    });
}

function deleteCheckItemConfirm(id) {
    $.ajax({
        url: "/KBCheckItemDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"cid": id} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteConfirmModal').modal('hide');
            console.log('rest:' + res.node2Size);
            if(res.node2Size === 0) {
                deleteNode(1, res.superId);
            } else {
                window.location.reload();
            }
        },
        error: function() {
            console.log('error!');
        }
    });
}

function deleteNode(level, id) {
    console.log("    deleteNode level:" + level);
    nodeDeleteModal = $('#deleteNodeConfirmModal');
    nodeDeleteModal.modal('show');

    nodeDeleteModal.on('shown.bs.modal', function (){
        $('#node-delete-body').html('是否级联删除？');
        $('#node-delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="deleteNodeConfirm(' + level + ', ' + id + ')">确认</button>');
        nodeDeleteModal.off('shown.bs.modal');
    });
}

function deleteNodeConfirm(level, id) {
    $.ajax({
        url: "/KBNodeDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"nodeId": id, "level": level} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteNodeConfirmModal').modal('hide');
            console.log('rest:' + res.nodeSize + " level:" + level);
            if(res.nodeSize === 0) {
                deleteNodeConfirm(res.nextLevel, res.superId);
            } else {
                window.location.reload();
            }
        },
        error: function() {
            console.log('error!');
        }
    });
}

function addIndicator() {
    var indicator = {
        "base": base.base_name,
        "lv1": $('#lv1').val(),
        "lv2": $('#lv2').val(),
        "lv3": $('#lv3').val(),
        "molecular": $('#molecular').val(),
        "denominator": $('#denominator').val(),
        "description": $('#description').val(),
        "input": $('#input').val(),
    }

    $.ajax({
        url: "/KBAddIndicator",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(indicator),
        success: function(res) {
            //关闭对话框
            $("#IndicatorAddModal").modal("hide");
            //刷新页面
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function addFunctionPoint() {
    var fPoint = {
        "base": base.base_name,
        "lv1": $('#lv1fPoint').val(),
        "lv2": $('#lv2fPoint').val(),
        "lv3": $('#lv3fPoint').val(),
        "description": $('#fPointDescription').val(),
        "serial": $('#serial').val(),
    }

    $.ajax({
        url: "/KBAddFunctionPoint",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(fPoint),
        success: function(res) {
            //关闭对话框
            $("#FunctionPointAddModal").modal("hide");
            //刷新页面
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function addCheckItem() {
    var cItem = {
        "base": base.base_name,
        "lv1": $('#lv1cItem').val(),
        "lv2": $('#lv2cItem').val(),
        "advice": $('#cItemAdvice').val(),
        "serial": $('#cItemSerial').val(),
        "weight": $('#cItemWeight').val(),
    }

    $.ajax({
        url: "/KBAddCheckItem",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(cItem),
        success: function(res) {
            //关闭对话框
            $("#CheckItemAddModal").modal("hide");
            //刷新页面
            window.location.reload();
        },
        error: function() {
           console.log('error!');
       }
    });
}

function genBugLibList(bugItems) {
    var mainContent = $('#main-content');
    mainContent.html('');
    var thead1 = ' <div class="table-responsive">'+
                      '<table class="table table-striped table-sm">' +
                          '<thead>' +
                          '<tr>' +
                              '<th>漏洞编号</th>' +
                              '<th>漏洞名称</th>' +
                              '<th>风险级别</th>' +
                              '<th>漏洞类别</th>' +
                              '<th style="text-align:center">操作</th>'
                          '</tr>' +
                          '</thead>' +
                          '<tbody>';
    var tbody = '';
    bugItems.forEach(function (bugItem) {
        tbody += '<tr>' +
                     '<td>'+bugItem.bid+'</td>' +
                     '<td>'+bugItem.name+'</td>' +
                     '<td>'+bugItem.level+'</td>' +
                     '<td>'+bugItem.type+'</td>' +
                     '<td style="text-align:center">\n' +
                       '<a type="button" class="layui-btn layui-btn-small layui-btn-primary" href="toBugDetail?bid=' + bugItem.bid + '&baseId='+ base.base_id +'">详情</a>\n' +
                       '<button type="button" class="layui-btn layui-btn-small layui-btn-danger" onclick="deleteBugItem(' + bugItem.bid + ')">删除</button>\n' +
                     '</td>\n' +
                 '</tr>';

    });

    var thead2 = '</tbody>'+
          '</table>'+
      '</div>';

    mainContent.append( thead1 + tbody +thead2);
}

function pageInit(status, bid, baseId) {
    console.log(status);
    if(status === 0) {
        lockInput();
        $('#btnRow').html('<button class="btn btn-primary" onclick="editBugItem('+ bid +')">修改</button>');
    } else if(status === 1) {
        $('#btnRow').html('<button class="btn btn-primary" onclick="addBugItem('+ baseId +')">添加</button>');
    }
}

function lockInput() {
    $(':text').attr("readonly", "readonly");
    $('textarea').attr("readonly", "readonly");
}

function editBugItem(id) {
    $(':text').removeAttr("readonly");
    $('textarea').removeAttr("readonly");
    $('#bid').attr("readonly", "readonly");//不允许修改漏洞编号
    $('#btnRow').html(
            '<button class="btn btn-primary" onclick="updateBugItem('+id+')">保存修改</button>'+
            '<button class="btn btn-default" onclick="window.location.reload()">取消修改</button>');
}

function updateBugItem(id) {
    var solution = $('#solution').val();
    var bytesCount = 0;
    var i = 0
    for(i = 0; i < solution.length && bytesCount<3999; i++) {
      var c = solution.charAt(i);
      if (/^[\u0000-\u00ff]$/.test(c)){ //匹配单字节
        bytesCount += 1;
      } else {
        bytesCount += 2;
      }
    }
    var solution_2 = solution.substring(i);
    var solution = solution.substring(0, i);

    var bugItem = {
        "bid": id,
        "port": $('#port').val(),
        "type": $('#type').val(),
        "level": $('#level').val(),
        "cve_num": $('#cve_num').val(),
        "bugtraq": $('#bugtraq').val(),
        "name": $('#name').val(),
        "information": $('#information').val(),
        "cvss": $('#cvss').val(),
        "description": $('#description').val(),
        "solution": solution,
        "solution_2": solution_2,
    }
    //console.log(bugItem);

    $.ajax({
        url: "/KBBugLibUpdate",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(bugItem),
        success: function(res) {
            //刷新页面
            window.location.href = 'toBugDetail?bid=' + id + '&baseId='+ -1;
        },
        error: function() {
           console.log('error!');
       }
    });
}

function deleteBugItem(id) {
    bItemDeleteModal = $('#deleteConfirmModal');
    bItemDeleteModal.modal('show');

    bItemDeleteModal.on('shown.bs.modal', function (){
        $('#delete-body').html('确认删除该项？');
        $('#delete-footer').html(
           '<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>' +
           '<button type="button" class="btn btn-primary" onclick="bugItemDeleteConfirm(' + id + ')">确认</button>');
        bItemDeleteModal.off('shown.bs.modal')
    });
}

function bugItemDeleteConfirm(id) {
    $.ajax({
        url: "/KBBugLibDelete",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"bid": id} ),
        success: function(res) {//请求成功完成后要执行的方法
            $('#deleteConfirmModal').modal('hide');
            window.location.reload();
        },
        error: function() {
            console.log('error!');
        }
    });
}

function addBugItem(baseId) {
    var bugItem = {
        "bid": $('#bid').val(),
        "port": $('#port').val(),
        "type": $('#type').val(),
        "level": $('#level').val(),
        "cve_num": $('#cve_num').val(),
        "bugtraq": $('#bugtraq').val(),
        "name": $('#name').val(),
        "information": $('#information').val(),
        "cvss": $('#cvss').val(),
        "description": $('#description').val(),
        "solution": $('#solution').val(),
        "base_id": baseId,
    }
    if(bid == undefined) {
        alert("漏洞编号不得为空！");
        return;
    }

    $.ajax({
        url: "/KBAddBugLib",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(bugItem),
        success: function(res) {
            //刷新页面
            window.location.href = 'toBugDetail?bid=' + bugItem.bid + '&baseId='+ -1;
        },
        error: function() {
           console.log('error!');
       }
    });
}

function sentJSONToServer(jsonArray, info_table, file_name, sheet_name) {
    switch(info_table) {
        case "CHECK_ITEM":
        jsonArray.forEach(function (jsonItem) {
            var cItem = {
                "base": file_name.substring(0, file_name.lastIndexOf('.')),
                "lv1": jsonItem['检查范围'],
                "lv2": jsonItem['检查项名称'],
                "advice": jsonItem['检查意见'],
                "serial": jsonItem['检查项编号'],
                "weight": jsonItem['权重'],
            }
            insertCheckItem(cItem);
        });
        break;

        case "ITEM":
        jsonArray.forEach(function (jsonItem) {
            if(contains('检测项（二级）', Object.keys(jsonItem))) { // 该行有二级
                var item2 = {
                    "aqjb": '二级',
                    "article_name": sheet_name,
                    "point_name": jsonItem['检测点'],
                    "base_name": file_name.substring(0, file_name.lastIndexOf('.')),
                    "nr": jsonItem['检测项（二级）'],
                    "dx": jsonItem['检测实施（二级）'].substring(jsonItem['检测实施（二级）'].indexOf('b) 测评对象：') + 8, jsonItem['检测实施（二级）'].indexOf('c) 测评实施') - 2),
                    "ssbz": jsonItem['检测实施（二级）'].substring(jsonItem['检测实施（二级）'].indexOf('c) 测评实施') + 3),
                    "fxdj": '-',
                    "sxh": 0,
                }
                insertItem(item2);
//                console.log(item2);
            }
            if(contains('检测项（三级）', Object.keys(jsonItem))) {
                var item3 = {
                    "aqjb": '三级',
                    "article_name": sheet_name,
                    "point_name": jsonItem['检测点'],
                    "base_name": file_name.substring(0, file_name.lastIndexOf('.')),
                    "nr": jsonItem['检测项（三级）'],
                    "dx": jsonItem['检测实施（三级）'].substring(jsonItem['检测实施（三级）'].indexOf('b) 测评对象：') + 8, jsonItem['检测实施（三级）'].indexOf('c) 测评实施') - 2),
                    "ssbz": jsonItem['检测实施（三级）'].substring(jsonItem['检测实施（三级）'].indexOf('c) 测评实施') + 3),
                    "fxdj": '-',
                    "sxh": 0,
                }
                insertItem(item3);
//                console.log(item3);
            }
        });
        break;
    }
}

function contains(obj, arr) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;
        }
    }
    return false;
}

function insertBase(file_name, info_table) {
    var base_class, base_type;
    switch(info_table) {
        case "CHECK_ITEM":
        base_class = 1;
        base_type = 2;
        break;

        case "ITEM":
        base_class = 0;
        base_type = 0;
        break;
    }

    $.ajax({
    //先插入新知识库
        url: "/KBAddBase",
        async: false,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify( {"base_name": file_name.substring(0, file_name.lastIndexOf('.')), "zt": 0, "base_class": base_class, "base_type": base_type} ),
        success: function(res) {
            if(res.result === 0) {
                //成功，继续执行其它内容
            } else {
                alert('该知识库已存在！');
                window.location.reload();
            }
        },
        error: function() {
           console.log('Base insert error!');
       }
    });
}

function insertCheckItem(cItem) {
    $.ajax({
        url: "/KBAddCheckItem",
        async: false,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(cItem),
        success: function(res) {
            window.location.reload();
        },
        error: function() {
           console.log('CheckItem insert error!');
       }
    });
}

function insertItem(item) {
    $.ajax({
        url: "/KBAddItem",
        async: false,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(item),
        success: function(res) {
            window.location.reload();
        },
        error: function() {
           console.log(item);
           console.log('error!');
       }
    });
}