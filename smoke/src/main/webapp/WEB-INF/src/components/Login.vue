<template>
<form class="login">
  <h1 class="lg_header"  data-text="欢迎登陆本系统">欢迎登陆本系统</h1>
  <div class="lg_container">
    <label for="user_name" class="lg_label">账号</label>
    <input type="text" id="user_name" class="ip lg_ip" name="name" placeholder="账号">
  </div>
  <div class="lg_container">
    <label for="user_password"  class="lg_label">密码</label>
    <input type="password" id="user_password"  class="ip lg_ip" name="password"  placeholder="密码">
  </div>
  <div class="lg_container">
  <label for="user_code" class="lg_label">验证码</label>
    <img class="lg_code_img" src="/code" alt="验证码"/>
    <input type="text" id="user_code" name="code" class="ip lg_ip lg_ip_code" placeholder="请输入验证码" />
  </div>
  <div class="lg_container lg_container_bn">
    <button class="bn bn_big lg_bn_reset" type="reset">重置</button>
    <button class="bn bn_big lg_bn_login" type="button" id="bn_login" v-on:click="submit()">登录</button>
  </div>
</form>
</template>
<script>
  export default{
    methods: {
      submit: function(event){
        var key,
            thisPwd = document.getElementById("user_password").value,

            result,
            _this = this;

        // bodyRSA,设置RSA加密
        setMaxDigits(130);

        key = new RSAKeyPair("10001", "", "8246a46f44fc4d961e139fd70f4787d272d374532f4d2d9b7cbaad6a15a8c1301319aa6b3f30413b859351c71938aec516fa7147b69168b195e81df46b6bed7950cf3a1c719d42175f73d7c97a85d7d20a9e83688b92f05b3059bb2ff75cd7190a042cd2db97ebc2ab4da366f2a7085556ed613b5a39c9fdd2bb2595d1dc23b5");
        result = encryptedString(key, encodeURIComponent(thisPwd));
        var data = {
          name : document.getElementById('user_name').value,
          password: result,
          code: document.getElementById('user_code').value
        };
        data = JSON.stringify(data);
        // 发送ajax请求
        Ajax({
          url: '/login',
          type: 'post',
          dataType: 'JSON',
          contentType: 'JSON',
          data: data,
          success: function(data){
            var msg  = data;
            console.log(data);
            debugger;
            if(msg.status === 1){
              // 显示登录成功，然后页面跳转
              // 将东西存储到session里面
              debugger;
              _this.$router.push('manage');
            }else{
              alert(msg.message);
            }
          },
          error: function(data){
            alert('登陆错误，请确定你的账户是否存在且密码无误');
          }
        });
    }
  }
}

</script>

<style scopted>
.login{
    width: 60%;
    min-width: 700px;
    margin: 5% auto 0;
    padding: 4em;
    box-sizing: border-box;
    background: #0a101a;
    border-radius: 1em;
    font-family: 'Microsoft YaHei';
    box-shadow: 0 0 29px 9px #0a101a;
}

.lg_container{
    margin: 4% 0;
    padding: 0 0;
    overflow: hidden;
}

.ip{
    padding: 0;
    text-indent: .2em;
    /* width: 100%; */
    height: 1.5em;
}
.lg_label{
    width: 4em;
    float: left;
    /* height: 1.5em; */
    /* padding-left: .8em; */
    /* text-align: left; */
}
.lg_ip{
  border: none;
  outline: none;
  /* padding: 10px  0; */
  padding-top: 10px;
  padding-bottom: 10px;
  margin: 0 0 0 3em;
  /* float: left; */
  display: block;
  text-indent: .2em;
  font-size: 1.1em;
  width: 80%;
  color: white;
  background: #0a101a;
  line-height: 2em;
  border-bottom: 1px solid rgb(14,22,37);
  height: 2em;
}

.lg_ip_code{
  width: 54%;
}
.lg_code_img{
    float: right;
    height: 100%;
    width: 20%;
      height: 3em;
}

.lg_container_bn{
  overflow: hidden;
}

.bn{
  background: white;
}

.bn:hover{
  background: #3ec2e5;
  cursor: pointer;
}

.bn_big{
  width: 38%;
  text-align: center;
  padding: 2%;
  font-size: 1.2em;
  border: none;
  background: #39c;
  color: white;
  border-radius: 40px;
}

.lg_bn_login{
  float: right;
}

/* é®ç½©å±‚*/
.prop{
      width: 100%;
      height: 100vh;
      position: fixed;
      top: 0;
      left: 0;
      background: rgba(0,0,0,0.4);
    }
.prop_header{
  position: fixed;
  width: 500px;
  padding-bottom: 18px;
  box-sizing: border-box;
  overflow: hidden;
  left: 50%;
  top: 50%;
  border-radius: 5px 5px 0 0;
  border: 1px solid #aaa;
  margin-left: -290px;
  margin-top: -130px;
  background: #17233b;
}
.close_prop{
  border-left: 1px solid #aaa;
  border-bottom: 1px solid #aaa;
  color: #aaa;
  width: 1.4em;
  height: 1.3em;
  float: right;
  text-align: center;
  line-height: 1.3em;
  border-radius: 0 0 0 20%;
  cursor: pointer;
}
.close_prop:hover{
  border: 1px solid #e23838;
  background-color: #e23838;
  color: white;
}
.prop_container{
    position: fixed;
    width: 500px;
    padding: 40px;
    left: 50%;
    box-sizing: border-box;
    top: 50%;
    border: 1px solid #aaa;
    border-top: 0px;
    margin-left: -290px;
    margin-top: -100px;
    background: #17233b;
    border-radius:  0 0 5px 5px;
}

/* sidebar */

.wrapper{
  min-width: 600px;
  overflow: hidden;
}
.sidebar{
  float: left;
  width: 200px;
}
.wrap_container{
  margin-left: 200px;
}
.sb_container{
  padding: 0;
  list-style: 0;
}
.sb_choice a{
  display: block;
}
.router-link-active{

}

.background_black{
  background: linear-gradient(90deg, #0a0f19 ,#17233b);
  height: 100vh;
  font-family: 'Microsoft YaHei';
  overflow-x: hidden;
}

.lg_header{
    margin: 0;
    padding: 0;
    font-weight: 500;
    text-align: center;
    font-size: 2.3em;
    color: #3b73de;
    display: inline-block;
    font-family: Miscrosoft YaHei;
    width: 100%;
    background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(#3b73de), to(#3ec2e5));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.lg_label{
    width: 4em;
    color: white;
    float: left;
    font-size: 1.3em;
    height: 2em;
    line-height: 2em;
}
</style>
