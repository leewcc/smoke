<template>
<div  class="login"  id="manager">
    <h2  class="lg_header"  data-text="老师列表">老师列表</h2>
    <div v-if="show_teacher()">
        <div class="one_tea">
            <div class="tea_id">老师工号</div>
            <div class="tea_name">姓名</div>
            <div class="tea_tele">电话</div>
            <div class="add_tea">
                <button type="button" class="bn tea_add_bn" v-on:click="add_tea_button">添加老师</button>
            </div>
        </div>
        <div class="one_tea" v-for="tea in teachers">
            <div class="tea_id">{{tea.userId}}</div>
            <div class="tea_name">{{tea.userName}}</div>
            <div class="tea_tele">{{(tea.phones && tea.phones.length != 0)?tea.phones[0].userPhone:'无'}}</div>
            <div class="add_tea">
				<router-link :to="{name: 'updata', query: {index : tea.userId}}" class="tea_change_link tea_bn"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></router-link>
	            <div class="tea_bn" v-on:click="change_name(tea.userId, tea.userName)">修改</div>
	            <div class="tea_bn" v-on:click="delete_tea(tea.userId)" title="删除"><i class="fa fa-trash-o" aria-hidden="true"></i></div>
            </div>
        </div>
    </div>
    <div v-else class="not_tea">暂无老师信息，请检查是否网络出错</div>
</div>
</template>
<script>
	export default{
		data(){
			return pc_data;
		},
		created: function(){
			var _this = this;
			
			Ajax({
				url: '/smoke/teachersInfo',
				type: 'get',
				success: function(data){
					var arr = null;
					if(typeof arr === 'object'){
						arr = data;
					}
					// 处理老师列表
					pc_data.teachers = arr;
						
				},
				error: function(data){
					if( (!pc_data.teachers) || pc_data.teachers.length < 1){

					}
				}
			})
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
				globals.updata_teacher('create');
			},
			change_name: function(user_id, user_name){
				// 创建遮罩层
				globals.updata_teacher('updata', user_id, user_name);
			},
			
			delete_tea: function(user_id){
				// 已检查
				Ajax({
					url: '/smoke/teacher/'+ user_id,
					type: 'delete',
					success: function(data){
						if(data.status == 1){
							console.log('删除user_id为' +  user_id + '的老师成功');
							for(var i = 0; i < pc_data.teachers.length; i++){
								if(pc_data.teachers[i].userId === user_id){
									pc_data.teachers.splice(i,1);
									break;
								}
							}
						}else{
							alert('删除失败，原因为' + data.message);
						}
					},
					error: function(data){
						alert('删除失败，原因为' + data.message);
					}
				});
			},
			show_device: function(user_id){
				Ajax({
					url: '/user/{userId}/roomlist',
					type: 'get',

				});
			}
		}
	}
</script>
<style>
#manager{
	color: white;
}
.one_tea{
	overflow: hidden;
	width: 100%;
	padding: 10px 0;
	border-bottom: 1px solid white;
}
.tea_id, .tea_name, .tea_tele, .add_tea{
	width: 24%;
	display: inline-block;
}
.lg_header{
    margin: 0;
    padding: 0;
    font-weight: 500;
    text-align: center;
    font-size: 2.3em;
    color: #3b73de;
    display: inline-block;
    font-family: 'Microsoft YaHei';
    width: 100%;
    background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#3b73de), to(#3ec2e5));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.not_tea{
	text-align: center;
    margin: 20px 0;
}
.tea_add_bn{
    color: white;
    background-color: #3b73de;
    border: none;
    border-radius: 20px;
    padding: 3px 20px;
}
.send_add_tea{
	clear: both;
    margin: 20px auto;
    display: block;
}
.tea_add_bn:hover,.send_add_tea:hover{
	background: #3ec2e5;
}
.tea_name_text{
	text-indent: 10px;
}
.tea_change_link{
	color: white;
	text-decoration: none;
}
.tea_change_link:hover{
	color: #3b73de;
}
.tea_bn{
	display: inline-block;
	cursor: pointer;
    padding: 0px 3px;
}
.tea_bn:hover{
	color: #3b73de;
}
</style>