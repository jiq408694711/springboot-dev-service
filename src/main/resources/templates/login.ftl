<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<h1>欢迎使用，请登录！</h1>
<form th:action="@{/login}" method="post">
    <#if error != null>
        ${error}
    </#if>
    <#if logout != null>
        ${logout}
    </#if>
    <div><label> 用户名 : <input type="text" name="username"/> </label></div>
    <div><label> 密 码 : <input type="password" name="password"/> </label></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div><input type="submit" value="登录"/></div>
</form>
</body>
</html>