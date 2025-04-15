print("hello from client!")

Events.SubscribeRemote("test", function (msg)
    print("server said", msg)
end)
Events.CallRemote('test')
