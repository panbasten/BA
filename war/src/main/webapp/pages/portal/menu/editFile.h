<form method="POST" id="portal_edit_form">

	<fly:verticalLayout margin="20">
		<fly:labelObject text="${text}" buddy="fs" style="margin:0 0 5px 0;" />
		<textarea id="fs" name="fs" size="12">
			${fileText}
		</textarea>
	</fly:verticalLayout>
	
	<fly:inputText id="rootDir" name="rootDir" type="hidden" value="${rootDir}" />
	<fly:inputText id="fileName" name="fileName" type="hidden" value="${fileName}" />
	<fly:inputText id="category" name="category" type="hidden" value="${category}" />

</form>
