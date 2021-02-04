$(document).ready(function() {
	checkActiveSession();
	if (parseInt($("#smsContent").length) > 0) {
		$('#smsContent').countSms('#sms-counter');
	}
});

function imgError(image) {
	image.onerror = "";
	image.src = "images/profile-pic.jpg";
	return true;
}

function sendMessage(obj) {

	var contact = $(obj).parents("tr").find("input[name='parentContact']")
			.val();
	var message = $(obj).parents("tr").find("textarea[name='msgTextArea']")
			.val();
	$.ajax({
		url : "manualSmsAjax.html?smsContent=" + message + "&selectedStudent="
				+ contact,
		method : "post",
		success : function(a) {

			alert("SMS has been sent to "+ contact);
			$(obj).parents("tr").find("textarea[name='msgTextArea']").val("");
			$(obj).parents("tr").find("textarea[name='msgTextArea']").attr("placeholder","Type something...");

		}
	});
}

function checkActiveSession() {
	var url = location.href;
	if (url.indexOf("sessionList") == -1) {
		$.ajax({
			url : "checkActiveSession.json",
			method : "get",
			async :false, 
			success : function(a) {

				if (a.status == "0") {
					alert("Please activate atleast one session to continue...");
					location.href = "sessionList.html";
				} else {
					$("select[name='sessionPk']").val(a.status);
					if(url.indexOf("receiveStudentFee") != -1){
						getMonthYearForSession('feeMonth', 'feeYear');
					}
				}
			}
		});
	}

}

function getMonthYearForSession(monthId, yearId){
	
	var sessionPk = $("select[name='sessionPk']").val();
	$.ajax({
		url : "getYearAndMonthDropdownForSession.json?pk="+sessionPk,
		method : "get",
		success : function(response) {
			var yearList = response.yearList;
			var monthList = response.monthList;
			
			var yearOptions = "<option value=''>Select Year</option>";
			$.each(yearList, function(index, value){
				yearOptions = yearOptions + "<option value='"+value+"'>"+value+"</option>";
			})
			
			var monthOptions = "<option value=''>Select Month</option>";
			$.each(monthList, function(index, value){
				monthOptions = monthOptions + "<option value='"+value+"'>"+value+"</option>";
			})
			
			$("#"+monthId).html(monthOptions);
			$("#"+yearId).html(yearOptions);
		}
	});
	
}

