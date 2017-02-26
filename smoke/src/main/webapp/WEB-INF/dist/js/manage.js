var globals = {
	renderStr: function(type,user_name){
		var str = '';
		if(type === 'create'){
			str = '<div class="prop_area">' + 
			'<input type="text" class="tea_name_text" placeholder="请输入老师名字"/>' +
			'<button type="text" class="send_add_tea">确认</button>' + 
			'</div>';
		}else if(type === 'updata'){
			str = '<div class="prop_area">' + 
			'<input type="text" class="tea_name_text" placeholder="请输入老师名字" value="'+ user_name +'"/>' +
			'<button type="text" class="send_add_tea">确认</button>' + 
			'</div>';
		}else if(type === 'add_tea_tele'){
			str = '<div class="prop_area">' + 
			'<input type="text" class="tea_name_text" placeholder="请输入添加的老师号码" value=""/>' +
			'<button type="text" class="send_add_tea">确认</button>' + 
			'</div>';
		}else if(type === 'update_tea_tele'){
			str = '<div class="prop_area">' + 
				'<input type="text" class="tea_name_text" placeholder="请输入修改的老师号码" value="' + user_name + '"/>' +
				'<button type="text" class="send_add_tea">确认</button>' + 
				'</div>';
		}else if(type === 'add_tea_room'){
				str = '<div class="prop_area">' + 
			'<input type="text" class="tea_name_text" placeholder="请输入添加的房间名字" value=""/>' +
			'<button type="text" class="send_add_tea">确认</button>' + 
			'</div>';
		}else if(type === 'add_tea_equi'){
				str = '<div class="prop_area">' + 
				'<input type="text" class="tea_id_text" placeholder="请输入添加的设备编号" value=""/>' +
			'<input type="text" class="tea_name_text" placeholder="请输入添加的设备名字" value=""/>' +
			'<button type="text" class="send_add_tea">确认</button>' + 
			'</div>';
		}
		return str;
	},
	updata_teacher: function(type, user_id, user_name){
		// 1. 渲染遮罩层；
		var parent = globals.render(),
			div = document.body.querySelector('.prop_container');
		var str = globals.renderStr(type, user_name);
		
		// 插入
		div.innerHTML = str;
		// 添加绑定事件
		document.body.querySelector('.close_prop').addEventListener('click', function(e){
				parent.parentNode.removeChild(parent);
		});
		document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
			var value = document.body.querySelector('.tea_name_text');
			if(value){
				value = value.value;
				Ajax({
					url: '/smoke/teacher',
					type: type==='create'?'post':'put',
					data: {
						userId: user_id,
						userName : value 
					},
					success: function(data){
						printf('158行：添加老师按钮添加data:' + data);
						if(data.status.toUpperCase() === 'SUCCESS'){
							all_data.teachers.push(data.user);
							// 触发按钮关闭事件；
							var btn = document.body.querySelector('.close_prop');
							var event = document.createEvent("MouseEvents");
							event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
							btn.dispatchEvent(event);
						}else{
							if(type==='create'){
								alert('添加老师失败');
							}else{
								alert('修改老师失败');
							}
						}
					},
					error: function(){
						if(type==='create'){
							alert('添加老师失败');
						}else{
							alert('修改老师失败');
						}
					}
				})
			}
		});		
	},
	render: function(){
		var div;
		if(document.body.querySelector('.prop')){
			div = document.body.querySelector('.prop');
		}else{
			div = document.createElement('div');
			div.className = 'prop';
			div.innerHTML = '<div class="prop_header"><span class="close_prop">X</span></div><div class="prop_container"></div>';
			document.body.appendChild(div);
		}
		return div;
	},
	updata_template: '<div><h2>老师列表</h2><div v-if="teachars"><div class="one_tea"><div class="tea_id">老师工号</div><div class="tea_name">姓名</div><div class="add_tea"><button type="button" v-on:click="add_tea_button">添加老师</button></div></div><div class="one_tea" v-for="tea in teachars"><div class="tea_id">{{tea.userId}}</div><div class="tea_name">{{tea.userName}}</div><div class="tea_bn" v-on:click="change_name(tea.userId)">修改</div></div></div><div v-else class="not_tea">        暂无老师信息，请检查是否网络出错</div></div><input type="text" class="tea_name_text" placeholder="请输入老师名字" /><button type="text">提交完毕</button><div class="updata_teacher"><h1 class="updata_heading">修改老师信息</h1><div class="updata_container"><div class="updata_head"><button class="bn_small updata_bn" v-on:click="return_back()">返回</button></div><div class="updata_content">        	<div class="updata_one_input">        		<label class="updata_label lb_name" for="u_user_name">姓名</label>        		<input id="u_user_name" type="text" name="user_name" value="{{{temp.userName}}" disabled="disabled">        	</div>        	<div class="updata_one_input">        		<label class="updata_label lb_name">手机号</label>        		<div class="updata_one_input" v-for="tele in temp.userPhone">        			<input class="u_user_tele" id="u_user_tele{{$index}}" type="text" name="user_name" value="{{tele.userPhone}}">        			<div v-on:click="updata_user_phone(tele.userId, tele.userPhone, $index)">修改</div>	        			<div v-on:click="delete_user_phone(tele.phoneId,tele.userId, tele.userPhone, $index)">删除</div>	        		</div>        		<div v-on:click="add_user_phone($index, tele.userId)">添加手机号</div>        	</div>        	<div class="updata_one_input">        		<label class="updata_label lb_name">房间</label>        		<div class="updata_one_input" v-for="room in temp.rooms">        			<input class="u_user_room" id="u_user_room{{$index}}" type="text" name="user_name" value="{{room.roomName}}">        			<div v-on:click="updata_user_room($index, room.roomId)">修改</div>	        			<div v-on:click="delete_user_room($index, room.roomId)">删除</div>	        			<div class="rooms_equi_list">        				<label class="updata_label lb_name">设备</label>        				<div class="rooms_equi" v-for="equi in room.equipments">        					<input class="u_user_equi"  id="u_user_equi{{$index}}" type="text" name="user_name" value="{{tele.userPhone}}">		        			<div v-on:click="updata_user_equi($index, room.roomId,equi.equipmentId)">修改</div>			        			<div v-on:click="delete_user_equi($index, room.roomId,equi.equipmentId)">删除</div>	        				</div>        				<div v-on:click="add_user_equi($index, room.roomId)">添加设备</div>        			</div>        		</div>        		<div v-on:click="add_user_rooms($index, temp.userId)">添加房间</div>        	</div></div></div></div>'
}

