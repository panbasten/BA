<div id="${editorId}" class="hb-content-panels ui-tab-side-container hb-editor-content-height">
	<ul id="navigator-ul" class="ui-tabs hb-tab-navi">
		<bq:foreach items="${browseEntities}" var="entity">
			<li category="${entity.code}" id="${entity.id}">
				<a href="#editorContent-navi-${entity.code}">
					<div class="ui-navi-icon ui-navi-icon-${entity.code}"></div>
					${entity.desc}
				</a>
			</li>
		</bq:foreach>
	</ul>
	<div class="ui-tab-panel-container hb-editor-content-height-no-padding" >
		<bq:foreach items="${browseEntities}" var="entity">
			<bq:include src="navi/navi_${entity.code}.h" />
		</bq:foreach>
	</div>
</div>