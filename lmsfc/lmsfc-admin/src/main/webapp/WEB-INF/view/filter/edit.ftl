<script type="text/javascript">

    $(document).on('bjui.beforeCloseDialog', function(e) {
        var $dialog = $(e.target)
        // do something...
        $dialog.navtab('reloadForm', true);
    })
</script>

<div class="bjui-pageContent">
    <form action="${rc.contextPath}/filter/edit" class="pageForm" data-toggle="validate" method="post">
        <#--<input type="hidden" name="dialog.id" value="edce142bc2ed4ec6b623aacaf602a4de">-->
        <input type="hidden" name="filter.id" value="${filter.id?default("")}">
        <table class="table table-condensed table-hover">
            <tbody>
            <tr>
                <td colspan="2" align="center"><h4>Edit Filter</h4></td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_filterName" class="control-label x90">Filter Name:</label>
                    <input type="text" name="filter.filterName" id="j_dialog_filterName" value="${filter.filterName?default("")}"
                           data-rule="Filter Name:required;length[2~50];" data-tip="请输入Filter Name" size="20">
                </td>
                <td>
                    <label for="j_dialog_filterClassName" class="control-label x90">FilterClass Name：</label>
                    <input type="text" name="filter.filterClassName" id="j_dialog_filterClassName" value="${filter.filterClassName?default("")}"
                           data-rule="required;length[~50]" size="20">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_filterClassParams" class="control-label x90">FilterClassParams：</label>
                    <input type="text" name="filter.filterClassParams" id="j_dialog_filterClassParams" value="${filter.filterClassParams?default("")}"
                           data-rule="required;length[~50]" size="20">
                </td>
                <td>
                    <label for="j_dialog_paramType" class="control-label x90">Param Type:</label>
                    <#--<input type="text" name="filter.paramType" id="j_dialog_paramType" value="" data-rule="required" size="10">-->
                    <select name="filter.paramType" id="j_dialog_paramType" data-toggle="selectpicker" data-rule="required">
                        <option value=""> -- 请选择 -- </option>
                        <option value="String" <#if filter.paramType=="String">selected</#if>>String</option>
                        <option value="Node" <#if filter.paramType=="Node">selected</#if>>Node</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label for="j_dialog_setParamMethodName" class="control-label x90">SetParamMethodName:</label>
                <#--<input type="text" name="filter.paramType" id="j_dialog_paramType" value="" data-rule="required" size="10">-->
                    <select name="filter.setParamMethodName" id="j_setParamMethodName" data-toggle="selectpicker" data-rule="required">
                        <option value=""> -- 请选择 -- </option>
                        <option value="Construct">Construct</option>
                    </select>
                </td>
            </tr>

            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="submit" class="btn-default">保存</button></li>
    </ul>
</div>