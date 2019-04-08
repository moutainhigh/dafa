new function () {
	var ws = null;
	var connected = false;

	var serverUrl;
	var connectionStatus;
	var sendMessage;

	var connectButton;
	var disconnectButton;
	var sendButton;

	var clock = '';

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
		clearInterval(clock);
		if (ws) {
			console.log('CLOSING ...');
			ws.close();
		}
		connected = false;
		connectionStatus.text('CLOSED');

		serverUrl.removeAttr('disabled');
		connectButton.show();
		disconnectButton.hide();
		//sendMessage.attr('disabled', 'disabled');
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
	// var pos1 =[];
	// var pos2 = [];
	// var pos3 =[];
	var $balance = $('.blance-amount')//余额
	var $userPoint = $('.userPoint')
	var $issue = $('.issue span') //期号
	var times = 0
	var $endTiem = $('.time') //倒计时
	var $counts = $('.users-count .count')//在线用户数

	//投注区数据
	var $totalAmount = $('total-amount')

	var $hongTotal = $('.betting-are .hong .top')
	var $hongBonus = $('.betting-are .hong .middle .bonus')
	var $hongMy = $('.betting-are .hong .bottom')

	var $heiTotal = $('.betting-are .hei .top')
	var $heiBonus = $('.betting-are .hei .middle .bonus')
	var $heiMy = $('.betting-are .hei .bottom')

	var $luckTotal = $('.betting-are .luck .top')
	var $luckBonus = $('.betting-are .luck .middle .bonus')
	var $luckMy = $('.betting-are .luck .bottom')
	//状态
	var openStatus = $('.status span')
	//走势图
	var $trendHH = $('.trend .dx')
	var $trendType = $('.trend .type')
	var addMessage = function (data, type) {
		//console.log("data:"+data)
		var json = JSON.parse(data)
		var proto = json.proto;
		var data2 = json.data;
		//console.log(proto)
		var protoCN = "";
		//var resultCN =""
		var msgCN = ""
		if (proto == 702) {
			var betInfo = data2.betInfo
			protoCN = "投注通知" + (++betCount)
			// var pos1Temp = betInfo[0].bettingAmount //红
			// var pos2Temp = betInfo[1].bettingAmount	//黑
			// var pos3Temp = betInfo[2].bettingAmount //幸运一击

			// var result = "";
			// if(pos1Temp.length>pos1.length){
			// 	for (var i = pos1.length; i < pos1Temp.length; i++) {
			// 		result += pos1Temp[i]+","
			// 	}
			// }
			//在线用户数
			$counts.text(data2.userCount)
			//总投注金额
			var areaAmount = data2.areaAmount;
			

			$hongTotal.text(areaAmount[0])
			
			$heiTotal.text(areaAmount[1]) 

			$luckTotal.text(areaAmount[2])
			
		} else if (proto == 712) {
			protoCN = "在线人数"
			//在线用户数
			$counts.text(data2.userCount)
			//
			//openStatus.text('等待开奖')
		} else if (proto == 713) {
			var userBonus = data2.userBonus
			protoCN = "场景通知"
			$balance.text(data2.userBalance)
			$counts.text(data2.onlineCount)
			$issue.text(data2.issue) //期号
			$('.userId').text(data2.uid)
			$userPoint.text(data2.userRebate)
			//倒计时
			clearInterval(clock);
			times = data2.stateTime
			$endTiem.text(times)
			clock = setInterval(pers.endTime, 1000); 
			//总投注金额
			$totalAmount.text(data2.totalBetAmount)

			//投注区
			var perBetAmount = data2.perBetAmount;
			var myBetList = data2.myBetList;
			var userBonus = data2.userBonus.split(',');

			$hongTotal.text(perBetAmount[0])
			$hongBonus.text(userBonus[0]) 
			$hongMy.text(myBetList[0])

			$heiTotal.text(perBetAmount[1]) 
			$heiBonus.text(userBonus[1])
			$heiMy.text(myBetList[1])

			$luckTotal.text(perBetAmount[2])
			$luckBonus .text(userBonus[2])
			$luckMy.text(myBetList[2])

			//状态
			var status = data2.status;
			if(status==1){
				openStatus.text('投注中')
			}else if(status==2){
				openStatus.text('投注倒计时')
			}else if(status==3){
				openStatus.text('开奖中')
			}
			//走势图
			var trend = data2.trend
			$trendHH.html('');//初始化走势图
			$trendType.html('');
			//var trendHH = []
			//var trendType = []
			for(var i=0;i<trend.length;i++){
				var types = trend[i];
				if(types.length>1){
					var typeArr = types.split('')
					if(typeArr[0]==1){
						//trendHH.push('红')
						$trendHH.append('<li class="hong">红</li>')
					}else if(typeArr[0]==2){
						//trendHH.push('黑')
						$trendHH.append('<li>黑</li>')
					}
					
					//trendType.push(pers.pokerType(typeArr[1]))
					$trendType.append('<li class="luck">'+pers.pokerType2(typeArr[1])+'</li>')
				}else if(types.length==1){
					if(types==1){
						//trendHH.push('红')
						$trendHH.append('<li class="hong">红</li>')
					}else if(types==2){
						//trendHH.push('黑')
						$trendHH.append('<li>黑</li>')
					}
					//trendType.push('单张')
					$trendType.append('<li>单张</li>')
				}
			}
		} else if (proto == 707) {
			protoCN = "排行榜"
		} else if (proto == 710) {
			//console.log(data)
			protoCN = "结算通知"
			var pokerCompareArray = data2.pokerCompare
			var resultCN = pers.pokersToNum(data2.pokers)
			var pokerType = pers.pokerTypeHH(data2.pokerType)
			var pokerCompare = pers.pokerCompareHH(pokerCompareArray)
			msgCN = $('<div>').text("   " + resultCN + " - " + pokerType + " - " + pokerCompare);



			//倒计时
			clearInterval(clock);
			times = data2.stateTime
			$endTiem.text(times)
			clock = setInterval(pers.endTime, 1000); //倒计时
			//开奖号码
			var resultCNarray = resultCN.split(",");
			var hong = resultCNarray.slice(0, 3)
			var hei = resultCNarray.slice(3, 6)
			$(".open-result .hong-hei .hong").text(hong)
			$(".open-result .hong-hei .hei").text(hei)
			$(".pokerType").text(pokerType)
			$(".compare").text(pokerCompare)
			//
			openStatus.text('等待下注倒计时')

			//投注区数据
			$totalAmount.text("0")
			$hongTotal.text("0")
			$hongMy.text("0")
			$heiTotal.text("0")
			$heiMy.text("0")
			$luckTotal.text("0")
			$luckMy.text("0")

			//走势图
			// for (var i = 0; i < pokerCompareArray.length; i++) {
			// }
			var isHong =  pokerCompareArray[0]
			var isHei =  pokerCompareArray[1]
			var isLuck =  pokerCompareArray[2]

			//红黑走势
			var len = $trendHH.children().length-1
			$("ul.dx li:eq("+len+")").remove();//删除最后一个元素
			
			if(isHong==1){
				$("<li class=''>红</li>").prependTo($("ul.dx"));//追加
			}else if(isHei==1){
				$("<li>黑</li>").prependTo($("ul.dx"));//追加
			}
			//牌型走势
			var len1 = $trendType.children().length-1
			$("ul.type li:eq("+len+")").remove();//删除最后一个元素
			if(isLuck>1){
				console.log(isLuck)
				console.log(pers.pokerType2(isLuck))
				$("<li class=''>"+pers.pokerType2(isLuck)+"</li>").prependTo($("ul.type"));//追加
			}else{
				$("<li class=''>单张</li>").prependTo($("ul.type"));//追加
			}


		} else if (proto == 709) {
			protoCN = "开始下注"
			///倒计时
			clearInterval(clock);
			times = data2.stateTime
			$endTiem.text(times)
			clock = setInterval(pers.endTime, 1000);
			//期数
			$issue.text(data2.issue)
			//
			openStatus.text('投注中')
			
		} else if (proto == 716) {
			betCount = 0;
			protoCN = "停止投注"
			openStatus.text('等待开奖')

			//倒计时
			clearInterval(clock);
			times = data2.stateTime
			$endTiem.text(times)
			clock = setInterval(pers.endTime, 1000); 

		}

		var msg = $('<div>').text(protoCN + data);


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
				pokerTypeResult += pers.pokerType(pokerType)
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
					// if (pokerCompare == 0) {
					// 	pokerCompareResult += "N"
					// } else if (pokerCompare == 1) {
					// 	pokerCompareResult += "幸运一击"
					// }
					pokerCompareResult += pers.pokerType(pokerCompare)
				}
			}
			return pokerCompareResult
		},
		pokerType: function (pokerType) {
			//豹子6 順金5 金花4 順子3  (對子>9) 2  (對子2~8) 1   單張0
			var pokerTypeResult = ""
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
			return pokerTypeResult
		},
		pokerType2: function (pokerType) {
			//豹子6 順金5 金花4 順子3  (對子>9) 2  (對子2~8) 1   單張0
			var pokerTypeResult = ""
			if (pokerType == 6) {
				pokerTypeResult += "豹子"
			} else if (pokerType == 5) {
				pokerTypeResult += "顺金"
			} else if (pokerType == 4) {
				pokerTypeResult += "金花"
			} else if (pokerType == 3) {
				pokerTypeResult += "顺子"
			} else if (pokerType == 2) {
				pokerTypeResult += "对子9"
			} else if (pokerType == 1) {
				pokerTypeResult += "对子"
			} else if (pokerType == 0) {
				pokerTypeResult += "单张"
			}
			return pokerTypeResult
		},
		endTime: function () {
			times--;
			//console.log("times1"+times)
			if (times < 0) {
				$endTiem.text("0")//.text("0");
				clearInterval(clock); //清除js定时器
			} else {
				$endTiem.text(times) //.text(times)
			}
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
			//发送信息
			sendButton.click(function (e) {
				var msg = $('#sendMessage').val();
				msg = msg.replace('%a',$('.issue').text().trim()).replace('%b',$('.userPoint').text().trim())
				//console.log(msg)
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