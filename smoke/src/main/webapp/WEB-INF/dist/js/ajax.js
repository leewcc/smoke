function Ajax(config){
  var configs = {
    type: 'get',
    // 传输数据
    data: '',
    // 路径
    url: '',
    dataType: 'json',
    contentType: 'json',
    success: function(data){
      alert('成功');
    },
    error: function(data){
      alert('错误');
    }
  };
  var xhr = new XMLHttpRequest();
  // 初始化，处理头部信息
  function init(){
    for(var i in configs){
      if(config[i]){
        configs[i] = config[i];
      }
    }
  }


  function changeHeader(){
    var change = {
      'json': 'application/json',
      'form': 'application/x-www-form-urlencoded',
      'html': 'text/html',
      'xml': 'text/xml'
    }
    if(configs.contentType){
      xhr.setRequestHeader('Content-type', change[configs.contentType.toLowerCase()]);
    }
    if(configs.dataType){
      xhr.setRequestHeader('Accept', change[configs.dataType.toLowerCase()])
    }

  }

  function get(){
    xhr.open('GET', configs.url, true);
  changeHeader();
    // 将data的数据合并
    xhr.onreadystatechange = function(){
      if (xhr.readyState === 4 && xhr.status === 200){

        configs.success(JSON.parse(xhr.responseText));
      }
      if(xhr.status >= 400){
        configs.error(JSON.parse(xhr.responseText));
      }
    }
    xhr.send(null);
  }
  function post(){
    xhr.open(configs.type.toUpperCase(), configs.url, true);
    changeHeader();
    xhr.onreadystatechange = function(){
      if (xhr.readyState === 4 && xhr.status === 200){
        configs.success(JSON.parse(xhr.responseText));
      }
    }
    if(configs.data instanceof Object){
      configs.data = JSON.stringify(configs.data);
    }
    xhr.send(configs.data);
  }
  init();
  
  if(configs.type === 'GET' ||configs.type === 'get' ){
    get();
  }else{
    post();
  }
}
