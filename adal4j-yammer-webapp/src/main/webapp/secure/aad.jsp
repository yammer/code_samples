<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AAD Secure Page</title>
</head>
<body>
<div style="font-family:Comic Sans MS, cursive, sans-serif">
    <h3>Current user logged into ${tenant} Tenant</h3>
    <a href="https://developer.yammer.com/docs/rest-api-rate-limits">Yammer REST API Documentation & Rate Limits</a>
    <p>/messages/my_feed.json ............................. ${my_feed}</p>
    <p>/messages/algo.json ............................. ${algo}</p>
    <p>/messages.json ............................. ${messages}</p>
    <p>/messages/following.json ............................. ${following}</p>
    <p>/messages/sent.json ............................. ${sent}</p>
    <p>/messages/received.json ............................. ${received}</p>
    <p>/messages/in_thread/:thread_id.json ............................. ${in_thread}</p>
    <p>/messages/in_group/:group_id.json ............................. ${in_group}</p>
    <p>/messages/about_topic/[:id].json ............................. ${about_topic}</p>
    <p>/messages/open_graph_objects/[:ogo_id].json ............................. ${ogo}</p>
    <p>/threads/[:thread_id].json ............................. ${thread_meta_data}</p>
    <p>/threads/[:thread_id].json ............................. ${thread_meta_data}</p>
    <p>topics/[:topic_id].json ............................. ${topic_meta_data}</p>
    <p>users.json ............................. ${user}</p>
    <p>users/current.json ............................. ${current_user}</p>
    <p>users/[:user_id].json ............................. ${user_by_id}</p>
    <p>/users/by_email.json?email=[:email] ............................. ${user_by_email}</p>
    <p>/users/in_group/[:id].json ............................. ${users_by_group}</p>
    <p>/users/liked_message/[:message_id].json ............................. ${liked_message}</p>
    <p>/streams/notifications.json ............................. ${notifications}</p>
    <p>/suggestions.json ............................. ${suggestions}</p>
    <p>/search.json ............................. ${search}</p>
    <p>/networks/current.json ............................. ${networks}</p>
    <p>/uploaded_files/[:file_id]/version/[:version_id]/preview ............................. ${file_preview}</p>
    <p>/uploaded_files/[:file_id]/version/[:version_id]/large_preview ............................. ${file_large_preview}</p>
    <p>/uploaded_files/[:file_id]/version/[:version_id]/thumbnail ............................. ${file_thumbnail}</p>
</div>
<form action="/adal4jsample">
    <input type="submit" value="Return to Home Page">
</form>
</body>
</html>