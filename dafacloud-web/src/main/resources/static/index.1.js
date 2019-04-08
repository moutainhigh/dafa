new function () {
	var ws = null;
	var connected = false;

	var serverUrl;
	var connectionStatus;
	var sendMessage;

	var connectButton;
	var disconnectButton;
	var sendButton;

	var open = function () {
		var url = serverUrl.val();
		ws = new WebSocket(url);
		ws.onopen = onOpen;
		ws.onclose = onClose;
		ws.onmessage = onMessage;
		ws.onerror = onError;

		connectionStatus.text('OPENING ...');
		serverUrl.attr('disabled', 'disabled');
		connectButton.hide();
		disconnectButton.show();
	}

	var close = function () {
		if (ws) {
			console.log('CLOSING ...');
			ws.close();
		}
		connected = false;
		connectionStatus.text('CLOSED');

		serverUrl.removeAttr('disabled');
		connectButton.show();
		disconnectButton.hide();
		sendMessage.attr('disabled', 'disabled');
		sendButton.attr('disabled', 'disabled');
	}

	var clearLog = function () {
		$('#messages').html('');
	}

	var onOpen = function () {
		console.log('OPENED: ' + serverUrl.val());
		connected = true;
		connectionStatus.text('OPENED');
		sendMessage.removeAttr('disabled');
		sendButton.removeAttr('disabled');
	};

	var onClose = function () {
		console.log('CLOSED: ' + serverUrl.val());
		ws = null;
	};

	var onMessage = function (event) {
		var data = event.data;
		addMessage(data);
	};

	var onError = function (event) {
		alert(event.data);
	}

	var betCount = 0;//统计投注通知次数
	var pos1 =[];
	var pos2 = [];
	var pos3 =[];
	var addMessage = function (data, type) {
		var json = JSON.parse(data)
		var proto = json.proto;
		var data2 = json.data;
		console.log(proto)
		var protoCN = "";
		//var resultCN =""
		var msgCN = ""
		if (proto == 702) {
			var betInfo = data2.betInfo 
			protoCN = "投注通知"+(++betCount)
			// var pos1Temp = betInfo[0].bettingAmount //红
			// var pos2Temp = betInfo[1].bettingAmount	//黑
			// var pos3Temp = betInfo[2].bettingAmount //幸运一击

			// var result = "";
			// if(pos1Temp.length>pos1.length){
			// 	for (var i = pos1.length; i < pos1Temp.length; i++) {
			// 		result += pos1Temp[i]+","
			// 	}
			// }
		}else if (proto == 712) {
			protoCN = "在线人数"
		}else if (proto == 713) {
			protoCN = "场景通知"
		} else if (proto == 707) {
			protoCN = "排行榜"
		} else if (proto == 710) {
			protoCN = "结算通知"
			var resultCN = pers.pokersToNum(data2.pokers)
			var pokerType = pers.pokerTypeHH(data2.pokerType)
			var pokerCompare = pers.pokerCompareHH(data2.pokerCompare)
			msgCN = $('<pre>').text("   " + resultCN + " - " + pokerType + " - " + pokerCompare);
		}else if (proto == 709) {
			protoCN = "开始下注"
		}else if (proto == 716) {
			betCount=0;
			protoCN = "停止投注"
		}

		var msg = $('<pre>').text(protoCN + data);


		if (type === 'SENT') {
			msg.addClass('sent');
		}
		var messages = $('#messages');
		messages.append(msg);
		if (msgCN != "") {
			messages.append(msgCN)
		}

		var msgBox = messages.get(0);
		while (msgBox.childNodes.length > 1000) {
			msgBox.removeChild(msgBox.firstChild);
		}
		//msgBox.scrollTop = msgBox.scrollHeight;
	}

	pers = {
		pokersToNum: function (pokers) {
			pokersArray = pokers.split(",")
			var result = "";
			for (var i = 0; i < pokersArray.length; i++) {
				var p = pokersArray[i];
				if (p % 4 == 0) {
					result += "方块";
				} else if (p % 4 == 1) {
					result += "梅花";
				} else if (p % 4 == 2) {
					result += "红桃";
				} else if (p % 4 == 3) {
					result += "黑桃";
				}
				result += p >> 2;
				result += ","
			}
			return result.substr(0, result.length - 1)
		},
		pokerTypeHH: function (pokerTypeArray) {
			var pokerTypeResult = ""
			for (var i = 0; i < pokerTypeArray.length; i++) {
				var pokerType = pokerTypeArray[i]
				//豹子6 順金5 金花4 順子3  (對子>9) 2  (對子2~8) 1   單張0
				if (pokerType == 6) {
					pokerTypeResult += "豹子,"
				} else if (pokerType == 5) {
					pokerTypeResult += "顺金,"
				} else if (pokerType == 4) {
					pokerTypeResult += "金花,"
				} else if (pokerType == 3) {
					pokerTypeResult += "顺子,"
				} else if (pokerType == 2) {
					pokerTypeResult += "对子>9,"
				} else if (pokerType == 1) {
					pokerTypeResult += "对子2～8,"
				} else if (pokerType == 0) {
					pokerTypeResult += "单张,"
				}
			}
			return pokerTypeResult.substring(0, pokerTypeResult.length - 1)
		},
		pokerCompareHH: function (pokerCompareArray) {
			var pokerCompareResult = ""
			for (var i = 0; i < pokerCompareArray.length; i++) {
				var pokerCompare = pokerCompareArray[i]
				if (i != 2) {
					if (pokerCompare == 0) {
						pokerCompareResult += "小"
					} else if (pokerCompare == 1) {
						pokerCompareResult += "大"
					}
				} else {
					if (pokerCompare == 0) {
						pokerCompareResult += "N"
					} else if (pokerCompare == 1) {
						pokerCompareResult += "幸运一击"
					}
				}
			}
			return pokerCompareResult
		}

	}

	WebSocketClient = {
		init: function () {
			serverUrl = $('#serverUrl');
			connectionStatus = $('#connectionStatus');
			sendMessage = $('#sendMessage');

			connectButton = $('#connectButton');
			disconnectButton = $('#disconnectButton');
			sendButton = $('#sendButton');

			connectButton.click(function (e) {
				close();
				open();
			});

			disconnectButton.click(function (e) {
				close();
			});

			sendButton.click(function (e) {
				var msg = $('#sendMessage').val();
				addMessage(msg, 'SENT');
				ws.send(msg);
			});

			$('#clearMessage').click(function (e) {
				clearLog();
			});

			var isCtrl;
			sendMessage.keyup(function (e) {
				if (e.which == 17) isCtrl = false;
			}).keydown(function (e) {
				if (e.which == 17) isCtrl = true;
				if (e.which == 13 && isCtrl == true) {
					sendButton.click();
					return false;
				}
			});
		}
	};
}

$(function () {
	WebSocketClient.init();
});