function printf(str){
	console.log(str)
};
var tea = {
	// HTML代码片段
	template: '<div>'+
		   ' <h2>老师列表</h2>'+
		   ' <div v-if="show_teacher()">'+
		'		<div class="one_tea">'+
	'				<div class="tea_id">老师工号</div>'+
	'				<div class="tea_name">姓名</div>'+
	'				<div class="tea_tele">电话</div>'+
	'				<div class="add_tea"><button type="button" v-on:click="add_tea_button">添加老师</button></div>' +
	'			</div>' +
	'			<div class="one_tea" v-for="tea in teachers">' +
	'				<div class="tea_id">{{tea.userId}}</div>'+
	'				<div class="tea_name">{{tea.userName}}</div>'+
	'				<div class="tea_tele">{{tea.phones.length!=0?tea.phones[0]:'+ '无'+'}}</div>'+
	// '<div class="tea_bn" v-on:click="change_name(tea.userId, tea.userName)">修改</div>' +
	'<router-link class="tea_bn" :to={path:"/updata", params:{index: $index}}>修改</router-link>' +
	'<div class="tea_bn" v-on:click="delete_tea(tea.userId)">删除</div>' +
	'<div class="tea_bn" v-on:click="show_device(tea.userId)">展开</div>' + 
	'			</div>'+
	'		</div>'+
	'		<div v-else class="not_tea">'+
	'			暂无老师信息，请检查是否网络出错'+
	'		</div>'+
	'	</div>',
	data: function(){
		return all_data
	},
	methods: {
		show_teacher: function(){
			if(this.teachers){
				return true;
			}
			return false;
		},
		add_tea_button: function(){
			// 添加一个按钮
			printf('添加信息');
			globals.updata_teacher('create');
		},
		change_name: function(user_id, user_name){
			// 创建遮罩层
			globals.updata_teacher('updata', user_id, user_name);

		},
		delete_tea: function(user_id){
			Ajax({
				url: '/smoke/teacher/'+ user_id,
				type: 'delete',
				success: function(data){
					alert('删除成功');
					for(var i = 0; i < all_data.teachers.length; i++){
						if(all_data.teachers[i].userId === user_id){
							all_data.teachers.splice(i,1);
							break;
						}
					}
				},
				error: function(data){
					alert('删除失败');
				}
			});
		},
		show_device: function(user_id){
			Ajax({
				url: '/user/{userId}/roomlist',
				type: 'get',

			});
		}
	},
	created: function(){
		var _this = this;
		Ajax({
			url: '/smoke/teachersInfo',
			type: 'get',
			success: function(data){
				var arr = null;
				if(typeof data === 'string'){
					data = JSON.parse(data);
					arr = data;
				}
					// 处理老师列表
				_this.$set(_this.$data, 'teachers', arr);
					
			},
			error: function(data){
				if( (!all_data.teachers) || all_data.teachers.length < 1){

				}
			}
		})
	}
}
var all_data = {
	teachers: [
	{ 
		"userId": 2, 
		"userName": "陈俊铭2", 
		"rooms": null, 
		"phones": null 
	}]
};

