<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
Welcome, this is home

<form>
    email : <input type="text" name="email">
    name : <input type="text" name="name">
    <input type="button" value="제출" onclick="submit_user()">
</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

    function submit_user() {

        alert("submit_user()");

        var data = {
            email: $('input[name=email]').val(),
            name: $('input[name=name]').val()
        };

        console.log("data: ", data);

        $.ajax({
            url: "/user/insert",
            type: "POST",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data){
                alert(JSON.stringify(data)+'추가되었습니다.');
            },
            error: function(error){
                alert("error: "+ JSON.stringify(error));
            }

        })
    }
</script>
</html>
