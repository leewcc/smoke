var bn_login = document.getElementById('bn_login');
bn_login.addEventListener('click', function(event){
	var key,
	    thisPwd = document.getElementById("user_password").value,
	    result;

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
	  	var msg  = null;
	    console.log(data);
	    msg = JSON.parse(data);
	    if(msg.status === 1){
	    	// 显示登录成功，然后页面跳转
	    	// 将东西存储到session里面
	    	window.location.href="manage.html";
	    }else{
	    	alert(msg.message);
	    }
	  },
	  error: function(data){
		alert('登陆错误，请确定你的账户是否存在且密码无误');
	  }
	});
});