function initDatatableWithoutPagination(tableId) {

	var myTable = $('#' + tableId)
	// .wrap("<div class='dataTables_borderWrap' />") //if you are applying
	// horizontal scrolling (sScrollX)
	.DataTable({
		bAutoWidth : false,
		/*
		 * "aoColumns": [ { "bSortable": false }, null, null,null ],
		 */
		"aaSorting" : [],

		// "bProcessing": true,
		// "bServerSide": true,
		// "sAjaxSource": "http://127.0.0.1/table.php" ,

		// ,
		"sScrollY" : "200px",
		"bPaginate" : false,
		"destroy" : true,
		// "sScrollX": "100%",
		// "sScrollXInner": "120%",
		// "bScrollCollapse": true,
		// Note: if you are applying horizontal scrolling (sScrollX) on a
		// ".table-bordered"
		// you may want to wrap the table inside a "div.dataTables_borderWrap"
		// element

		// "iDisplayLength": 50

		select : {
			style : 'multi'
		}
	});

	$.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

	new $.fn.dataTable.Buttons(
			myTable,
			{
				buttons : [
						{
							"extend" : "colvis",
							"text" : "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							columns : ':not(:first):not(:last)'
						},
						{
							"extend" : "copy",
							"text" : "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "csv",
							"text" : "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "excel",
							"text" : "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "pdf",
							"text" : "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "print",
							"text" : "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>Print</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							autoPrint : false,
							message : ''
						} ]
			});
	myTable.buttons().container().appendTo($('.tableTools-container'));

	// style the message box
	var defaultCopyAction = myTable.button(1).action();
	myTable
			.button(1)
			.action(
					function(e, dt, button, config) {
						defaultCopyAction(e, dt, button, config);
						$('.dt-button-info')
								.addClass(
										'gritter-item-wrapper gritter-info gritter-center white');
					});

	var defaultColvisAction = myTable.button(0).action();
	myTable
			.button(0)
			.action(
					function(e, dt, button, config) {

						defaultColvisAction(e, dt, button, config);

						if ($('.dt-button-collection > .dropdown-menu').length == 0) {
							$('.dt-button-collection')
									.wrapInner(
											'<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
									.find('a').attr('href', '#').wrap("<li />")
						}
						$('.dt-button-collection').appendTo(
								'.tableTools-container .dt-buttons')
					});

	// //

	setTimeout(function() {
		$($('.tableTools-container')).find('a.dt-button').each(function() {
			var div = $(this).find(' > div').first();
			if (div.length == 1)
				div.tooltip({
					container : 'body',
					title : div.parent().text()
				});
			else
				$(this).tooltip({
					container : 'body',
					title : $(this).text()
				});
		});
	}, 500);

	myTable.on('select', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop('checked',
					true);
		}
	});
	myTable.on('deselect', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop('checked',
					false);
		}
	});

	// ///////////////////////////////
	// table checkboxes
	$('th input[type=checkbox], td input[type=checkbox]')
			.prop('checked', false);

	// select/deselect all rows according to table header checkbox
	$(
			'#' + tableId + ' > thead > tr > th input[type=checkbox], #'
					+ tableId + '_wrapper input[type=checkbox]').eq(0).on(
			'click', function() {
				var th_checked = this.checked;// checkbox inside "TH" table
												// header
				try {
					$('#' + tableId).find('tbody > tr').each(function() {
						var row = this;
						if (th_checked)
							myTable.row(row).select();
						else
							myTable.row(row).deselect();
					});
				} catch (e) {
				}
				;
			});

	// select/deselect a row when the checkbox is checked/unchecked
	$('#' + tableId).on('click', 'td input[type=checkbox]', function() {
		try {
			var row = $(this).closest('tr').get(0);
			if (this.checked)
				myTable.row(row).deselect();
			else
				myTable.row(row).select();
		} catch (e) {
		}
		;
	});

	$(document).on('click', '#' + tableId + ' .dropdown-toggle', function(e) {
		e.stopImmediatePropagation();
		e.stopPropagation();
		e.preventDefault();
	});

	return myTable;
}

