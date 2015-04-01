<script type="text/javascript">
    $(document).on('bjui.beforeCloseDialog', function(e) {
        var $dialog = $(e.target)
        $dialog.navtab('reloadForm', true);
    })
</script>

<div class="bjui-pageContent">
    <form action="${rc.contextPath}/filterRule/edit" class="pageForm" data-toggle="validate" method="post">
    <#--<input type="hidden" name="dialog.id" value="edce142bc2ed4ec6b623aacaf602a4de">-->
        <input type="hidden" name="filterRule.id" value="${filterRule.id?default("")}">
        <table class="table table-condensed table-hover">
            <tbody>
            <tr>
                <td colspan="2" align="center"><h4>Edit Filter Rule</h4></td>
            </tr>
            <tr>
                <td>
                    <label for="j_dialog_filterName" class="control-label x90">Filter Rule Name:</label>
                    <input type="text" name="filterRule.name" id="j_dialog_name" value="${filterRule.name?default("")}"
                           data-rule="required;length[2~50];" size="20">
                </td>
                <td>
                    <label for="j_dialog_sourceDomain" class="control-label x90">Source Domain：</label>
                    <input type="text" name="filterRule.sourceDomain" id="j_dialog_sourceDomain" value="${filterRule.sourceDomain?default("")}"
                           data-rule="required;length[~50];url;" size="20">
                </td>
            </tr>
            <#--<tr>-->
                <#--<td colspan="2" align="right"><div id="multipleLocations2"></div></td>-->
            <#--</tr>-->

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

<#--<div id="templates_container" class="hidden">-->
    <#--<div id="multipleLocationsTemplate" style="text-align: center; padding:4px 0px 4px 0px;">-->
        <#--<label for="startDateMultiLocations_0">From</label>-->
        <#--<input type="date" id="startDateMultiLocations_0" name="startDateMultiLocations_0" title="Start date in new location" onkeyup="" />-->
        <#--<label for="multiLocation_0">Location</label>-->
        <#--<select id="multiLocation_0" title="Where do you live?" onchange="" name="multiLocation_0">-->
            <#--<option value="locationEngland" selected="selected">England</option>-->
            <#--<option value="locationScotland">Scotland</option>-->
        <#--</select>-->
        <#--<input type="button" value="&nbsp;-&nbsp;" id="deleteLocation_0" title="Delete this location"-->
               <#--onclick="$(document).dynamicForm('remove', '_anchor_', this);" />-->
        <#--<input type="button" value="&nbsp;+&nbsp;" id="addLocation_0" title="Add a location"-->
               <#--onclick="$(document).dynamicForm('add', '_anchor_', this);" />-->
        <#--<br />-->
    <#--</div>-->
<#--</div>-->

<#--<script type="text/javascript">-->
    <#--$(document).ready(function() {-->
        <#--$(document).dynamicForm('set', '#multipleLocations2', '#multipleLocationsTemplate')-->
                <#--.dynamicForm('init');-->
    <#--});-->

<#--</script>-->