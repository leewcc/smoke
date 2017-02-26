<template>
	<div class="devices">
		<div class="dev_sub_nav">
			<a  class="dev_more" href="javascript:window.history.go(-1)"> < </a>
			<h1 class="dev_heading">{{getRoomData(roomId)}}</h1>
		</div>
		<div class="dev_container" v-for="device in equipments"  v-if="show_device(device.roomId)">
			<div class="dev_header">
				<div class="dev_header_name">（{{device.equipmentId }}）{{device.equipmentName }}</div>
				<div class="dev_header_time">{{getDate(device.recordTime) }}</div>
			</div>
			<div class="dev_status">
				<div v-bind:class="device.status == 0?'dev_con_normal':'dev_con_warn'">{{device.status == 0?'正常':'异常' }}</div>
				<div class="dev_con_status">
                    <span class="dev_con_header">PM 1.0</span>
                    <span class="dev_con_num_show">{{device.pm1_0 }}</span>
                </div>
                <div class="dev_con_status">
                    <span class="dev_con_header">PM 2.5</span>
                    <span class="dev_con_num_show">{{device.pm2_5 }}</span>
                </div>
                <div class="dev_con_status">
                    <span class="dev_con_header">PM 10</span>
                    <span class="dev_con_num_show">{{device.pm10 }}</span>
                </div>
			</div>
		</div>
		<div class="dev_history">
			<router-link to="/history" class="dev_history_bn">查看历史警报</router-link>
		</div>
	</div>
</template>
<script scopted>
	export default{
		data () {
		    return all_data
		},
		methods: {
			show_device: function(room_id){
				if(room_id == all_data.roomId){
					return true;
				}else{
					return false;
				}
			},
            getRoomData: function(room_id){
                for(var i = 0; i < all_data.rooms.length; i++){
                    if(all_data.rooms[i].roomId == room_id){
                        return all_data.rooms[i].roomName;
                    }
                }
                return room_id;
            },
            getDate: function(date){
                var time = date == 0? new Date():new Date(date);
                var str = time.toLocaleString();
                var index = str.lastIndexOf(':');

                str = str.slice(0, index);
                return str;
            }
		},
		created: function(){
			// 加载数据
			// OK这个加载的是roomId的数据
			var href = window.location.href;
			var roomId = href.substring( href.indexOf('roomId=') + 7, href.length);
			all_data.roomId = roomId? roomId: all_data.roomId;
			if(roomId){
				socket.send('see_equipment/'+ roomId);
			}else{
				// 找不到房间号，错误提示，然后跳转页面到房间号
				console.log('找不到房间号');
				alert('找不到房间号');
				window.history.go(-1);
			}
		}

	}
</script>
<style>
	.devices {
        overflow-x: hidden;
        background: #17233b;
        background: linear-gradient(90deg, #0a0f19, #17233b);
        height: 100vh;
        color: #ddd;
    }
    
    .dev_sub_nav {
        position: relative;
        padding: 1em 0;
        text-align: center;
        width: 100vw;
    }
    
    .dev_more {
        font-size: 2em;
        position: absolute;
        line-height: 2rem;
        left: .5em;
        font-weight: bolder;
        text-decoration: none;
        color: aliceblue;
    }
    
    .dev_header {
        overflow: hidden;
        padding: .8em 0em;
        border-bottom: .1em solid;
    }
    
    .dev_heading {
        background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#bdbfc2), to(#ffffff));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        margin: 0;
        text-align: center;
        width: 100vw;
    }
    
    .dev_status {
        padding: 1em 0.2em;
        overflow: hidden;
    }
    
    .dev_header_name {
        float: left;
    }
    
    .dev_header_time {
        float: right;
    }
    
    .dev_container {
        background: rgb(12, 19, 31);
        background: linear-gradient(to right, #0a0f19, #17233b);
        margin: 1% 1em;
    }
    
    .dev_history {
        margin: 2em auto;
        padding: 1em 2em;
        width: 7em;
        text-align: center;
        border: .1em solid;
        display: block;
        color: white;
        border-radius: 3em;
    }
    
    .dev_history:hover {
        background: #0a0f19;
    }
    
    .dev_con_status {
        width: 23%;
        padding: 1em 0;
        display: inline-block;
        text-align: center;
        margin-left: 0.1em;
    }
    .dev_con_num_show {
        font-size: 1em;
        color: aliceblue;
    }
    .dev_con_num_show:after{
        content: 'ug/cm^3';
        font-size: .7em;
    }
    
    .dev_con_normal,
    .dev_con_warn {
        float: left;
        width: 2.6em;
        height: 2.6em;
        margin: 0;
        border-radius: 100%;
        font-size: 1.5em;
        font-weight: bolder;
        line-height: 2.6em;
        text-align: center;
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
    }
    
    .dev_con_normal {
        border: 0.2em solid #039932;
        background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#039c90), to(#039932));
    }
    
    .dev_history_bn {
        color: white;
        text-decoration: none;
    }
    
    .dev_con_warn {
        border: 0.2em solid #ff5b00;
        background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#ff5b00), to(#ff0200));
    }
    
    .dev_con_header {
        display: block;
        font-size: 0.7em;
    }
</style>