function initDatatableWithPagination(tableId) {

	var myTable = $('#' + tableId)
	// .wrap("<div class='dataTables_borderWrap' />") //if you are applying
	// horizontal scrolling (sScrollX)
	.DataTable({
		bAutoWidth : false,
		/*
		 * "aoColumns": [ { "bSortable": false }, null, null,null ],
		 */
		"aaSorting" : [],

		// "bProcessing": true,
		// "bServerSide": true,
		// "sAjaxSource": "http://127.0.0.1/table.php" ,

		// ,
		// "sScrollY": "200px",
		// "bPaginate": false,
		"destroy" : false,
		// "sScrollX": "100%",
		// "sScrollXInner": "120%",
		// "bScrollCollapse": true,
		// Note: if you are applying horizontal scrolling (sScrollX) on a
		// ".table-bordered"
		// you may want to wrap the table inside a "div.dataTables_borderWrap"
		// element

		// "iDisplayLength": 50

		select : {
			style : 'multi'
		}
	});

	$.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

	new $.fn.dataTable.Buttons(
			myTable,
			{
				buttons : [
						{
							"extend" : "colvis",
							"text" : "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							columns : ':not(:first):not(:last)'
						},
						{
							"extend" : "copy",
							"text" : "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "csv",
							"text" : "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "excel",
							"text" : "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "pdf",
							"text" : "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
							"className" : "btn btn-white btn-primary btn-bold"
						},
						{
							"extend" : "print",
							"text" : "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>Print</span>",
							"className" : "btn btn-white btn-primary btn-bold",
							autoPrint : false,
							message : ''
						} ]
			});
	myTable.buttons().container().appendTo($('.tableTools-container'));

	// style the message box
	var defaultCopyAction = myTable.button(1).action();
	myTable
			.button(1)
			.action(
					function(e, dt, button, config) {
						defaultCopyAction(e, dt, button, config);
						$('.dt-button-info')
								.addClass(
										'gritter-item-wrapper gritter-info gritter-center white');
					});

	var defaultColvisAction = myTable.button(0).action();
	myTable
			.button(0)
			.action(
					function(e, dt, button, config) {

						defaultColvisAction(e, dt, button, config);

						if ($('.dt-button-collection > .dropdown-menu').length == 0) {
							$('.dt-button-collection')
									.wrapInner(
											'<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
									.find('a').attr('href', '#').wrap("<li />")
						}
						$('.dt-button-collection').appendTo(
								'.tableTools-container .dt-buttons')
					});

	// //

	setTimeout(function() {
		$($('.tableTools-container')).find('a.dt-button').each(function() {
			var div = $(this).find(' > div').first();
			if (div.length == 1)
				div.tooltip({
					container : 'body',
					title : div.parent().text()
				});
			else
				$(this).tooltip({
					container : 'body',
					title : $(this).text()
				});
		});
	}, 500);

	myTable.on('select', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop('checked',
					true);
		}
	});
	myTable.on('deselect', function(e, dt, type, index) {
		if (type === 'row') {
			$(myTable.row(index).node()).find('input:checkbox').prop('checked',
					false);
		}
	});

	// ///////////////////////////////
	// table checkboxes
	$('th input[type=checkbox], td input[type=checkbox]')
			.prop('checked', false);

	// select/deselect all rows according to table header checkbox
	$(
			'#' + tableId + ' > thead > tr > th input[type=checkbox], #'
					+ tableId + '_wrapper input[type=checkbox]').eq(0).on(
			'click', function() {
				var th_checked = this.checked;// checkbox inside "TH" table
												// header
				try {
					$('#' + tableId).find('tbody > tr').each(function() {
						var row = this;
						if (th_checked)
							myTable.row(row).select();
						else
							myTable.row(row).deselect();
					});
				} catch (e) {
				}
				;
			});

	// select/deselect a row when the checkbox is checked/unchecked
	$('#' + tableId).on('click', 'td input[type=checkbox]', function() {
		try {
			var row = $(this).closest('tr').get(0);
			if (this.checked)
				myTable.row(row).deselect();
			else
				myTable.row(row).select();
		} catch (e) {
		}
		;
	});

	$(document).on('click', '#' + tableId + ' .dropdown-toggle', function(e) {
		e.stopImmediatePropagation();
		e.stopPropagation();
		e.preventDefault();
	});

	return myTable;
}

