<script type="text/javascript">

    $('#filterDetailListTbl').on('afterdelete.bjui.tablefixed', function(e) {
        var $tbody = $(e.relatedTarget);
        console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！');
        $(this).navtab('reloadForm', true);
    })
</script>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${rc.contextPath}/filterDetail/list" method="post">
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
            <a href="${rc.contextPath}/filterDetail/toEdit/new" class="btn btn-default" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-mask" data-mask="true">添加</a>
            <#--<div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i> 双击行可编辑</div>&nbsp;-->
            <div class="pull-right">
                <div class="btn-group">
                    <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
                    <ul class="dropdown-menu right" role="menu">
                        <li><a href="book1.xlsx" data-toggle="doexport" data-confirm-msg="确定要导出信息吗？">导出<span style="color: green;">全部</span></a></li>
                        <li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>
                        <li class="divider"></li>
                        <li><a href="${rc.contextPath}/filterDetail/deleteBatch" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <form action="${rc.contextPath}/filterDetail/saveBatch" id="j_custom_form" class="pageForm" data-toggle="validate" method="post">
        <table id="filterDetailListTbl" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
            <thead>
                <tr>
                    <#--<th>No.</th>-->
                    <th data-order-field="filterRule.name">Filter Rule</th>
                    <th data-order-field="category">Category</th>
                    <th>Param1</th>
                    <th>Param2</th>
                    <th>Sub No</th>
                    <th>Filter</th>
                    <th>Parent Node</th>
                    <th data-order-field="updatedDt">Updated Date</th>
                    <th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
                    <th width="100">操作</th>
                </tr>
            </thead>
            <tbody>
            <#list pageResult.content as c>
                <tr data-id="${c.id}">
                    <#--<td>No.</td>-->
                    <td>${c.filterRule.name}</td>
                    <td>${c.category}</td>
                    <td>${c.paramValue1}</td>
                    <td>${c.paramValue2}</td>
                    <td>${c.subNum}</td>
                    <td>${c.filter.filterName}</td>
                    <td>
                        <#if c.filterRule.id!="">
                             ${c.filterRule.name?default("")}  -  ${c.parentNode.category} - ${c.parentNode.filter.filterName}
                        </#if>
                    </td>
                    <td>${c.updatedDt}</td>
                    <td><input type="checkbox" name="ids" data-toggle="icheck" value="${c.id}"></td>
                    <td>
                        <a href="${rc.contextPath}/filterDetail/toEdit/${c.id}" class="btn btn-default" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-mask" data-mask="true">编辑</a>
                        <#--<a href="${rc.contextPath}/filter/toEdit/${c.id}" class="btn btn-green" data-toggle="navtab" data-id="filterEditForm" data-title="编辑-${c.filterName}">编辑</a>-->
                        <a href="${rc.contextPath}/filterDetail/delete/${c.id}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删</a>
                    </td>
                </tr>

            </#list>
            </tbody>
        </table>
    </form>
</div>
<#include "*/common/pageFooter.ftl"/>