print("hello from client!")

Events.Subscribe("test", function (msg)
    print("server said", msg)
end)
Events.CallRemote('test')
