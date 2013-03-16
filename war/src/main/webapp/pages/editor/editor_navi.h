<div id="${editorId}" class="fly-content-panels ui-tab-side-container fly-editor-content-height">
	<ul id="navigator-ul" class="ui-tabs fly-tab-navi">
		<fly:foreach items="${browseEntities}" var="entity">
			<li category="${entity.code}" id="${entity.id}">
				<a href="#editorContent-navi-${entity.code}">
					<div class="ui-navi-icon ui-navi-icon-${entity.code}"></div>
					${entity.desc}
				</a>
			</li>
		</fly:foreach>
	</ul>
	<div class="ui-tab-panel-container fly-editor-content-height-no-padding" >
		<fly:foreach items="${browseEntities}" var="entity">
			<fly:include src="navi/navi_${entity.code}.h" />
		</fly:foreach>
	</div>
</div>