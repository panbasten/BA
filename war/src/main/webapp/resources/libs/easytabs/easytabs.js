/*
 * jQuery EasyTabs plugin 3.1.1
 *
 * Copyright (c) 2010-2011 Steve Schwartz (JangoSteve)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Date: Tue Jan 26 16:30:00 2012 -0500
 */
( function($) {

  $.easytabs = function(container, options) {

        // Attach to plugin anything that should be available via
        // the $container.data('easytabs') object
    var plugin = this,
        $container = $(container),

        defaults = {
          animate: true,
          panelActiveClass: "ui-tab-state-active",
          tabActiveClass: "ui-tab-state-active",
          defaultTab: "li:first-child",
          animationSpeed: "normal",
          tabs: "> ul > li",
          createTab: "<li><a href='##tabId'>#modifyTag#tabText#closeButton</a></li>",
          updateHash: false,
          cycle: false,
          collapsible: false,
          collapsedClass: "collapsed",
          collapsedByDefault: true,
          uiTabs: false,
          transitionIn: 'fadeIn',
          transitionOut: 'fadeOut',
          transitionInEasing: 'swing',
          transitionOutEasing: 'swing',
          transitionCollapse: 'slideUp',
          transitionUncollapse: 'slideDown',
          transitionCollapseEasing: 'swing',
          transitionUncollapseEasing: 'swing',
          containerClass: "",
          tabsClass: "",
          tabClass: "",
          panelClass: "",
          onBeforeSelect: "",
          onAfterSelect: "",
          onSave: "",
          onDiscard: "",
          cache: true,
          closable: false,
          closePanel: true,
          checkModify: false,
          modifyCloseTip: "Are you sure remove the tab?",
          closeButton: "<span class='ui-button ui-icon ui-icon-close'></span>",
          modifyTag: "<span class='ui-modify-tag'></span>",
          panelContext: $container
        },

        // Internal instance variables
        // (not available via easytabs object)
        $defaultTab,
        $defaultTabLink,
        transitions,
        lastHash,
        skipUpdateToHash,
        animationSpeeds = {
    	  none: 0,
          fast: 200,
          normal: 400,
          slow: 600
        },

        // Shorthand variable so that we don't need to call
        // plugin.settings throughout the plugin code
        settings;

    // =============================================================
    // Functions available via easytabs object
    // =============================================================

    plugin.init = function() {

      plugin.settings = settings = $.extend({}, defaults, options);
      
      // Add jQuery UI's crazy class names to markup,
      // so that markup will match theme CSS
      if ( settings.uiTabs ) {
        settings.tabActiveClass = 'ui-tabs-selected';
        settings.containerClass = 'ui-tabs ui-widget ui-widget-content ui-corner-all';
        settings.tabsClass = 'ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all';
        settings.tabClass = 'ui-state-default ui-corner-top';
        settings.panelClass = 'ui-tabs-panel ui-widget-content ui-corner-bottom';
      }

      // If collapsible is true and defaultTab specified, assume user wants defaultTab showing (not collapsed)
      if ( settings.collapsible && options.defaultTab !== undefined && options.collpasedByDefault === undefined ) {
        settings.collapsedByDefault = false;
      }

      // Convert 'normal', 'fast', and 'slow' animation speed settings to their respective speed in milliseconds
      if ( typeof(settings.animationSpeed) === 'string' ) {
        settings.animationSpeed = animationSpeeds[settings.animationSpeed];
      }

      $('a.anchor').remove().prependTo('body');

      // Store easytabs object on container so we can easily set
      // properties throughout
      $container.data('easytabs', {});

      plugin.setTransitions();

      plugin.getTabs();

      addClasses();

      setDefaultTab();

      bindToTabClicks();

      initHashChange();

      initCycle();

      // Append data-easytabs HTML attribute to make easy to query for
      // easytabs instances via CSS pseudo-selector
      $container.attr('data-easytabs', true);
    };

    // Set transitions for switching between tabs based on options.
    // Could be used to update transitions if settings are changes.
    plugin.setTransitions = function() {
      transitions = ( settings.animate ) ? {
          show: settings.transitionIn,
          hide: settings.transitionOut,
          speed: settings.animationSpeed,
          collapse: settings.transitionCollapse,
          uncollapse: settings.transitionUncollapse,
          halfSpeed: settings.animationSpeed / 2
        } :
        {
          show: "show",
          hide: "hide",
          speed: 0,
          collapse: "hide",
          uncollapse: "show",
          halfSpeed: 0
        };
    };
    
    plugin.initTab = function(tab){
    	var $tab = tab,
    	$a = $tab.children('a'),

    	// targetId is the ID of the panel, which is either the
    	// `href` attribute for non-ajax tabs, or in the
    	// `dataTarget` attribute for ajax tabs since the `href` is
    	// the ajax URL
    	targetId = $tab.children('a').data('target')||$tab.children('a').attr('dataTarget');

    	$tab.data('easytabs', {});

    	// If the tab has a `dataTarget` attribute, and is thus an ajax tab
    	if ( targetId !== undefined && targetId !== null ) {
    		$tab.data('easytabs').ajax = $a.attr('href');
    	} else {
    		targetId = $a.attr('href');
    	}
    	targetId = targetId.match(/#([^\?]+)/)[0].substr(1);

    	$matchingPanel = settings.panelContext.find("#" + targetId);

    	// If tab has a matching panel, add it to panels
    	if ( $matchingPanel.length ) {
    		// Store panel height before hiding
    		$matchingPanel.data('easytabs', {
    			position: $matchingPanel.css('position'),
    			visibility: $matchingPanel.css('visibility')
    			});

    		// Don't hide panel if it's active (allows `getTabs` to be called manually to re-instantiate tab collection)
    		$matchingPanel.not(settings.panelActiveClass).hide();

    		plugin.panels = plugin.panels.add($matchingPanel);

    		$tab.data('easytabs').panel = $matchingPanel;

    	// Otherwise, remove tab from tabs collection
    	} else {
    		plugin.tabs = plugin.tabs.not($tab);
    	}
    };
    
    // Find and instantiate tabs and panels.
    // Could be used to reset tab and panel collection if markup is
    // modified.
    plugin.getTabs = function() {
      var $matchingPanel;

      // Find the initial set of elements matching the setting.tabs
      // CSS selector within the container
      plugin.tabs = $container.find(settings.tabs),

      // Instantiate panels as empty jquery object
      plugin.panels = $(),
      
      plugin.tabs.each(function(){
    	  plugin.initTab($(this));
      });
    };
    
    plugin.resolverModifyCloseTip =function($clicked) {
    	var tabId = $clicked.data("tabId"),
    		tabText = $clicked.data("tabText");
    	if($clicked.data("dataTarget")){
    		tabId = $clicked.data("dataTarget");
    	}
    	return settings.modifyCloseTip.replace(/#tabId/g,tabId).replace(/#tabText/g,tabText);
    };
    
    // Remove tab and fire callback
    plugin.removeTab = function($clicked, callback) {
    	if($clicked.data('modify')){
    		Flywet.cw("ConfirmDialog",null,{type:"confirm",text:plugin.resolverModifyCloseTip($clicked),
    			footerButtons:[
		    		{componentType : "fly:PushButton", type : "button", label : "保存", title : "保存并关闭", events : { 
		    			click:function(e,d){
		    				// 保存
		    				if(settings.onSave){
		  	    			  	eval(settings.onSave+"($clicked);");
		  	    		  	}
		    				plugin.removeSilence($clicked, callback);
		    				d.target.hide();
		    			} }},
		    		{componentType : "fly:PushButton", type : "button", label : "放弃", title : "放弃保存并关闭", events : { 
		    			click:function(e,d){
		    				if(settings.onDiscard){
		  	    			  	eval(settings.onDiscard+"($clicked);");
		  	    		  	}
		    				plugin.removeSilence($clicked, callback);
		    				d.target.hide();
		    			} }},
		    		{componentType : "fly:PushButton", type : "button", label : "取消", title : "取消", events : { click:"hide" }}
		    	]});
    	}else{
    		plugin.removeSilence($clicked, callback);
    	}
    };
    
    plugin.removeSilence = function($clicked, callback) {
    	var index = $clicked.parent().index(),
    	$tab = $clicked.parent(),
    	$targetPanel = $tab.data('easytabs').panel;
    	if($clicked.data('closePanel')){
    		Flywet.destroyWidget($targetPanel);
    	}
    	
    	if($tab.hasClass(settings.tabActiveClass)){
    		Flywet.destroyWidget($tab);
    		plugin.tabs = $container.find(settings.tabs);
    		var newIndex = index == plugin.tabs.length ? index - 1 : index;
    		plugin.selectTab($(plugin.tabs[newIndex]).children("a").first());
    	}else{
    		Flywet.destroyWidget($tab);
    		plugin.tabs = $container.find(settings.tabs);
    	}
    };
    
    plugin.getActiveTab = function(){
    	var $tab;
    	for(var i=0;i<plugin.tabs.length;i++){
			$tab = $(plugin.tabs[i]);
			if($tab.hasClass(settings.tabActiveClass)){
				return $tab.children("a").first();
			}
		}
    	return $tab
    };
    
    plugin.getTab = function(tabSelector){
    	var $tab;
    	
    	if(tabSelector == undefined || tabSelector == null){
    		return plugin.getActiveTab();
    	}

        // Find tab container that matches selector (like 'li#tab-one' which contains tab link)
        if ( ($tab = plugin.tabs.filter(tabSelector)).length === 0 ) {

          // Find direct tab link that matches href (like 'a[href="#panel-1"]')
          if ( ($tab = plugin.tabs.find("a[href='" + tabSelector + "']")).length === 0 ) {

            // Find direct tab link that matches selector (like 'a#tab-1')
            if ( ($tab = plugin.tabs.find("a" + tabSelector)).length === 0 ) {

              // Find direct tab link that matches dataTarget (lik 'a[dataTarget="#panel-1"]')
              if ( ($tab = plugin.tabs.find("[dataTarget='" + tabSelector + "']")).length === 0 ) {

                // Find direct tab link that ends in the matching href (like 'a[href$="#panel-1"]', which would also match http://example.com/currentpage/#panel-1)
                if ( ($tab = plugin.tabs.find("a[href$='" + tabSelector + "']")).length === 0 ) {

                  $.error('Tab \'' + tabSelector + '\' does not exist in tab set');
                }
              }
            }
          }
        } else {
          // Select the child tab link, since the first option finds the tab container (like <li>)
          $tab = $tab.children("a").first();
        }
        return $tab;
    };
    
    plugin.matchActiveTab = function($clicked) {
    	if( ! $clicked.hasClass(settings.tabActiveClass) || ! $targetPanel.hasClass(settings.panelActiveClass) ){
    		return false;
    	}
    	return true;
    };
    
    // Select tab and fire callback
    plugin.selectTab = function($clicked, callback) {
      if(settings.onBeforeSelect){
	      eval(settings.onBeforeSelect+"($clicked,plugin.getActiveTab());");
	  }
      var url = window.location,
          hash = url.hash.match(/^[^\?]*/)[0],
          $targetPanel = $clicked.parent().data('easytabs').panel,
          ajaxUrl = $clicked.parent().data('easytabs').ajax;

      // Tab is collapsible and active => toggle collapsed state
      if( settings.collapsible && ! skipUpdateToHash && ($clicked.hasClass(settings.tabActiveClass) || $clicked.hasClass(settings.collapsedClass)) ) {
        plugin.toggleTabCollapse($clicked, $targetPanel, ajaxUrl, callback);

      // Tab is not active and panel is not active => select tab
      } else if( ! $clicked.hasClass(settings.tabActiveClass) || ! $targetPanel.hasClass(settings.panelActiveClass) ){
        activateTab($clicked, $targetPanel, ajaxUrl, callback);

      // Cache is disabled => reload (e.g reload an ajax tab).
      } else if ( ! settings.cache ){
        activateTab($clicked, $targetPanel, ajaxUrl, callback);
      }
      
      if(settings.onAfterSelect){
	      eval(settings.onAfterSelect+"(plugin.getActiveTab());");
	  }
    };

    // Toggle tab collapsed state and fire callback
    plugin.toggleTabCollapse = function($clicked, $targetPanel, ajaxUrl, callback) {
      plugin.panels.stop(true,true);

      if( fire($container,"easytabs:before", [$clicked, $targetPanel, settings]) ){
        plugin.tabs.filter("." + settings.tabActiveClass).removeClass(settings.tabActiveClass).children().removeClass(settings.tabActiveClass);

        // If panel is collapsed, uncollapse it
        if( $clicked.hasClass(settings.collapsedClass) ){

          // If ajax panel and not already cached
          if( ajaxUrl && (!settings.cache || !$clicked.parent().data('easytabs').cached) ) {
            $container.trigger('easytabs:ajax:beforeSend', [$clicked, $targetPanel]);

            $targetPanel.load(ajaxUrl, function(response, status, xhr){
              $clicked.parent().data('easytabs').cached = true;
              $container.trigger('easytabs:ajax:complete', [$clicked, $targetPanel, response, status, xhr]);
            });
          }

          // Update CSS classes of tab and panel
          $clicked.parent()
            .removeClass(settings.collapsedClass)
            .addClass(settings.tabActiveClass)
            .children()
              .removeClass(settings.collapsedClass)
              .addClass(settings.tabActiveClass);

          $targetPanel
            .addClass(settings.panelActiveClass)
            [transitions.uncollapse](transitions.speed, settings.transitionUncollapseEasing, function(){
              $container.trigger('easytabs:midTransition', [$clicked, $targetPanel, settings]);
              if(typeof callback == 'function') callback();
              setTimeout( function(){ resizeSub($container); }, 0);
            });

        // Otherwise, collapse it
        } else {

          // Update CSS classes of tab and panel
          $clicked.addClass(settings.collapsedClass)
            .parent()
              .addClass(settings.collapsedClass);

          $targetPanel
            .removeClass(settings.panelActiveClass)
            [transitions.collapse](transitions.speed, settings.transitionCollapseEasing, function(){
              $container.trigger("easytabs:midTransition", [$clicked, $targetPanel, settings]);
              if(typeof callback == 'function') callback();
            });
        }
      }
      
    };


    // Find tab with target panel matching value
    plugin.matchTab = function(hash) {
      return plugin.tabs.find("[href='" + hash + "'],[dataTarget='" + hash + "']").first();
    };

    // Find panel with `id` matching value
    plugin.matchInPanel = function(hash) {
      return ( hash ? plugin.panels.filter(':has(' + hash + ')').first() : [] );
    };

    // Select matching tab when URL hash changes
    plugin.selectTabFromHashChange = function() {
      var hash = window.location.hash.match(/^[^\?]*/)[0],
          $tab = plugin.matchTab(hash),
          $panel;

      if ( settings.updateHash ) {

        // If hash directly matches tab
        if( $tab.length ){
          skipUpdateToHash = true;
          plugin.selectTab( $tab );

        } else {
          $panel = plugin.matchInPanel(hash);

          // If panel contains element matching hash
          if ( $panel.length ) {
            hash = '#' + $panel.attr('id');
            $tab = plugin.matchTab(hash);
            skipUpdateToHash = true;
            plugin.selectTab( $tab );

          // If default tab is not active...
          } else if ( ! $defaultTab.hasClass(settings.tabActiveClass) && ! settings.cycle ) {

            // ...and hash is blank or matches a parent of the tab container or
            // if the last tab (before the hash updated) was one of the other tabs in this container.
            if ( hash === '' || plugin.matchTab(lastHash).length || $container.closest(hash).length ) {
              skipUpdateToHash = true;
              plugin.selectTab( $defaultTabLink );
            }
          }
        }
      }
    };

    // Cycle through tabs
    plugin.cycleTabs = function(tabNumber){
      if(settings.cycle){
        tabNumber = tabNumber % plugin.tabs.length;
        $tab = $( plugin.tabs[tabNumber] ).children("a").first();
        skipUpdateToHash = true;
        plugin.selectTab( $tab, function() {
          setTimeout(function(){ plugin.cycleTabs(tabNumber + 1); }, settings.cycle);
        });
      }
    };

    // Convenient public methods
    plugin.publicMethods = {
      select: function(tabSelector){
    	  plugin.selectTab(plugin.getTab(tabSelector));
      },
      
      isActive: function(tabSelector){
    	  var at = plugin.getActiveTab();
    	  return (at.attr("href")==tabSelector || at.attr("dataTarget")==tabSelector);
      },
      
      hasMatch: function(hash){
    	  return (plugin.matchTab(hash).length>0);
      },
      
      setTabModify: function(cfg) {
    	  var $taba = plugin.getTab(cfg.tabSelector);
    	  if(cfg.modify){
    		  $taba.find("span.ui-modify-tag").html("*");
    		  $taba.data("modify", true);
    	  }else{
    		  $taba.find("span.ui-modify-tag").html("");
    		  $taba.data("modify", false);
    	  }
      },
      
      addTab: function(newTab) {
    	  var createTabString = settings.createTab.replace(/#tabId/g,newTab.tabId).replace(/#tabText/g,newTab.tabText);
    	  
    	  var closable = (newTab.closable)?newTab.closable:settings.closable;
    	  if(closable) {
    		  createTabString = createTabString.replace(/#closeButton/g, settings.closeButton);
    	  } else {
    		  createTabString = createTabString.replace(/#closeButton/g, "");
    	  }
    	  
    	  var checkModify = (newTab.checkModify!=undefined)?newTab.checkModify:settings.checkModify;
    	  if(checkModify) {
    		  createTabString = createTabString.replace(/#modifyTag/g, settings.modifyTag);
    	  } else {
    		  createTabString = createTabString.replace(/#modifyTag/g, "");
    	  }
    	  
    	  var $tab = $(createTabString);
    	  $(plugin.tabs).parent().append($tab);
    	  plugin.tabs = $container.find(settings.tabs);
    	  
    	  plugin.initTab($tab);
    	  
    	  $tab.addClass(settings.tabClass);
    	  
    	  var $taba = $tab.children("a").first();
    	  if(newTab.exdata){
    		  $taba.data("exdata",newTab.exdata);
    	  }
    	  
    	  if(newTab.dataTarget){
    		  $taba.attr("dataTarget",newTab.dataTarget);
    	  }
    	  
    	  $taba.data({
    		  tabId:newTab.tabId,
    		  tabText:newTab.tabText,
    		  checkModify:checkModify,
    		  modify:false
    	  });
    	  var closePanel = (newTab.closePanel!=undefined)?newTab.closePanel:settings.closePanel;
    	  $taba.data("closePanel",closePanel);
    	  
    	  $taba.bind('mouseover.easytabs', function(e) {
              var element = $(this);
              element.addClass('ui-tab-state-hover');
          })
          .bind('mouseout.easytabs', function(e) {
              var element = $(this);
              element.removeClass('ui-tab-state-hover');
          })
    	  .bind("click.easytabs", function(e) {
    		  var element = $(this);
              if($(e.target).is(':not(.ui-icon-close)')) {
	    		  settings.cycle = false;
	    		  skipUpdateToHash = false;
	    		  plugin.selectTab( $(this) );
              }
    		  e.preventDefault();
	      });
    	  
    	  // close
    	  if(closable) {
    		  var $tabClose = $tab.find("span.ui-icon-close");
    		  $tabClose.bind("click.easytabs",$taba,function(e){
    			  plugin.removeTab(e.data);
    			  e.preventDefault();
    		  });
    	  }
    	  
    	  plugin.selectTab($taba);
      }
    };

    // =============================================================
    // Private functions
    // =============================================================

    // Triggers an event on an element and returns the event result
    var fire = function(obj, name, data) {
      var event = $.Event(name);
      obj.trigger(event, data);
      return event.result !== false;
    }

    // Add CSS classes to markup (if specified), called by init
    var addClasses = function() {
      $container.addClass(settings.containerClass);
      plugin.tabs.parent().addClass(settings.tabsClass);
      plugin.tabs.addClass(settings.tabClass);
      plugin.panels.addClass(settings.panelClass);
    };

    // Set the default tab, whether from hash (bookmarked) or option,
    // called by init
    var setDefaultTab = function(){
      var hash = window.location.hash.match(/^[^\?]*/)[0],
          $selectedTab = plugin.matchTab(hash).parent(),
          $panel;

      // If hash directly matches one of the tabs, active on page-load
      if( $selectedTab.length === 1 ){
        $defaultTab = $selectedTab;
        settings.cycle = false;

      } else {
        $panel = plugin.matchInPanel(hash);

        // If one of the panels contains the element matching the hash,
        // make it active on page-load
        if ( $panel.length ) {
          hash = '#' + $panel.attr('id');
          $defaultTab = plugin.matchTab(hash).parent();

        // Otherwise, make the default tab the one that's active on page-load
        } else {
          $defaultTab = plugin.tabs.parent().find(settings.defaultTab);
          if ( $defaultTab.length === 0 ) {
            $.error("The specified default tab ('" + settings.defaultTab + "') could not be found in the tab set.");
          }
        }
      }

      $defaultTabLink = $defaultTab.children("a").first();

      activateDefaultTab($selectedTab);
    };

    // Activate defaultTab (or collapse by default), called by setDefaultTab
    var activateDefaultTab = function($selectedTab) {
      var defaultPanel,
          defaultAjaxUrl;

      if ( settings.collapsible && $selectedTab.length === 0 && settings.collapsedByDefault ) {
        $defaultTab
          .addClass(settings.collapsedClass)
          .children()
            .addClass(settings.collapsedClass);

      } else {

        defaultPanel = $( $defaultTab.data('easytabs').panel );
        defaultAjaxUrl = $defaultTab.data('easytabs').ajax;

        if ( defaultAjaxUrl && (!settings.cache || !$defaultTab.data('easytabs').cached) ) {
          $container.trigger('easytabs:ajax:beforeSend', [$defaultTabLink, defaultPanel]);
          defaultPanel.load(defaultAjaxUrl, function(response, status, xhr){
            $defaultTab.data('easytabs').cached = true;
            $container.trigger('easytabs:ajax:complete', [$defaultTabLink, defaultPanel, response, status, xhr]);
          });
        }

        $defaultTab.data('easytabs').panel
          .show()
          .addClass(settings.panelActiveClass);

        $defaultTab
          .addClass(settings.tabActiveClass)
          .children()
            .addClass(settings.tabActiveClass);
      }
    };

    // Bind tab-select funtionality to namespaced click event, called by
    // init
    var bindToTabClicks = function() {
      plugin.tabs.children("a").bind("click.easytabs", function(e) {

        // Stop cycling when a tab is clicked
        settings.cycle = false;

        // Hash will be updated when tab is clicked,
        // don't cause tab to re-select when hash-change event is fired
        skipUpdateToHash = false;

        // Select the panel for the clicked tab
        plugin.selectTab( $(this) );

        // Don't follow the link to the anchor
        e.preventDefault();
      });
    };
    
    var resizeSub = function(target){
    	target.find("div.ui-panel:visible,div.tabs-container:visible,div.layout:visible")
			.each(function() {
				$(this).triggerHandler("_resize", [ true ]);
			});
    };

    // Activate a given tab/panel, called from plugin.selectTab:
    //
    //   * fire `easytabs:before` hook
    //   * get ajax if new tab is an uncached ajax tab
    //   * animate out previously-active panel
    //   * fire `easytabs:midTransition` hook
    //   * update URL hash
    //   * animate in newly-active panel
    //   * update CSS classes for inactive and active tabs/panels
    //
    // TODO: This could probably be broken out into many more modular
    // functions
    var activateTab = function($clicked, $targetPanel, ajaxUrl, callback) {
      plugin.panels.stop(true,true);

      if( fire($container,"easytabs:before", [$clicked, $targetPanel, settings]) ){
        var $visiblePanel = plugin.panels.filter(":visible"),
            $panelContainer = $targetPanel.parent(),
            targetHeight,
            visibleHeight,
            heightDifference,
            showPanel,
            hash = window.location.hash.match(/^[^\?]*/)[0];

        if (settings.animate) {
          targetHeight = getHeightForHidden($targetPanel);
          visibleHeight = $visiblePanel.length ? setAndReturnHeight($visiblePanel) : 0;
          heightDifference = targetHeight - visibleHeight;
        }

        // Set lastHash to help indicate if defaultTab should be
        // activated across multiple tab instances.
        lastHash = hash;

        // TODO: Move this function elsewhere
        showPanel = function() {
          // At this point, the previous panel is hidden, and the new one will be selected
          $container.trigger("easytabs:midTransition", [$clicked, $targetPanel, settings]);

          // Gracefully animate between panels of differing heights, start height change animation *after* panel change if panel needs to contract,
          // so that there is no chance of making the visible panel overflowing the height of the target panel
          if (settings.animate && settings.transitionIn == 'fadeIn') {
            if (heightDifference < 0)
              $panelContainer.animate({
            	  opacity: 'show'
              }, transitions.halfSpeed).css({ 'min-height': '' });
          }

          if ( settings.updateHash && ! skipUpdateToHash ) {
            //window.location = url.toString().replace((url.pathname + hash), (url.pathname + $clicked.attr("href")));
            // Not sure why this behaves so differently, but it's more straight forward and seems to have less side-effects
            window.location.hash = '#' + $targetPanel.attr('id');
          } else {
            skipUpdateToHash = false;
          }

          $targetPanel
            [transitions.show](transitions.speed, settings.transitionInEasing, function(){
              $panelContainer.css({'min-height': ''}); // After the transition, unset the height
              $container.trigger("easytabs:after", [$clicked, $targetPanel, settings]);
              // callback only gets called if selectTab actually does something, since it's inside the if block
              if(typeof callback == 'function'){
                callback();
              }
              setTimeout( function(){ resizeSub($panelContainer); }, 0);
          });
        };

        if ( ajaxUrl && (!settings.cache || !$clicked.parent().data('easytabs').cached) ) {
          $container.trigger('easytabs:ajax:beforeSend', [$clicked, $targetPanel]);
          $targetPanel.load(ajaxUrl, function(response, status, xhr){
            $clicked.parent().data('easytabs').cached = true;
            $container.trigger('easytabs:ajax:complete', [$clicked, $targetPanel, response, status, xhr]);
          });
        }

        // Gracefully animate between panels of differing heights, start height change animation *before* panel change if panel needs to expand,
        // so that there is no chance of making the target panel overflowing the height of the visible panel
        if( settings.animate && settings.transitionOut == 'fadeOut' ) {
          if( heightDifference > 0 ) {
            $panelContainer.animate({
            	opacity: 'show'
            }, transitions.halfSpeed);
          } else {
            // Prevent height jumping before height transition is triggered at midTransition
            $panelContainer.css({ 'min-height': $panelContainer.height() });
          }
        }

        // Change the active tab *first* to provide immediate feedback when the user clicks
        plugin.tabs.filter("." + settings.tabActiveClass).removeClass(settings.tabActiveClass).children().removeClass(settings.tabActiveClass);
        plugin.tabs.filter("." + settings.collapsedClass).removeClass(settings.collapsedClass).children().removeClass(settings.collapsedClass);
        $clicked.parent().addClass(settings.tabActiveClass).children().addClass(settings.tabActiveClass);

        plugin.panels.filter("." + settings.panelActiveClass).removeClass(settings.panelActiveClass);
        $targetPanel.addClass(settings.panelActiveClass);

        if( $visiblePanel.length ) {
          $visiblePanel
            [transitions.hide](transitions.speed, settings.transitionOutEasing, showPanel);
        } else {
          $targetPanel
            [transitions.uncollapse](transitions.speed, settings.transitionUncollapseEasing, showPanel);
        }
      }
      
    };

    // Get heights of panels to enable animation between panels of
    // differing heights, called by activateTab
    var getHeightForHidden = function($targetPanel){

      if ( $targetPanel.data('easytabs') && $targetPanel.data('easytabs').lastHeight ) {
        return $targetPanel.data('easytabs').lastHeight;
      }

      // this is the only property easytabs changes, so we need to grab its value on each tab change
      var display = $targetPanel.css('display'),

          // Workaround, because firefox returns wrong height if element itself has absolute positioning
          height = $targetPanel
            .wrap($('<div>', {position: 'absolute', 'visibility': 'hidden', 'overflow': 'hidden'}))
            .css({'position':'relative','visibility':'hidden','display':'block'})
            .outerHeight();

      $targetPanel.unwrap();

      // Return element to previous state
      $targetPanel.css({
        position: $targetPanel.data('easytabs').position,
        visibility: $targetPanel.data('easytabs').visibility,
        display: display
      });

      // Cache height
      $targetPanel.data('easytabs').lastHeight = height;

      return height;
    };

    // Since the height of the visible panel may have been manipulated due to interaction,
    // we want to re-cache the visible height on each tab change, called
    // by activateTab
    var setAndReturnHeight = function($visiblePanel) {
      var height = $visiblePanel.outerHeight();

      if( $visiblePanel.data('easytabs') ) {
        $visiblePanel.data('easytabs').lastHeight = height;
      } else {
        $visiblePanel.data('easytabs', {lastHeight: height});
      }
      return height;
    };

    // Setup hash-change callback for forward- and back-button
    // functionality, called by init
    var initHashChange = function(){

      // enabling back-button with jquery.hashchange plugin
      // http://benalman.com/projects/jquery-hashchange-plugin/
      if(typeof $(window).hashchange === 'function'){
        $(window).hashchange( function(){
          plugin.selectTabFromHashChange();
        });
      } else if ($.address && typeof $.address.change === 'function') { // back-button with jquery.address plugin http://www.asual.com/jquery/address/docs/
        $.address.change( function(){
          plugin.selectTabFromHashChange();
        });
      }
    };

    // Begin cycling if set in options, called by init
    var initCycle = function(){
      var tabNumber;
      if (settings.cycle) {
        tabNumber = plugin.tabs.index($defaultTab);
        setTimeout( function(){ plugin.cycleTabs(tabNumber + 1); }, settings.cycle);
      }
    };


    plugin.init();
    
  };

  $.fn.easytabs = function(options, arguments) {
	if (typeof options == "string") {
	  return $(this).data('easytabs').publicMethods[options](arguments);
	}
	  
	return this.each(function() {
	  var $this = $(this),
	      plugin = $this.data('easytabs');
	  // Initialization was called with $(el).easytabs( { options } );
	  if (undefined === plugin) {
		  plugin = new $.easytabs(this, options);
		  $this.data('easytabs', plugin);
	  }
	});
  };

})(jQuery);

Flywet.widget.EasyTabs = function(cfg) {
	this.cfg = cfg;
	this.id = this.cfg.id;
	this.jqId = Flywet.escapeClientId(this.id);
	this.jq = $(this.jqId);
	this.easytabs= this.jq.easytabs(this.cfg);
	
	
};

Flywet.extend(Flywet.widget.EasyTabs, Flywet.widget.BaseWidget);

/**
 * 添加Tab节点
 * cfg:
 * 	tabId: TAB节点的ID，与panel中的div的ID对应
 *	tabText: TAB节点的显示文字
 *	dataTarget: TAB的表示区别属性
 *	closable: 是否可以关闭
 *	closePanel: 当关闭时是否同时关闭对应的Panel
 *	checkModify: 是否检查TAB的修改状态
 */
Flywet.widget.EasyTabs.prototype.addTab = function(cfg){
	this.jq.easytabs("addTab",cfg);
};

/**
 * 匹配
 */
Flywet.widget.EasyTabs.prototype.hasMatch = function(hash){
	return this.jq.easytabs("hasMatch",hash);
};

/**
 * 选择
 */
Flywet.widget.EasyTabs.prototype.select = function(tabSelector){
	this.jq.easytabs("select",tabSelector);
};

/**
 * 是否激活
 */
Flywet.widget.EasyTabs.prototype.isActive = function(tabSelector){
	return this.jq.easytabs("isActive",tabSelector);
};

/**
 * 设置Tab的修改状态
 * @param tabSelector tab选择器，如果为空返回当前激活的Tab
 * @param modify 是否修改
 */
Flywet.widget.EasyTabs.prototype.setTabModify = function(tabSelector,modify){
	this.jq.easytabs("setTabModify",{tabSelector:tabSelector,modify:modify});
};