var updata = {
	// HTML代码片段
	template: globals.updata_template,
	data: all_data,
	methods: {
		return_back: function(){
			for(var i = 0; i < all_data.teachers.length; i++){
				if(all_data.teachers[i].userId === all_data.temp.userId){
					all_data.teachers[i] = all_data.temp;
					break;
				}
			}
			window.location.history.go(-1);
		},
		add_user_phone: function(tea_index, user_id){
			var parent = globals.render(),
				div = document.body.querySelector('.prop_container');
			var str = globals.renderStr('add_tea_tele');
			// 插入
			div.innerHTML = str;
			// 添加绑定事件
			document.body.querySelector('.close_prop').addEventListener('click', function(e){
					parent.parentNode.removeChild(parent);
			});
			document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
				var value = document.body.querySelector('.tea_name_text');
				if(value){
					value = value.value;
					Ajax({
						url: '/smoke/phone',
						type: 'post',
						data: {
							userId: user_id,
							userPhone: value 
						},
						success: function(data){
							printf('158行：添加手机号按钮添加data:' + data);
							if(data.status.toUpperCase() === 'SUCCESS'){
								// 触发按钮关闭事件；
								var btn = document.body.querySelector('.close_prop');
								var event = document.createEvent("MouseEvents");
								all_data.temp.phones.push({
									userId: user_id,
									userPhone: value 
								});
								event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
								btn.dispatchEvent(event);
								
							}else{
								alert('添加手机号失败');
							}
						},
						error: function(){
							alert('添加手机号失败');
						}
					})
				}
			});		
		},
		delete_user_phone: function(phoneId,userId, userPhone, index){
			Ajax({
				url: '/smoke/phone/' + userPhone,
				type: 'delete',
				data: {
					userPhone: userPhone
				},
				success: function(data){
					alert('删除成功');
					all_data.temp.phones.splice(index, 1);
				},
				error: function(data){
					alert('删除失败');
				}
			})
		},
		update_user_phone: function(user_id, user_phone,index){
			var value = document.getElementById('u_user_tele'+ index);
			if(value){
				value = value.value;
				Ajax({
					url: '/smoke/phone',
					type: 'put',
					data: {
						userId: user_id,
						userPhone: value 
					},
					success: function(data){
						printf('158行：修改手机号按钮修改data:' + data);
						if(data.status.toUpperCase() === 'SUCCESS'){
							// 触发按钮关闭事件；
							// 找到之前的，然后删除之前的
							var arr = all_data.temp.phones;
							for(var i = 0; i < arr.length;i++){
								if(arr[i].userId === user_id &&
									arr[i].userPhone === user_phone){
									arr[i].userPhone === value;
								}
							}
						}else{
							alert('修改手机号失败');
						}
					},
					error: function(){
						alert('修改手机号失败');
					}
				});
			}
		},
		updata_user_room: function(index, room_id){
			var value = document.getElementById('u_user_room'+ index);
			if(value){
				value = value.value;
				Ajax({
					url: '/smoke/room/' + room_id,
					type: 'put',
					data: {
						roomId: room_id,
						roomName: value
					},
					success: function(data){
						printf('修改房间名字按钮修改data:' + data);
						if(data.status.toUpperCase() === 'SUCCESS'){
							// 触发按钮关闭事件；
							// 找到之前的，然后删除之前的
							var arr = all_data.temp.rooms;
							for(var i = 0; i < arr.length;i++){
								if(arr[i].room_id === room_id){
									arr[i].roomName === value;
								}
								}
						}else{
							alert('修改房间名字失败');
						}
					},
					error: function(){
						alert('修改房间名字失败');
					}
				});
			}
		},
		delete_user_room: function(index, room_id){
			Ajax({
				url: '/smoke/room/' + room_id,
				type: 'delete',
				data: {
					room_id: room_id
				},
				success: function(data){
					alert('删除成功');
					all_data.temp.rooms.splice(index, 1);
				},
				error: function(data){
					alert('删除失败');
				}
			});
		},
		add_user_room: function(index,user_id){
				var parent = globals.render(),
				div = document.body.querySelector('.prop_container');
				var str = globals.renderStr('add_tea_equi');
				// 插入
				div.innerHTML = str;
				// 添加绑定事件
				document.body.querySelector('.close_prop').addEventListener('click', function(e){
						parent.parentNode.removeChild(parent);
				});
				document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
					var value = document.body.querySelector('.tea_name_text');
					if(value){
						value = value.value;
						Ajax({
							url: '/smoke/equipment',
							type: 'post',
							data: {
								userId: user_id,
								roomName: value 
							},
							success: function(data){
								if(data.status.toUpperCase() === 'SUCCESS'){
									// 触发按钮关闭事件；
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
									all_data.temp.rooms.push({
										userId: user_id,
										// roomId: data.data.roomId,
										roomName: value  
									});
									event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
									btn.dispatchEvent(event);
									
								}else{
									alert('添加房间失败');
								}
							},
							error: function(){
								alert('添加房间失败');
							}
						})
					}
				});		
		},
		// 修改到这里
		updata_user_equi: function(index, room_id,equi_id){
			var value = document.getElementById('u_user_equi'+ index);
			if(value){
				value = value.value;
				Ajax({
					url: '/smoke/room/' + room_id,
					type: 'put',
					data: {
					    equipmentId: equi_id,
					    equipmentName: value,
					    roomId:room_id
					},
					success: function(data){
						printf('修改房间名字按钮修改data:' + data);
						if(data.status.toUpperCase() === 'SUCCESS'){
							// 触发按钮关闭事件；
							// 找到之前的，然后删除之前的
							var arr = all_data.temp.rooms;
							for(var i = 0; i < arr.length;i++){
								if(arr[i].room_id === room_id){
									arr[i].roomName === value;
								}
								}
						}else{
							alert('修改设备名字失败');
						}
					},
					error: function(){
						alert('修改设备名字失败');
					}
				});
			}
		},
		delete_user_room: function(index, room_id, equi_id){
			Ajax({
				url: '/smoke/euqipment/' + equi_id,
				type: 'delete',
				success: function(data){
					var temp = all_data.temp.rooms;
					alert('删除成功');
					for(var i = 0; i < temp.length; i++){
						if(temp[i].roomId === room_id){
							temp[i].equiments.splice(index, 1);
						}
					}
				},
				error: function(data){
					alert('删除失败');
				}
			});
		},
		add_user_room: function(index,room_id){
				var parent = globals.render(),
				div = document.body.querySelector('.prop_container');
				var str = globals.renderStr('add_tea_room');
				// 插入
				div.innerHTML = str;
				// 添加绑定事件
				document.body.querySelector('.close_prop').addEventListener('click', function(e){
						parent.parentNode.removeChild(parent);
				});
				document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
					var name_value = document.body.querySelector('.tea_name_text');
					var id_value = document.body.querySelector('.tea_id_text');
					if(value){
						value = value.value;
						Ajax({
							url: '/smoke/room',
							type: 'post',
							data: {
									equipmentId: id_value,
					    equipmentName: name_value,
					    roomId:room_id
							},
							success: function(data){
								if(data.status.toUpperCase() === 'SUCCESS'){
									// 触发按钮关闭事件；
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
									// 这里添加的应该是设备
									all_data.temp.rooms.push({
										equipmentId: id_value,
						    equipmentName: name_value,
						    roomId:room_id
									});
									event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
									btn.dispatchEvent(event);
									
								}else{
									alert('添加设备失败');
								}
							},
							error: function(){
								alert('添加设备失败');
							}
						})
					}
				});		
		}
	},
	created: function(){
		//建立一个这个老师的副本
		debugger;
		var index = window.location.href.lastIndexOf('/');
		index =  window.location.href.slice(index + 1, window.location.href.length);
		var temp = JSON.parse(JSON.stringify(all_data.teachers[index]));
		var equi = null;
		all_data.temp = temp;
		// 获取这个老师的房间信息
		// 获取这个老师的设备信息，好想哭啊啊啊啊
		Ajax({
			url: '/user/{userId}/roomlist',
			type: 'get',
			success: function(data){
				if( typeof data === 'string'){
					data = JSON.parse(data);
				}
				if(data.success == true){
					all_data.temp.rooms = data.data;
					// 再发送几个ajax获取到房间的设备信息
				}
			}
		});
		equi = all_data.temp.rooms;
		for(var i = 0; i < equi; i++){
			Ajax({
				url: '/room/' + equi.roomId + '/equipmentlist',
				type: 'get',
				success: function(data){
					if(typeof data === 'string'){
						data = JSON.parse(data);
					}
					all_data.tepm.rooms[i].equipments = data;
				},
				error: function(){

				}
			})
		}
	}
};
var routes = [
	{ path: '/', component: tea  },
  { path: '/tea', component: tea  },
  { path: '/updata/:index', name: 'updata', component: updata  }
]

// 3. 创建 router 实例，然后传 `routes` 配置
// 你还可以传别的配置参数, 不过先这么简单着吧。
var router = new VueRouter({
  routes :routes// （缩写）相当于 routes: routes
})

// 4. 创建和挂载根实例。
// 记得要通过 router 配置参数注入路由，
// 从而让整个应用都有路由功能
var app = new Vue({
  router: router
}).$mount('#app')