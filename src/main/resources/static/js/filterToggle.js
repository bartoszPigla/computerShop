		$('.tree-toggle').click(function() {
			$(this).parent().children('ul.tree').toggle(200);
		});

		$('.collapse').on(
				'shown.bs.collapse',
				function() {
					$(this).parent().find(".glyphicon-plus").removeClass(
							"glyphicon-plus").addClass("glyphicon-minus");
				}).on(
				'hidden.bs.collapse',
				function() {
					$(this).parent().find(".glyphicon-minus").removeClass(
							"glyphicon-minus").addClass("glyphicon-plus");
				});

		function showMe(domElementToHide, thisElement) {
			document.getElementById(domElementToHide).style.display = (thisElement.checked) ? "block"
					: "none";
		}