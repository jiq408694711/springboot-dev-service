<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>欢迎你： ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.username!'xxx'}</h1>
<h1>欢迎你： ${username}! </h1>
<p>登陆后显示页面。点击 <a href="/springboot-service/logout">这里</a> 退出系统</p>
<p>点击 <a href="/springboot-service/user/list">这里</a> 进入用户系统</p>

<h3>功能列表</h3>
<p>点击 <a href="/springboot-service/async/task">这里</a> 执行异步函数</p>
<p>点击 <a href="/springboot-service/fileupload/">这里</a> 进入文件上传下载页</p>
</body>
</html>