<template>
    <div class="updata_teacher login">
        <h1  class="lg_header"  data-text="老师列表">修改老师信息</h1>
        <div class="updata_container">
            <div class="updata_head">
                <button class="bn_small updata_bn lg_bn bn bn_middle" v-on:click="return_back()">返回</button>
            </div>
            <div class="updata_content">
                <div class="updata_one_input">
                    <label class="updata_label lb_name" for="u_user_name">姓名</label>
                    <div class="updata_one_input_phone">
                    	<p class="u_user_tele" id="u_user_name" >{{temp.userName}}</p>
                    </div>
                </div>
                <div class="updata_one_input">
                	<div class="updata_label">
	                    <label class=" lb_name">手机号</label>
	                    <div class="add_bn_updata" v-on:click="add_user_phone(temp.userId)" title="添加手机号"><i class="fa fa-plus" aria-hidden="true"></i></div>
                    </div>
                    <div class="updata_one_input_phone" v-for="(tele, index) in temp.phones">
                    	<p  class="u_user_tele" v-bind:id="renderID('u_user_tele', index)">{{tele.userPhone}}</p>

                        <div class="updata_bn" v-on:click="update_user_phone(tele.userId, tele.userPhone, index, tele.id)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></div>

                        <div class="delete_bn" v-on:click="delete_user_phone(tele.userId, tele.userPhone, index, tele.id)"><i class="fa fa-trash-o" aria-hidden="true"></i></div>
                    </div>

                </div>
                <div class="updata_one_input">
                	<div class="updata_label">
                    	<label class=" lb_name">房间</label>
                    	<div  class="add_bn_updata" v-on:click="add_user_rooms(temp.userId)"  title="添加房间"><i class="fa fa-plus" aria-hidden="true"></i></div>
                    </div>
                    <div class="updata_one_input_phone" v-for="(room,index) in temp.rooms">

                        <p class="u_user_room" v-bind:id="renderID('u_user_room',index)">{{room.roomName}}</p>

                        <div class="updata_bn"  v-on:click="update_user_room(index, room.roomId, room.roomName)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></div>
                        <div class="delete_bn" v-on:click="delete_user_room(index, room.roomId)"><i class="fa fa-trash-o" aria-hidden="true"></i></div>
                        <div class="rooms_equi_list">
                            <div class="updata_label">
                            	<label class="lb_name">设备</label>
                            	 <div class="add_bn_updata"  v-on:click="add_user_equi(room.roomId)"  title="添加设备"><i class="fa fa-plus" aria-hidden="true"></i></div>
                            </div>
                            <div class="rooms_equi">
	                            <div  v-for="(equi ,i ) in room.equipments">
	                                <p class="u_user_equi" v-bind:id="renderID('u_user_equi',i)" >[{{equi.equipmentId}}]{{equi.equipmentName}}</p>
	                                <div class="updata_bn"  v-on:click="update_user_equi(i, room.roomId,equi.equipmentId, equi.equipmentName, equi.id)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></div>
	                                <div class="delete_bn" v-on:click="delete_user_equi(i, room.roomId, equi.equipmentId, equi.id)"><i class="fa fa-trash-o" aria-hidden="true"></i></div>
	                            </div>
	                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
	export default{
		data(){
			return pc_data;
		},
		methods: {
			return_back: function(){
				for(var i = 0; i < pc_data.teachers.length; i++){
					if(pc_data.teachers[i].userId === pc_data.temp.userId){
						pc_data.teachers[i] = pc_data.temp;
						break;
					}
				}
				this.$router.go(-1);
			},
			renderID: function(str_1, str_2){
				return str_1 + str_2;
			},
			add_user_phone: function(user_id){
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
  								if(data.status == true){
  									if( !(pc_data.temp.phones instanceof Array)){
  										pc_data.temp.phones = [];
  									}
  									var btn = document.body.querySelector('.close_prop');
  									var event = document.createEvent("MouseEvents");

  									pc_data.temp.phones.push({
  										userId: user_id,
  										id: data.data.id,
  										userPhone: value
  									});
  									console.log('[system ]data.id' + data.data.id);
  									event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
  									btn.dispatchEvent(event);
  								}else{
  									alert(data.message);
  								}
  							},
  							error: function(){
  								alert('添加手机号失败');
  							}
  						})
  					}
  				});
  			},
  			delete_user_phone: function(user_id, user_phone, index, phone_id){
  				Ajax({
  					url: '/smoke/phone/' + phone_id + '/' + user_id + '/' + user_phone ,
  					type: 'delete',
  					data: {
  						userId: user_id,
  						userPhone: user_phone,
  						id: phone_id
  					},
  					success: function(data){
  						if(data.status == 1){
  							debugger;
  							pc_data.temp.phones.splice(index, 1);
  						}else{
  							alert(data.message);
  						}
  					},
  					error: function(data){
  						alert('删除失败');
  					}
  				})
  			},
  			update_user_phone: function(user_id, user_phone, index, phone_id){
  				var parent = globals.render(),
  					div = document.body.querySelector('.prop_container');
  				var str = globals.renderStr('update_tea_tele' , user_phone);
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
  					// 缺少对这个value的判断
  					Ajax({
  						url: '/smoke/phone',
  						type: 'put',
  						data: {
  							userId: user_id,
  							userPhone: value,
  							id: phone_id
  						},
  						success: function(data){
  							if(data.status == 1){
  								// 触发按钮关闭事件；
  								// 找到之前的，然后删除之前的
  								var arr = pc_data.temp.phones;
  								for(var i = 0; i < arr.length;i++){
  									if(arr[i].id == phone_id){
  										arr[i].userPhone = value;
  										break;
  									}
  								}
  								var btn = document.body.querySelector('.close_prop');
  								var event = document.createEvent("MouseEvents");
  								event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
  								btn.dispatchEvent(event);
  							}else{
  								alert(data.message);
  							}
  						},
  						error: function(){
  							alert('修改手机号失败');
  						}
  					});
  				}
  		      });
  			},
			update_user_room: function(index, room_id, room_name){
				var parent = globals.render(),
					div = document.body.querySelector('.prop_container');
				var str = globals.renderStr('update_tea_tele', room_name);
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
							url: '/smoke/room/' ,
							type: 'put',
							data: {
								roomId: room_id,
								roomName: value
							},
							success: function(data){

								if(data.status === 1){
									// 触发按钮关闭事件；
									// 找到之前的，然后删除之前的
									var arr = pc_data.temp.rooms;
									for(var i = 0; i < arr.length;i++){
										if(arr[i].roomId === room_id){
											arr[i].roomName = value;
										}
									}
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
									event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
									btn.dispatchEvent(event);
								}else{
									alert(data.message);
								}
							},
							error: function(){
								alert('修改房间名字失败');
							}
						});
					}
				});
			},
			delete_user_room: function(index, room_id){
				Ajax({
					url: '/smoke/room/' + room_id,
					type: 'delete',
					data: {
						room_id: room_id
					},
					success: function(data){
						if(data.status == 1){
							pc_data.temp.rooms.splice(index, 1);
						}else{
							alert(data.message);
						}
					},
					error: function(data){
						alert('删除失败');
					}
				});
			},
			add_user_rooms: function(user_id){
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
					var value = document.body.querySelector('.tea_name_text');
					if(value){
						value = value.value;
						Ajax({
							url: '/smoke/room',
							type: 'post',
							data: {
								userId: pc_data.temp.userId,
								roomName: value
							},
							success: function(data){
								if(data.success == true){
									// 触发按钮关闭事件；
									pc_data.temp.rooms.push(data.data);
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
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
			update_user_equi: function(index, room_id, equi_id, equi_name, id){
				var parent = globals.render(),
					div = document.body.querySelector('.prop_container');
				var str = globals.renderStr('update_tea_equi', equi_name, equi_id);
				// 插入
				div.innerHTML = str;
				// 添加绑定事件
				document.body.querySelector('.close_prop').addEventListener('click', function(e){
						parent.parentNode.removeChild(parent);
				});
				document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
					var name_value = document.body.querySelector('.tea_name_text');
					var id_value = document.body.querySelector('.tea_id_text');
					if(id_value.value && name_value.value){
						Ajax({
							url: '/smoke/equipment',
							type: 'put',
							data: {
							    equipmentId: id_value.value,
							    equipmentName: name_value.value,
							    roomId: room_id,
							    id: id
							},
							success: function(data){
								if(data.status == 1){
									// 触发按钮关闭事件；
									// 找到之前的，然后删除之前的
									var arr = pc_data.temp.rooms;
									for(var i = 0; i < arr.length;i++){
										if(arr[i].roomId === room_id){
											for(var j = 0;j < arr[i].equipments.length; j++){
												if(arr[i].equipments[j].id === id){
													arr[i].equipments[j].equipmentName = name_value.value;
													arr[i].equipments[j].equipmentId = id_value.value;
												}
											}
										}
									}
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
									event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
									btn.dispatchEvent(event);
								}else{
									alert(data.message);
								}
							},
							error: function(){
								alert('修改设备名字失败');
							}
						});
					}
				});
			},
			delete_user_equi: function(index, room_id, equi_id, id){
				console.log('删除的设备号码是index' + index + ',room_id'+ room_id + 'equi_id'+ equi_id);
				Ajax({
					url: '/smoke/equipment/' + id,
					type: 'delete',
					success: function(data){
						var temp = pc_data.temp.rooms;
						if(data.status == 1){
							for(var i = 0; i < temp.length; i++){
								if(temp[i].roomId === room_id){
									temp[i].equipments.splice(index, 1);
								}
							}
						}else{
							alert(data.message);
						}
					},
					error: function(data){
						alert('删除失败');
					}
				});
			},
			add_user_equi: function(room_id){
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
					var name_value = document.body.querySelector('.tea_name_text');
					var id_value = document.body.querySelector('.tea_id_text');
					if(id_value.value && name_value.value){
						Ajax({
							url: '/smoke/equipment',
							type: 'post',
							data: {
								equipmentId: id_value.value,
							    equipmentName: name_value.value,
							    roomId:room_id
							},
							success: function(data){
								if(data.success == true){
									//将设备添加到列表上
									for(var i = 0; i < pc_data.temp.rooms.length;i++){
										if(pc_data.temp.rooms[i].roomId === room_id){
											if(pc_data.temp.rooms[i].equipments == null){
												pc_data.temp.rooms[i].equipments = [];
											}
											// da
											pc_data.temp.rooms[i].equipments.push({
												currentStatus: null,
												equipmentId: id_value.value,
												equipmentName: name_value.value,
							    				roomId: room_id,
							    				id: data.data.id
											})
										}
									}
									// 触发按钮关闭事件；
									var btn = document.body.querySelector('.close_prop');
									var event = document.createEvent("MouseEvents");
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
			var index = window.location.href.lastIndexOf('=');
			index =  window.location.href.slice(index + 1, window.location.href.length);
			var temp = null;
			for(var i = 0; i < pc_data.teachers.length; i++){
				if(pc_data.teachers[i].userId == index){
					temp = JSON.parse(JSON.stringify(pc_data.teachers[i]));
					break;
				}
			}
			var rooms = null;
			pc_data.temp = temp;
			Ajax({
				url: '/smoke/teacher/' + temp.userId + '/room',
				type: 'get',
				success: function(data){
					pc_data.temp.rooms = data.rooms;
					// 再发送几个ajax获取到房间的设备信息
					// rooms = pc_data.temp.rooms;

				},
				error: function(data){
					alert('获取老师的房间失败');
				}
			});

		}
	}
</script>
<style>
	.updata_teacher{
		color: white;
	}
	.updata_head{
	    overflow: hidden;
    	margin: 20px 0;
	}
	.bn_middle{
	    float: right;
        width: 17%;
	    border: none;
	    padding: 2%;
	    font-size: 1.2em;
        line-height: 1.2em;
	    background: #39c;
	    color: #fff;
	    text-align: center;
	    border-radius: 40px;
	}
	.u_user_room{
		margin-top: 0;
		width: 9em;
	    display: inline-block;
	}
	.updata_label{
		width: 4.5em;
    	float: left;
	}
	.u_user_tele{
	    display: inline-block;
		margin-top: 0px;
	}
	.add_bn_updata, .delete_bn, .updata_bn {
		display: inline-block;
		cursor: pointer;
	}
	.add_bn_updata:hover, .delete_bn:hover, .updata_bn:hover{
		color: #3b73de;
	}
	.updata_one_input{
		overflow: hidden;
		margin-top: 20px;
	}
	.u_user_equi{
	    display: inline-block;
	    width: 5em;
	    margin-top: 0px;
	}
	.updata_one_input_phone{
		margin-left: 5.2em;
		overflow:hidden;
	}
	.rooms_equi_list{
		float: right;
	    width: 44%;
	}
	.rooms_equi{
		margin-left: 4.6em;
	}
</style>
