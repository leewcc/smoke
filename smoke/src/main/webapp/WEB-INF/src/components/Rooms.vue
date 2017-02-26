<template>
	<div>
		<div id="tea_msg">
			<h2 class="tea_name_sub_heading">{{tea.userName}}</h2>
		</div>
		<div class="rooms">
			<div v-for=" room in rooms"  class="room_link one_room" v-bind:class="room.roomStatus== 0? 'room_normal':'room_warn'" >
				<router-link :to="{path:'/device', query: { roomId: room.roomId}}" class="room_link_a">
					<div class="room_name">
						{{room.roomName}}
					</div>
					<div class="room_status" v-bind:class="room.roomStatus== 0? 'color_normal':'color_warn'" ><div class="point"></div></div>
				</router-link>
			</div>
		</div>
		</div>
	</div>
</template>
<script>
	export default{
		data(){
			return all_data
		},
		// 将数据绑定在前一个组件好像不太好
		methods: {
			
		},
		created: function(){
			if(!all_data.tea){
				socket.send('see_room');
			}
		}
	}
</script>
<style>
	html {
        min-height: 100%;
    }
    #app{
        width: 100%;
    }
    body {
        margin: 0;
        padding: 0;
        font-family: '微软雅黑';
        font-size: 1em;
        min-height: 100%;
    }
    #see_login{
        overflow-x: hidden;
        padding: .5em 1.4em;
    }
    #tea_msg{
        background: #070c14;
        background: linear-gradient(#070c14 ,#0a101b,#070c14);
        text-align: center;
        width: 100%;
        color: white;
        padding: 2% 0;
        font-size: 1em;
    }
    .tea_name_sub_heading{
        padding: 0;
        margin: 0;
        font-weight: normal;
        font-size: 1.2em;
    }
    .tea_num{
        padding: 0;
        margin: 0;
        font-weight: normal;
        color: #ddd;
        font-size: 1em;
    }
    .rooms{
        overflow-x: hidden;
        background: #17233b;
        background: linear-gradient(90deg, #0a0f19 ,#17233b);
        height: 100vh;
    }
    .one_room{
        float: left;
        width: 44%;
        box-sizing: border-box;
        background: #22406F;
        padding: 0% 0;
        margin: 7% 1.5% 0 3%;
        text-align: center;
        border-radius: 1.3em;
    }
    .point{
        width: 1em;
        height: 1em;
        margin: .1em auto;
        border-radius: 50%;
    }
    .color_normal .point{
        background-color: #04b243;
    }
    .color_warn .point{
        background-color: red;
    }
    .room_link{
        display: block;
        color: #777;
        text-decoration: none;
        height: 10em;
    }
    .room_warn{
        box-shadow: 0 0 1em 0.3em red;
    }
    .room_name{
        color: white;
    }
    .room_status{
        color: rgb(4, 178, 67);
        font-size: 1.6em;
        font-weight: bolder;
    }
    .room_link_a{
        text-decoration: none;
        display: block;
        overflow: hidden;
        padding: 36% 0 0;
    }
    .color_warn{
        color: red;
    }
</style>