<html>
<head>
    <title>登录</title>
    <style>.error{color:red;}</style>
</head>
<body>

<div class="error">${error}</div>
<form action="" method="post">
    用户名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"><br/>
    自动登录：<input type="checkbox" name="rememberMe"><br/>
    <input type="submit" value="登录">
</form>

</body>
</html>