(function() {
	var $, SmsCounter;

	window.SmsCounter = SmsCounter = (function() {
		function SmsCounter() {
		}

		SmsCounter.gsm7bitChars = "@£$¥èéùìòÇ\\nØø\\rÅåΔ_ΦΓΛΩΠΨΣΘΞÆæßÉ !\\\"#¤%&'()*+,-./0123456789:;<=>?¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿abcdefghijklmnopqrstuvwxyzäöñüà";

		SmsCounter.gsm7bitExChar = "\\^{}\\\\\\[~\\]|€";

		SmsCounter.gsm7bitRegExp = RegExp("^[" + SmsCounter.gsm7bitChars
				+ "]*$");

		SmsCounter.gsm7bitExRegExp = RegExp("^[" + SmsCounter.gsm7bitChars
				+ SmsCounter.gsm7bitExChar + "]*$");

		SmsCounter.gsm7bitExOnlyRegExp = RegExp("^[\\"
				+ SmsCounter.gsm7bitExChar + "]*$");

		SmsCounter.GSM_7BIT = 'GSM_7BIT';

		SmsCounter.GSM_7BIT_EX = 'GSM_7BIT_EX';

		SmsCounter.UTF16 = 'UTF16';

		SmsCounter.messageLength = {
			GSM_7BIT : 160,
			GSM_7BIT_EX : 160,
			UTF16 : 70
		};

		SmsCounter.multiMessageLength = {
			GSM_7BIT : 153,
			GSM_7BIT_EX : 153,
			UTF16 : 67
		};

		SmsCounter.count = function(text) {
			var count, encoding, length, messages, per_message, remaining;
			encoding = this.detectEncoding(text);
			length = text.length;
			if (encoding === this.GSM_7BIT_EX) {
				length += this.countGsm7bitEx(text);
			}
			per_message = this.messageLength[encoding];
			if (length > per_message) {
				per_message = this.multiMessageLength[encoding];
			}
			messages = Math.ceil(length / per_message);
			remaining = (per_message * messages) - length;
			if (remaining == 0 && messages == 0) {
				remaining = per_message;
			}
			return count = {
				encoding : encoding,
				length : length,
				per_message : per_message,
				remaining : remaining,
				messages : messages
			};
		};

		SmsCounter.detectEncoding = function(text) {
			switch (false) {
			case text.match(this.gsm7bitRegExp) == null:
				return this.GSM_7BIT;
			case text.match(this.gsm7bitExRegExp) == null:
				return this.GSM_7BIT_EX;
			default:
				return this.UTF16;
			}
		};

		SmsCounter.countGsm7bitEx = function(text) {
			var char2, chars;
			chars = (function() {
				var _i, _len, _results;
				_results = [];
				for (_i = 0, _len = text.length; _i < _len; _i++) {
					char2 = text[_i];
					if (char2.match(this.gsm7bitExOnlyRegExp) != null) {
						_results.push(char2);
					}
				}
				return _results;
			}).call(this);
			return chars.length;
		};

		return SmsCounter;

	})();

	if (typeof jQuery !== "undefined" && jQuery !== null) {
		$ = jQuery;
		$.fn.countSms = function(target) {
			var count_sms, input;
			input = this;
			target = $(target);
			count_sms = function() {
				var count, k, v, _results;
				count = SmsCounter.count(input.val());
				_results = [];
				for (k in count) {
					v = count[k];
					_results.push(target.find("." + k).text(v));
				}
				return _results;
			};
			this.on('keyup', count_sms);
			return count_sms();
		};
	}

}).call(this);

function sendSms(obj) {
	try {
		var smsContent = $("#smsContent").val();
		var studentList = $('.body_checkbox:checked').length;
		if (smsContent.length > 0 && studentList > 0) {
			var messageCount = 0;
			$.ajax({
				url : "getSmsCount.json",
				data : {
					"smsContent" : smsContent
				},
				method : "get",
				async : false,
				success : function(result) {
					messageCount = result.smsCount;
				}
			});
			if (parseInt(messageCount) > 1) {
				if (confirm("This message will be counted as " + messageCount
						+ " messages. Are you sure to send?")) {
					$(obj).parents("form").submit();
				}
			} else {
				$(obj).parents("form").submit();
			}
		} else{
			alert("Please select atleast one student and enter some message to send.");
			return false;
		}
	} catch (e) {
		console.log(e);
	}
}

function sendSmsContent(obj) {
	try {
		var smsContent = $("#smsContent").val();		
		if (smsContent.length > 0) {
			var messageCount = 0;
			$.ajax({
				url : "getSmsCount.json",
				data : {
					"smsContent" : smsContent
				},
				method : "get",
				async : false,
				success : function(result) {
					messageCount = result.smsCount;
				}
			});
			if (parseInt(messageCount) > 1) {
				if (confirm("This message will be counted as " + messageCount
						+ " messages. Are you sure to send?")) {
					$(obj).parents("form").submit();
				}
			} else {
				$(obj).parents("form").submit();
			}
		}
	} catch (e) {
		console.log(e);
	}
}
