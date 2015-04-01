<script type="text/javascript">
    $('#tabledit1').on('afterdelete.bjui.tabledit', function(e) {
        var $tbody = $(e.relatedTarget);
        console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！');
        $("#pagerForm").submit();
    })
</script>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${rc.contextPath}/filter/list" method="post">
        <input type="hidden" name="pageCurrent" value="${pageResult.number + 1?int}">
        <input type="hidden" name="pageSize" value="${pageResult.size}">
        <input type="hidden" name="totalCount" value="${pageResult.totalElements}">
        <input type="hidden" name="orderField" value="${orderField}">
        <input type="hidden" name="orderDirection" value="${orderDirection}">
        <div class="bjui-searchBar">
            <label>姓名：</label><input type="text" value="" name="name" size="10">&nbsp;
            <!--<label>护照号：</label><input type="text" value="" name="passportno" size="8">&nbsp;
            <label>出生日期:</label><input type="text" value="" name="birthday" size="10">&nbsp;-->
            <button type="submit" class="btn-default" data-icon="search">查询</button>&nbsp;
            <a class="btn btn-orange" href="javascript:;" onclick="$(this).navtab('reloadForm', true)" data-icon="undo">清空查询</a>&nbsp;
            <div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i> 双击行可编辑</div>&nbsp;
            <div class="pull-right">
                <div class="btn-group">
                    <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
                    <ul class="dropdown-menu right" role="menu">
                        <li><a href="book1.xlsx" data-toggle="doexport" data-confirm-msg="确定要导出信息吗？">导出<span style="color: green;">全部</span></a></li>
                        <li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>
                        <li class="divider"></li>
                        <li><a href="${rc.contextPath}/filter/deleteBatch" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>
                    </ul>
                </div>
            </div>
            <button type="button" class="btn-green" data-toggle="tableditadd" data-target="#tabledit1" data-num="1" data-icon="plus">添加编辑行</button>&nbsp;
            <#--<button type="button" class="btn-green" onclick="$(this).tabledit('add', $('#tabledit1'), 2)">添加编辑行2</button>-->
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <form action="${rc.contextPath}/filter/saveBatch" id="j_custom_form" class="pageForm" data-toggle="validate" method="post">
        <table id="tabledit1" class="table table-bordered table-hover table-striped table-top" data-toggle="tabledit" data-initnum="0" data-action="${rc.contextPath}/filter/save">
            <thead>
            <tr data-idname="filter.id">
                <th>No.</th>
                <#--<th title="No."><input type="text" name="filterList[#index#].id" class="no" data-rule="required" value="1" size="2"></th>-->
                <th title="Filter Name" data-order-field="filterName"><input type="text" name="filter.filterName" data-rule="required" value="" size="10"></th>
                <th title="Filter Class Name"><input type="text" name="filter.filterClassName" data-rule="required" value="" size="10"></th>
                <th title="Filter Class Parameters"><input type="text" name="filter.filterClassParams" data-rule="required" value="" size="10"></th>
                <th title="Parameter Type"><input type="text" name="filter.paramType" data-rule="required" value="" size="5"></th>
                <th title="Updated Date" data-order-field="updatedDt"><input type="text" name="filter.updatedDt" data-rule="" class="j_custom_issuedate" data-toggle="datepicker" value="" size="10"></th>
                <th title="" data-addtool="true" width="100">
                    <a href="javascript:;" class="btn btn-green" data-toggle="dosave">保存</a>
                    <#--<a href="${rc.contextPath}/filter/delete" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>-->
                </th>
            </tr>
            </thead>
            <tbody>
            <#list pageResult.content as content>
                <tr data-id="${content.id}">
                    <td>${pageResult.content_index}</td>
                    <td>${content.filterName}</td>
                    <td>${content.filterClassName}</td>
                    <td>${content.filterClassParams}</td>
                    <td>${content.paramType}</td>
                    <td>${content.updatedDt}</td>
                    <td data-noedit="true">
                        <button type="button" class="btn-green" data-toggle="doedit">编辑</button>
                        <a href="${rc.contextPath}/filter/delete/${content.id}" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
                    </td>
                </tr>

            </#list>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="10">10</option>
                <option value="20">20</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${pageResult.totalElements} 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${pageResult.totalElements}" data-page-size="${pageResult.size}" data-page-current="${pageResult.number +1?int}">

    </div>
    <div>
        <ul>
            <li><button type="button" class="btn-close" data-icon="close">取消</button></li>
            <#--<li><button type="submit" class="btn-default" data-icon="save">全部保存</button></li>-->
        </ul>
    </div>
</div>