<template>
	<div id="history">
		<div class="hty_sub_nav">
            <a class="hty_more" href="javascript:window.history.go(-1)">
                < </a>
                    <h1 class="hty_heading">历史警报</h1>
        </div>
		<div v-for="hty in historys" class="hty">
			<h1 class="hty_sub_heading">{{getDate(hty.recordTime)}}</h1>
			<div v-for="record in hty.records" class="hty_rec">
				<div class="hty_header" v-if="hty.recordTime != 0">
					<span class="hty_con_num_show">（{{record.equipmentId}}）{{record.equipmentName}}</span>
				</div>
				<div class="hty_con_status" v-if="hty.recordTime != 0">
                    <span class="hty_con_header">PM 1.0</span>
                    <span class="hty_con_num_show">{{record.pm1_0}}</span>
                </div>
                <div class="hty_con_status" v-if="hty.recordTime != 0">
                    <span class="hty_con_header">PM 2.5</span>
                    <span class="hty_con_num_show">{{record.pm2_5}}</span>
                </div>
                <div class="hty_con_status" v-if="hty.recordTime != 0">
                    <span class="hty_con_header">PM 10</span>
                    <span class="hty_con_num_show">{{record.pm10}}</span>
                </div>
            </div>
		</div>
	</div>
</template>
<script>
	export default{
		data() {
			return all_data
		},
		methods: {
			getDate: function(date){

				var time = date == 0? new Date():new Date(date);
				var str = time.toLocaleString();
				var index = str.lastIndexOf(':');

				str = str.slice(0, index);
				return str;
			}
		},
		created: function(){

			var roomId = all_data.roomId;
			if(roomId){
				socket.send('see_history/'+ roomId);
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
	 #history{
        overflow-x: hidden;
        background: #17233b;
        background: linear-gradient(90deg, #0a0f19, #17233b);
        height: 100vh;
        color: #ddd;
    }
    .hty_heading{
        background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#bdbfc2), to(#ffffff));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        margin: 0;
        text-align: center;
        font-size: 2rem;
        line-height: 2rem;
        width: 100vw;
    }
    .hty_sub_heading{
        font-size: 1em;
        margin: 0 1em .2em 0;
        text-align: right;
    }
    .hty_sub_nav {
        position: relative;
        padding: 1em 0;
        font-size: 1.3rem;
        line-height: 1.3rem;
        text-align: center;
        width: 100vw;
    }
    .hty_more{
        font-size: 2rem;
        line-height: 2rem;
        position: absolute;
        left: .5em;
        font-weight: bolder;
        text-decoration: none;
        color: aliceblue;
        z-index: 3;
    }

    .hty_rec{
        flex: 0 0 0 40%;
        display: flex;
        margin: 1em 0;

    }
    .hty_header{
        flex: 2;
    }
    .hty {
        background: rgb(16,24,45);
        margin: 1% 0;
        padding: 0.7em 0;
        text-align: center;
    }
    .hty_con_status {
        width: 23%;
        display: inline-block;
        text-align: center;
        border-left: .1em solid;
    }
    .hty_con_header {
        display: block;
        font-size: 0.7em;
    }
    .hty_name{
        font-size: .8em;
        color: aliceblue;
    }
    .hty_con_num_show {
        font-size: 1em;
        color: aliceblue;
    }
    .hty_con_num_show:after{
        content: 'ug/cm^3';
        font-size: .7em;
    }
</style>