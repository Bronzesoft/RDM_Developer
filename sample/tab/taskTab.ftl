<#-- data from database.(list(String)) -->
<table border="1px,solid,#999;" style=" margin-top:10px;">
	<tr>
		<th style="width:40px;">Sn</th>
		<th style="width:340px;">Value</th>
	</tr>
	
	<#list task as k>
		<tr>
			<td style="text-align: center;">${k_index + 1}</td>
			<td>${k}</td>
		</tr>
	</#list>
</table>

Total row : ${task?size}<br>

<#list meetingPresence as presence>
	${presence.sn} ---- ${presence.name}<br>
</#list>


<#list meetingRecord as record>
	${record.sn}--------${record.userId}<br>
</#list>

<#list reviewExpertAction as review>
	${review.sn}----${review.userId}<br>
</#list>


<#list refrence as r>
	${r.name}----${r.address}----${r.extendName}----${r.fileSize}<br>
</#list>

<br>
<#-- array. -->
<#list array as a>
${a}
</#list>

<br>
<#-- list(Object[]). -->
<#list arrayList as item>
	<#list item as a>
		${a}
	</#list>
</#list>

<br>
<#-- map(value is a string). -->
<#list map?keys as item>
	${map[item]}
</#list>

<br>
<#-- map(value is an array) -->
<#list map2?keys as item>
	<#list map2[item] as m>
		${m}
	</#list>
</#list>

<br>
<#-- format date -->
${date?string("yyyy-MM-dd")}<br>
${date?string("yyyy/MM/dd")}<br>
${date?string("yyyy/MM/dd HH:mm:ss")}<br>
<p>
${date?date}<br>
${date?time}<br>
${date?datetime}<br>

<#assign date2 = date>
${date2?date}<br>
${date2?time}<br>
${date2?datetime}<br>

<#-- method -->
${method(3,4)}
