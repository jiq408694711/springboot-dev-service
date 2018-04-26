<html>
<head>
    <title>Welcome!</title>
</head>
<body>

<div th:if="${message}">
    <h2 th:text="${message}"/>
</div>

<div>
    <form method="POST" enctype="multipart/form-data" action="/springboot-service/fileupload/">
        <table>
            <tr>
                <td><input type="file" name="file"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Upload"/></td>
            </tr>
        </table>
        <!-- 因为与spring security集成，防止CSRF（Cross-site request forgery跨站请求伪造）的发生，
        限制了除了get以外的大多数方法，需要添加以下input才能正常使用post方法 -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<div>
    <ul>
    <#list files as file>
        <li>
            <a href="${file}" >${file}</a>
        </li>
    </#list>
    <#--<li th:each="file : ${files}">-->
    <#--<a th:href="${file}" th:text="${file}"/>-->
    <#--</li>-->
    </ul>
</div>
<div>
    <p>点击 <a href="/springboot-service/main/home">这里</a> 回到主页!</p>
</div>
</body>
</html>