<form method="post" id="portal_menu_form">

	<fly:verticalLayout margin="20">
		<div style="padding: 0 0 30px;">
		    <input type="radio" name="note" value="note1" checked="checked" onclick="changeNote('note1');" style="float:left;width:100px;" />
		    <div style="width:200px;float:left;text-align:left;">记事板1</div>
		    <input type="radio" name="note" value="note2" onclick="changeNote('note2');" style="float:left;width:100px;" />
		    <div style="float:left;width:100px;text-align:left;">记事板2</div>
		</div>
		<textarea id="fs" name="fs" rows="16">
			${miText}
		</textarea>
	</fly:verticalLayout>
	
	<fly:inputText id="miId" name="miId" type="hidden" value="${miId}" />

	<fly:includeJs src="page.js"/>

</form>
