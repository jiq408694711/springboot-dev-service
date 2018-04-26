<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>欢迎你： ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.username!'xxx'}</h1>
<h1>欢迎你： ${username}! </h1>
<p>用戶列表頁。点击 <a href="/springboot-service/logout">这里</a> 退出系统</p>
</body>
</html>