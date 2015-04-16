<script type="text/javascript">

    $('#articleTaskListTbl').on('afterdelete.bjui.tablefixed', function(e) {
        var $tbody = $(e.relatedTarget);
        console.log('你删除了一条数据，还有['+ $tbody.find('> tr').length +']条数据！');
        $(this).navtab('reloadForm', true);
    })
</script>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${rc.contextPath}/articleTask/list" method="post">
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
            <a href="${rc.contextPath}/articleTask/toEdit/new" class="btn btn-default" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-mask" data-mask="true">添加</a>
            <#--<div class="alert alert-info search-inline"><i class="fa fa-info-circle"></i> 双击行可编辑</div>&nbsp;-->
            <div class="pull-right">
                <div class="btn-group">
                    <button type="button" class="btn-default dropdown-toggle" data-toggle="dropdown" data-icon="copy">批量操作<span class="caret"></span></button>
                    <ul class="dropdown-menu right" role="menu">
                        <li><a href="book1.xlsx" data-toggle="doexport" data-confirm-msg="确定要导出信息吗？">导出<span style="color: green;">全部</span></a></li>
                        <li><a href="book1.xlsx" data-toggle="doexportchecked" data-confirm-msg="确定要导出选中项吗？" data-idname="expids" data-group="ids">导出<span style="color: red;">选中</span></a></li>
                        <li class="divider"></li>
                        <li><a href="${rc.contextPath}/articleTask/deleteBatch" data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？" data-idname="delids" data-group="ids">删除选中</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent">
    <form action="${rc.contextPath}/articleTask/saveBatch" id="j_custom_form" class="pageForm" data-toggle="validate" method="post">
        <table id="articleTaskListTbl" data-toggle="tablefixed" data-width="100%" data-nowrap="true">
            <thead>
                <tr>
                    <#--<th>No.</th>-->
                    <th data-order-field="articleTask.name">Name</th>
                    <th data-order-field="articleTask.url">Article Url</th>
                    <th>Filter Rule</th>
                    <th>Batch Task</th>
                    <th>Type</th>
                    <th>State</th>
                    <th>Finish Time</th>
                    <th data-order-field="updatedDt">Updated Date</th>
                    <th width="26"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
                    <th width="100">操作</th>
                </tr>
            </thead>
            <tbody>
            <#list pageResult.content as c>
                <tr data-id="${c.id}">
                    <#--<td>No.</td>-->
                    <td>${c.name}</td>
                    <td>${c.url}</td>
                    <td>${c.filterRule.name}</td>
                    <td>${c.batchArticleTaskJob.name}</td>
                    <td>${c.type}</td>
                    <td>
                        <#assign key=c.state/>${artStateMap[key?string]}
                        <#--<#assign newValue=artStateMap[key]/>${newValue}-->
                    </td>
                    <td>${c.finishTime}</td>
                    <td>${c.updatedDt}</td>
                    <td><input type="checkbox" name="ids" data-toggle="icheck" value="${c.id}"></td>
                    <td>
                        <a href="${rc.contextPath}/articleTask/view/${c.id}" class="btn btn-default" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-mask" data-mask="true">查看</a>
                        <#if key == 0 || key == 110  || key ==112>
                            <a href="${rc.contextPath}/articleTask/runTask/${c.id}" class="btn btn-default" data-toggle="doajax" data-confirm-msg="确定要开始抓取？">抓取</a>
                        <#elseif key == 111>
                            <a href="#" class="btn btn-blue disabled" data-toggle="doajax">抓取</a>
                        <#elseif key == 120  || key ==122>
                            <a href="${rc.contextPath}/articleTask/runTask/${c.id}" class="btn btn-default" data-toggle="doajax" data-confirm-msg="确定要生成？">生成</a>
                        <#elseif key == 121>
                            <a href="#" class="btn btn-blue disabled" data-toggle="doajax">生成</a>
                        <#elseif key == 130  || key ==132>
                            <a href="${rc.contextPath}/articleTask/runTask/${c.id}" class="btn btn-default" data-toggle="doajax" data-confirm-msg="确定要组装？">组装</a>
                        <#elseif key == 131>
                            <a href="#" class="btn btn-blue disabled" data-toggle="doajax">组装</a>
                        <#elseif key == 140  || key ==142>
                            <a href="${rc.contextPath}/articleTask/runTask/${c.id}" class="btn btn-default" data-toggle="doajax" data-confirm-msg="确定要发布？">发布</a>
                        <#elseif key == 141>
                            <a href="#" class="btn btn-blue disabled" data-toggle="doajax">发布</a>
                        </#if>
                        <br>
                        <a href="${rc.contextPath}/articleTask/toEdit/${c.id}" class="btn btn-default" data-toggle="dialog" data-width="800" data-height="400" data-id="dialog-mask" data-mask="true">编辑</a>
                        <#--<a href="${rc.contextPath}/filter/toEdit/${c.id}" class="btn btn-green" data-toggle="navtab" data-id="filterEditForm" data-title="编辑-${c.filterName}">编辑</a>-->
                        <a href="${rc.contextPath}/articleTask/delete/${c.id}" class="btn btn-red" data-toggle="doajax" data-confirm-msg="确定要删除该行信息吗？">删除</a>
                    </td>
                </tr>

            </#list>
            </tbody>
        </table>
    </form>
</div>
<#include "*/common/pageFooter.ftl"/>