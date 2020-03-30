<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>test page</title>
    <script src="https://code.jquery.com/jquery-2.2.1.js"></script>
</head>
<body>
Welcome, this is home

<form id="form-login">
    email : <input type="text" name="email">
    name : <input type="text" name="name">
    <button type="button" id="submitBtn">제출</button>
</form>

</body>
</html>
<script type="text/javascript">
    $(document).on('click','#submitBtn',function(){
        var data = {
            email: $('input[name=email]').val(),
            name: $('input[name=name]').val()
        };

        console.log("data: ", data);
        console.log(JSON.stringify(data));

        $.ajax({
            url: "/user/insert",
            type: "POST",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',

            beforeSend: function(){
                alert("submit_user()");
            },
            success: function(data){
                alert(JSON.stringify(data)+'추가되었습니다.');
            },
            error: function(error){
                alert("error: "+ JSON.stringify(error));
            }

        })


        var user_id = 1;

        $.ajax({
            url: "/user/update/"+user_id,
            type: "POST",
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',

            beforeSend: function(){
                alert("submit_user()");
            },
            success: function(data){
                alert(JSON.stringify(data)+'추가되었습니다.');
            },
            error: function(error){
                alert("error: "+ JSON.stringify(error));
            }

        })
    })
</script>